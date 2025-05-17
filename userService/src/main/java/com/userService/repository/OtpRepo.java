package com.userService.repository;

import com.userService.entity.Otp;
import com.userService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface OtpRepo extends JpaRepository<Otp,Long> {

    @Query("SELECT p FROM Otp p WHERE  p.user =:user")
    Otp findByUser(@Param("user") User user);
}
