package kz.balaguide.controllers.course;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import kz.balaguide.core.entities.Course;
import kz.balaguide.core.dtos.requests.CourseRequest;
import kz.balaguide.services.course.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/courses")
@RequiredArgsConstructor
@Slf4j
public class CourseController {

    private final CourseService courseService;

    /**
     * Adds a new course.
     *
     * @param courseRequest the dto for course to be added
     * @return ResponseEntity with the added course
     */
    @PostMapping("/add-course")
    public ResponseEntity<Course> addCourse(@RequestBody @Valid CourseRequest courseRequest) {
        Course savedCourse = courseService.addCourse(courseRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
    }

    /**
     * Updates an existing course.
     *
     * @param courseId the ID of the course to update
     * @param updatedCourse the course with updated information
     * @return ResponseEntity with the updated course
     */
    @PutMapping("/{courseId}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable Long courseId,
            @RequestBody @Valid Course updatedCourse) {
        Course updated = courseService.updateInformation(courseId, updatedCourse);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves all courses.
     *
     * @return ResponseEntity with a list of all courses
     */
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getCourses();
        return ResponseEntity.ok(courses);
    }

    /**
     * Delete method for {@link Course}
     */
    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long courseId) {
        try {
            courseService.removeCourse(courseId);
            return ResponseEntity.ok("Course removed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Endpoint to search for courses based on a query and filter.
     *
     * @param query the search query
     * @return a {@link List} of {@link Course} entities that match the query
     */
    @GetMapping("/search-courses")
    public ResponseEntity<List<Course>> searchCourses(@RequestParam String query) {
        List<Course> courses = courseService.searchCourses(query);
        return ResponseEntity.ok(courses);
    }

    /**
     * Enrolls a child in a course.
     *
     * @param courseId the ID of the course
     * @param childId the ID of the child
     * @return ResponseEntity with success status
     */
    @PostMapping("/{courseId}/enroll/{childId}")
    public ResponseEntity<Void> enrollChild(
            @PathVariable Long courseId,
            @PathVariable Long childId) {
        courseService.enrollChild(courseId, childId);
        return ResponseEntity.ok().build();
    }

    /**
     * Unenrolls a child from a course.
     *
     * @param courseId the ID of the course
     * @param childId the ID of the child
     * @return ResponseEntity with success status
     */
    @DeleteMapping("/{courseId}/unenroll/{childId}")
    public ResponseEntity<Void> unenrollChild(
            @PathVariable Long courseId,
            @PathVariable Long childId) {
        courseService.unenrollChild(courseId, childId);
        return ResponseEntity.ok().build();
    }
}
