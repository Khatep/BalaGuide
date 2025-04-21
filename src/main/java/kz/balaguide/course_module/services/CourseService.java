package kz.balaguide.course_module.services;

import kz.balaguide.common_module.core.exceptions.buisnesslogic.generic.CourseFullException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.generic.IneligibleChildException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.ChildNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.CourseNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.EducationCenterNotFoundException;
import kz.balaguide.common_module.core.entities.Child;
import kz.balaguide.common_module.core.entities.Course;
import kz.balaguide.course_module.dto.CreateCourseRequest;
import kz.balaguide.course_module.dto.EnrollmentActionDto;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    Course createAndSaveCourse(CreateCourseRequest course) throws EducationCenterNotFoundException;

    Course updateInformation(Long courseId, Course updatedCourse) throws CourseNotFoundException;

    boolean removeCourse(Long courseId) throws CourseNotFoundException;

    List<Course> getCourses();

    boolean enrollChild(Course course, Child child, EnrollmentActionDto enrollmentActionDto)
            throws CourseNotFoundException, ChildNotFoundException, CourseFullException, IneligibleChildException;

    List<Course> searchCourses(String query);

/*
    boolean unenrollChild(EnrollmentActionDto enrollmentActionDto)
            throws ChildNotFoundException, ChildNotEnrolledToCourseException;
*/

    //boolean isCourseFull(Course course);

    //int getCurrentParticipants(Course course) throws CourseNotFoundException;

    boolean isChildEligible(Course course, Child child);

    Optional<Course> findCourseById(Long aLong);
}
