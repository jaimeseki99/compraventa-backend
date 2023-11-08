package net.ausiasmarch.compraventa.api;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.compraventa.entity.ProductoEntity;
import net.ausiasmarch.compraventa.service.ProductoService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/producto")
public class ProductoApi {

    @Autowired
    ProductoService oProductoService;

    @GetMapping("/{id}") 
    public ResponseEntity<ProductoEntity> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oProductoService.get(id));
    }

    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody ProductoEntity oProductoEntity) {
        return ResponseEntity.ok(oProductoService.create(oProductoEntity));
    }

    @PutMapping("")
    public ResponseEntity<ProductoEntity> update(@RequestBody ProductoEntity oProductoEntity) {
        return ResponseEntity.ok(oProductoService.update(oProductoEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oProductoService.delete(id));
    } 

    @GetMapping("")
    public ResponseEntity<Page<ProductoEntity>> getPage(Pageable oPageable) {
        return ResponseEntity.ok(oProductoService.getPage(oPageable));
    }

    @PostMapping("/populate/{amount}")
    public ResponseEntity<Long> populate(@PathVariable("amount") Integer amount) {
        return ResponseEntity.ok(oProductoService.populate(amount));
    }

    @DeleteMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(oProductoService.empty());
    }

}