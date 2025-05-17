package com.todoService.repository;

import com.todoService.model.Item_details;
import com.todoService.model.Items;
import com.todoService.model.Priority;
import com.todoService.model.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.Date;
@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepositoryTest;

    @AfterEach
    void tearDown() {
        itemRepositoryTest.deleteAll();
    }

    @Test
    void CheckfindByTitleWhenTitleExists() {
        // given
        Items item = new Items(new Item_details("test", Priority.HIGH, Status.COMPLETED,new Date()),1,"testing item");
        itemRepositoryTest.save(item);

        //when
       Items exists = itemRepositoryTest.findByTitle("testing item");
        //then
        assertThat(exists.getTitle()).isEqualTo("testing item");
    }

    @Test
    public void CheckfindByTitleWhenTitleIsNotExist() {

        Items item = itemRepositoryTest.findByTitle("testing item");

        assertThat(item).isNull();
    }


}