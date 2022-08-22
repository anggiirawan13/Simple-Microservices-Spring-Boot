package com.anggi.irawan.springcloudcifservice.models;

import com.anggi.irawan.springcloudcifservice.models.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Table(name = "m_cif_work")
public class MCifWorkModel extends BaseModel {

    @Id
    @GeneratedValue(generator = "cif-system")
    @GenericGenerator(name = "cif-system", strategy = "uuid2")
    private String id;

    @ManyToOne
    @JoinColumn(name = "cif_id", nullable = false)
    private MCifModel mcif;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private BigInteger penghasilan;
}
