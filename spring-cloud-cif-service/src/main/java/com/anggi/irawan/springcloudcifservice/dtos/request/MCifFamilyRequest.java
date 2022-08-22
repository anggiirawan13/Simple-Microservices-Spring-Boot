package com.anggi.irawan.springcloudcifservice.dtos.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MCifFamilyRequest {

    private String cifId;
    private String type;
}
