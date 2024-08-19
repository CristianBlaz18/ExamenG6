package com.g6.examen.controller;

import com.g6.examen.dao.ClienteRepository;
import com.g6.examen.entity.ClienteEntity;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes/v1")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @PostMapping("/crear")
    @Transactional
    public ResponseEntity<?> createClientes(@RequestBody ClienteEntity cliente) {
//        cliente.getVentas().forEach(ventas-> ventas.setCliente(cliente));
        return new ResponseEntity<>(clienteRepository.save(cliente), HttpStatus.CREATED);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ClienteEntity>> listarClientes(){
        return ResponseEntity.ok(clienteRepository.findAll());
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ClienteEntity> actualizarCliente(@PathVariable("id") Long id,
                                                    @RequestBody  ClienteEntity cliente){
        Optional<ClienteEntity> clienteExistente = clienteRepository.findById(id);
        if(clienteExistente.isPresent()){
            ClienteEntity datosActualizar = clienteExistente.get();
            datosActualizar.setNombres(cliente.getNombres());
            datosActualizar.setEmail(cliente.getEmail());
            return ResponseEntity.ok(clienteRepository.save(datosActualizar));
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ClienteEntity());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable("id") Long id) {
        Optional<ClienteEntity> clienteExistente = clienteRepository.findById(id);
        if (clienteExistente.isPresent()) {
            clienteRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Cliente Eliminado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente No Encontrado");
        }
    }


}
