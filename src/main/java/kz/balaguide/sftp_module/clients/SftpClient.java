package kz.balaguide.sftp_module.clients;

import kz.balaguide.sftp_module.config.FeignConfig;
import kz.balaguide.sftp_module.dtos.SimpleResponseDto;
import kz.balaguide.sftp_module.dtos.UploadResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "sftp-client",
        url = "${sftp.url}",
        path = "${sftp.path}",
        configuration = FeignConfig.class
)
public interface SftpClient {
    @PostMapping("/upload")
    ResponseEntity<UploadResponseDto> upload(@RequestBody byte[] file,
                                             @RequestParam("fileName") String filename);

    @GetMapping("/download/{filename}")
    ResponseEntity<byte[]> download(@PathVariable String filename);

    @DeleteMapping("/{filename}")
    ResponseEntity<SimpleResponseDto> delete(@PathVariable String filename);

}
