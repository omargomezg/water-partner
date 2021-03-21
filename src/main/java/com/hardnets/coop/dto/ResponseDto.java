package com.hardnets.coop.dto;

import lombok.Data;

@Data
public class ResponseDto {
    private Boolean status;
    private String message;
    private Object payLoad;

    public ResponseDto() {
        this.status = true;
    }
}
