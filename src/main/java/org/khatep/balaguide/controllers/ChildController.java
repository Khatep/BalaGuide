package org.khatep.balaguide.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.khatep.balaguide.models.entities.Child;
import org.khatep.balaguide.models.entities.Course;
import org.khatep.balaguide.services.ChildService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/children")
@RequiredArgsConstructor
public class ChildController {

    private final ChildService childService;

    /**
     * Retrieve all children.
     *
     * @return a list of all children
     */
    @GetMapping
    public ResponseEntity<List<Child>> getAllChildren() {
        List<Child> children = childService.findAll();
        return ResponseEntity.ok(children);
    }

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
            return ResponseEntity.noContent().build();
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
