package com.hardnets.coop.service;

import com.hardnets.coop.model.constant.RoleEnum;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author Omar Gómez - omar.fdo.gomez@gmail.com
 */
@Service
public class ProfileService {

    /**
     * Obtiene todos los roles del sistema
     *
     * @return una lista de roles
     */
    public List<RoleEnum> getAll() {
        return Arrays.asList(RoleEnum.values());
    }
}
