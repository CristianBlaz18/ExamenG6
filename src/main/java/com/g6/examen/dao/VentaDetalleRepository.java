package com.g6.examen.dao;

import com.g6.examen.entity.ClienteEntity;
import com.g6.examen.entity.VentaDetalleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VentaDetalleRepository extends JpaRepository<VentaDetalleEntity,Long> {

    @Query("SELECT d FROM VentaDetalleEntity d WHERE d.venta.id = :ventaId")
    List<VentaDetalleEntity> findDetallesByVentaIdJPQL(@Param("ventaId") Long ventaId);

    @Query(value = "SELECT * FROM venta_detalle WHERE venta_id = :ventaId", nativeQuery = true)
    List<VentaDetalleEntity> findDetallesByVentaIdNative(@Param("ventaId") Long ventaId);
}
