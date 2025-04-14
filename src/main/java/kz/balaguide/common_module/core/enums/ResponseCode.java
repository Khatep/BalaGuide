package kz.balaguide.common_module.core.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    // Ошибки (0XXX)

    // Общие ошибки
    //0000 - 0099
    _0000,
    _0001,
    _0002,
    _0003,
    _0004,
    _0005,
    _0006,

    // NOT FOUND: 0100 - 0199
    _0100,
    _0101,
    _0102,
    _0103,
    _0104,

    // ALREADY EXISTS 0200 - 0299
    _0200,

    // Financial Operations 0300 - 0399
    _0300,
    _0301,

    // Child's exception 0400 - 0499
    _0400,
    _0401,
    _0402,

    // Parent's exception 0600 - 0699

    //Course's exception 0800 - 0899
    _0800,

    // Teacher's exception 0900 - 0999

    // Успешные операции (1XXX)
    // Успешные операции по ребенку 1000 - 1299
    _1000,
    _1001,
    _1002,
    _1003,
    _1004,
    _1005,

    // Успешные операции по родителю 1300 - 1600
    _1300,
}

