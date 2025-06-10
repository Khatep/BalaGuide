package kz.balaguide.sftp_module.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UploadResponseDto {
    private boolean success;
    private String message;
    private String fileName;

    public UploadResponseDto(boolean success, String message, String fileName) {
        this.success = success;
        this.message = message;
        this.fileName = fileName;
    }
}
