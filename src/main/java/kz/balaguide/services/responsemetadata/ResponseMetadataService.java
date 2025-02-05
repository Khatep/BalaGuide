package kz.balaguide.services.responsemetadata;

import kz.balaguide.core.entities.ResponseMetadata;
import kz.balaguide.core.enums.ResponseCode;

public interface ResponseMetadataService {
    ResponseMetadata findByCode(ResponseCode code);
}
