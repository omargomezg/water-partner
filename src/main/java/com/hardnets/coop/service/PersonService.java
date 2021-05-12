package com.hardnets.coop.service;

import com.hardnets.coop.dto.request.FilterDto;

import java.util.List;

public interface PersonService<T, C> {

    T update(T entity);

    List<T> getUsers(FilterDto filter);

    T getByRut(String rut);

    T create(C entity) throws Exception;

    T updatePassword(String rut, String password);

}
