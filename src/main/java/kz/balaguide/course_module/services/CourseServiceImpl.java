package kz.balaguide.course_module.services;

import kz.balaguide.common_module.core.exceptions.buisnesslogic.generic.ChildNotEnrolledToCourseException;
import lombok.RequiredArgsConstructor;
import kz.balaguide.common_module.core.annotations.ForLog;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.generic.CourseFullException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.generic.IneligibleChildException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.ChildNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.CourseNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.EducationCenterNotFoundException;
import kz.balaguide.course_module.mappers.CourseMapper;
import kz.balaguide.common_module.core.entities.Child;
import kz.balaguide.common_module.core.entities.Course;
import kz.balaguide.common_module.core.entities.EducationCenter;
import kz.balaguide.common_module.core.dtos.requests.CourseRequest;
import kz.balaguide.child_module.repository.ChildRepository;
import kz.balaguide.course_module.repository.CourseRepository;
import kz.balaguide.education_center_module.repository.EducationCenterRepository;
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
    private final EducationCenterRepository educationCenterRepository;
    private final CourseMapper courseMapper;

    /**
     * Adds a new course to the system.
     *
     * @param courseRequest the {@link CourseRequest} entity to be added
     * @return the saved {@link Course} entity
     */
    @Override
    @ForLog
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Course addCourse(CourseRequest courseRequest) {
        EducationCenter educationCenter = educationCenterRepository.findById(courseRequest.educationCenterId())
                .orElseThrow(() -> new EducationCenterNotFoundException("Education center with id: " + courseRequest.educationCenterId() + " not found") );

        Course course = courseMapper.mapCourseRequestToCourse(courseRequest, educationCenter);

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
                .orElseThrow(() -> new CourseNotFoundException("Course with id: " + courseId + " not found"));

        courseMapper.mapUpdatedCourseToExisting(existingCourse, updatedCourse);

        return courseRepository.save(existingCourse);
    }

    /**
     * Retrieves a list of all courses available in the system.
     *
     * @return a {@link List} of {@link Course} heirs
     */
    @Override
    @ForLog
    @Transactional(readOnly = true)
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }


    @Override
    @ForLog
    public boolean removeCourse(Long courseId) {
        childRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course with id " + courseId + " not found"));

        childRepository.deleteById(courseId);

        return !childRepository.existsById(courseId);
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
                .orElseThrow(() -> new CourseNotFoundException("Course with id: " + courseId + " not found "));
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new ChildNotFoundException("Child not found with id: " + childId));

        if (this.isCourseFull(course)) {
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

    @Override
    @ForLog
    public List<Course> searchCourses(String query)  {
        return courseRepository.findByNameContainingIgnoreCase(query)
                .stream()
                .sorted()
                .toList();
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
                .orElseThrow(() -> new CourseNotFoundException("Course with id: " + courseId + " not found"));

        childRepository.findById(childId)
                .orElseThrow(() -> new ChildNotFoundException("Child with id: " + childId + " not found"));

        List<Course> enrolledCourses = courseRepository.findAllByChildId(childId);

        if (!enrolledCourses.isEmpty())
            throw new ChildNotEnrolledToCourseException("Child with id: "+ childId + " is not enrolled in any courses");

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
    public int getCurrentParticipants(Course course) {
        return courseRepository.findById(course.getId())
                .map(Course::getCurrentParticipants)
                .orElseThrow(() -> new CourseNotFoundException("Course with id: " + course.getId() + " not found"));
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
