package com.todoService.controller;

import com.todoService.model.Items;
import com.todoService.service.ItemServiceImpl;
import com.todoService.service.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.todoService.service.restTemplateService;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class Controller {
    @Autowired
    private ItemServiceImpl itemService;

    @Autowired
    private restTemplateService restTemplateService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping()
    public List<Items> getAllItems(@RequestHeader("Authorization") String token) {
        String pureToken=token.replace("Bearer ", "");
        String email = jwtUtil.extractSubject(pureToken);
        boolean checkToken = restTemplateService.checkToken(pureToken,email);
        if(checkToken==false) {
            throw new RuntimeException("invalid token");
        }
        int userId = restTemplateService.getUserId(pureToken,email);
        return itemService.findAll(userId);
    }

    @PostMapping()
    public void addNewItem(@Valid @RequestBody Items newItem,@RequestHeader String token) {
        String pureToken=token.replace("Bearer ", "");
        String email = jwtUtil.extractSubject(pureToken);
        boolean checkToken = restTemplateService.checkToken(pureToken,email);
        if(checkToken==false) {
            throw new RuntimeException("invalid token");
        }
        int userId = restTemplateService.getUserId(pureToken,email);
        Items theNewItem = new Items(newItem.getItem_details_id(),userId,newItem.getTitle());
        itemService.addNewItem(theNewItem);
    }


    @DeleteMapping(path = "/{id}")
    public void deleteById(@PathVariable(value = "id") Long id,@RequestHeader String token) {
        String pureToken=token.replace("Bearer ", "");
        String email = jwtUtil.extractSubject(pureToken);
        boolean checkToken = restTemplateService.checkToken(pureToken,email);
        if(checkToken==false) {
            throw new RuntimeException("invalid token");
        }
        int userId = restTemplateService.getUserId(pureToken,email);
        Optional<Items> item=itemService.findById(id);
        if(userId!= item.get().getUser_id()){
            throw new RuntimeException("access denied");
        }
        itemService.deleteItem(id);
    }

    @PutMapping("/{id}")
    public Items update(@PathVariable Long id, @RequestBody Items item,@RequestHeader String token) {
        String pureToken=token.replace("Bearer ", "");
        String email = jwtUtil.extractSubject(pureToken);
        boolean checkToken = restTemplateService.checkToken(pureToken,email);
        if(checkToken==false) {
            throw new RuntimeException("invalid token");
        }
        int userId = restTemplateService.getUserId(pureToken,email);
        if(userId!= item.getUser_id()){
            throw new RuntimeException("access denied");
        }
        return itemService.updateItem(item, id);
    }

    @GetMapping("/search")
    public Items findByTitle(@RequestParam String title,@RequestHeader String token) {
        String pureToken=token.replace("Bearer ", "");
        String email = jwtUtil.extractSubject(token);
        boolean checkToken = restTemplateService.checkToken(token,email);
        if(checkToken==false) {
            throw new RuntimeException("invalid token");
        }
        int userId = restTemplateService.getUserId(pureToken,email);
        Items item = itemService.searchByTitle(title);
        if(item.getUser_id()!=userId){
            throw new RuntimeException("access denied");
        }
        return item;
    }


}
