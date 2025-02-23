package kz.balaguide.common_module.repositories.responsemessage;

import kz.balaguide.common_module.core.entities.ResponseMetadata;
import kz.balaguide.common_module.core.enums.ResponseCode;

//TODO: времменно отключен так как в базе данных пока нет информации про message
public interface ResponseMessageRepository
        //extends JpaRepository<ResponseMetadata, Long>
{
    ResponseMetadata findByCode(ResponseCode code);
}
