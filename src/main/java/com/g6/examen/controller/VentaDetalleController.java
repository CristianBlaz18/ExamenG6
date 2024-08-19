package com.g6.examen.controller;

import com.g6.examen.dao.ProductoRepository;
import com.g6.examen.dao.VentaDetalleRepository;
import com.g6.examen.dao.VentaRespository;
import com.g6.examen.entity.ClienteEntity;
import com.g6.examen.entity.ProductoEntity;
import com.g6.examen.entity.VentaDetalleEntity;
import com.g6.examen.entity.VentaEntity;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/detalle-venta/v1")
public class VentaDetalleController {
    private final VentaDetalleRepository ventaDetalleRepository;
    private final ProductoRepository productoRepository;
    private final VentaRespository ventaRepository;

    public VentaDetalleController(VentaDetalleRepository ventaDetalleRepository, ProductoRepository productoRepository, VentaRespository ventaRepository) {
        this.ventaDetalleRepository = ventaDetalleRepository;
        this.productoRepository = productoRepository;
        this.ventaRepository = ventaRepository;
    }

    @PostMapping("/crear")
    @Transactional
    public ResponseEntity<VentaDetalleEntity> agregarDetalle(@RequestParam("venta_id") Long ventaId,
                                                             @RequestParam("producto_id") Long productoId,
                                                             @RequestParam("cantidad") int cantidad) {
        Optional<VentaEntity> ventaExistente = ventaRepository.findById(ventaId);
        Optional<ProductoEntity> productoExistente = productoRepository.findById(productoId);

        if (ventaExistente.isPresent() && productoExistente.isPresent()) {
            VentaDetalleEntity detalle = new VentaDetalleEntity();
            detalle.setVenta(ventaExistente.get());
            detalle.setProducto(productoExistente.get());
            detalle.setCantidad(cantidad);
            detalle.setPrecio(productoExistente.get().getPrecio());
            return ResponseEntity.ok(ventaDetalleRepository.save(detalle));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new VentaDetalleEntity());
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<VentaDetalleEntity> modificarDetalle(@PathVariable("id") Long id,
                                                               @RequestParam("producto_id") Long productoId,
                                                               @RequestParam("cantidad") int cantidad) {
        Optional<VentaDetalleEntity> detalleExistente = ventaDetalleRepository.findById(id);
        Optional<ProductoEntity> productoExistente = productoRepository.findById(productoId);

        if (detalleExistente.isPresent() && productoExistente.isPresent()) {
            VentaDetalleEntity detalle = detalleExistente.get();
            detalle.setProducto(productoExistente.get());
            detalle.setCantidad(cantidad);
            detalle.setPrecio(productoExistente.get().getPrecio());
            return ResponseEntity.ok(ventaDetalleRepository.save(detalle));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new VentaDetalleEntity());
    }



    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarDetalle(@PathVariable("id") Long id) {
        Optional<VentaDetalleEntity> detalleExiste = ventaDetalleRepository.findById(id);
        if (detalleExiste.isPresent()) {
            ventaDetalleRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Detalle Eliminado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Detalle No Encontrada");
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<VentaDetalleEntity>> listarVentas() {
        return ResponseEntity.ok(ventaDetalleRepository.findAll());
    }
}
