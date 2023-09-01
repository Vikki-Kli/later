package org.example.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

    List<Item> findAllByOwner_id(Long id);
    List<Item> findByDescriptionContainingIgnoreCase(String s);
}
