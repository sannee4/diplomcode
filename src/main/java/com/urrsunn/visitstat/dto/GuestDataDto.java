package com.urrsunn.visitstat.dto;

import com.urrsunn.visitstat.entity.StayAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestDataDto {
    private String fullPath;
    private String house;

    private String arrivalDate;
    private Long stayAddressId;
    private String birthDate;
    private String gender;
    private String livingCity;
    private StayAddress stayAddress;
}

