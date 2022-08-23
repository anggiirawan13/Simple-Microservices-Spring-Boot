package com.anggi.irawan.springcloudcifservice.controllers;

import com.anggi.irawan.springcloudcifservice.components.endpoints.MCifEndpoint;
import com.anggi.irawan.springcloudcifservice.dtos.request.MCifRequest;
import com.anggi.irawan.springcloudcifservice.dtos.response.DefaultResponse;
import com.anggi.irawan.springcloudcifservice.services.MCifService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = MCifEndpoint.PATH)
@AllArgsConstructor
public class MCifController {

    private MCifService mCifService;

    @PostMapping
    public DefaultResponse addMCif(@RequestBody MCifRequest request) {
        return mCifService.addCif(request);
    }

    @PutMapping(path = MCifEndpoint.PATH_URL)
    public DefaultResponse updateMCif(@PathVariable(value = MCifEndpoint.VARIABLE_PATH_PARAM_ID) String id, @RequestBody MCifRequest request) {
        return mCifService.updateCif(id, request);
    }

    @GetMapping(value = MCifEndpoint.PATH_URL)
    public DefaultResponse listMCif() {
        return mCifService.listCif();
    }

    @DeleteMapping(value = MCifEndpoint.PATH_URL)
    public DefaultResponse deleteMCif(@PathVariable(value = MCifEndpoint.VARIABLE_PATH_PARAM_ID) String id) {
        return mCifService.deleteCif(id);
    }
}
