package com.on.user.repository;

import com.on.user.entity.OnAUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OnAUserRepository extends JpaRepository<OnAUser, Long> {

    @Query("select u from OnAUser u where user_code = :userCode and status = 1")
    OnAUser verifyUser(@Param("userCode") String userCOde);

}
