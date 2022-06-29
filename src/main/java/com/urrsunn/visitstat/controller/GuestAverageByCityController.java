package com.urrsunn.visitstat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urrsunn.visitstat.model.Guest;
import com.urrsunn.visitstat.model.GuestRequest;
import com.urrsunn.visitstat.service.DateFormatter;
import com.urrsunn.visitstat.service.GuestService;
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
public class GuestAverageByCityController {
    private final GuestService guestService;
    private final ObjectMapper objectMapper;
    private final DateFormatter dateFormatter;

    @GetMapping({"/guestAverageByCity"})
    public ModelAndView guestAverageByCity() {
        Guest guest = new Guest();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("averageGuestByCity");
        modelAndView.addObject("guestRequest", new GuestRequest());
        modelAndView.addObject("guest", guest);
        return modelAndView;
    }

    @PostMapping("/guestAverageByCity")
    public String guestAverageByCity(@ModelAttribute("guestRequest") GuestRequest guestRequest, Model model) {
        Guest guest;
        try {
            if (guestRequest.getCity() == null)
                guestRequest.setCity(EMPTY_STRING_VALUE);
            guest = guestService.guestAverageByHotel(guestRequest.getCity(), "", "");
        } catch (Exception e) {
            log.error(e.getMessage());
            guest = new Guest();
        }

        model.addAttribute("gender", guest.getGender());
        model.addAttribute("age", guest.getAge());
        return "averageGuestByCity";
    }

}
