package com.anggi.irawan.springcloudcifservice.services;

import com.anggi.irawan.springcloudcifservice.dtos.request.MCifRequest;
import com.anggi.irawan.springcloudcifservice.dtos.response.DefaultResponse;

public interface MCifService {

    DefaultResponse addCif(MCifRequest request);

    DefaultResponse updateCif(String id, MCifRequest request);

    DefaultResponse listCif();

    DefaultResponse findByIdCif(String id);

    DefaultResponse deleteCif(String id);
}
