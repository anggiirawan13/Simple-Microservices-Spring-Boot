package com.anggi.irawan.springcloudcifservice.services.impl;

import com.anggi.irawan.springcloudcifservice.dtos.request.MCifAddressRequest;
import com.anggi.irawan.springcloudcifservice.dtos.response.DefaultResponse;
import com.anggi.irawan.springcloudcifservice.dtos.response.MCifAddressResponse;
import com.anggi.irawan.springcloudcifservice.dtos.response.MCifResponse;
import com.anggi.irawan.springcloudcifservice.exceptions.BussinesException;
import com.anggi.irawan.springcloudcifservice.models.MCifAddressModel;
import com.anggi.irawan.springcloudcifservice.models.MCifModel;
import com.anggi.irawan.springcloudcifservice.repositories.MCifAddressRepository;
import com.anggi.irawan.springcloudcifservice.repositories.MCifRepository;
import com.anggi.irawan.springcloudcifservice.services.MCifAddressService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@CacheConfig(cacheNames = "cifAddressCache")
public class MCifAddressServiceImpl implements MCifAddressService {

    private MCifAddressRepository addressRepository;
    private MCifRepository mCifRepository;

    @SneakyThrows
    @Transactional
    @CacheEvict (cacheNames = "cifAddress", allEntries = true)
    @Override
    public DefaultResponse addMCifAddress(MCifAddressRequest request) {
        Optional<MCifModel> getDataCif = mCifRepository.findById(request.getCifId());
        if(getDataCif.isEmpty()){
            throw  new Exception("data tidak ditemukan");
        }

        MCifModel mcifResponse = getDataCif.get();

        MCifAddressModel cifAddressResponse = transformModelToRequest(request, mcifResponse);
        MCifAddressModel response = addressRepository.save(cifAddressResponse);

        MCifResponse addressResponse = new MCifResponse().transformCifResponseFromModel(mcifResponse);

        MCifAddressResponse transformResponse = transformAddressResponseToModel(response, addressResponse);

        return new DefaultResponse(true, "Add data success", transformResponse, null);
    }

    @SneakyThrows
    @Transactional
    @CacheEvict(cacheNames = "cifAddress", key = "#id", allEntries = true)
    @Override
    public DefaultResponse updateMCifAddress(String id, MCifAddressRequest request)
    {
        Optional<MCifModel> getCifData = mCifRepository.findById(request.getCifId());
        if(getCifData.isEmpty()){
            throw  new Exception("MCIF ID NOT FOUNDS");
        }

        MCifModel mCifFounds = getCifData.get();

        Optional<MCifAddressModel> findAddressById = addressRepository.findById(id);
        if(findAddressById.isEmpty()){
            throw new Exception("MCIF ADDRESS NOT FOUNDS");
        }

        findAddressById.get().setAddress(request.getAddress());
        findAddressById.get().setMcif(mCifFounds);

        MCifAddressModel savedDataNew = addressRepository.save(findAddressById.get());

        MCifResponse responseDariTable = new MCifResponse().transformCifResponseFromModel(mCifFounds);

        MCifAddressResponse transformResponse = transformAddressResponseToModel(savedDataNew, responseDariTable);

        return new DefaultResponse(true, "Update data success", transformResponse, null);
    }


    @SneakyThrows
    @Cacheable (cacheNames = "cifs")
    @Override
    public DefaultResponse allDataMCifAddress() {
        List<MCifAddressModel> findAllAddress = addressRepository.findAll();

        List<MCifAddressResponse> listAddress = new ArrayList<>();

        findAllAddress.forEach(data -> {
            MCifResponse cifResponse = new MCifResponse().transformCifResponseFromModel(data.getMcif());
            MCifAddressResponse cifAddressResponse = transformAddressResponseToModel(data, cifResponse);

            listAddress.add(cifAddressResponse);
        });

        return new DefaultResponse(true, "Get list cif data success", listAddress, null);
    }

    @SneakyThrows
    @Caching(evict = {@CacheEvict (cacheNames = "cifAddresss", key = "#id"), @CacheEvict(cacheNames = "cifAddress", allEntries = true)})
    @Transactional
    @Override
    public void deleteMCifAddress(String id) {
        Optional<MCifAddressModel> findMCifAddressById = addressRepository.findById(id);

        if(findMCifAddressById.isEmpty()){
            throw  new Exception("ID NOT FOUNDS");
        }

        addressRepository.deleteById(id);
    }

    @SneakyThrows
    @Cacheable(cacheNames = "cifAddresss", key = "#id", unless = "#result == null")
    @Transactional
    @Override
    public DefaultResponse findByIdMCifAddress(String id) {
        MCifAddressModel findByIdCif = addressRepository.findById(id)
                .orElseThrow(() -> new BussinesException("DATA NOT FOUNDS"));
        MCifAddressResponse response = new MCifAddressResponse();
        response.setId(findByIdCif.getId());
        response.setAddress(findByIdCif.getAddress());

        return new DefaultResponse(true, "Get cif data by id success", response, null);
    }

    private MCifAddressModel transformModelToRequest(MCifAddressRequest request, MCifModel mCif){
        return MCifAddressModel.builder()
                .address(request.getAddress())
                .mcif(mCif)
                .createdBy("DENI")
                .createdAt(LocalDate.now())
                .isDeleted(false)
                .build();
    }

    private MCifAddressResponse transformAddressResponseToModel(MCifAddressModel mCifAddress, MCifResponse response){
        MCifAddressResponse cifAddressResponse = new MCifAddressResponse();
        cifAddressResponse.setId(mCifAddress.getId());
        cifAddressResponse.setAddress(mCifAddress.getAddress());
        cifAddressResponse.setCifId(response);
        return  cifAddressResponse;
    }
}
