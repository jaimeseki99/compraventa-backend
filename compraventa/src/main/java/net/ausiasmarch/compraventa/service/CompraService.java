package net.ausiasmarch.compraventa.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import net.ausiasmarch.compraventa.entity.CompraEntity;
import net.ausiasmarch.compraventa.entity.ProductoEntity;
import net.ausiasmarch.compraventa.entity.UsuarioEntity;
import net.ausiasmarch.compraventa.exception.ResourceNotFoundException;
import net.ausiasmarch.compraventa.repository.CompraRepository;
import net.ausiasmarch.compraventa.repository.ProductoRepository;
import net.ausiasmarch.compraventa.repository.UsuarioRepository;

@Service
public class CompraService {
    
    @Autowired
    CompraRepository oCompraRepository;

    @Autowired
    ProductoService oProductoService;

    @Autowired
    UsuarioService oUsuarioService;

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    ProductoRepository oProductoRepository;

    @Autowired
    SesionService oSesionService;

    public CompraEntity get(Long id) {
        return oCompraRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Compra no encontrada"));
    }

    public Long create(CompraEntity oCompraEntity) {

        // oSesionService.onlyAdminsOrUsers();
        oCompraEntity.setId(null);
        int cantidadComprada = oCompraEntity.getCantidad();
        ProductoEntity productoComprado = oProductoService.get(oCompraEntity.getProducto().getId());
        double precio = productoComprado.getPrecio();
        double costeTotal = cantidadComprada * precio;
        UsuarioEntity usuarioCompra = oCompraEntity.getUsuario();

        oUsuarioService.actualizarSaldoUsuario(usuarioCompra, costeTotal);
        oProductoService.actualizarStock(productoComprado, cantidadComprada);

        CompraEntity oCompraEntityCreada = new CompraEntity(cantidadComprada, costeTotal, new Date(System.currentTimeMillis()), usuarioCompra, productoComprado);
 
        return oCompraRepository.save(oCompraEntityCreada).getId();
    }

    public CompraEntity update(CompraEntity oCompraEntity) {
        CompraEntity oCompraEntityBaseDatos = this.get(oCompraEntity.getId());
        oSesionService.onlyAdminsOrUsersWithIisOwnData(oCompraEntityBaseDatos.getUsuario().getId());
        if (oSesionService.isUser()) {
            oCompraEntity.setUsuario(oSesionService.getSesionUsuario());
            return oCompraRepository.save(oCompraEntity);
        } else {
            return oCompraRepository.save(oCompraEntity);
        }
    }

    public Long delete(Long id) {

        CompraEntity oCompraCancelada = this.get(id);
        oSesionService.onlyAdminsOrUsersWithIisOwnData(oCompraCancelada.getUsuario().getId());

        if (oCompraCancelada != null) {
            int cantidadVendida = oCompraCancelada.getCantidad();
            double costeCompra = oCompraCancelada.getCoste();
            ProductoEntity productoVendido = oCompraCancelada.getProducto();
            UsuarioEntity usuarioCompra = oCompraCancelada.getUsuario();

            if (productoVendido != null) {
                int stockActual = productoVendido.getStock();
                int nuevoStock = stockActual + cantidadVendida;
                productoVendido.setStock(nuevoStock);
                oProductoService.update(productoVendido);
            }

            oUsuarioService.actualizarSaldoUsuario(usuarioCompra, -costeCompra);

            oCompraRepository.deleteById(id);
        }

        return id;
    }

    public Page<CompraEntity> getPage(Pageable oPageable, Long id_usuario, Long id_producto) {
        oSesionService.onlyAdminsOrUsers();
    if (id_usuario!= 0 && id_producto != 0) {
        return oCompraRepository.findByUsuarioIdAndProductoId(id_usuario, id_producto, oPageable);
    } else if (id_usuario != 0) {
        return oCompraRepository.findByUsuarioId(id_usuario, oPageable);
    } else if (id_producto != 0) {
        return oCompraRepository.findByProductoId(id_producto, oPageable);
    } else {
        return oCompraRepository.findAll(oPageable);
    }
}


    public Long populate(Integer amount) {
        oSesionService.onlyAdmins();
        for (int i = 0; i < amount; i++) {
            oCompraRepository.save(new CompraEntity(i, i, new Date(System.currentTimeMillis()), oUsuarioService.getOneRandom(), oProductoService.getOneRandom()));
        }
        return oCompraRepository.count();
    }

    @Transactional
    public Long empty() {
        oSesionService.onlyAdmins();
        oCompraRepository.deleteAll();
        oCompraRepository.resetAutoIncrement();
        oCompraRepository.flush();
        return oCompraRepository.count();
    }


}
