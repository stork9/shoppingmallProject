package com.example.day0706.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
    Page<Item> findByCategory(String category, PageRequest pageRequest);
    Page<Item> findByTitleContainingIgnoreCaseOrDescContainingIgnoreCaseOrShortDescContainingIgnoreCase(String title,String desc,String shortDesc,PageRequest pageRequest);

}
