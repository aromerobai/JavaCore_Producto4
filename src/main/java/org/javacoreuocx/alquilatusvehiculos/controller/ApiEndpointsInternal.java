package org.javacoreuocx.alquilatusvehiculos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import org.javacoreuocx.alquilatusvehiculos.model.Cliente;
import org.javacoreuocx.alquilatusvehiculos.model.Oficina;
import org.javacoreuocx.alquilatusvehiculos.repository.ClienteRepository;
import org.javacoreuocx.alquilatusvehiculos.repository.OficinaRepository;

@RestController
@RequestMapping(value = "/api/internal")
public class ApiEndpointsInternal {

    @Autowired
    private OficinaRepository oficinaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/clientes")
    public List<Cliente> getAllClientes() {
        return  clienteRepository.findAll();
    }

    @GetMapping("/clientesNombre")
    public Cliente getAllClientes(String username) {
        return  clienteRepository.findByUsername(username);
    }

    @GetMapping("/oficinasCodigoPostal")
    public Optional<Oficina> getAllOficinas(String codigoPostal) {
        return oficinaRepository.findByCodigoPostal(codigoPostal);
    }

}
