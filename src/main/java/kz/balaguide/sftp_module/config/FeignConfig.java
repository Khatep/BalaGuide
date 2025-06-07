package kz.balaguide.sftp_module.config;


import feign.codec.Encoder;
import feign.jackson.JacksonEncoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(
        basePackages = {"**.sftp_module.clients"}
)
public class FeignConfig {
    @Bean
    public Encoder feignEncoder() {
        return new JacksonEncoder();
    }
}
