package com.anggi.irawan.springcloudcifservice.services;

import com.anggi.irawan.springcloudcifservice.dtos.request.MCifAddressRequest;
import com.anggi.irawan.springcloudcifservice.dtos.response.DefaultResponse;

public interface MCifAddressService {

    DefaultResponse addMCifAddress(MCifAddressRequest request);

    DefaultResponse updateMCifAddress(String id, MCifAddressRequest request);

    DefaultResponse allDataMCifAddress();

    DefaultResponse findByIdMCifAddress(String id);

    void deleteMCifAddress(String id);
}
