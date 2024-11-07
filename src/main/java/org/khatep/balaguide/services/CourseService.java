package org.khatep.balaguide.services;

import org.khatep.balaguide.exceptions.*;
import org.khatep.balaguide.models.entities.Child;
import org.khatep.balaguide.models.entities.Course;
import org.khatep.balaguide.models.requests.CourseRequest;

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
            throws ChildNotFoundException, ChildNotEnrolledException;

    boolean isCourseFull(Course course);

    int getCurrentParticipants(Course course) throws CourseNotFoundException;

    boolean isChildEligible(Course course, Child child);

}
