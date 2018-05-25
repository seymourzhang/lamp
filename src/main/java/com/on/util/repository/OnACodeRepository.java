package com.on.util.repository;

import com.on.util.Entity.OnACode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OnACodeRepository extends JpaRepository<OnACode, Long> {

    @Query(name = "select code from OnACode code where code.codeType = :codeType")
    List<OnACode> findAllByCodeType(@Param("codeType") String codeType);
}
