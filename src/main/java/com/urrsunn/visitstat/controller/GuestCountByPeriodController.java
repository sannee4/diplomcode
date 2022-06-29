package com.urrsunn.visitstat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urrsunn.visitstat.entity.StayAddress;
import com.urrsunn.visitstat.model.GuestRequest;
import com.urrsunn.visitstat.service.DateFormatter;
import com.urrsunn.visitstat.service.GuestService;
import com.urrsunn.visitstat.service.impl.DateUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/v1")
@Getter
@Setter
@RequiredArgsConstructor
@Slf4j
public class GuestCountByPeriodController {
    private final GuestService guestService;
    private final ObjectMapper objectMapper;
    private final DateFormatter dateFormatter;

    @GetMapping("/guestCountByPeriod")
    public ModelAndView guestCountByPeriod() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("guestCountByPeriod");
        modelAndView.addObject("guestRequest", new GuestRequest());
        modelAndView.addObject("days", DateUtils.days);
        modelAndView.addObject("months", DateUtils.months);
        modelAndView.addObject("years", DateUtils.years);
        return modelAndView;
    }

    @PostMapping("/guestCountByPeriod")
    public String guestCountByPeriod(@ModelAttribute("guestRequest") GuestRequest guestRequest, Model model) {
        List<StayAddress> guestList = new ArrayList<>();
        try {
            guestList = guestService.questCountByPeriod(
                    dateFormatter.getDateByParams(guestRequest.getDayFrom(), guestRequest.getMonthFrom(), guestRequest.getYearFrom()),
                    dateFormatter.getDateByParams(guestRequest.getDayTo(), guestRequest.getMonthTo(), guestRequest.getYearTo()));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        model.addAttribute("countGuests", guestList.size());
        try {
            model.addAttribute("fullPathByGuestList", objectMapper.writeValueAsString(guestList.stream().map(StayAddress::getFullPath).collect(Collectors.toList())));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return "guestCountByDateResponse";
    }

}
