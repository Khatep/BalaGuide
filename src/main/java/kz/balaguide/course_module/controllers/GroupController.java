package kz.balaguide.course_module.controllers;

import jakarta.validation.Valid;
import kz.balaguide.common_module.core.dtos.responses.ApiResponse;
import kz.balaguide.common_module.core.entities.Group;
import kz.balaguide.common_module.core.entities.ResponseMetadata;
import kz.balaguide.common_module.core.enums.ResponseCode;
import kz.balaguide.common_module.services.responsemetadata.ResponseMetadataService;
import kz.balaguide.course_module.dto.CreateGroupRequest;
import kz.balaguide.course_module.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final ResponseMetadataService responseMetadataService;


    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Group>> createGroup(@RequestBody @Valid CreateGroupRequest createGroupRequest) {
        Group group = groupService.createGroup(createGroupRequest);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._2000);
        ApiResponse<Group> apiResponse = ApiResponse.<Group>builder()
                .responseMetadata(responseMetadata)
                .data(group)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
