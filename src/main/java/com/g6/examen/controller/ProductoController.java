package com.g6.examen.controller;

import com.g6.examen.dao.ProductoRepository;
import com.g6.examen.entity.ClienteEntity;
import com.g6.examen.entity.ProductoEntity;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos/v1")
public class ProductoController {

    private final ProductoRepository productoRepository;

    public ProductoController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @PostMapping("/crear")
    @Transactional
    public ResponseEntity<?> createProductos(@RequestBody ProductoEntity producto) {
//        cliente.getVentas().forEach(ventas-> ventas.setCliente(cliente));
        return new ResponseEntity<>(productoRepository.save(producto), HttpStatus.CREATED);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoEntity>> listarProductos(){
        return ResponseEntity.ok(productoRepository.findAll());
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ProductoEntity> actualizarProducto(@PathVariable("id") Long id,
                                                           @RequestBody  ProductoEntity producto){
        Optional<ProductoEntity> productoExistente = productoRepository.findById(id);
        if(productoExistente.isPresent()){
            ProductoEntity datosActualizar = productoExistente.get();
            datosActualizar.setNombre(producto.getNombre());
            datosActualizar.setPrecio(producto.getPrecio());
            return ResponseEntity.ok(productoRepository.save(datosActualizar));
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ProductoEntity());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable("id") Long id) {
        Optional<ProductoEntity> productoExistente = productoRepository.findById(id);
        if (productoExistente.isPresent()) {
            productoRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Producto Eliminado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto No Encontrado");
        }
    }
}
