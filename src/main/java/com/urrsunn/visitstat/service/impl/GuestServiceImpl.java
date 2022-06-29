package com.urrsunn.visitstat.service.impl;

import com.urrsunn.visitstat.dto.GuestDataDto;
import com.urrsunn.visitstat.entity.StatInfo;
import com.urrsunn.visitstat.entity.StayAddress;
import com.urrsunn.visitstat.model.Guest;
import com.urrsunn.visitstat.repo.StatInfoRepository;
import com.urrsunn.visitstat.repo.StayAddressRepository;
import com.urrsunn.visitstat.service.GuestService;
import com.urrsunn.visitstat.utils.FileRead;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {
    private final StayAddressRepository stayAddressRepository;
    private final StatInfoRepository statInfoRepository;
    private final FileRead fileRead;

    @Override
    public List<StayAddress> guestCountByDate(LocalDate date) {
        return stayAddressRepository.getGuestCountByDate(date.toString());
    }

    @Override
    public List<StayAddress> questCountByPeriod(LocalDate date, LocalDate dateTo) {
        return stayAddressRepository.getGuestsCountByPeriod(date.toString(), dateTo.toString());
    }

    @Override
    public List<StayAddress> guestCountByDateWithHotel(LocalDate date, String city, String street, String house) {
        return stayAddressRepository.guestsCountByDateWithHotel(date.toString(), wrapLikeSymbol(city), wrapLikeSymbol(street), wrapLikeSymbol(house));
    }

    private String wrapLikeSymbol(String value) {
        if (!value.isEmpty()) {
            value = value.replaceFirst(value.charAt(0) + "", (value.charAt(0) + "").toUpperCase());
        }
        return "%" + value + "%";
    }

    @Override
    public List<StayAddress> guestCountByPeriodWithHotel(LocalDate dateFrom, LocalDate dateTo, String city, String street, String house) {
        return stayAddressRepository.guestsCountByPeriodWithHotel(dateFrom.toString(), dateTo.toString(), wrapLikeSymbol(city), wrapLikeSymbol(street), wrapLikeSymbol(house));
    }

    @Override
    public Guest guestAverageByHotel(String city, String street, String house) {
        List<StayAddress> result = stayAddressRepository.guestAverageByHotel(wrapLikeSymbol(city), wrapLikeSymbol(street), wrapLikeSymbol(house));
        Guest guest = new Guest();
        guest.setGender(calculateGenderFromResult(result));
        guest.setAge(calculateAvgYearFromResult(result));
        return guest;
    }

    private Integer calculateAvgYearFromResult(List<StayAddress> result) {
        return Year.now().getValue() - ((result.get(0).getAge() + result.get(1).getAge()) / 2);
    }

    private String calculateGenderFromResult(List<StayAddress> result) {
        return (result.get(0).getGuestCount() > result.get(1).getGuestCount()) ? result.get(0).getGender() : result.get(1).getGender();
    }

    @Override
    public String guestCountByLivingCityAndDate(String citiesFromQuery, LocalDate dateTo) {
        Map<String, List<String>> fiasMap = fileRead.getMap();
        List<String> cities = List.of(citiesFromQuery.split(","));
        List<String> fiasList = new ArrayList<>();
        for (String city : cities) {
            if (fiasMap.containsKey(city.toLowerCase())) {
                List<String> values = fiasMap.get(city.toLowerCase());
                fiasList.addAll(values);
            }
        }
        if (fiasList.isEmpty()) {
            return "Нет данных по запросу";
        }
        return stayAddressRepository.guestCountByLivingCityAndDate(fiasList, dateTo.toString()).get(0).getId() + "";
    }

    @Override
    public void saveData(List<GuestDataDto> guestDataDtoList) {
        statInfoRepository.saveAll(guestDataDtoList.stream().map(this::buildStatInfo).collect(Collectors.toList()));
    }

    private StatInfo buildStatInfo(GuestDataDto it) {
        StatInfo statInfo = new StatInfo();
        statInfo.setArrivalDate(new Date(Optional.ofNullable(it.getArrivalDate()).orElse(it.getArrivalDate())));
        statInfo.setBirthDate(Optional.ofNullable(it.getBirthDate()).orElse(it.getBirthDate()));
        statInfo.setGender(Optional.ofNullable(it.getGender()).orElse(it.getGender()));
        statInfo.setLivingCity(Optional.ofNullable(it.getLivingCity()).orElse(it.getLivingCity()));
        statInfo.setStayAddress(buildStayAddress(it));

        return statInfo;
    }

    private StayAddress buildStayAddress(GuestDataDto it) {
        StayAddress stayAddress = new StayAddress();
        stayAddress.setFullPath(Optional.ofNullable(it.getFullPath()).orElse(it.getFullPath()));
        stayAddress.setHouse(Optional.ofNullable(it.getHouse()).orElse(it.getHouse()));
        return stayAddress;
    }

}
