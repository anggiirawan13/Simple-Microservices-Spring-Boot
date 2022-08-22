package com.anggi.irawan.springcloudcifservice.dtos.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MCifRequest {

    private String idKtp;
    private String name;
    private String npwp;
    private String noTelephone;
    private String email;
    private String type;
}
