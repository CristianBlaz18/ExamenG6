package com.g6.examen.controller;

import com.g6.examen.dao.ClienteRepository;
import com.g6.examen.dao.VentaRespository;
import com.g6.examen.entity.ClienteEntity;
import com.g6.examen.entity.ProductoEntity;
import com.g6.examen.entity.VentaEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ventas/v1")
public class VentaController {

    private final VentaRespository ventaRepository;
    private final ClienteRepository clienteRepository;

    public VentaController(VentaRespository ventaRepository, ClienteRepository clienteRepository) {
        this.ventaRepository = ventaRepository;
        this.clienteRepository = clienteRepository;
    }

    @PostMapping("/crear/{idCliente}")
    public ResponseEntity<?> crearVenta(@PathVariable("idCliente") Long idCliente,
                                       @RequestBody VentaEntity venta) {

        ClienteEntity cliente = clienteRepository.findById(idCliente).orElseThrow(()->
                new RuntimeException("Error no hay clientes con ese id"));

        venta.setCliente(cliente);
        cliente.getVentas().add(venta);
        VentaEntity nuevaVenta = ventaRepository.save(venta);

        return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);


    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<VentaEntity> actualizarVenta(@PathVariable("id") Long id,
                                                       @RequestParam("cliente_id") Long clienteId,
                                                       @RequestBody VentaEntity venta) {
        Optional<VentaEntity> ventaExistente = ventaRepository.findById(id);
        if (ventaExistente.isPresent()) {
            VentaEntity datosActualizar = ventaExistente.get();
            Optional<ClienteEntity> clienteExistente = clienteRepository.findById(clienteId);
            if (clienteExistente.isPresent()) {
                datosActualizar.setCliente(clienteExistente.get());
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new VentaEntity());
            }
            datosActualizar.setFecha(venta.getFecha());
            datosActualizar.setTotal(venta.getTotal());
            return ResponseEntity.ok(ventaRepository.save(datosActualizar));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new VentaEntity());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarVenta(@PathVariable("id") Long id) {
        Optional<VentaEntity> ventaExistente = ventaRepository.findById(id);
        if (ventaExistente.isPresent()) {
            ventaRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Venta Eliminado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venta No Encontrada");
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<VentaEntity>> listarVentas() {
        return ResponseEntity.ok(ventaRepository.findAll());
    }

    @GetMapping("/listarVentasCliente/{id}")
    public ResponseEntity<?> listarVentasCliente(@PathVariable("id") Long id){
        return new ResponseEntity<>(ventaRepository.findByClienteNative(id),HttpStatus.OK);
    }
}
