package com.g6.examen.dao;

import com.g6.examen.entity.ClienteEntity;
import com.g6.examen.entity.VentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VentaRespository extends JpaRepository<VentaEntity,Long> {

    @Query("SELECT v FROM VentaEntity v WHERE v.cliente.id = :clienteId")
    List<VentaEntity> findVentasByClienteIdJPQL(@Param("clienteId") Long clienteId);

    @Query(value = "SELECT * FROM ventas WHERE cliente_id = :clienteId", nativeQuery = true)
    List<VentaEntity> findVentasByClienteIdNative(@Param("clienteId") Long clienteId);
}
