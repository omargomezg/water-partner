package com.hardnets.coop.controller;

import com.hardnets.coop.model.dto.items.ItemDto;
import com.hardnets.coop.model.dto.items.ItemsDto;
import com.hardnets.coop.service.impl.ItemServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/item")
@RestController
public class ItemController {

    private final ItemServiceImpl itemService;

    @GetMapping
    public ResponseEntity<ItemsDto> getAll(@RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
        return ResponseEntity.ok(itemService.getAll(pageIndex, pageSize));
    }

    @PostMapping
    public ResponseEntity<ItemDto> create(@RequestBody ItemDto itemDto) {
        return new ResponseEntity<ItemDto>(itemService.create(itemDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> update() {
        return null;
    }

    @DeleteMapping
    public ResponseEntity<String> delete() {
        return null;
    }
}
