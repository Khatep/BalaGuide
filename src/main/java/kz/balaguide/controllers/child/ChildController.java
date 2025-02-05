package kz.balaguide.controllers.child;

import jakarta.validation.Valid;
import kz.balaguide.core.dtos.responses.ApiResponse;
import kz.balaguide.core.entities.ResponseMetadata;
import kz.balaguide.core.enums.ResponseCode;
import kz.balaguide.services.responsemetadata.ResponseMetadataService;
import lombok.RequiredArgsConstructor;
import kz.balaguide.core.entities.Child;
import kz.balaguide.core.entities.Course;
import kz.balaguide.services.child.ChildService;
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

    /**
     * Retrieve all children.
     *
     * @return a list of all children
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<Child>>> getAllChildren(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Child> children = childService.findAll(page, size);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._1000);
        ApiResponse<Page<Child>> response = new ApiResponse<>(
                responseMetadata,
                children
        );

        return ResponseEntity.ok(response);
    }

    //TODO: Переделать все контроллеры с сервисами под ApiResponse

    /**
     * Retrieve a child by ID.
     *
     * @param id the ID of the child to retrieve
     * @return the child if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Child> getChildById(@PathVariable Long id) {
        return childService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Update an existing child by ID.
     *
     * @param id    the ID of the child to update
     * @param child the updated child information
     * @return the updated child if found, otherwise a not found status
     */
    @PutMapping("/{id}")
    public ResponseEntity<Child> updateChild(@PathVariable Long id, @RequestBody @Valid Child child) {
        return childService.update(id, child)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete a child by ID.
     *
     * @param id the ID of the child to delete
     * @return no content status if deletion is successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChild(@PathVariable Long id) {
        if (childService.removeChild(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieve courses that a child is enrolled in.
     *
     * @param id the ID of the child
     * @return list of courses the child is enrolled in
     */
    @GetMapping("/{id}/my-courses")
    public ResponseEntity<List<Course>> getChildCourses(@PathVariable Long id) {
        return childService.findById(id)
                .map(child -> ResponseEntity.ok(childService.getMyCourses(child)))
                .orElse(ResponseEntity.notFound().build());
    }
}
