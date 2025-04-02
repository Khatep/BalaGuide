package kz.balaguide.common_module.repositories.responsemessage;

import kz.balaguide.common_module.core.entities.ResponseMetadata;
import kz.balaguide.common_module.core.enums.ResponseCode;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.HashMap;

//TODO: Временое решение пока не настроем Docker с базой данных и файлами init.sql (DIP-21)
@Component
public class ResponseMessageRepositoryTemp {

    private final Map<ResponseCode, ResponseMetadata> responseMetadataMap = new HashMap<>();

    public ResponseMessageRepositoryTemp() {
        for (ResponseCode code : ResponseCode.values()) {
            responseMetadataMap.put(code, createMetadata(code));
        }
    }

    private ResponseMetadata createMetadata(ResponseCode code) {
        return switch (code) {
            case _0000 -> new ResponseMetadata(1L, code, "BAD REQUEST");
            case _0001 -> new ResponseMetadata(2L, code, "RUNTIME EXCEPTION");
            case _0002 -> new ResponseMetadata(3L, code, "Illegal argument exception");

            case _0100 -> new ResponseMetadata(4L, code, "Child not found");
            case _0101 -> new ResponseMetadata(5L, code, "Children not found");
            case _0102 -> new ResponseMetadata(6L, code, "Parent not found");
            case _0103 -> new ResponseMetadata(7L, code, "Course not found");
            case _0104 -> new ResponseMetadata(8L, code, "Education center not found");

            case _0200 -> new ResponseMetadata(9L, code, "User already exists");

            case _0300 -> new ResponseMetadata(10L, code, "Balance update error");
            case _0301 -> new ResponseMetadata(11L, code, "Insufficient funds");

            case _0400 -> new ResponseMetadata(12L, code, "Child does not belong to parent");
            case _0401 -> new ResponseMetadata(13L, code, "Child not enrolled to course");
            case _0402 -> new ResponseMetadata(14L, code, "Ineligible child");

            case _0800 -> new ResponseMetadata(15L, code, "Course is full");

            case _1000 -> new ResponseMetadata(16L, code, "Children retrieved successfully");
            case _1001 -> new ResponseMetadata(17L, code, "Child retrieved successfully");
            case _1002 -> new ResponseMetadata(18L, code, "Child updated successfully");
            case _1003 -> new ResponseMetadata(19L, code, "Child removed successfully");
            case _1004 -> new ResponseMetadata(20L, code, "Child's courses retrieved successfully");

            case _1300 -> new ResponseMetadata(20L, code, "Parent created successfully");
            default -> new ResponseMetadata(99L, code, "Default message");
        };
    }

    public ResponseMetadata findByCode(ResponseCode code) {
        return responseMetadataMap.get(code);
    }
}
