package com.userService.repository;

import com.userService.entity.User;
import jakarta.transaction.Transactional;
import org.hibernate.type.internal.UserTypeVersionJavaTypeWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Query(value="select u from User u where email = ?1")
    User findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value="DELETE FROM User u WHERE u.email = ?1")
    void deleteByEmail(String email);

}
