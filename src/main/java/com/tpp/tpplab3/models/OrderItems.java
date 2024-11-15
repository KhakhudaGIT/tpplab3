package com.tpp.tpplab3.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "order_items")
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItems menuItem;

    @Min(1)
    private Integer quantity;

    // Гетери та сетери
    public Integer getOrderItemId() {
        return orderItemId;
    }
    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }
    public Orders getOrder() {
        return order;
    }
    public void setOrder(Orders order) {
        this.order = order;
    }
    public MenuItems getMenuItem() {
        return menuItem;
    }
    public void setMenuItem(MenuItems menuItem) {
        this.menuItem = menuItem;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
