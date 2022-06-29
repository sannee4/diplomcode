package com.urrsunn.visitstat.repo;

import com.urrsunn.visitstat.entity.StayAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StayAddressRepository extends JpaRepository<StayAddress, Long> {

    @Query(value = "SELECT * FROM stay_address sa, (" +
            "SELECT stayaddress_id, arrivaldate FROM stat_info si) as si " +
            "WHERE id = stayaddress_id and fullpath is not null and fullpath != '' and si.arrivaldate = :date ",
            nativeQuery = true)
    List<StayAddress> getGuestCountByDate(@Param("date") String date);

    @Query(value = "SELECT * FROM stay_address sa, ( " +
            "SELECT stayaddress_id, arrivaldate, stayingdate " +
            "FROM stat_info si) as si " +
            "WHERE id = stayaddress_id and fullpath is not null and fullpath != '' and si.arrivaldate >= :dateFrom " +
            "and si.arrivaldate <= si.stayingdate and si.stayingdate <= :dateTo",
            nativeQuery = true)
    List<StayAddress> getGuestsCountByPeriod(@Param("dateFrom") String dateFrom, @Param("dateTo") String dateTo);

    @Query(value = "SELECT * FROM stay_address sa, (" +
            "SELECT stayaddress_id, arrivaldate " +
            "FROM stat_info si) as si " +
            "WHERE id = stayaddress_id and fullpath is not null and fullpath != '' and si.arrivaldate = :date " +
            "and fullpath like :city and fullpath like :street and house like :house",
            nativeQuery = true)
    List<StayAddress> guestsCountByDateWithHotel(@Param("date") String date, @Param("city") String city, @Param("street") String street, @Param("house") String house);

    @Query(value = "SELECT * FROM stay_address sa, (" +
            "SELECT stayaddress_id, arrivaldate, stayingdate " +
            "FROM stat_info si) as si " +
            "WHERE id = stayaddress_id and fullpath is not null and fullpath != '' and si.arrivaldate >= :dateFrom " +
            "and si.arrivaldate <= si.stayingdate and si.stayingdate <= :dateTo " +
            "and fullpath like :city and fullpath like :street and house like :house",
            nativeQuery = true)
    List<StayAddress> guestsCountByPeriodWithHotel(@Param("dateFrom") String dateFrom, @Param("dateTo") String dateTo, @Param("city") String city, @Param("street") String street, @Param("house") String house);

    @Query(value = "select sa.id, sa.fullpath, count(si.gender) guest_count, sa.house, si.gender gender, " +
            "substring( cast(avg(cast(SUBSTRING(si.birthdate, 1, 4)as Int))as Varchar), 1, 4) age " +
            "from stay_address sa, stat_info si " +
            "WHERE sa.id = si.stayaddress_id and fullpath is not null and fullpath != '' and fullpath like :city and fullpath like :street  and house like :house and si.gender != ''  and  si.birthdate != '' group by si.gender, sa.id, sa.fullpath, sa.house",
            nativeQuery = true)
    List<StayAddress> guestAverageByHotel(@Param("city") String city, @Param("street") String street, @Param("house") String house);

    @Query(value = "select count(*) id , count(*) age, count(*) fullpath, count(*) house, count(*) gender, count(*) guest_count " +
            "from stat_info " +
            "where livingcity in :fiasList and arrivaldate = :dateTo",
            nativeQuery = true)
    List<StayAddress> guestCountByLivingCityAndDate(@Param("fiasList") List<String> fiasList, @Param("dateTo") String dateTo);
}
