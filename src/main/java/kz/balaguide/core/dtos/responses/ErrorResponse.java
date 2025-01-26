package kz.balaguide.core.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse { //todo rename to API result
    private int statusCode; //TODO enum status code
    private String message;
}
