package net.ausiasmarch.compraventa.api;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.compraventa.entity.CompraEntity;
import net.ausiasmarch.compraventa.service.CompraService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/compra")
public class CompraApi {
    
@Autowired
    CompraService oCompraService;

    @GetMapping("/{id}")
    public ResponseEntity<CompraEntity> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oCompraService.get(id));
    }

    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody CompraEntity oCompraEntity) {
        return ResponseEntity.ok(oCompraService.create(oCompraEntity));
    }

    @PutMapping("")
    public ResponseEntity<CompraEntity> update(@RequestBody CompraEntity oCompraEntity) {
        return ResponseEntity.ok(oCompraService.update(oCompraEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oCompraService.delete(id));
    }

    @GetMapping("")
    public ResponseEntity<Page<CompraEntity>> getPage(Pageable pageable, @RequestParam(value = "usuario", defaultValue = "0", required = false) Long id_usuario,
            @RequestParam(value = "producto", defaultValue = "0", required = false) Long id_producto) {
        return ResponseEntity.ok(oCompraService.getPage(pageable, id_usuario, id_producto));
    }

    @PostMapping("/populate/{amount}")
    public ResponseEntity<Long> populate(@PathVariable("amount") Integer amount) {
        return ResponseEntity.ok(oCompraService.populate(amount));
    }

    @DeleteMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(oCompraService.empty());
    }

}
