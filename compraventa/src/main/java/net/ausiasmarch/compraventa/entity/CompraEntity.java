package net.ausiasmarch.compraventa.entity;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "compra")
public class CompraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El número de unidades compradas del producto no puede estar vacío")
    @Min(value = 1, message = "Debes comprar al menos una unidad para efectuar la compra")
    private int cantidad;

    private double coste;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fecha;

    @NotNull
    @NotBlank(message = "Debes especificar el usuario que realiza la compra")
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;

    @NotNull
    @NotBlank(message = "Debes especificar el producto que deseas comprar")
    @ManyToOne
    @JoinColumn(name = "id_producto")
    private ProductoEntity producto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getCoste() {
        return coste;
    }

    public void setCoste(double coste) {
        this.coste = coste;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public ProductoEntity getProducto() {
        return producto;
    }

    public void setProducto(ProductoEntity producto) {
        this.producto = producto;
    }

    public CompraEntity () {
        
    }

    public CompraEntity(int cantidad, double coste, Date fecha) {
        this.cantidad = cantidad;
        this.coste = coste;
        this.fecha = fecha;
    }

    public CompraEntity(Long id, int cantidad, double coste, Date fecha) {
        this.id = id;
        this.cantidad = cantidad;
        this.coste = coste;
        this.fecha = fecha;
    }

    public CompraEntity(int cantidad, double coste, Date fecha, UsuarioEntity usuario, ProductoEntity producto) {
        this.cantidad = cantidad;
        this.coste = coste;
        this.fecha = fecha;
        this.usuario = usuario;
        this.producto = producto;
    }
}
