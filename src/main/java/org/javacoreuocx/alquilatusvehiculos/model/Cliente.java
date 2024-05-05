package org.javacoreuocx.alquilatusvehiculos.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellidos;
    @Column(unique = true)
    private String nif;
    @Column(unique = true)
    private String username;
    private String password;
    private String rol;

    public Cliente() {
    }

    public Cliente(String nombre, String apellidos, String username, String password) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.username = username;
        this.password = password;
    }

    public Cliente(Long id, String nombre, String apellidos, String email, String password) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
