package com.anggi.irawan.springcloudcifservice.dtos.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@Builder
public class MCifWorkRequest {

    private String cifId;
    private String address;
    private BigInteger penghasilan;
}
