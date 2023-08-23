package org.example.item;

import org.example.exception.AccessException;
import org.example.user.User;
import org.example.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ItemService {

    private ItemRepository itemRepository;
    private UserRepository userRepository;

    public ItemService(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }
    public Collection<ItemDto> findAll(long userId) {
        return itemRepository.findAllByOwner_id(userId).stream().map(ItemMapper::pojoToDto).toList();
    }

    public ItemDto getItem(long id) {
        return ItemMapper.pojoToDto(itemRepository.getById(id));
    }

    public Collection<ItemDto> search(String text) {
        return itemRepository.findAllByDescriptionContaining(text).stream().map(ItemMapper::pojoToDto).toList();
    }

    public ItemDto createItem(ItemDto itemDto, long userId) {
        Item item = ItemMapper.dtoToPojo(itemDto);
        User owner = userRepository.getById(userId);
        item.setOwner(owner);
        return ItemMapper.pojoToDto(itemRepository.save(item));
    }

    public ItemDto editItem(ItemDto itemDto, long id, long userId) {
        User owner = userRepository.getById(userId);
        Item oldItem = itemRepository.getById(id);
        if (!oldItem.getOwner().equals(owner)) throw new AccessException("Редактировать ресурс может только его автор");
        Item item = ItemMapper.dtoToPojo(itemDto);
        item.setOwner(owner);
        item.setId(id);
        return ItemMapper.pojoToDto(itemRepository.save(item));
    }

    public void deleteItem(long id, long userId) {
        User owner = userRepository.getById(userId);
        Item oldItem = itemRepository.getById(id);
        if (!oldItem.getOwner().equals(owner)) throw new AccessException("Удалить ресурс может только его автор");
        itemRepository.deleteById(id);
    }

    public boolean checkUrl(long itemId) {
        return itemRepository.checkUrl(itemRepository.getById(itemId));
    }
}
