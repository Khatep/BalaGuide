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
        long index = 0L;
        return switch (code) {
            case _0000 -> new ResponseMetadata(++index, code, "BAD REQUEST");
            case _0001 -> new ResponseMetadata(++index, code, "RUNTIME EXCEPTION");
            case _0002 -> new ResponseMetadata(++index, code, "Illegal argument exception");
            case _0003 -> new ResponseMetadata(++index, code, "Token expired exception");
            case _0004 -> new ResponseMetadata(++index, code, "Unauthorized exception");
            case _0005 -> new ResponseMetadata(++index, code, "Bad credentials exception");
            case _0006 -> new ResponseMetadata(++index, code, "Access denied exception");
            case _0007 -> new ResponseMetadata(++index, code, "Invalid compact JWT string");

            case _0100 -> new ResponseMetadata(++index, code, "Child not found");
            case _0101 -> new ResponseMetadata(++index, code, "Children not found");
            case _0102 -> new ResponseMetadata(++index, code, "Parent not found");
            case _0103 -> new ResponseMetadata(++index, code, "Course not found");
            case _0104 -> new ResponseMetadata(++index, code, "Education center not found");

            case _0200 -> new ResponseMetadata(++index, code, "User already exists");

            case _0300 -> new ResponseMetadata(++index, code, "Balance update error");
            case _0301 -> new ResponseMetadata(++index, code, "Insufficient funds");

            case _0400 -> new ResponseMetadata(++index, code, "Child does not belong to parent");
            case _0401 -> new ResponseMetadata(++index, code, "Child not enrolled to course");
            case _0402 -> new ResponseMetadata(++index, code, "Ineligible child");

            case _0800 -> new ResponseMetadata(++index, code, "Course is full");

            case _1007 -> new ResponseMetadata(++index, code, "Child created successfully");
            case _1000 -> new ResponseMetadata(++index, code, "Children retrieved successfully");
            case _1001 -> new ResponseMetadata(++index, code, "Child retrieved successfully");
            case _1002 -> new ResponseMetadata(++index, code, "Child updated successfully");
            case _1003 -> new ResponseMetadata(++index, code, "Child removed successfully");
            case _1004 -> new ResponseMetadata(++index, code, "Child's courses retrieved successfully");
            case _1005 -> new ResponseMetadata(++index, code, "Child enrolled successfully");
            case _1006 -> new ResponseMetadata(++index, code, "Child's unenrolled successfully");


            case _1300 -> new ResponseMetadata(++index, code, "Parent created successfully");

            case _1600 -> new ResponseMetadata(++index, code, "Education center created successfully");

            case _1900 -> new ResponseMetadata(++index, code, "Course created successfully");

            case _2000 -> new ResponseMetadata(++index, code, "Group created successfully");

            case _2200 -> new ResponseMetadata(++index, code, "Teacher created successfully");
            default -> new ResponseMetadata(99L, code, "Default message");
        };
    }

    public ResponseMetadata findByResponseCode(ResponseCode code) {
        return responseMetadataMap.get(code);
    }
}
