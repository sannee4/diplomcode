package com.urrsunn.visitstat.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GuestRequest {
    private String city;
    private String street;
    private String house;
    private String dayFrom;
    private String monthFrom;
    private String yearFrom;
    private String dayTo;
    private String monthTo;
    private String yearTo;
}
