package com.example.day0706.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailDto extends Timestamped{

    private Long cartItemId; //장바구니 상품 아이디

    private String title; //상품명

    private int price; //상품 금액

    private int count; //수량

    private Long itemId;

    private String filePath;



}
