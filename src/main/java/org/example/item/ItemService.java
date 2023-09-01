package org.example.item;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.AccessException;
import org.example.exception.NoSuchItemException;
import org.example.user.User;
import org.example.user.UserRepository;
import org.example.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
@Slf4j
public class ItemService {

    private ItemRepository itemRepository;
    private UserRepository userRepository;
    private UserService userService;

    public ItemService(ItemRepository itemRepository, UserRepository userRepository, UserService userService) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public Collection<ItemDto> findAll(long userId) {
        userService.checkUserById(userId);
        return itemRepository.findAllByOwner_id(userId).stream().map(ItemMapper::pojoToDto).toList();
    }

    public ItemDto getItem(long id) {
        checkItemById(id);
        return ItemMapper.pojoToDto(itemRepository.findById(id).get());
    }

    public Collection<ItemDto> search(String text) {
        return itemRepository.findByDescriptionContainingIgnoreCase(text).stream().map(ItemMapper::pojoToDto).toList();
    }

    public ItemDto createItem(ItemDto itemDto, long userId) {
        userService.checkUserById(userId);

        Item item = ItemMapper.dtoToPojo(itemDto);
        User owner = userRepository.findById(userId).get();
        item.setOwner(owner);

        Item savedItem = itemRepository.save(item);
        log.info("Создано " + savedItem);
        return ItemMapper.pojoToDto(savedItem);
    }

    public ItemDto editItem(ItemDto itemDto, long id, long userId) {
        checkItemById(id);
        userService.checkUserById(userId);

        User owner = userRepository.findById(userId).get();
        Item oldItem = itemRepository.findById(id).get();
        if (!oldItem.getOwner().equals(owner)) throw new AccessException("Редактировать ресурс может только его автор");

        Item item = ItemMapper.dtoToPojo(itemDto);
        item.setOwner(owner);
        item.setId(id);

        Item savedItem = itemRepository.save(item);
        log.info("Изменено " + savedItem);
        return ItemMapper.pojoToDto(savedItem);
    }

    public void deleteItem(long id, long userId) {
        userService.checkUserById(userId);
        checkItemById(id);

        User owner = userRepository.findById(userId).get();
        Item oldItem = itemRepository.findById(id).get();
        if (!oldItem.getOwner().equals(owner)) throw new AccessException("Удалить ресурс может только его автор");

        itemRepository.deleteById(id);
        log.info("Удалена ссылка " + id);
    }

    public boolean checkUrl(long itemId) {
        checkItemById(itemId);
        return itemRepository.checkUrl(itemRepository.findById(itemId).get());
    }

    public void checkItemById(long id) {
        if (!itemRepository.existsById(id)) {
            throw new NoSuchItemException("Не найдена ссылка " + id);
        }
    }
}
