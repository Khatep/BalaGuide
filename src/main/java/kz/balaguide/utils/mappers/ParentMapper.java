package kz.balaguide.utils.mappers;

import kz.balaguide.core.dtos.auth.CreateParentRequest;
import kz.balaguide.core.entities.Parent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ParentMapper {
    public Parent mapCreateParentRequestToParent(CreateParentRequest createParentRequest) {
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
