package org.sadtech.consultant.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class SaveUnit {

    @Id
    private Long id;
    private Long idStage;

}
