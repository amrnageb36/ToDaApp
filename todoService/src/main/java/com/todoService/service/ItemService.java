package com.todoService.service;

import com.todoService.model.Items;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    List<Items> findAll(int id);
    void addNewItem(Items newItem);
    void deleteItem(Long id);
    Items updateItem(Items item, Long id);
    Items searchByTitle(String title);
    Optional<Items> findById(Long id);
}
