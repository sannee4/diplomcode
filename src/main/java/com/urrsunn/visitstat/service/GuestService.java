package com.urrsunn.visitstat.service;

import com.urrsunn.visitstat.dto.GuestDataDto;
import com.urrsunn.visitstat.entity.StayAddress;
import com.urrsunn.visitstat.model.Guest;

import java.time.LocalDate;
import java.util.List;

public interface GuestService {
    List<StayAddress> guestCountByDate(LocalDate date);

    List<StayAddress> guestCountByDateWithHotel(LocalDate date, String city, String street, String house);

    List<StayAddress> questCountByPeriod(LocalDate dateFrom, LocalDate dateTo);

    List<StayAddress> guestCountByPeriodWithHotel(LocalDate dateFrom, LocalDate dateTo, String city, String street, String house);

    Guest guestAverageByHotel(String city, String street, String house);

    String guestCountByLivingCityAndDate(String city, LocalDate dateByParams);

    void saveData(List<GuestDataDto> guestDataDtoList);
}
