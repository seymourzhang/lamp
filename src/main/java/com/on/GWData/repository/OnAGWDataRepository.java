package com.on.GWData.repository;

import com.on.GWData.Entity.OnAGWData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.List;

public interface OnAGWDataRepository extends JpaRepository<OnAGWData, Long> {

    @Query("select o from OnAGWData o")
    List<OnAGWData> findByOrder(Pageable pageable);

    @Procedure(name="on_a_TransGWData", outputParameterName = "_res")
    String on_a_TransGWData();

}
