package kz.balaguide.course_module.services;

import kz.balaguide.common_module.core.exceptions.buisnesslogic.generic.ChildNotEnrolledToCourseException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.generic.CourseFullException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.generic.IneligibleChildException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.ChildNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.CourseNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.EducationCenterNotFoundException;
import kz.balaguide.common_module.core.entities.Child;
import kz.balaguide.common_module.core.entities.Course;
import kz.balaguide.common_module.core.dtos.requests.CourseRequest;

import java.util.List;

public interface CourseService {

    Course addCourse(CourseRequest course) throws EducationCenterNotFoundException;

    Course updateInformation(Long courseId, Course updatedCourse) throws CourseNotFoundException;

    boolean removeCourse(Long courseId) throws CourseNotFoundException;

    List<Course> getCourses();

    boolean enrollChild(Long courseId, Long childId)
            throws CourseNotFoundException, ChildNotFoundException, CourseFullException, IneligibleChildException;

    List<Course> searchCourses(String query);

    boolean unenrollChild(Long courseId, Long childId)
            throws ChildNotFoundException, ChildNotEnrolledToCourseException;

    boolean isCourseFull(Course course);

    int getCurrentParticipants(Course course) throws CourseNotFoundException;

    boolean isChildEligible(Course course, Child child);

}
