package kz.balaguide.common_module.repositories.responsemessage;

import kz.balaguide.common_module.core.entities.ResponseMetadata;
import kz.balaguide.common_module.core.enums.ResponseCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseMessageRepository extends JpaRepository<ResponseMetadata, Long> {
    ResponseMetadata findByResponseCode(ResponseCode responseCode);
}
