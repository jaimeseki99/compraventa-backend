package net.ausiasmarch.compraventa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import net.ausiasmarch.compraventa.entity.ProductoEntity;
import net.ausiasmarch.compraventa.exception.InssuficientStockException;
import net.ausiasmarch.compraventa.exception.ResourceNotFoundException;
import net.ausiasmarch.compraventa.repository.ProductoRepository;

@Service
public class ProductoService {
    
    @Autowired
    ProductoRepository oProductoRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    @Autowired
    SesionService oSesionService;

    public ProductoEntity get(Long id) {
        return oProductoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
    }

    public Long create(ProductoEntity oProductoEntity) {
        oSesionService.onlyAdmins();
        oProductoEntity.setId(null);
        return oProductoRepository.save(oProductoEntity).getId();
    }

    public ProductoEntity update(ProductoEntity oProductoEntity) {
        oSesionService.onlyAdmins();
        return oProductoRepository.save(oProductoEntity);
    }

    public Long delete(Long id) {
        oSesionService.onlyAdmins();
        oProductoRepository.deleteById(id);
        return id;
    }
    
    public Page<ProductoEntity> getPage(Pageable oPageable) {
        oSesionService.onlyAdminsOrUsers();
        return oProductoRepository.findAll(oPageable);
    }

    public Long populate(Integer amount) {
        oSesionService.onlyAdmins();
        for (int i=0; i < amount; i++) {
            oProductoRepository.save(new ProductoEntity("nombre" + i, "categoria" + i, i, i, "descripcion" + i));
        }
        return oProductoRepository.count();
    }

    public ProductoEntity getOneRandom() {
        Pageable oPageable = PageRequest.of((int) (Math.random() * oProductoRepository.count()), 1);
        return oProductoRepository.findAll(oPageable).getContent().get(0);
    } 

    @Transactional
    public void actualizarStock(ProductoEntity oProductoEntity, int cantidad) {
        ProductoEntity productoEncontrado = oProductoRepository.findById(oProductoEntity.getId()).orElse(null);

        if (productoEncontrado != null) {
        int stockActual = productoEncontrado.getStock();
        int nuevoStock = stockActual - cantidad;
        productoEncontrado.setStock(nuevoStock);
        oProductoRepository.save(productoEncontrado);
        }
    }

    @Transactional
    public Long empty() {
        oSesionService.onlyAdmins();
        oProductoRepository.deleteAll();
        oProductoRepository.resetAutoIncrement();
        oProductoRepository.flush();
        return oProductoRepository.count();
    }


}
