package com.example.day0706.controller;


import com.example.day0706.domain.Item;
import com.example.day0706.domain.ItemRequestDto;
import com.example.day0706.domain.Member;
import com.example.day0706.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/admin/postItem")
    public String postItem(){
        return "postItem";
    }

    @PostMapping("/admin/item")
    public String saveItem(@ModelAttribute ItemRequestDto itemRequestDto){
        itemService.createItem(itemRequestDto);
        return "redirect:/api/itemList/0";
    }

    @GetMapping("/api/itemList/{page}")
    public String listItem(Model model,@PathVariable int page){
        Page<Item> itemList = itemService.allItem(page);
        model.addAttribute("items",itemList);
        page+=1;
        int totalPage = itemList.getTotalPages();
        int startPage = Math.max(page-4,1);
        int endPage = Math.min(page+4,totalPage);
//        model.addAttribute("totalPage",totalPage);
        model.addAttribute("nowPage",page);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("pageType","all");
        return "index";
    }

    @GetMapping("/api/itemList/{category}/{page}")
    public String categoryList(Model model,@PathVariable String category, @PathVariable int page){
        Page<Item> itemList = itemService.categoryItem(category,page);
        model.addAttribute("items",itemList);
        page+=1;
        int totalPage = itemList.getTotalPages();
        int startPage = Math.max(page-4,1);
        int endPage = Math.min(page+4,totalPage);
//        model.addAttribute("totalPage",totalPage);
        model.addAttribute("nowPage",page);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("category",category);
        model.addAttribute("pageType","all");
        return "categoryList";
    }

    @GetMapping("/api/itemOne/{id}")
    public String itemOne(@AuthenticationPrincipal Member member, Model model, @PathVariable Long id){
        Item item = itemService.oneItem(id);
        model.addAttribute("member", member);
        model.addAttribute("item",item);
        return "itemOne";
    }

    @GetMapping("/api/searchList/{page}")
    public String searchList(Model model,@RequestParam("itemName") String itemName, @PathVariable int page){
        Page<Item> itemList = itemService.searchItem(itemName,page);
        model.addAttribute("items",itemList);
        page+=1;
        int totalPage = itemList.getTotalPages();
        int startPage = Math.max(page-4,1);
        int endPage = Math.min(page+4,totalPage);
        //아무 상품도 없으면 0페이지만 나오도록 설정
        if(totalPage==0) endPage=0;
        model.addAttribute("nowPage",page);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("pageType","search");
        model.addAttribute("itemName",itemName);
        return "index";
    }
}
