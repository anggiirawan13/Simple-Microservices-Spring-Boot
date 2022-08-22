package com.anggi.irawan.springcloudcifservice.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultResponse {

    private boolean success;
    private String messages;
    private Object data;
    private Object additionalEntiy;
}
