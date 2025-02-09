package kz.balaguide.controllers.parent;

import jakarta.validation.Valid;
import kz.balaguide.core.dtos.auth.CreateParentRequest;
import kz.balaguide.core.dtos.responses.ApiResponse;
import kz.balaguide.core.entities.ResponseMetadata;
import kz.balaguide.core.enums.ResponseCode;
import kz.balaguide.services.responsemetadata.ResponseMetadataService;
import lombok.RequiredArgsConstructor;
import kz.balaguide.core.entities.Child;
import kz.balaguide.core.entities.Course;
import kz.balaguide.core.entities.Parent;
import kz.balaguide.core.dtos.requests.AddBalanceRequest;
import kz.balaguide.services.parent.ParentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/parents")
@RequiredArgsConstructor
public class ParentController {

    //TODO: Переделать все контроллеры с сервисами под ApiResponse

    private final ParentService parentService;
    private final ResponseMetadataService responseMetadataService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Parent>> createParent(@RequestBody @Valid CreateParentRequest createParentRequest) {
        Parent parent = parentService.save(createParentRequest);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._1300);
        ApiResponse<Parent> apiResponse = ApiResponse.<Parent>builder()
                .responseMetadata(responseMetadata)
                .data(parent)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Endpoint to add a child to the parent's account.
     *
     * @param parentId the id of parent
     * @param child the {@link Child} entity to be added
     * @return the saved {@link Child} entity
     */
    @PostMapping("{parentId}/add-child")
    public ResponseEntity<Child> addChild(@PathVariable Long parentId, @RequestBody @Valid Child child) {
        Child addedChild = parentService.addChild(parentId, child);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedChild);

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
        parentService.removeChild(parentId, childId);
        return ResponseEntity.ok("Child removed successfully");

    }

    /**
     * Endpoint to get the list of children associated with a parent.
     *
     * @param parentId the ID of the {@link Parent} entity
     * @return a {@link List} of {@link Child} entities
     */
    @GetMapping("/{parentId}/my-children")
    public ResponseEntity<List<Child>> getMyChildren(@PathVariable Long parentId) {
        List<Child> children = parentService.getMyChildren(parentId);
        return ResponseEntity.ok(children);

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
        parentService.enrollChildToCourse(parentId, childId, courseId);
        return ResponseEntity.ok("Child enrolled successfully");
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
        parentService.unenrollChildFromCourse(parentId, childId, courseId);
        return ResponseEntity.ok("Child unenrolled successfully");
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
        String message = parentService.addBalance(parentId, addBalanceRequest.amountOfMoney(), addBalanceRequest.numberOfBankCard());
        return ResponseEntity.ok(message);

    }

    /**
     * Endpoint to remove a parent account.
     *
     * @param parentId the ID of the {@link Parent} entity to be removed
     * @return a message indicating removal success or failure
     */
    @DeleteMapping("/{parentId}")
    public ResponseEntity<String> removeParent(@PathVariable Long parentId) {
        parentService.removeParent(parentId);
        return ResponseEntity.ok("Parent removed successfully");
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
        Parent updatedParent = parentService.updateParent(parentId, parent);
        return ResponseEntity.ok(updatedParent);
    }
}
