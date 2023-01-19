package com.roshka.dtaporteria.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Data
public class Sync {
    public Sync(Integer id, Integer cantidad) {
        this.id = id;
        this.cantidad = cantidad;
    }
    @Id
    private Integer id;
    private Integer cantidad;
}