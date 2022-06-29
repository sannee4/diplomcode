package com.urrsunn.visitstat.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "stay_address")
public class StayAddress {
    @Id
    @Column
    @GeneratedValue
    private Long id;
    @Column(name = "fullpath")
    private String fullPath;
    @Column
    private String house;

    private Integer guestCount;
    private String gender;
    private Integer age;
}
