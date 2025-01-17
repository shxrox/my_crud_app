package com.examp.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService service;

    @GetMapping
    public String listItems(Model model) {
        model.addAttribute("items", service.findAll());
        return "item-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("item", new Item());
        return "item-form";
    }

    @PostMapping
    public String saveItem(@ModelAttribute("item") Item item) {
        service.save(item);
        return "redirect:/items";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("item", service.findById(id));
        return "item-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/items";
    }

    @GetMapping("/search")
    public String searchItems(@RequestParam("name") String name, Model model) {
        model.addAttribute("items", service.findAll().stream()
                .filter(item -> item.getName().toLowerCase().contains(name.toLowerCase()))
                .toList());
        return "item-list";
    }
}
