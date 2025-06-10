package kz.balaguide.course_module.controllers;

import jakarta.validation.Valid;
import kz.balaguide.common_module.core.dtos.responses.ApiResponse;
import kz.balaguide.common_module.core.entities.Group;
import kz.balaguide.common_module.core.entities.Lesson;
import kz.balaguide.common_module.core.entities.ResponseMetadata;
import kz.balaguide.common_module.core.enums.ResponseCode;
import kz.balaguide.common_module.services.responsemetadata.ResponseMetadataService;
import kz.balaguide.course_module.dto.CreateGroupRequest;
import kz.balaguide.course_module.services.GroupService;
import kz.balaguide.course_module.services.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final ResponseMetadataService responseMetadataService;
    private final LessonService lessonService;

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

    @GetMapping("/{groupId}/lessons")
    public ResponseEntity<ApiResponse<List<Lesson>>> getLessonsByGroupId(@PathVariable Long groupId) {
        List<Lesson> lessons = lessonService.findByGroupId(groupId);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._2000);
        ApiResponse<List<Lesson>> apiResponse = ApiResponse.<List<Lesson>>builder()
                .responseMetadata(responseMetadata)
                .data(lessons)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

}
