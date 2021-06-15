package com.hardnets.coop.controller;

import com.hardnets.coop.model.constant.RoleEnum;
import com.hardnets.coop.model.entity.BillEntity;
import com.hardnets.coop.model.entity.DropDownListEntity;
import com.hardnets.coop.model.entity.GenericAttributeEntity;
import com.hardnets.coop.model.entity.ItemEntity;
import com.hardnets.coop.repository.ListRepository;
import com.hardnets.coop.service.CompanyService;
import com.hardnets.coop.service.ItemCalculationService;
import com.hardnets.coop.service.ProfileService;
import com.hardnets.coop.service.SaleDocumentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Omar Gómez - omar.fdo.gomez@gmail.com
 */
@RestController
@AllArgsConstructor
@RequestMapping("/v1/configuration")
public class ConfigurationController {

    private final ListRepository listRepository;
    private final ProfileService profileService;
    private final CompanyService companyService;
    private final ItemCalculationService itemCalculationService;
    private final SaleDocumentService<BillEntity> saleDocumentService;

    @GetMapping("")
    public List<DropDownListEntity> getAll(@PathVariable String type) {
        return listRepository.findAllByDropDownListType(type.toUpperCase(Locale.ROOT)).orElse(new ArrayList<>());
    }

    @GetMapping("/role")
    public List<RoleEnum> getAllRoles() {
        return profileService.getAll();
    }

    @GetMapping("/attribute")
    public List<GenericAttributeEntity> getAttributes() {
        return companyService.getAllByCompany();
    }

    @GetMapping("/test-libre-dte")
    public ResponseEntity<Boolean> testLibreDte() {
        saleDocumentService.emitDocumentTaxElectronic();
        return ResponseEntity.ok(true);
    }

    @PostMapping("/attribute")
    public ResponseEntity<Boolean> saveAttributes(@RequestBody List<GenericAttributeEntity> genericAttribute) {
        companyService.saveAttributes(genericAttribute);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/item")
    public List<ItemEntity> getAllItems() {
        return itemCalculationService.findAll();
    }
}
