package com.anggi.irawan.springcloudcifservice.models;

import com.anggi.irawan.springcloudcifservice.models.base.BaseModel;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "m_cif")
@SuperBuilder
public class MCifModel extends BaseModel {

    @Id
    @GeneratedValue(generator = "cif-system")
    @GenericGenerator(name = "cif-system", strategy = "uuid2")
    private String id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 30)
    private String email;

    @Column(nullable = false, length = 16, unique = true)
    private String idKtp;

    @Column(nullable = false, length = 50, unique = true)
    private String noTelephone;

    @Column(length = 30, unique = true)
    private String npwp;

    @Column(nullable = false, length = 26)
    private String type;
}
