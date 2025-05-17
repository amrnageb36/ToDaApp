package com.userService.repository;

import com.userService.entity.Token;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokenRepository extends JpaRepository<Token,Integer> {

    Token findByToken(String token);

    @Modifying
    @Transactional
    @Query("DELETE FROM Token t WHERE t.user.id = ?1")
    void deleteTokenByUserId(int userId);
}
