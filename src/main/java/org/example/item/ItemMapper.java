package org.example.item;

public class ItemMapper {

    public static Item dtoToPojo(ItemDto dto) {
        Item pojo = new Item();
        pojo.setUrl(dto.getUrl());
        pojo.setDescription(dto.getDescription());
        return pojo;
    }

    public static ItemDto pojoToDto(Item pojo) {
        ItemDto dto = new ItemDto();
        dto.setUrl(pojo.getUrl());
        dto.setDescription(pojo.getDescription());
        dto.setUsername(pojo.getOwner().getName());
        return dto;
    }
}
