package com.on.mygirl.repository;

import com.on.mygirl.Entity.MyGirl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MyGirlReposity extends JpaRepository<MyGirl, Long> {

    @Query(value = "select count(*) from MyGirl where id = :id ", nativeQuery = true)
    int findById(@Param("id") int id);

    @Modifying
    @Query(value = "delete from my_girl where id = ?", nativeQuery = true)
    int deleteAllById(String id);

    @Query(value = "select u from MyGirl u where u.userId = :userId")
    List<MyGirl> findByUserId(@Param("userId") Long userId);
}
