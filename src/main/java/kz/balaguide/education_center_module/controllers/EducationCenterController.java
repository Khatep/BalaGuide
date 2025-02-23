package kz.balaguide.education_center_module.controllers;

import kz.balaguide.education_center_module.services.EducationCenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/v1/education-centers")
@RequiredArgsConstructor
public class EducationCenterController {

    private final EducationCenterService educationCenterService;


}
