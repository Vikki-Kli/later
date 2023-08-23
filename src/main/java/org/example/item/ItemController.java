package org.example.item;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/items")
public class ItemController {

    private ItemService itemService;

    public ItemController(ItemService service){
        itemService = service;
    }

    @GetMapping()
    public Collection<ItemDto> findAll(@RequestHeader("X-Later-User-Id") Long userId) {
        return itemService.findAll(userId);
    }

    @GetMapping("/{id}")
    public ItemDto getItem(@PathVariable long id) {
        return itemService.getItem(id);
    }

    @GetMapping("/search")
    public Collection<ItemDto> search(@RequestParam("text") String text) {
        return itemService.search(text);
    }

    @PostMapping()
    public ItemDto createItem(@Valid @RequestBody ItemDto item, @RequestHeader("X-Later-User-Id") Long userId) {
        return itemService.createItem(item, userId);
    }

    @PatchMapping("/{id}")
    public ItemDto editItem(@Valid @RequestBody ItemDto item,
                            @PathVariable long id,
                            @RequestHeader("X-Later-User-Id") Long userId) {
        return itemService.editItem(item, id, userId);
    }

    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable long id, @RequestHeader("X-Later-User-Id") long userId) {
        itemService.deleteItem(id, userId);
        return "Ссылка " + id + " была удалена";
    }

    @GetMapping("/check/{id}")
    public Boolean checkUrl(@PathVariable long id) {
        return itemService.checkUrl(id);
    }

}
