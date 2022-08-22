package com.anggi.irawan.springcloudcifservice.dtos.response;

import lombok.Data;

import java.math.BigInteger;

@Data
public class MCifWorkResponse {

    private String id;
    private MCifResponse cifId;
    private String address;
    private BigInteger penghasilan;
}
