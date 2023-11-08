package net.ausiasmarch.compraventa.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.compraventa.bean.UsuarioBean;
import net.ausiasmarch.compraventa.service.SesionService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/sesion")
public class SesionController {
    
    @Autowired
    SesionService oSesionService;

    @PostMapping() 
    public ResponseEntity<String> login(@RequestBody UsuarioBean oUsuarioBean) {
        return ResponseEntity.ok("\"" + oSesionService.login(oUsuarioBean) + "\"");
    }

}
