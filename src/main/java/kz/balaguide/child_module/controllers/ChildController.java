package kz.balaguide.child_module.controllers;

import jakarta.validation.Valid;
import kz.balaguide.child_module.services.ChildService;
import kz.balaguide.common_module.core.dtos.responses.ApiResponse;
import kz.balaguide.common_module.core.entities.Child;
import kz.balaguide.common_module.core.entities.Course;
import kz.balaguide.common_module.core.entities.ResponseMetadata;
import kz.balaguide.common_module.core.enums.ResponseCode;
import kz.balaguide.common_module.services.responsemetadata.ResponseMetadataService;
import kz.balaguide.course_module.dto.EnrollmentActionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/children")
@RequiredArgsConstructor
public class ChildController {

    private final ChildService childService;
    private final ResponseMetadataService responseMetadataService;

    @PostMapping("/enroll-to-course")
    public ResponseEntity<ApiResponse<Boolean>> enrollChild(@RequestBody EnrollmentActionDto enrollmentActionDto) {
        boolean isEnrolled = childService.enrollChildToCourse(enrollmentActionDto);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._1005);
        ApiResponse<Boolean> apiResponse = ApiResponse.<Boolean>builder()
                .responseMetadata(responseMetadata)
                .data(isEnrolled)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/unenroll-from-course")
    public ResponseEntity<ApiResponse<Boolean>> unenrollChild(EnrollmentActionDto enrollmentActionDto) {
        boolean isUnenrolled = childService.unenrollChildFromCourse(enrollmentActionDto);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._1006);
        ApiResponse<Boolean> apiResponse = ApiResponse.<Boolean>builder()
                .responseMetadata(responseMetadata)
                .data(isUnenrolled)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}/my-courses")
    public ResponseEntity<ApiResponse<List<Course>>> getChildCourses(
            @PathVariable Long id
    ) {
        Child child = childService.findById(id);
        List<Course> childCourses = childService.getMyCourses(child);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._1004);
        ApiResponse<List<Course>> apiResponse = ApiResponse.<List<Course>>builder()
                .responseMetadata(responseMetadata)
                .data(childCourses)
                .build();

        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/{childId}")
    public ResponseEntity<ApiResponse<Child>> getChildById(
            @PathVariable Long childId
    ) {
        Child child = childService.findById(childId);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._1001);
        ApiResponse<Child> apiResponse = ApiResponse.<Child>builder()
                .responseMetadata(responseMetadata)
                .data(child)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{childId}")
    public ResponseEntity<ApiResponse<Child>> updateChild(
            @PathVariable Long childId,
            @RequestBody @Valid Child child //todo: use dto
    ) {
        Child updatedChild = childService.update(childId, child);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._1002);
        ApiResponse<Child> apiResponse = ApiResponse.<Child>builder()
                .responseMetadata(responseMetadata)
                .data(updatedChild)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{childId}")
    public ResponseEntity<ApiResponse<Void>> deleteChild(
            @PathVariable Long childId
    ) {

        childService.removeChild(childId);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._1003);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .responseMetadata(responseMetadata)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<Child>>> getAllChildren(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Child> children = childService.findAll(page, size);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._1000);
        ApiResponse<Page<Child>> apiResponse = ApiResponse.<Page<Child>>builder()
                .responseMetadata(responseMetadata)
                .data(children)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/attendance/scan")
    public ResponseEntity<ApiResponse<Boolean>> scanQrAndMarkAttendance(
            @RequestParam Long childId,
            @RequestParam Long lessonId
    ) {
        boolean isMarked = childService.markAttendanceFromQr(childId, lessonId);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(
                ResponseCode._1000
        );

        ApiResponse<Boolean> apiResponse = ApiResponse.<Boolean>builder()
                .responseMetadata(responseMetadata)
                .data(isMarked)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

}
