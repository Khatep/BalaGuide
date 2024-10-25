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

    @Override
    @ForLog
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

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

    @Override
    @ForLog
    @Transactional(readOnly = true)
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

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

        if (isChildEligible(course, child)) {
            throw new IneligibleChildException("Child is not eligible for this course.");
        }

        courseRepository.enrollChildInCourse(childId, courseId);
        course.setCurrentParticipants(course.getCurrentParticipants() + 1);
        courseRepository.save(course);
        return true;
    }

    @Override
    @ForLog
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean removeParticipant(Long courseId, Long childId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new RuntimeException("Child not found with id: " + childId));

        if (!child.getCoursesEnrolled().contains(course))
            return false;

        course.setCurrentParticipants(course.getCurrentParticipants() - 1);
        child.getCoursesEnrolled().remove(course);
        return true;
    }

    @Override
    @ForLog
    @Transactional(readOnly = true)
    public boolean isCourseFull(Course course) {
        return course.getCurrentParticipants() >= course.getMaxParticipants();
    }

    @Override
    @ForLog
    @Transactional(readOnly = true)
    public int getCurrentParticipants(Long courseId) {
        return courseRepository.findById(courseId)
                .map(Course::getCurrentParticipants)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
    }

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
