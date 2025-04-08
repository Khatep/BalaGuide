package kz.balaguide.child_module.controllers;

import jakarta.validation.Valid;
import kz.balaguide.common_module.core.dtos.responses.ApiResponse;
import kz.balaguide.common_module.core.entities.ResponseMetadata;
import kz.balaguide.common_module.core.enums.ResponseCode;
import kz.balaguide.common_module.services.responsemetadata.ResponseMetadataService;
import lombok.RequiredArgsConstructor;
import kz.balaguide.common_module.core.entities.Child;
import kz.balaguide.common_module.core.entities.Course;
import kz.balaguide.child_module.services.ChildService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/children")
@RequiredArgsConstructor
public class ChildController {

    private final ChildService childService;
    private final ResponseMetadataService responseMetadataService;

    /**
     * Retrieve all children.
     *
     * @return a list of all children
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
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

    /**
     * Retrieve a child by ID.
     *
     * @param id the ID of the child to retrieve
     * @return the child if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Child>> getChildById(
            @PathVariable Long id
    ) {
        Child child = childService.findById(id);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._1001);
        ApiResponse<Child> apiResponse = ApiResponse.<Child>builder()
                .responseMetadata(responseMetadata)
                .data(child)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Update an existing child by ID.
     *
     * @param id    the ID of the child to update
     * @param child the updated child information
     * @return the updated child if found, otherwise a not found status
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Child>> updateChild(
            @PathVariable Long id,
            @RequestBody @Valid Child child
    ) {
        Child updatedChild = childService.update(id, child);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._1002);
        ApiResponse<Child> apiResponse = ApiResponse.<Child>builder()
                .responseMetadata(responseMetadata)
                .data(updatedChild)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Delete a child by ID.
     *
     * @param id the ID of the child to delete
     * @return no content status if deletion is successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteChild(
            @PathVariable Long id
    ) {

        childService.removeChild(id);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._1003);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .responseMetadata(responseMetadata)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieve courses that a child is enrolled in.
     *
     * @param id the ID of the child
     * @return list of courses the child is enrolled in
     */
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
}
