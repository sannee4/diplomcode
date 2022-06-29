package com.urrsunn.visitstat.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Data
public class GuestRequestDto {
    private final List<GuestDataDto> guestDataDtoList;
}