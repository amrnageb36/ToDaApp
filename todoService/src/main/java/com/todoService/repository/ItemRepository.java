package com.todoService.repository;


import com.todoService.model.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Items,Long> {
   @Query("select i from Items i where i.title = ?1")
    Items findByTitle(String title);

    @Query("SELECT i FROM Items i WHERE i.user_id = :user_id")
    List<Items> findAllByUserId(@Param("user_id") int user_id);

}
