package kz.balaguide.common_module.services.responsemetadata;

import kz.balaguide.common_module.core.entities.ResponseMetadata;
import kz.balaguide.common_module.core.enums.ResponseCode;

public interface ResponseMetadataService {
    ResponseMetadata findByCode(ResponseCode code);
}
