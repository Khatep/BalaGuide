package kz.balaguide.education_center_module.services;

import kz.balaguide.common_module.core.entities.EducationCenter;
import kz.balaguide.education_center_module.dtos.EducationCenterCreateReq;

public interface EducationCenterService {
    EducationCenter createEducationCenter(EducationCenterCreateReq educationCenterCreateReq);
}
