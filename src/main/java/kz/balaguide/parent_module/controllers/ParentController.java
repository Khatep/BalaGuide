package kz.balaguide.parent_module.controllers;

import jakarta.validation.Valid;
import kz.balaguide.course_module.dto.EnrollmentActionDto;
import kz.balaguide.parent_module.dtos.CreateChildRequest;
import kz.balaguide.parent_module.dtos.CreateParentRequest;
import kz.balaguide.common_module.core.dtos.responses.ApiResponse;
import kz.balaguide.common_module.core.entities.ResponseMetadata;
import kz.balaguide.common_module.core.enums.ResponseCode;
import kz.balaguide.common_module.services.responsemetadata.ResponseMetadataService;
import kz.balaguide.parent_module.dtos.UpdateParentRequest;
import lombok.RequiredArgsConstructor;
import kz.balaguide.common_module.core.entities.Child;
import kz.balaguide.common_module.core.entities.Parent;
import kz.balaguide.common_module.core.dtos.requests.AddBalanceRequest;
import kz.balaguide.parent_module.services.ParentService;
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
        Parent parent = parentService.createParentAndSave(createParentRequest);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._1300);
        ApiResponse<Parent> apiResponse = ApiResponse.<Parent>builder()
                .responseMetadata(responseMetadata)
                .data(parent)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/update/{parentId}")
    public ResponseEntity<Parent> updateParent(@PathVariable Long parentId, @RequestBody @Valid UpdateParentRequest parent) {
        Parent updatedParent = parentService.updateParent(parentId, parent);
        return ResponseEntity.ok(updatedParent);
    }

    @PostMapping("{parentId}/add-child")
    public ResponseEntity<ApiResponse<Child>> addChild(@PathVariable Long parentId,
                                                       @RequestBody @Valid CreateChildRequest createChildRequest) {
        Child child = parentService.addChild(parentId, createChildRequest);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._1007);
        ApiResponse<Child> apiResponse = ApiResponse.<Child>builder()
                .responseMetadata(responseMetadata)
                .data(child)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @DeleteMapping("{parentId}/remove-child/{childId}")
    public ResponseEntity<String> removeChild(@PathVariable Long parentId, @PathVariable Long childId) {
        parentService.removeChild(parentId, childId);
        return ResponseEntity.ok("Child removed successfully");
    }

    @GetMapping("/{parentId}/my-children")
    public ResponseEntity<ApiResponse<List<Child>>> getMyChildren(@PathVariable Long parentId) {
        List<Child> children = parentService.getMyChildren(parentId);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._1000);
        ApiResponse<List<Child>> apiResponse = ApiResponse.<List<Child>>builder()
                .responseMetadata(responseMetadata)
                .data(children)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    //TODO нужно пополнение сделать по другому
    @PostMapping("/{parentId}/add-balance")
    public ResponseEntity<String> addBalance(@PathVariable Long parentId, @RequestBody AddBalanceRequest addBalanceRequest) {

        String message = parentService.addBalance(
                parentId,
                addBalanceRequest.amountOfMoney(),
                addBalanceRequest.bankCard()
        );

        return ResponseEntity.ok(message);
    }
}
