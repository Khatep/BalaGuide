package kz.balaguide.sftp_module.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SimpleResponseDto {
    private boolean success;
    private String message;

    public SimpleResponseDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

}
