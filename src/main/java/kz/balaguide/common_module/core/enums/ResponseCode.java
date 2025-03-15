package kz.balaguide.common_module.core.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    // Ошибки (0XXX)

    // Общие ошибки
    //0000 - 0099
    _0000("Bad request exception"),
    _0001("Runtime exception"),
    _0002("Illegal argument exception"),

    // NOT FOUND: 0100 - 0199
    _0100("Child not found"),
    _0101("Children not found"),
    _0102("Parent not found"),
    _0103("Course not found"),
    _0104("Education center not found"),

    // ALREADY EXISTS 0200 - 0299
    _0200("User already exists"),

    // Financial Operations 0300 - 0399
    _0300("Balance update exception"),
    _0301("Insufficient funds"),

    // Child's exception 0400 - 0499
    _0400("Child does not belong to parent"),
    _0401("Child is not enrolled in the course"),
    _0402("Child is ineligible"),

    // Parent's exception 0600 - 0699

    //Course's exception 0800 - 0899
    _0800("Course is full"),

    // Teacher's exception 0900 - 0999

    // Успешные операции (1XXX)
    // Успешные операции по ребенку 1000 - 1299
    _1000("Children retrieved successfully"),
    _1001("Child retrieved successfully"),
    _1002("Child updated successfully"),
    _1003("Child removed successfully"),
    _1004("Child's courses retrieved successfully"),
    _1005("Child enrolled successfully"),

    // Успешные операции по родителю 1300 - 1600
    _1300("Parent created successfully"),

    ;
    private final String message;
}

