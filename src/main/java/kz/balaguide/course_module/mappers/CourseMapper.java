package kz.balaguide.course_module.mappers;

import kz.balaguide.common_module.core.entities.Course;
import kz.balaguide.common_module.core.entities.EducationCenter;
import kz.balaguide.course_module.dto.CreateCourseRequest;
import org.springframework.stereotype.Component;

/**
 * Mapper class to convert between Course and CourseRequest objects.
 */
@Component
public class CourseMapper {

    /**
     * Maps a {@link CreateCourseRequest} to a {@link Course} entity.
     *
     * @param createCourseRequest   the request object containing course details
     * @param educationCenter the education center associated with the course
     * @return a new {@link Course} object populated with the provided details
     */
    public Course mapCourseRequestToCourse(CreateCourseRequest createCourseRequest, EducationCenter educationCenter) {
        return Course.builder()
                .name(createCourseRequest.name())
                .description(createCourseRequest.description())
                .courseCategory(createCourseRequest.courseCategory())
                .ageRange(createCourseRequest.ageRange())
                .price(createCourseRequest.price())
                .numberOfLessonsInWeek(createCourseRequest.numberOfLessons())
                .durabilityByWeeks(createCourseRequest.durabilityByWeeks())
                .educationCenter(educationCenter)
                .build();
    }

    /**
     * Maps a {@link Course} entity to a {@link CreateCourseRequest} object.
     *
     * @param course the {@link Course} entity to be mapped
     * @return a {@link CreateCourseRequest} object populated with course details
     */
    public CreateCourseRequest mapCourseToCourseRequest(Course course) {
        return CreateCourseRequest.builder()
                .educationCenterId(course.getEducationCenter().getId())
                .name(course.getName())
                .description(course.getDescription())
                .courseCategory(course.getCourseCategory())
                .ageRange(course.getAgeRange())
                .price(course.getPrice())
                .numberOfLessons(course.getNumberOfLessonsInWeek())
                .build();
    }

    /**
     * Updates an existing {@link Course} entity with values from an updated {@link Course} entity.
     *
     * @param existingCourse the original {@link Course} entity to be updated
     * @param updatedCourse  the {@link Course} entity containing updated values
     */
    public void mapUpdatedCourseToExisting(Course existingCourse, Course updatedCourse) {
        existingCourse.setName(updatedCourse.getName());
        existingCourse.setDescription(updatedCourse.getDescription());
        existingCourse.setEducationCenter(updatedCourse.getEducationCenter());
        existingCourse.setCourseCategory(updatedCourse.getCourseCategory());
        existingCourse.setAgeRange(updatedCourse.getAgeRange());
        existingCourse.setPrice(updatedCourse.getPrice());
        existingCourse.setNumberOfLessonsInWeek(updatedCourse.getNumberOfLessonsInWeek());
    }
}
