package com.urrsunn.visitstat.service.impl;

import java.util.ArrayList;
import java.util.List;

import static com.urrsunn.visitstat.service.DateFormatter.digitDateFormat;

public class DateUtils {
    public static List<String> days = new ArrayList<>();
    static {
        for (int i = 1; i <= 31; i++) {
            days.add(digitDateFormat(i));
        }
    }
    public static List<String> months = List.of("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь");
    public static List<String> years = List.of("2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022");
}
