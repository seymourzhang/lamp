package com.on.GWData.repository;

import com.on.GWData.Entity.OnAGWData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OnAGWDataRepository extends CrudRepository<OnAGWData, Long> {

    @Query("select o from OnAGWData o")
    List<OnAGWData> findByOrder(Pageable pageable);


}
