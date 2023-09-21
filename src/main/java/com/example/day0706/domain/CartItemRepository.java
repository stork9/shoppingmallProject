package com.example.day0706.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // 카트 아이디와 아이템 아이디를 이용하여 카트 아이템 레퍼지토리의 엔티티 조회
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);
    List<CartItem> findByCartId(Long cartId);

    // 장바구니 페이지에 전달할 CartDetailDto 를 쿼리로 조회해서 CartDetailDtoList 에 담아줌
    @Query("select new com.example.day0706.domain.CartDetailDto(ci.id, i.title, i.price, ci.count, i.id, i.filePath) " +
            "from CartItem ci " +
            "join Item i " +
            "on ci.item = i " +
            "where ci.cart.id = :cartId " +
            "and i.id = ci.item.id"
    )
    List<CartDetailDto> findCartDetailDtoList(Long cartId);


}
