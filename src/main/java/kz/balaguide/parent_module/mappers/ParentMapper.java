package kz.balaguide.parent_module.mappers;

import kz.balaguide.parent_module.dtos.CreateParentRequest;
import kz.balaguide.common_module.core.entities.Parent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ParentMapper {
    public Parent mapCreateParentRequestToParent(final CreateParentRequest createParentRequest) {
        return Parent.builder()
                .firstName(createParentRequest.firstName())
                .lastName(createParentRequest.lastName())
                .phoneNumber(createParentRequest.phoneNumber())
                .birthDate(createParentRequest.birthDate())
                .email(createParentRequest.email())
                .balance(BigDecimal.ZERO)
                .build();
    }
}
