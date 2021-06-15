package com.hardnets.coop.service;

import com.hardnets.coop.model.entity.CompanyEntity;
import com.hardnets.coop.model.entity.GenericAttributeEntity;
import com.hardnets.coop.model.entity.UserEntity;
import com.hardnets.coop.repository.GenericAttributeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Omar Gómez - omar.fdo.gomez@gmail.com
 */
@Log4j2
@AllArgsConstructor
@Service
public class CompanyService {

    private final GenericAttributeRepository genericAttributeRepository;

    public List<GenericAttributeEntity> getAllByCompany() {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return genericAttributeRepository.findAllByCompany(user.getCompany());
    }

    public void saveAttributes(List<GenericAttributeEntity> attributes) {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        attributes.forEach(attribute -> genericAttributeRepository
                .findByCompanyIdAndAttributeKey(user.getCompany().getId(), attribute.getKey())
                .ifPresentOrElse(
                        x -> updateAttribute(x, attribute),
                        () -> createAttribute(attribute, user.getCompany())
                ));
    }

    private void createAttribute(GenericAttributeEntity attribute, CompanyEntity company) {
        attribute.setCompany(company);
        genericAttributeRepository.save(attribute);
    }

    private void updateAttribute(GenericAttributeEntity dbAttribute, GenericAttributeEntity attribute) {
        dbAttribute.setValue(attribute.getValue());
        genericAttributeRepository.save(dbAttribute);
    }
}
