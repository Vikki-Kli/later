package org.example.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

    Item getById(Long id);
    Item save(Item item);
    void deleteById(Long id);
    List<Item> findAllByOwner_id(Long id);
    List<Item> findAllByDescriptionContaining(String s);
}
