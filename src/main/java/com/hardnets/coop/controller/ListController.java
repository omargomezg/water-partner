package com.hardnets.coop.controller;

import com.hardnets.coop.model.entity.DropDownListEntity;
import com.hardnets.coop.repository.ListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Omar Gómez - omar.fdo.gomez@gmail.com
 */
@RestController
@RequestMapping("/v1/list")
public class ListController {

    @Autowired
    ListRepository listRepository;

    @GetMapping("/{type}")
    public List<DropDownListEntity> getAll(@PathVariable String type) {
        return listRepository.findAllByDropDownListType(type.toUpperCase(Locale.ROOT)).orElse(new ArrayList<>());
    }
}
