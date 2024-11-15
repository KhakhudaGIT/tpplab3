package com.tpp.tpplab3.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menu_items")
public class MenuItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer menuItemId;

    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[\\p{L}0-9 ]+$", message = "Name can only contain letters, numbers, and spaces")
    private String name;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @NotBlank(message = "Category is required")
    @Pattern(regexp = "^[\\p{L}0-9 ]+$", message = "Category can only contain letters, numbers, and spaces")
    private String category;

    @OneToMany(mappedBy = "menuItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItems> orderItems = new ArrayList<>();

    // Гетери та сетери
    public Integer getMenuItemId() {
        return menuItemId;
    }
    public void setMenuItemId(Integer menuItemId) {
        this.menuItemId = menuItemId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public List<OrderItems> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }
}
