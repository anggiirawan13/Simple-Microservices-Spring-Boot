package com.anggi.irawan.springcloudcifservice.dtos.response;

import lombok.Data;

@Data
public class MCifAddressResponse {

    private String id;
    private MCifResponse cifId;
    private String address;
}
