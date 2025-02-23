package kz.balaguide.common_module.services.responsemetadata;

import kz.balaguide.common_module.core.entities.ResponseMetadata;
import kz.balaguide.common_module.core.enums.ResponseCode;
import kz.balaguide.common_module.repositories.responsemessage.ResponseMessageRepositoryTemp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseMetadataServiceImpl implements ResponseMetadataService {
    private final ResponseMessageRepositoryTemp responseMessageRepositoryTemp;

    @Override
    public ResponseMetadata findByCode(ResponseCode responseCode) {
        return responseMessageRepositoryTemp.findByCode(responseCode);
    }
}
