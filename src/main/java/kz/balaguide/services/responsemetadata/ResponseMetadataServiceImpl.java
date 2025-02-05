package kz.balaguide.services.responsemetadata;

import kz.balaguide.core.entities.ResponseMetadata;
import kz.balaguide.core.enums.ResponseCode;
import kz.balaguide.core.repositories.responsemessage.ResponseMessageRepositoryTemp;
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
