package net.ausiasmarch.compraventa.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "producto")
public class ProductoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "Define la categoría del producto")
    private String categoria;

    @Min(value = 0, message = "El stock debe ser un número entero que sea mayor o igual a 0")
    private int stock;

    @DecimalMin(value = "0.0", message = "El precio debe ser un número decimal que sea mayor o igual a 0")
    private double precio;

    @NotBlank(message = "Introduzca una breve descripción del producto")
    private String descripcion;

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
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCompras() {
        return compras.size();
    }

    @OneToMany(mappedBy = "producto", fetch = jakarta.persistence.FetchType.LAZY)
    private List<CompraEntity> compras;

    public ProductoEntity() {

    }

     public ProductoEntity(Long id, String nombre, String categoria, int stock, double precio, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.stock = stock;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public ProductoEntity(String nombre, String categoria, int stock, double precio, String descripcion) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.stock = stock;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    
}
