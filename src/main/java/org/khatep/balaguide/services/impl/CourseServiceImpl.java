package org.khatep.balaguide.services.impl;

import lombok.RequiredArgsConstructor;
import org.khatep.balaguide.aop.annotations.ForLog;
import org.khatep.balaguide.exceptions.CourseFullException;
import org.khatep.balaguide.exceptions.IneligibleChildException;
import org.khatep.balaguide.models.entities.Child;
import org.khatep.balaguide.models.entities.Course;
import org.khatep.balaguide.repositories.ChildRepository;
import org.khatep.balaguide.repositories.CourseRepository;
import org.khatep.balaguide.services.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ChildRepository childRepository;

    /**
     * Adds a new course to the system.
     *
     * @param course the {@link Course} entity to be added
     * @return the saved {@link Course} entity
     */
    @Override
    @ForLog
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    /**
     * Updates the information of an existing course.
     *
     * @param courseId the ID of the course to update
     * @param updatedCourse the {@link Course} entity containing updated information
     * @return the updated {@link Course} entity
     * @throws RuntimeException if the course is not found
     */
    @Override
    @ForLog
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public Course updateInformation(Long courseId, Course updatedCourse) {
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));

        existingCourse.setName(updatedCourse.getName());
        existingCourse.setDescription(updatedCourse.getDescription());
        existingCourse.setEducationCenter(updatedCourse.getEducationCenter());
        existingCourse.setCategory(updatedCourse.getCategory());
        existingCourse.setAgeRange(updatedCourse.getAgeRange());
        existingCourse.setPrice(updatedCourse.getPrice());
        existingCourse.setDurability(updatedCourse.getDurability());
        existingCourse.setAddress(updatedCourse.getAddress());
        existingCourse.setMaxParticipants(updatedCourse.getMaxParticipants());
        existingCourse.setCurrentParticipants(updatedCourse.getCurrentParticipants());

        return courseRepository.save(existingCourse);
    }

    /**
     * Retrieves a list of all courses available in the system.
     *
     * @return a {@link List} of {@link Course} entities
     */
    @Override
    @ForLog
    @Transactional(readOnly = true)
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    /**
     * Enrolls a child in a specified course.
     *
     * @param courseId the ID of the course to enroll the child in
     * @param childId the ID of the child to enroll
     * @return true if the enrollment is successful
     * @throws CourseFullException if the course is full
     * @throws IneligibleChildException if the child is not eligible for the course
     * @throws RuntimeException if the course or child is not found
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @ForLog
    public boolean enrollChild(Long courseId, Long childId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new RuntimeException("Child not found with id: " + childId));

        if (isCourseFull(course)) {
            throw new CourseFullException("Course is full and cannot enroll more participants.");
        }

        if (!isChildEligible(course, child)) {
            throw new IneligibleChildException("Child is not eligible for this course.");
        }

        courseRepository.enrollChildInCourse(childId, courseId);
        course.setCurrentParticipants(course.getCurrentParticipants() + 1);
        courseRepository.save(course);
        return true;
    }

    /**
     * Unenrolls a child from a specified course.
     *
     * @param courseId the ID of the course to unenroll the child from
     * @param childId the ID of the child to unenroll
     * @return true if the unenrollment is successful
     * @throws RuntimeException if the course or child is not found, or if the child is not enrolled in any courses
     */
    @Override
    @ForLog
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean unenrollChild(Long courseId, Long childId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));

        childRepository.findById(childId)
                .orElseThrow(() -> new RuntimeException("Child not found with id: " + childId));

        List<Course> enrolledCourses = courseRepository.findAllByChildId(childId);

        if (!enrolledCourses.isEmpty())
            throw new RuntimeException("This child is not enrolled in any courses");

        course.setCurrentParticipants(course.getCurrentParticipants() - 1);
        courseRepository.unenrollChildInCourse(childId, courseId);
        return true;
    }

    /**
     * Checks if a course is full.
     *
     * @param course the {@link Course} entity to check
     * @return true if the current number of participants is equal to or exceeds the maximum allowed
     */
    @Override
    @ForLog
    @Transactional(readOnly = true)
    public boolean isCourseFull(Course course) {
        return course.getCurrentParticipants() >= course.getMaxParticipants();
    }

    /**
     * Retrieves the current number of participants in a course.
     *
     * @param course the {@link Course} entity to check
     * @return the current number of participants
     * @throws RuntimeException if the course is not found
     */
    @Override
    @ForLog
    @Transactional(readOnly = true)
    public int getCurrentParticipants(Course course) {
        return courseRepository.findById(course.getId())
                .map(Course::getCurrentParticipants)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + course.getId()));
    }

    /**
     * Checks if a child is eligible for a specified course based on age range.
     *
     * @param course the {@link Course} entity to check against
     * @param child the {@link Child} entity to verify eligibility
     * @return true if the child is eligible for the course
     */
    @Override
    @ForLog
    public boolean isChildEligible(Course course, Child child) {
        String[] ageRangeParts = course.getAgeRange().split("-");
        int minAge = Integer.parseInt(ageRangeParts[0].trim());
        int maxAge = Integer.parseInt(ageRangeParts[1].trim());
        int childAge = LocalDate.now().getYear() - child.getBirthDate().getYear();

        return childAge >= minAge && childAge <= maxAge;
    }
}
