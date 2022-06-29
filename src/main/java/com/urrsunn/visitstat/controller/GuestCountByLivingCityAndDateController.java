package com.urrsunn.visitstat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urrsunn.visitstat.model.Guest;
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

import static com.urrsunn.visitstat.controller.GuestCountByDateWithHotelController.EMPTY_STRING_VALUE;

@Controller
@RequestMapping("/v1")
@Getter
@Setter
@RequiredArgsConstructor
@Slf4j
public class GuestCountByLivingCityAndDateController {
    private final GuestService guestService;
    private final ObjectMapper objectMapper;
    private final DateFormatter dateFormatter;

    @GetMapping({"/guestCountByLivingCityAndDate"})
    public ModelAndView guestCountByLivingCityAndDate() {
        Guest guest = new Guest();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("guestCountByLivingCityAndDate");
        modelAndView.addObject("guestRequest", new GuestRequest());
        modelAndView.addObject("guest", guest);
        modelAndView.addObject("days", DateUtils.days);
        modelAndView.addObject("months", DateUtils.months);
        modelAndView.addObject("years", DateUtils.years);
        return modelAndView;
    }

    @PostMapping("/guestCountByLivingCityAndDate")
    public String guestCountByLivingCityAndDate(@ModelAttribute("guestRequest") GuestRequest guestRequest, Model model) {
        String result;
        try {
            if (guestRequest.getCity() == null)
                guestRequest.setCity(EMPTY_STRING_VALUE);
            result = guestService.guestCountByLivingCityAndDate(guestRequest.getCity(), dateFormatter.getDateByParams(guestRequest.getDayFrom(), guestRequest.getMonthFrom(), guestRequest.getYearFrom()));
        } catch (Exception e) {
            log.error(e.getMessage());
            result = EMPTY_STRING_VALUE;
        }

        model.addAttribute("result", result);
        return "guestCountByLivingCityAndDate";
    }

}
