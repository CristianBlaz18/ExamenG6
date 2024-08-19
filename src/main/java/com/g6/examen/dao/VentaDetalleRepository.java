package com.g6.examen.dao;

import com.g6.examen.entity.ClienteEntity;
import com.g6.examen.entity.VentaDetalleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaDetalleRepository extends JpaRepository<VentaDetalleEntity,Long> {
}
