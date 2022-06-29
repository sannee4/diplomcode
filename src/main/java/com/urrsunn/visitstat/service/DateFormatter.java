package com.urrsunn.visitstat.service;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DateFormatter {
    private static final String DATE_SPLIT_DIGIT = "-";

    public static String digitDateFormat(int dateValue) {
        if (dateValue < 10)
            return "0" + dateValue;
        return dateValue + "";
    }

    public LocalDate getDateByParams(String day, String month, String year) {
        String dateFormatted = year + DATE_SPLIT_DIGIT + digitDateFormat(Integer.parseInt(monthEnum.valueOf(month).monthNumber)) + DATE_SPLIT_DIGIT + day;
        return LocalDate.parse(dateFormatted);
    }
}

enum monthEnum {
    Январь("1"),
    Февраль("2"),
    Март("3"),
    Апрель("4"),
    Май("5"),
    Июнь("6"),
    Июль("7"),
    Август("8"),
    Сентябрь("9"),
    Октябрь("10"),
    Ноябрь("11"),
    Декабрь("12");


    public final String monthNumber;

    monthEnum(String month) {
        this.monthNumber = month;
    }
}