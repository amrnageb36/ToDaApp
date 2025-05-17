package com.todoService.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int id;

    @NotBlank(message = "Title must not be blank")
    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int user_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="item_details_id")
    @NotNull(message = "Item details must not be null")
    private Item_details item_details_id;

    public Items() {
    }

    public Items(Item_details item_details_id, int user_id, String title) {
        this.item_details_id = item_details_id;
        this.user_id = user_id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Item_details getItem_details_id() {
        return item_details_id;
    }

    public void setItem_details_id(Item_details item_details_id) {
        this.item_details_id = item_details_id;
    }
}
