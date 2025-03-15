package kz.balaguide.child_module.mappers;

import kz.balaguide.common_module.core.entities.Child;
import kz.balaguide.parent_module.dtos.CreateChildRequest;
import org.springframework.stereotype.Component;

@Component
public class ChildMapper {
    public Child mapCreateChildRequestToChild(final CreateChildRequest createChildRequest) {
        return Child.builder()
                .firstName(createChildRequest.firstName())
                .lastName(createChildRequest.lastName())
                .phoneNumber(createChildRequest.phoneNumber())
                .birthDate(createChildRequest.birthDate())
                .email(createChildRequest.email())
                .gender(createChildRequest.gender())
                .build();
    }
}
