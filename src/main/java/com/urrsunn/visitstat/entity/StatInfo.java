package com.urrsunn.visitstat.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "stat_info")
public class StatInfo {
    @Id
    @Column
    @GeneratedValue
    private Long id;
    @Column(name = "arrivaldate")
    private Date arrivalDate;
    @Column(name = "stayingdate")
    private Long stayAdressId;
    @Column(name = "birthdate")
    private String birthDate;
    @Column
    private String gender;
    @Column(name = "livingcity")
    private String livingCity;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "stayaddress_id", referencedColumnName = "id")
    private StayAddress stayAddress;
}
