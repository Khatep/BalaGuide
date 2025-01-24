package org.khatep.balaguide.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.khatep.balaguide.exceptions.ChildNotBelongToParentException;
import org.khatep.balaguide.models.entities.Child;
import org.khatep.balaguide.models.entities.Course;
import org.khatep.balaguide.models.entities.Parent;
import org.khatep.balaguide.models.requests.AddBalanceRequest;
import org.khatep.balaguide.services.ParentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/parents")
@RequiredArgsConstructor
public class ParentController {

    private final ParentService parentService;

    /**
     * Endpoint to add a child to the parent's account.
     *
     * @param parentId the id of parent
     * @param child the {@link Child} entity to be added
     * @return the saved {@link Child} entity
     */
    @PostMapping("{parentId}/add-child")
    public ResponseEntity<Child> addChild(@PathVariable Long parentId, @RequestBody @Valid Child child) {
        try {
            Child addedChild = parentService.addChild(parentId, child);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedChild);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    /**
     * Endpoint to remove a child from the parent's account.
     *
     * @param parentId the ID of the {@link Parent} entity which child will be removed
     * @param childId the ID of the {@link Child} entity to be removed
     * @return a message indicating removal success or failure
     */
    @DeleteMapping("{parentId}/remove-child/{childId}")
    public ResponseEntity<String> removeChild(@PathVariable Long parentId, @PathVariable Long childId) {
        try {
            parentService.removeChild(parentId, childId);
            return ResponseEntity.ok("Child removed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    /**
     * Endpoint to get the list of children associated with a parent.
     *
     * @param parentId the ID of the {@link Parent} entity
     * @return a {@link List} of {@link Child} entities
     */
    @GetMapping("/{parentId}/my-children")
    public ResponseEntity<List<Child>> getMyChildren(@PathVariable Long parentId) {
        try {
            List<Child> children = parentService.getMyChildren(parentId);
            return ResponseEntity.ok(children);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    /**
     * Endpoint to enroll a child in a course.
     *
     * @param parentId the ID of the {@link Parent} entity
     * @param childId the ID of the {@link Child} entity
     * @param courseId the ID of the {@link Course} entity
     * @return a message indicating enrollment success or failure
     */
    @PostMapping("/{parentId}/children/{childId}/enroll/{courseId}")
    public ResponseEntity<String> enrollChild(@PathVariable Long parentId, @PathVariable Long childId, @PathVariable Long courseId) {
        try {
            parentService.enrollChildToCourse(parentId, childId, courseId);
            return ResponseEntity.ok("Child enrolled successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Endpoint to unenroll a child from a course.
     *
     * @param parentId the ID of the {@link Parent} entity
     * @param childId the ID of the {@link Child} entity
     * @param courseId the ID of the {@link Course} entity
     * @return a message indicating unenrollment success or failure
     */
    @DeleteMapping("/{parentId}/children/{childId}/unenroll/{courseId}")
    public ResponseEntity<String> unenrollChild(@PathVariable Long parentId, @PathVariable Long childId, @PathVariable Long courseId) {
        try {
            parentService.unenrollChildFromCourse(parentId, childId, courseId);
            return ResponseEntity.ok("Child unenrolled successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Endpoint to add balance to a parent's account.
     *
     * @param parentId the ID of the {@link Parent} entity
     * @param addBalanceRequest the dto which represents amount of money and bank card number
     * @return a message indicating the updated balance
     */
    @PostMapping("/{parentId}/add-balance")
    public ResponseEntity<String> addBalance(@PathVariable Long parentId, @RequestBody AddBalanceRequest addBalanceRequest) {
        try {
            String message = parentService.addBalance(parentId, addBalanceRequest.amountOfMoney(), addBalanceRequest.numberOfBankCard());
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Endpoint to remove a parent account.
     *
     * @param parentId the ID of the {@link Parent} entity to be removed
     * @return a message indicating removal success or failure
     */
    @DeleteMapping("/{parentId}")
    public ResponseEntity<String> removeParent(@PathVariable Long parentId) {
        try {
            parentService.removeParent(parentId);
            return ResponseEntity.ok("Parent removed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Endpoint to update a parent's information.
     *
     * @param parentId the ID of the {@link Parent} entity to be updated
     * @param parent the {@link Parent} entity with updated information
     * @return the updated {@link Parent} entity
     */
    @PutMapping("/{parentId}")
    public ResponseEntity<Parent> updateParent(@PathVariable Long parentId, @RequestBody @Valid Parent parent) {
        try {
            Parent updatedParent = parentService.updateParent(parentId, parent);
            return ResponseEntity.ok(updatedParent);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
