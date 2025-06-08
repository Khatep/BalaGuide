package kz.balaguide.common_module.services.responsemetadata.impl;

import kz.balaguide.common_module.core.entities.ResponseMetadata;
import kz.balaguide.common_module.core.enums.ResponseCode;
import kz.balaguide.common_module.repositories.responsemessage.ResponseMessageRepository;
import kz.balaguide.common_module.services.responsemetadata.ResponseMetadataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseMetadataServiceImpl implements ResponseMetadataService {
    private final ResponseMessageRepository responseMessageRepository;

    @Override
    public ResponseMetadata findByCode(ResponseCode responseCode) {
        return responseMessageRepository.findByResponseCode(responseCode);
    }
}
