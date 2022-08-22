package com.anggi.irawan.springcloudcifservice.dtos.response;

import com.anggi.irawan.springcloudcifservice.models.MCifModel;
import lombok.Data;

@Data
public class MCifResponse {

    private String id;
    private String idKtp;
    private String name;
    private String npwp;
    private String noTelephone;
    private String email;
    private String type;

    public MCifResponse transformCifResponseFromModel(MCifModel cif){
        MCifResponse response = new MCifResponse();
        response.setId(cif.getId());
        response.setNpwp(cif.getNpwp());
        response.setType(cif.getType());
        response.setNoTelephone(cif.getNoTelephone());
        response.setName(cif.getName());
        response.setEmail(cif.getEmail());
        response.setIdKtp(cif.getIdKtp());

        return  response;
    }
}
