package com.anggi.irawan.springcloudcifservice.services.impl;

import com.anggi.irawan.springcloudcifservice.dtos.request.MCifRequest;
import com.anggi.irawan.springcloudcifservice.dtos.response.DefaultResponse;
import com.anggi.irawan.springcloudcifservice.dtos.response.MCifResponse;
import com.anggi.irawan.springcloudcifservice.exceptions.BussinesException;
import com.anggi.irawan.springcloudcifservice.helper.NullEmptyChecker;
import com.anggi.irawan.springcloudcifservice.models.MCifModel;
import com.anggi.irawan.springcloudcifservice.repositories.MCifRepository;
import com.anggi.irawan.springcloudcifservice.services.MCifService;
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
@CacheConfig(cacheNames = "cifCache")
public class MCifServiceImpl implements MCifService {

    private MCifRepository cifRepository;

    @Transactional
    @CacheEvict(cacheNames = "cifs", allEntries = true)
    @Override
    public DefaultResponse addCif(MCifRequest request) {
        MCifModel requestData = MCifModel.builder()
                .idKtp(request.getIdKtp())
                .name(request.getName())
                .email(request.getEmail())
                .noTelephone(request.getNoTelephone())
                .npwp(request.getNpwp())
                .type(request.getType())
                .createdBy("AWAN")
                .createdAt(LocalDate.now())
                .isDeleted(false)
                .build();

        MCifResponse responseData = saveMCifData(requestData);

        return new DefaultResponse(true, "Add data success", responseData, null);
    }

    @SneakyThrows
    @Transactional
    @CacheEvict(cacheNames = "cifs", key = "#id", allEntries = true)
    @Override
    public DefaultResponse updateCif(String id, MCifRequest request) {
        MCifModel getCifData = cifRepository.findById(id).orElseThrow(() -> new BussinesException("DATA NOT FOUNDS"));

        getCifData.setIdKtp(request.getIdKtp());
        getCifData.setName(request.getName());
        getCifData.setEmail(request.getEmail());
        getCifData.setNoTelephone(request.getNoTelephone());
        getCifData.setNpwp(request.getNpwp());
        getCifData.setType(request.getType());

        MCifResponse responseData = saveMCifData(getCifData);

        return new DefaultResponse(true, "Update data success", responseData, null);
    }

    @SneakyThrows
    @Cacheable(cacheNames = "cifs")
    @Override
    public DefaultResponse listCif() {
        List<MCifModel> getCifData = cifRepository.findAll();

        List<MCifResponse> responses = new ArrayList<>();

        getCifData.forEach(data -> {
            MCifResponse response = transformMCifModelToResponse(data);

            responses.add(response);
        });

        return new DefaultResponse(true, "Get list cif data success", responses, null);
    }

    @SneakyThrows
    @Caching(evict = {@CacheEvict(cacheNames = "cif", key = "#id"), @CacheEvict(cacheNames = "cifs", allEntries = true)})
    @Transactional
    @Override
    public DefaultResponse deleteCif(String id) {
        Optional<MCifModel> deleteResponse = cifRepository.findById(id);
        if (NullEmptyChecker.isNullOrEmpty(deleteResponse)) {
            throw new BussinesException("DATA NOT FOUNDS");
        }

        cifRepository.deleteById(id);

        return new DefaultResponse(true, "Delete cif data success", null, null);
    }

    @SneakyThrows
    @Cacheable(cacheNames = "cif", key = "#id", unless = "#result == null")
    @Transactional
    @Override
    public DefaultResponse findByIdCif(String id) {
        MCifModel getCifData = cifRepository.findById(id).orElseThrow(() -> new BussinesException("DATA NOT FOUNDS"));

        MCifResponse response = transformMCifModelToResponse(getCifData);

        return new DefaultResponse(true, "Get cif data by id success", response, null);
    }

    private MCifResponse saveMCifData(MCifModel data) {
        MCifModel saved = cifRepository.save(data);

        return transformMCifModelToResponse(saved);
    }

    private MCifResponse transformMCifModelToResponse(MCifModel data) {
        MCifResponse response = new MCifResponse();
        response.setId(data.getId());
        response.setEmail(data.getEmail());
        response.setIdKtp(data.getIdKtp());
        response.setName(data.getName());
        response.setNoTelephone(data.getNoTelephone());
        response.setType(data.getType());
        response.setNpwp(data.getNpwp());

        return response;
    }
}
