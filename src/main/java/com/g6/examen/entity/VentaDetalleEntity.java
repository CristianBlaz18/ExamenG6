package com.g6.examen.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "venta_detalle")
@Getter
@Setter
public class VentaDetalleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int cantidad;

    @Column(nullable = false)
    private double precio;

    @ManyToOne
    @JoinColumn(name = "venta_id",referencedColumnName = "id", nullable = false)
    private VentaEntity venta;

    @ManyToOne
    @JoinColumn(name = "producto_id",referencedColumnName = "id", nullable = false)
    private ProductoEntity producto;
}
