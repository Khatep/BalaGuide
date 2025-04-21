package kz.balaguide.education_center_module.mappers;

import kz.balaguide.common_module.core.entities.EducationCenter;
import kz.balaguide.common_module.core.enums.Role;
import kz.balaguide.education_center_module.dtos.EducationCenterCreateReq;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class EducationCenterMapper {
    public EducationCenter mapEducationCenterCreateReqToEducationCenter(EducationCenterCreateReq educationCenterCreateReq) {
        return EducationCenter.builder()
                .name(educationCenterCreateReq.name())
                .dateOfCreated(educationCenterCreateReq.dateOfCreated())
                .phoneNumber(educationCenterCreateReq.phoneNumber())
                .email(educationCenterCreateReq.email())
                .address(educationCenterCreateReq.address())
                .instagramLink(educationCenterCreateReq.instagramLink())
                .balance(BigDecimal.ZERO)
                .build();
    }
}
