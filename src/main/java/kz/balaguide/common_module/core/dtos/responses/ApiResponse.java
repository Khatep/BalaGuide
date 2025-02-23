package kz.balaguide.common_module.core.dtos.responses;

import kz.balaguide.common_module.core.entities.ResponseMetadata;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private ResponseMetadata responseMetadata ;
    private T data;
}
