package net.ausiasmarch.compraventa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import net.ausiasmarch.compraventa.bean.UsuarioBean;
import net.ausiasmarch.compraventa.entity.UsuarioEntity;
import net.ausiasmarch.compraventa.exception.ResourceNotFoundException;
import net.ausiasmarch.compraventa.exception.UnauthorizedException;
import net.ausiasmarch.compraventa.helper.JWTHelper;
import net.ausiasmarch.compraventa.repository.UsuarioRepository;

@Service
public class SesionService {

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    public String login(UsuarioBean oUsuarioBean) {
        oUsuarioRepository.findByUsernameAndContrasenya(oUsuarioBean.getUsername(), oUsuarioBean.getContrasenya())
            .orElseThrow(() -> new ResourceNotFoundException("Usuario y/o contraseña incorrecto(s)"));
        return JWTHelper.generateJWT(oUsuarioBean.getUsername());
    }

    public String getSesionUsername() {
        if(oHttpServletRequest.getAttribute("username") instanceof String) {
            return oHttpServletRequest.getAttribute("username").toString();
        } else {
            return null;
        }
    }

    public UsuarioEntity getSesionUsuario() {
        if (this.getSesionUsername() != null) {
            return oUsuarioRepository.findByUsername(this.getSesionUsername()).orElse(null);
        } else {
            return null;
        }
    }

    public Boolean isSessionActive() {
       if (this.getSesionUsername() != null) {
            return oUsuarioRepository.findByUsername(this.getSesionUsername()).isPresent();
       } else {
            return false;
       }
    }

    public Boolean isAdmin() {
        if (this.getSesionUsername() != null) {
            UsuarioEntity oUsuarioEntityEnSesion = oUsuarioRepository.findByUsername(this.getSesionUsername())
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
            return Boolean.FALSE.equals(oUsuarioEntityEnSesion.getRol());
        } else {
            return false;
        }
    }

    public Boolean isUser() {
       if (this.getSesionUsername() != null) {
        UsuarioEntity oUsuarioEntityEnSesion = oUsuarioRepository.findByUsername(this.getSesionUsername())
        .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        return Boolean.TRUE.equals(oUsuarioEntityEnSesion.getRol());
       } else {
        return false;
       }
    }

    public void onlyAdmins() {
        if (!this.isAdmin()) {
            throw new UnauthorizedException("Solo los administradores pueden hacer esto");
        }
    }

    public void onlyUsers() {
        if (!this.isUser()) {
            throw new UnauthorizedException("Solo los usuarios pueden hacer esto");
        }
    }

    public void onlyAdminsOrUsers() {
        if (!this.isSessionActive()) {
            throw new UnauthorizedException("Solo los usuarios y los admins pueden hacer esto");
        }
    }

    public void onlyUsersWithIisOwnData(Long id_usuario) {
        if (!this.isUser()) {
            throw new UnauthorizedException("Solo los usuarios pueden hacer esto");
        }
        if (!this.getSesionUsuario().getId().equals(id_usuario)) {
            throw new UnauthorizedException("Solo los usuarios pueden hacer esto");
        }
    }

    public void onlyAdminsOrUsersWithIisOwnData(Long id_usuario) {
        if (this.isSessionActive()) {
            if (!this.isAdmin()) {
                if (!this.isUser()) {
                    throw new UnauthorizedException("Solo los usuarios y los administradores con sus propios datos pueden hacer esto");
                } else {
                    if (!this.getSesionUsuario().getId().equals(id_usuario)) {
                        throw new UnauthorizedException("Solo los usuarios y los administradores con sus propios datos pueden hacer esto");
                    }
                }
            }
        } else {
            throw new UnauthorizedException("Solo los usuarios y los administradores pueden hacer esto");
        }
    }






    
    
}
