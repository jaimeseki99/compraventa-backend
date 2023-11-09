package net.ausiasmarch.compraventa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import net.ausiasmarch.compraventa.entity.UsuarioEntity;
import net.ausiasmarch.compraventa.exception.ResourceNotFoundException;
import net.ausiasmarch.compraventa.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    @Autowired
    SesionService oSesionService;

    public UsuarioEntity get(Long id){
        return oUsuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

   public Long create (UsuarioEntity oUsuarioEntity) {
       oSesionService.onlyAdmins();
       oUsuarioEntity.setId(null);
       oUsuarioEntity.setContrasenya("2868b648dcccb43788cc6c29df16bf3c899151d035892c3988b53fefbede53f7");
       return oUsuarioRepository.save(oUsuarioEntity).getId();
    }

    public UsuarioEntity update(UsuarioEntity oUsuarioEntity) {
        UsuarioEntity oUsuarioEntityBaseDatos = this.get(oUsuarioEntity.getId());
        oSesionService.onlyAdminsOrUsersWithIisOwnData(oUsuarioEntityBaseDatos.getId());
        if (oSesionService.isUser()) {
            oUsuarioEntity.setId(null);
            oUsuarioEntity.setRol(oUsuarioEntityBaseDatos.getRol());
            oUsuarioEntity.setContrasenya("2868b648dcccb43788cc6c29df16bf3c899151d035892c3988b53fefbede53f7");
            return oUsuarioRepository.save(oUsuarioEntity);
        } else {
            oUsuarioEntity.setId(null);
            oUsuarioEntity.setContrasenya("2868b648dcccb43788cc6c29df16bf3c899151d035892c3988b53fefbede53f7");
            return oUsuarioRepository.save(oUsuarioEntity);
        }
    }

    public Long delete(Long id) {
        oSesionService.onlyAdmins();
        oUsuarioRepository.deleteById(id);
        return id;
    }

    public Page<UsuarioEntity> getPage(Pageable oPageable) {
        oSesionService.onlyAdmins();
        return oUsuarioRepository.findAll(oPageable);
    }

    public Long populate(Integer amount) {
        oSesionService.onlyAdmins();
        for (int i=0; i < amount; i++) {
          oUsuarioRepository.save(new UsuarioEntity("nombre" + i, "apellido" + i, "usuario" + i,"email" + i + "gmail.com", "C/ Falsa" + i, "Teléfono" + i, i,"2868b648dcccb43788cc6c29df16bf3c899151d035892c3988b53fefbede53f7", true));
        }
        return oUsuarioRepository.count();
    }

    public UsuarioEntity getOneRandom() {
        oSesionService.onlyAdmins();
        Pageable oPageable = PageRequest.of((int) (Math.random() * oUsuarioRepository.count()), 1);
        return oUsuarioRepository.findAll(oPageable).getContent().get(0);
    }

    @Transactional
    public Long empty() {
        oSesionService.onlyAdmins();
        oUsuarioRepository.deleteAll();
        oUsuarioRepository.resetAutoIncrement();
        UsuarioEntity oUsuarioEntity1 = new UsuarioEntity(1L, "Jaime", "Serrano", "jaimeseki99", "jaime99sq@gmail.com", "C/La Senyera, 24", "601148404", 1000000.00, "2868b648dcccb43788cc6c29df16bf3c899151d035892c3988b53fefbede53f7", false);
        oUsuarioRepository.save(oUsuarioEntity1);
        oUsuarioEntity1 = new UsuarioEntity(2L, "Joan", "Serrano", "joaseki", "joaserqu93@hotmail.com", "C/ La Plata, 54", "693797773", 1000000.00, "2868b648dcccb43788cc6c29df16bf3c899151d035892c3988b53fefbede53f7",true);
        oUsuarioRepository.save(oUsuarioEntity1);
        return oUsuarioRepository.count();
    }

    @Transactional
    public void actualizarSaldoUsuario(UsuarioEntity oUsuarioEntity, double costeTotal) {
        UsuarioEntity usuarioEncontrado = oUsuarioRepository.findById(oUsuarioEntity.getId()).orElse(null);

                if (usuarioEncontrado != null) {
                    double saldoActual = usuarioEncontrado.getSaldo();
                    double nuevoSaldo = saldoActual - costeTotal;

                    if (nuevoSaldo >= 0) {
                        usuarioEncontrado.setSaldo(nuevoSaldo);
                        oUsuarioRepository.save(usuarioEncontrado);
                    }
                } else {
                    throw new ResourceNotFoundException("Usuario no encontrado");
                }
            }
}
