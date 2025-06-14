package kz.balaguide.education_center_module.controllers;

import jakarta.validation.Valid;
import kz.balaguide.child_module.mappers.ChildMapper;
import kz.balaguide.common_module.core.dtos.responses.ApiResponse;
import kz.balaguide.common_module.core.dtos.responses.ChildDto;
import kz.balaguide.common_module.core.dtos.responses.GroupDto;
import kz.balaguide.common_module.core.entities.*;
import kz.balaguide.common_module.core.enums.ResponseCode;
import kz.balaguide.common_module.services.responsemetadata.ResponseMetadataService;
import kz.balaguide.course_module.mappers.GroupMapper;
import kz.balaguide.education_center_module.dtos.*;
import kz.balaguide.education_center_module.services.EducationCenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/education-centers")
@RequiredArgsConstructor
public class EducationCenterController {

    private static final String DASHBOARD_URL = "{centerId}/dashboard";
    private final EducationCenterService educationCenterService;
    private final ResponseMetadataService responseMetadataService;
    private final GroupMapper groupMapper;
    private final ChildMapper childMapper;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<EducationCenter>> createEducationCenter(@RequestBody @Valid EducationCenterCreateReq educationCenterCreateReq) {
        EducationCenter educationCenter = educationCenterService.createEducationCenter(educationCenterCreateReq);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._1600);
        ApiResponse<EducationCenter> apiResponse = ApiResponse.<EducationCenter>builder()
                .responseMetadata(responseMetadata)
                .data(educationCenter)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/{educationalCenterId}/courses")
    public ResponseEntity<List<Course>> getAllCoursesByEducationalCenter(@PathVariable Long educationalCenterId) {
        List<Course> courses = educationCenterService.getCoursesByEducationCenter(educationalCenterId);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{educationCenterId}/groups")
    public ResponseEntity<ApiResponse<List<GroupDto>>> getAllGroupsByEducationCenterId(
            @PathVariable Long educationCenterId) {

        List<Group> groups = educationCenterService.findAllGroupsByEducationCenterId(educationCenterId);
        List<GroupDto> groupDtos = groups.stream()
                .map(groupMapper::toGroupDto)
                .toList();

        ResponseMetadata metadata = responseMetadataService.findByCode(ResponseCode._2000);
        ApiResponse<List<GroupDto>> response = ApiResponse.<List<GroupDto>>builder()
                .responseMetadata(metadata)
                .data(groupDtos)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{educationCenterId}/children")
    public ResponseEntity<ApiResponse<List<ChildDto>>> getChildrenByEducationCenter(
            @PathVariable Long educationCenterId
    ) {
        List<Child> children = educationCenterService.getChildrenByEducationCenter(educationCenterId);
        List<ChildDto> childDtos = children.stream()
                .map(childMapper::toChildDto)
                .toList();

        ResponseMetadata metadata = responseMetadataService.findByCode(ResponseCode._2000);
        ApiResponse<List<ChildDto>> response = ApiResponse.<List<ChildDto>>builder()
                .responseMetadata(metadata)
                .data(childDtos)
                .build();

        return ResponseEntity.ok(response);
    }



    @GetMapping(DASHBOARD_URL + "/total-revenue")
    public ResponseEntity<ApiResponse<Double>> getTotalRevenue(@PathVariable Long centerId) {
        Double totalRevenue = educationCenterService.calculateTotalRevenue(centerId);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._1601);
        ApiResponse<Double> apiResponse = ApiResponse.<Double>builder()
                .responseMetadata(responseMetadata)
                .data(totalRevenue)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping(DASHBOARD_URL + "/top-courses-by-revenue")
    public ResponseEntity<ApiResponse<List<CourseRevenueDTO>>> getTopCoursesByRevenue(@PathVariable Long centerId) {
        List<CourseRevenueDTO> topCourses = educationCenterService.getTopCoursesByRevenue(centerId, 5);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._1602);
        ApiResponse<List<CourseRevenueDTO>> apiResponse = ApiResponse.<List<CourseRevenueDTO>>builder()
                .responseMetadata(responseMetadata)
                .data(topCourses)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping(DASHBOARD_URL + "/children-per-course")
    public ResponseEntity<ApiResponse<List<CourseChildrenDTO>>> getChildrenPerCourse(@PathVariable Long centerId) {
        List<CourseChildrenDTO> distribution = educationCenterService.getChildrenDistributionByCourse(centerId);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._1603);
        ApiResponse<List<CourseChildrenDTO>> apiResponse = ApiResponse.<List<CourseChildrenDTO>>builder()
                .responseMetadata(responseMetadata)
                .data(distribution)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping(DASHBOARD_URL + "/revenue-by-month")
    public ResponseEntity<ApiResponse<List<MonthlyRevenueDTO>>> getRevenueByMonth(@PathVariable Long centerId) {
        List<MonthlyRevenueDTO> monthlyRevenue = educationCenterService.getMonthlyRevenue(centerId);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._1604);
        ApiResponse<List<MonthlyRevenueDTO>> apiResponse = ApiResponse.<List<MonthlyRevenueDTO>>builder()
                .responseMetadata(responseMetadata)
                .data(monthlyRevenue)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping(DASHBOARD_URL + "/children-growth-by-month")
    public ResponseEntity<ApiResponse<List<MonthlyChildrenGrowthDTO>>> getChildrenGrowthByMonth(@PathVariable Long centerId) {
        List<MonthlyChildrenGrowthDTO> childrenGrowth = educationCenterService.getMonthlyChildrenGrowth(centerId);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._1605);
        ApiResponse<List<MonthlyChildrenGrowthDTO>> apiResponse = ApiResponse.<List<MonthlyChildrenGrowthDTO>>builder()
                .responseMetadata(responseMetadata)
                .data(childrenGrowth)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping(DASHBOARD_URL + "/children-growth-by-month-fake")
    public ResponseEntity<ApiResponse<List<MonthlyChildrenGrowthDTO>>> getChildrenGrowthByMonthFake(@PathVariable Long centerId) {
        List<MonthlyChildrenGrowthDTO> childrenGrowth = educationCenterService.getMonthlyChildrenGrowthFake(centerId);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._1605);
        ApiResponse<List<MonthlyChildrenGrowthDTO>> apiResponse = ApiResponse.<List<MonthlyChildrenGrowthDTO>>builder()
                .responseMetadata(responseMetadata)
                .data(childrenGrowth)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping(DASHBOARD_URL + "/average-course-duration")
    public ResponseEntity<ApiResponse<Double>> getAverageCourseDuration(@PathVariable Long centerId) {
        Double averageDuration = educationCenterService.calculateAverageCourseDuration(centerId);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._1606);
        ApiResponse<Double> apiResponse = ApiResponse.<Double>builder()
                .responseMetadata(responseMetadata)
                .data(averageDuration)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping(DASHBOARD_URL + "/average-group-fill-percent")
    public ResponseEntity<ApiResponse<Double>> getAverageGroupFillPercent(@PathVariable Long centerId) {
        Double averageFillPercent = educationCenterService.calculateAverageGroupFillPercent(centerId);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._1607);
        ApiResponse<Double> apiResponse = ApiResponse.<Double>builder()
                .responseMetadata(responseMetadata)
                .data(averageFillPercent)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping(DASHBOARD_URL + "/returning-parents-count")
    public ResponseEntity<ApiResponse<Integer>> getReturningParentsCount(@PathVariable Long centerId) {
        Integer returningParents = educationCenterService.countReturningParents(centerId);

        ResponseMetadata responseMetadata = responseMetadataService.findByCode(ResponseCode._1608);
        ApiResponse<Integer> apiResponse = ApiResponse.<Integer>builder()
                .responseMetadata(responseMetadata)
                .data(returningParents)
                .build();

        return ResponseEntity.ok(apiResponse);
    }
}
