package kz.balaguide.core.repositories.responsemessage;

import kz.balaguide.core.entities.ResponseMetadata;
import kz.balaguide.core.enums.ResponseCode;
import org.springframework.data.jpa.repository.JpaRepository;

//TODO: времменно отключен так как в базе данных пока нет информации про message
public interface ResponseMessageRepository
        //extends JpaRepository<ResponseMetadata, Long>
{
    ResponseMetadata findByCode(ResponseCode code);
}
