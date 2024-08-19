package com.g6.examen.dao;

import com.g6.examen.entity.ClienteEntity;
import com.g6.examen.entity.VentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VentaRespository extends JpaRepository<VentaEntity,Long> {
    @Query(value =
            "SELECT v.id AS venta_id, v.fecha, v.total FROM ventas v INNER JOIN clientes c " +
                    "ON v.cliente_id = c.id WHERE c.id = :clienteId", nativeQuery = true)
    List<VentaEntity> findByClienteNative(@Param("clienteId") Long clienteId);
}
