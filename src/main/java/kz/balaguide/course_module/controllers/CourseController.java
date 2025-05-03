package kz.balaguide.course_module.controllers;

import jakarta.validation.Valid;
import kz.balaguide.common_module.core.dtos.responses.ApiResponse;
import kz.balaguide.common_module.core.entities.ResponseMetadata;
import kz.balaguide.common_module.core.enums.ResponseCode;
import kz.balaguide.common_module.services.responsemetadata.ResponseMetadataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import kz.balaguide.common_module.core.entities.Course;
import kz.balaguide.course_module.dto.CreateCourseRequest;
import kz.balaguide.course_module.services.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/courses")
@RequiredArgsConstructor
@Slf4j
public class CourseController {

    //TODO: Переделать все контроллеры с сервисами под ApiResponse

    private final CourseService courseService;
    private final ResponseMetadataService responseMetadataService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Course>> createCourse(@RequestBody @Valid CreateCourseRequest createCourseRequest) {
        Course savedCourse = courseService.createAndSaveCourse(createCourseRequest);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._1900);
        ApiResponse<Course> apiResponse = ApiResponse.<Course>builder()
                .responseMetadata(responseMetadata)
                .data(savedCourse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable Long courseId,
            @RequestBody @Valid Course updatedCourse) {
        Course updated = courseService.updateInformation(courseId, updatedCourse);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long courseId) {
        try {
            courseService.removeCourse(courseId);
            return ResponseEntity.ok("Course removed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/search-courses")
    public ResponseEntity<List<Course>> searchCourses(@RequestParam String query) {
        List<Course> courses = courseService.searchCourses(query);
        return ResponseEntity.ok(courses);
    }
}
