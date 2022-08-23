package com.anggi.irawan.springcloudcifservice.controllers;

import com.anggi.irawan.springcloudcifservice.components.endpoints.MCifAddressEndpoint;
import com.anggi.irawan.springcloudcifservice.dtos.request.MCifAddressRequest;
import com.anggi.irawan.springcloudcifservice.dtos.response.DefaultResponse;
import com.anggi.irawan.springcloudcifservice.services.MCifAddressService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = MCifAddressEndpoint.PATH)
@AllArgsConstructor
public class MCifAddressController {

    private MCifAddressService mCifAddressService;

    @PostMapping
    public DefaultResponse addMCif(@RequestBody MCifAddressRequest request) {
        return mCifAddressService.addMCifAddress(request);
    }

    @PutMapping(path = MCifAddressEndpoint.PATH_URL)
    public DefaultResponse updateMCif(@PathVariable(value = MCifAddressEndpoint.VARIABLE_PATH_PARAM_ID) String id, @RequestBody MCifAddressRequest request) {
        return mCifAddressService.updateMCifAddress(id, request);
    }

    @GetMapping(value = MCifAddressEndpoint.PATH_URL)
    public DefaultResponse listMCif() {
        return mCifAddressService.allDataMCifAddress();
    }

    @DeleteMapping(value = MCifAddressEndpoint.PATH_URL)
    public DefaultResponse deleteMCif(@PathVariable(value = MCifAddressEndpoint.VARIABLE_PATH_PARAM_ID) String id) {
        return mCifAddressService.deleteMCifAddress(id);
    }
}
