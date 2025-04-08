package kz.balaguide.auth_module.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInUserResponse<T> {
    private JwtResponseDto jwtResponseDto;
    T user;
}
