package org.javacoreuocx.alquilatusvehiculos.controller;

import org.javacoreuocx.alquilatusvehiculos.model.Cliente;
import org.javacoreuocx.alquilatusvehiculos.model.Oficina;
import org.javacoreuocx.alquilatusvehiculos.repository.ClienteRepository;
import org.javacoreuocx.alquilatusvehiculos.repository.OficinaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
public class ApiEndPoints {

    @Autowired
    private OficinaRepository oficinaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/oficinas")
    public List<Oficina> getAllOficinas() {return oficinaRepository.findAll();}

    @GetMapping("/clientes")
    public List<Cliente> getAllClientes() {
        return  clienteRepository.findAll();
    }

    @GetMapping("/oficinasCiudad")
    public Optional<Oficina> getOficinasId(String ciudad) {return oficinaRepository.findByCiudad(ciudad);}

    @GetMapping("/clientesNombre")
    public Cliente getAllClientes(String username) {
        return  clienteRepository.findByUsername(username);
    }
    @GetMapping("/oficinasCodigoPostal")
    public Optional<Oficina> getAllOficinas(String codigoPostal) {return oficinaRepository.findByCodigoPostal(codigoPostal);}

}