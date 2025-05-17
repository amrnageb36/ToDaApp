package com.todoService.service;

import com.todoService.excepionHandling.ItemAlreadyExistException;
import com.todoService.excepionHandling.ItemNotFoundException;
import com.todoService.model.Items;
import com.todoService.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    private restTemplateService restTemplateService;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Items> findAll(int id) {
        return itemRepository.findAllByUserId(id);
    }

    @Override
    @Transactional
    public void addNewItem(Items newItem) {

        Items item = itemRepository.findByTitle(newItem.getTitle());
            if (item!=null) {
                throw new ItemAlreadyExistException(String.format("Item with title = %s already exists", item.getTitle()));
            }
                itemRepository.save(newItem);
    }
    @Override
    @Transactional
    public void deleteItem(Long id) {

        Optional<Items> existingItem = itemRepository.findById(id);
        if (existingItem.isEmpty()) {
            throw new ItemNotFoundException(String.format("Item with id = %s not found", id));
        }
        itemRepository.deleteById(id);
    }


    @Override
    @Transactional
    public Items updateItem(Items item, Long id) {

        Items existingItem = itemRepository.findById(id).orElse(null);

        if (existingItem == null) {
            throw new ItemNotFoundException(String.format("Item with id = %s not found", id));
        }

        existingItem.setTitle(item.getTitle());
        existingItem.setItem_details_id(item.getItem_details_id());
        existingItem.setUser_id(item.getUser_id());

        return itemRepository.save(existingItem);
    }


    @Override
    public Items searchByTitle(String title) {

        Items item = itemRepository.findByTitle(title);
        if (item != null) {
            return  item;
        } else {
            throw new ItemNotFoundException(String.format("Item with title = %s not found", title));
        }

    }

    @Override
    public Optional<Items> findById(Long id) {
        Optional<Items> item = itemRepository.findById(id);
        if (item.isPresent()) {
            return  item;
        } else {
            throw new ItemNotFoundException(String.format("Item with id = %s not found", id));
        }
    }


}
