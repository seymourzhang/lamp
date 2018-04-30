package com.on.GWData.repository;

import com.on.GWData.Entity.OnADealData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OnADealDataRepository extends JpaRepository<OnADealData, Long> {

    @Query(value = "select u.*, ? imei from on_a_deal_data u where u.imei is null order by u.id desc limit 200", nativeQuery = true)
    List<OnADealData> findAllByImei(String imei);
}
