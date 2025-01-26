package kz.balaguide.utils.mappers;

import kz.balaguide.core.entities.Course;
import kz.balaguide.core.entities.EducationCenter;
import kz.balaguide.core.dtos.requests.CourseRequest;
import org.springframework.stereotype.Component;

/**
 * Mapper class to convert between Course and CourseRequest objects.
 */
@Component
public class CourseMapper {

    /**
     * Maps a {@link CourseRequest} to a {@link Course} entity.
     *
     * @param courseRequest   the request object containing course details
     * @param educationCenter the education center associated with the course
     * @return a new {@link Course} object populated with the provided details
     */
    public Course mapCourseRequestToCourse(CourseRequest courseRequest, EducationCenter educationCenter) {
        return Course.builder()
                .name(courseRequest.name())
                .description(courseRequest.description())
                .category(courseRequest.category())
                .ageRange(courseRequest.ageRange())
                .price(courseRequest.price())
                .durability(courseRequest.durability())
                .maxParticipants(courseRequest.maxParticipants())
                .currentParticipants(courseRequest.currentParticipants())
                .educationCenter(educationCenter)
                .build();
    }

    /**
     * Maps a {@link Course} entity to a {@link CourseRequest} object.
     *
     * @param course the {@link Course} entity to be mapped
     * @return a {@link CourseRequest} object populated with course details
     */
    public CourseRequest mapCourseToCourseRequest(Course course) {
        return CourseRequest.builder()
                .educationCenterId(course.getEducationCenter().getId())
                .name(course.getName())
                .description(course.getDescription())
                .category(course.getCategory())
                .ageRange(course.getAgeRange())
                .price(course.getPrice())
                .durability(course.getDurability())
                .maxParticipants(course.getMaxParticipants())
                .currentParticipants(course.getCurrentParticipants())
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
        existingCourse.setCategory(updatedCourse.getCategory());
        existingCourse.setAgeRange(updatedCourse.getAgeRange());
        existingCourse.setPrice(updatedCourse.getPrice());
        existingCourse.setDurability(updatedCourse.getDurability());
        existingCourse.setMaxParticipants(updatedCourse.getMaxParticipants());
        existingCourse.setCurrentParticipants(updatedCourse.getCurrentParticipants());
    }
}
