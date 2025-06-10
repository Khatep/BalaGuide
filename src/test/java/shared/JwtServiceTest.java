package shared;

import kz.balaguide.auth_module.dtos.JwtResponseDto;
import kz.balaguide.auth_module.services.JwtService;
import kz.balaguide.auth_module.services.RedisService;
import kz.balaguide.common_module.core.entities.AuthUser;
import kz.balaguide.common_module.core.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private RedisService redisService;
    private final String signingKey = Base64.getEncoder().encodeToString("test-secret-key-1234567890123456".getBytes());

    @BeforeEach
    void setUp() {
        jwtService = new JwtService(redisService);
        ReflectionTestUtils.setField(jwtService, "jwtSigningKey", signingKey);
        ReflectionTestUtils.setField(jwtService, "tokeLifetimeMillis", 3600000L); // 1 час
    }

    @Test
    void generateAndValidateToken() {
        AuthUser user = AuthUser.builder()
                .phoneNumber("77001112233")
                .role(Role.PARENT)
                .password("password")
                .build();
        user.setId(1L); // через сеттер

        JwtResponseDto jwt = jwtService.generateToken(user);
        assertNotNull(jwt);
        assertNotNull(jwt.getToken());

        boolean isValid = jwtService.isTokenValid(jwt.getToken(), user);
        assertTrue(isValid);

        String phone = jwtService.extractPhoneNumber(jwt.getToken());
        assertEquals("77001112233", phone);
    }

    @Test
    void tokenShouldBeInvalidIfExpired() {
        AuthUser user = AuthUser.builder()
                .phoneNumber("expireduser")
                .role(Role.PARENT)
                .password("password")
                .build();
        user.setId(1L); // через сеттер

        ReflectionTestUtils.setField(jwtService, "tokeLifetimeMillis", -1000L); // токен уже истек

        JwtResponseDto jwt = jwtService.generateToken(user);
        boolean isValid = jwtService.isTokenValid(jwt.getToken(), user);

        assertFalse(isValid);
    }
}
