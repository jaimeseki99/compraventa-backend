package net.ausiasmarch.compraventa.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellido;

    @NotBlank(message = "El username no puede estar vacío")
    private String username;

    @Email(message = "El email debe tener un formato válido")
    private String email;

    @NotBlank
    @Size(min = 5, max = 100, message = "El campo de la dirección no puede estar vacío, además debe contener un mínimo de 5 carácteres y un máximo de 100 carácteres")
    private String direccion;

    @Pattern(regexp = "\\d{9}", message = "El teléfono debe tener 9 dígitos")
    private String telefono;

    @DecimalMin(value = "0.0", message = "El saldo no puede ser negativo")
    private double saldo;

    @Pattern(regexp = "^[0-9a-fA-F]{64}$", message = "La contraseña debe tener formato hexadecimal")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String contrasenya = "2868b648dcccb43788cc6c29df16bf3c899151d035892c3988b53fefbede53f7";

    @NotNull(message = "Tienes que especificar el rol")
    private Boolean rol = false;

    @OneToMany(mappedBy = "usuario", fetch = jakarta.persistence.FetchType.LAZY)
    private List<CompraEntity> compras;

    public UsuarioEntity() {

    }

    public UsuarioEntity(Long id, String nombre, String apellido, String username, String email, String direccion, String telefono, double saldo, String contrasenya, Boolean rol) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.email = email;
        this.direccion = direccion;
        this.telefono = telefono;
        this.saldo = saldo;
        this.contrasenya = contrasenya;
        this.rol = rol;
    }

    public UsuarioEntity(String nombre, String apellido, String username, String email, String direccion, String telefono, double saldo, String contrasenya, Boolean rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.email = email;
        this.direccion = direccion;
        this.telefono = telefono;
        this.saldo = saldo;
        this.contrasenya = contrasenya;
        this.rol = rol;
    }

    public UsuarioEntity(String username, String contrasenya) {
        this.username = username;
        this.contrasenya = contrasenya;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

     public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

     public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public Boolean getRol() {
        return rol;
    }

    public void setRol(Boolean rol) {
        this.rol = rol;
    }

     public int getCompras() {
        return compras.size();
    }


    
}
