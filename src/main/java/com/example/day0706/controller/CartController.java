package com.example.day0706.controller;

import com.example.day0706.domain.*;
import com.example.day0706.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartItemRepository cartitemRepository;

    // 장바구니에 상품을 담는 로직
    @PostMapping(value = "/user/cart")
    public @ResponseBody
    ResponseEntity order(@RequestBody @Valid CartItemDto cartItemDto, BindingResult bindingResult, @AuthenticationPrincipal Member member){

        if(bindingResult.hasErrors()){ // cartItemDto 객체에 데이터 바인딩 시 에러있는지 검사
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }

            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        Long cartItemId;

        try {
            cartItemId = cartService.addCart(member,cartItemDto); //dto -> entity
        } catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST); // 장바구니에 잘 안담겼으면 404
        }

        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK); // 장바구니에 상품이 잘 담기면 200
    }

    // 유저 이메일을 받아와서, 카트 데이터들을 받아온다
    // 장바구니 get 페이지
    @GetMapping(value = "/user/cartPage")
    public ModelAndView orderHist(@AuthenticationPrincipal Member member, ModelAndView model){
        List<CartDetailDto> cartDetailList = cartService.getCartList(member);
        model.addObject("cartItems", cartDetailList);
        model.addObject("member" , member);
        model.setViewName("cartPage");
        return model;
    }

    // 장바구니 상품의 개수 수정 patch
    @PatchMapping(value = "/user/cartItem/{id}/{cartItemId}")
    public @ResponseBody ResponseEntity updateCartItem(@PathVariable("id") Long memberId, @PathVariable("cartItemId") Long cartItemId, int count, @AuthenticationPrincipal Member member){

        if(count <= 0){ // 수량 0개 이하로 요청할 경우 오류 ㄲ
            return new ResponseEntity<String>("최소 1개 이상 담아주세요", HttpStatus.BAD_REQUEST);
        } else if(!cartService.validateCartItem(member,cartItemId)){ // cartService 에서 검증 로직 발동!
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        cartService.updateCartItemCount(cartItemId, count); // 다 되면은 수정
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK); // 응답 리턴
    }

    // 장바구니에서 삭제할 요소 제거 delete
    @DeleteMapping(value = "/user/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity deleteCartItem(@PathVariable("cartItemId") Long cartItemId, @AuthenticationPrincipal Member member){

        if(!cartService.validateCartItem(member, cartItemId)){// cartService 에서 검증 로직 발동!
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        cartService.deleteCartItem(cartItemId); // 다 되면은 삭제
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK); // 응답 리턴
    }
}
