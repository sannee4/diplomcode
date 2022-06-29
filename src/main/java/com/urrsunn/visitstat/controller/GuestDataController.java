package com.urrsunn.visitstat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urrsunn.visitstat.dto.GuestDataDto;
import com.urrsunn.visitstat.service.GuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1")
@Slf4j
public class GuestDataController {
    private final GuestService guestService;
    private final ObjectMapper objectMapper;

    @PostMapping("/saveData")
    ResponseEntity<String> saveData(String guestData) {
        log.info("getting request: " + guestData);
        try {
            List<GuestDataDto> guestDataDtoList = Arrays.asList(objectMapper.readValue(guestData, GuestDataDto[].class));
            guestService.saveData(guestDataDtoList);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body("Data saved");
    }
}