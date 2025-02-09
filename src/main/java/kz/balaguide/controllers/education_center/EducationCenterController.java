package kz.balaguide.controllers.education_center;

import kz.balaguide.services.educationcenter.EducationCenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/v1/education-centers")
@RequiredArgsConstructor
public class EducationCenterController {
    private final EducationCenterService educationCenterService;


}
