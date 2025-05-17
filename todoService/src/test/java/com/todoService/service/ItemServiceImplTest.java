package com.todoService.service;

import com.todoService.excepionHandling.ItemAlreadyExistException;
import com.todoService.model.Item_details;
import com.todoService.model.Items;
import com.todoService.model.Priority;
import com.todoService.model.Status;
import com.todoService.repository.ItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock private ItemRepository itemRepository;
    @InjectMocks
    private ItemServiceImpl itemServiceTest;

    @BeforeEach
    void setUp() {
        itemServiceTest=new ItemServiceImpl(itemRepository);
    }

    /*@Test
    void checkFindAll() {
        itemServiceTest.findAll();

        verify(itemRepository).findAll();
    }*/

    @Test
    void checkAddNewItem() {
        Items item = new Items(new Item_details("test", Priority.HIGH, Status.COMPLETED,new Date()),1,"testing item");

        itemServiceTest.addNewItem((item));
        ArgumentCaptor<Items> itemArgumentCaptor = ArgumentCaptor.forClass(Items.class);

        verify(itemRepository).save(itemArgumentCaptor.capture());

        Items captorItem = itemArgumentCaptor.getValue();

        assertThat(captorItem).isEqualTo(item);
    }

    @Test
    void addNewItemWillThrow() {
        Items item = new Items(new Item_details("test", Priority.HIGH, Status.COMPLETED,new Date()),1,"testing item");

        given(itemRepository.findByTitle(item.getTitle()))
                .willReturn(item);
        assertThatThrownBy(()->itemServiceTest.addNewItem(item)).isInstanceOf(ItemAlreadyExistException.class)
                .hasMessageContaining(String.format("Item with title = %s already exists", item.getTitle()));

        verify(itemRepository,never()).save(any());
    }

    @Test
    @Disabled
    void deleteItem() {
    }

    @Test
    @Disabled
    void updateItem() {
    }

    @Test
    @Disabled
    void searchByTitle() {
    }

    @Test
    @Disabled
    void findById() {
    }
}