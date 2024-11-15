package com.tpp.tpplab3.service;

import com.tpp.tpplab3.models.MenuItems;
import com.tpp.tpplab3.repository.MenuItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemsRepository menuItemRepository;

    public List<MenuItems> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    public Optional<MenuItems> findMenuItemById(Integer id) {
        return menuItemRepository.findById(id);
    }

    public void saveMenuItem(MenuItems menuItem) {
        menuItemRepository.save(menuItem);
    }

    public void updateMenuItem(MenuItems updatedMenuItem) {
        Optional<MenuItems> existingMenuItemOpt = menuItemRepository.findById(updatedMenuItem.getMenuItemId());

        if (existingMenuItemOpt.isPresent()) {
            MenuItems existingMenuItem = existingMenuItemOpt.get();
            existingMenuItem.setName(updatedMenuItem.getName());
            existingMenuItem.setPrice(updatedMenuItem.getPrice());
            existingMenuItem.setCategory(updatedMenuItem.getCategory());
            menuItemRepository.save(existingMenuItem);
        }
    }

    public void deleteMenuItemById(Integer id) {
        menuItemRepository.deleteById(id);
    }
}
