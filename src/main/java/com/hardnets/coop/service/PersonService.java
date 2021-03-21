package com.hardnets.coop.service;

import java.util.Collection;

public interface PersonService<T, C> {

    T update(T entity);

    Collection<T> getUsers();

    T getByRut(String rut);

    T create(C entity) throws Exception;

    T updatePassword(String rut, String password);

}
