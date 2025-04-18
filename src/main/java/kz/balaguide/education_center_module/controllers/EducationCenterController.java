package kz.balaguide.education_center_module.controllers;

import kz.balaguide.common_module.core.dtos.responses.ApiResponse;
import kz.balaguide.common_module.core.entities.EducationCenter;
import kz.balaguide.common_module.core.entities.ResponseMetadata;
import kz.balaguide.common_module.core.enums.ResponseCode;
import kz.balaguide.common_module.services.responsemetadata.ResponseMetadataService;
import kz.balaguide.education_center_module.dtos.EducationCenterCreateReq;
import kz.balaguide.education_center_module.services.EducationCenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController("api/v1/education-centers")
@RequiredArgsConstructor
public class EducationCenterController {

    private final EducationCenterService educationCenterService;
    private final ResponseMetadataService responseMetadataService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<EducationCenter>> create(EducationCenterCreateReq educationCenterCreateReq) {
        EducationCenter educationCenter = educationCenterService.createEducationCenter(educationCenterCreateReq);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._1600);
        ApiResponse<EducationCenter> apiResponse = ApiResponse.<EducationCenter>builder()
                .responseMetadata(responseMetadata)
                .data(educationCenter)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
