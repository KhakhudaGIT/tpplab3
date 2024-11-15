package com.tpp.tpplab3.controllers;

import com.tpp.tpplab3.models.OrderItems;
import com.tpp.tpplab3.models.Orders;
import com.tpp.tpplab3.models.MenuItems;
import com.tpp.tpplab3.service.OrderItemService;
import com.tpp.tpplab3.service.OrderService;
import com.tpp.tpplab3.service.MenuItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/order-items")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MenuItemService menuItemService;

    @GetMapping
    public String listOrderItems(Model model) {
        model.addAttribute("orderItems", orderItemService.getAllOrderItems());
        return "order-items";
    }

    @GetMapping("/add")
    public String addOrderItemForm(Model model) {
        model.addAttribute("orderItem", new OrderItems());
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("menuItems", menuItemService.getAllMenuItems());
        return "add-order-item";
    }

    @PostMapping("/add")
    public String addOrderItem(@Valid @ModelAttribute OrderItems orderItem, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("orderItem", orderItem);
            model.addAttribute("orders", orderService.getAllOrders());
            model.addAttribute("menuItems", menuItemService.getAllMenuItems());
            return "add-order-item";
        }
        orderItemService.saveOrderItem(orderItem);
        return "redirect:/order-items";
}

    @GetMapping("/edit/{id}")
    public String editOrderItemForm(@PathVariable("id") Integer id, Model model) {
        Optional<OrderItems> orderItemOpt = orderItemService.findOrderItemById(id);
        if (orderItemOpt.isPresent()) {
            model.addAttribute("orderItem", orderItemOpt.get());
            model.addAttribute("orders", orderService.getAllOrders());
            model.addAttribute("menuItems", menuItemService.getAllMenuItems());
            return "edit-order-item";
        } else {
            return "redirect:/order-items";
        }
    }

    @PostMapping("/update/{id}")
public String updateOrderItem(
    @PathVariable("id") Integer id,
    @Valid @ModelAttribute OrderItems orderItem,
    BindingResult bindingResult, 
    @RequestParam("order.orderId") Integer orderId,
    @RequestParam("menuItem.menuItemId") Integer menuItemId, 
    Model model) {

    if (bindingResult.hasErrors()) {
        model.addAttribute("orderItem", orderItem);
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("menuItems", menuItemService.getAllMenuItems());
        return "edit-order-item";
    }

    Optional<Orders> order = orderService.findOrderById(orderId);
    Optional<MenuItems> menuItem = menuItemService.findMenuItemById(menuItemId);
    
    if (order.isPresent() && menuItem.isPresent()) {
        orderItem.setOrderItemId(id);
        orderItem.setOrder(order.get());
        orderItem.setMenuItem(menuItem.get());
        orderItemService.updateOrderItem(orderItem);
    }

    return "redirect:/order-items";
}


    @GetMapping("/delete/{id}")
    public String deleteOrderItem(@PathVariable("id") Integer id) {
        orderItemService.deleteOrderItemById(id);
        return "redirect:/order-items";
    }
}
