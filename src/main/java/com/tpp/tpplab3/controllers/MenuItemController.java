package com.tpp.tpplab3.controllers;

import com.tpp.tpplab3.models.MenuItems;
import com.tpp.tpplab3.service.MenuItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/menu-items")
public class MenuItemController {
    @Autowired
    private MenuItemService menuItemService;

    @GetMapping
    public String listMenuItems(Model model) {
        model.addAttribute("menuItems", menuItemService.getAllMenuItems());
        return "menu-items";
    }

    @GetMapping("/add")
    public String addMenuItemForm(Model model) {
        model.addAttribute("menuItem", new MenuItems());
        return "add-menu-item";
    }

    @PostMapping("/add")
    public String addMenuItem(@Valid @ModelAttribute("menuItem") MenuItems menuItem, BindingResult result) {
        if (result.hasErrors()) {
            return "add-menu-item";
        }
        menuItemService.saveMenuItem(menuItem);
        return "redirect:/menu-items";
    }

    @GetMapping("/edit/{id}")
    public String editMenuItemForm(@PathVariable("id") Integer id, Model model) {
        MenuItems menuItem = menuItemService.findMenuItemById(id).orElse(null);
        if (menuItem != null) {
            model.addAttribute("menuItem", menuItem);
            return "edit-menu-item";
        } else {
            return "redirect:/menu-items";
        }
    }

    @PostMapping("/update/{id}")
public String updateMenuItem(@PathVariable("id") Integer id, @Valid @ModelAttribute MenuItems menuItem, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
        model.addAttribute("menuItem", menuItem);
        return "edit-menu-item";
    }
    menuItem.setMenuItemId(id);
    menuItemService.updateMenuItem(menuItem);
    return "redirect:/menu-items";
}

    @GetMapping("/delete/{id}")
    public String deleteMenuItem(@PathVariable("id") Integer id) {
        menuItemService.deleteMenuItemById(id);
        return "redirect:/menu-items";
    }
}
