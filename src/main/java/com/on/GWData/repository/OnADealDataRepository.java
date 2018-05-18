package com.on.GWData.repository;

import com.on.GWData.Entity.OnADealData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.List;

public interface OnADealDataRepository extends JpaRepository<OnADealData, Long> {

    @Query(value = "select u.* from on_a_deal_data u where u.imei = ? order by u.id desc limit 5000", nativeQuery = true)
    List<OnADealData> findAllByImei(String imei);


    /*@Procedure(name="on_a_TransGWData", outputParameterName = "_res")
    String on_a_TransGWData();*/

    @Query(value = "call on_a_TransGWData()", nativeQuery = true)
    String on_a_TransGWData();
}
