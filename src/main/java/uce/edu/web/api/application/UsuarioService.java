package uce.edu.web.api.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uce.edu.web.api.application.representation.UsuarioRepresentation;
import uce.edu.web.api.domain.Usuario;
import uce.edu.web.api.infraestructure.UsuarioRepository;

@ApplicationScoped
public class UsuarioService {

    @Inject
    private UsuarioRepository usuarioRepository;

    @Transactional
    public UsuarioRepresentation consultarPorId(Integer id) {
        Usuario usuario = this.usuarioRepository.findById(id.longValue());
        return this.mapper(usuario);
    }
    @Transactional
    public UsuarioRepresentation validarUsuario(String useur, String password) {
        Usuario usuario = this.usuarioRepository.buscarPorCredenciales(useur, password);
        return this.mapper(usuario);
    }
    @Transactional
    public UsuarioRepresentation buscarPorUsuari(String useur){
        Usuario usuR = this.usuarioRepository.find("useur= ?1", useur).firstResult();
        return this.mapper(usuR);
    }

    private UsuarioRepresentation mapper(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        UsuarioRepresentation usuarioR = new UsuarioRepresentation();
        usuarioR.useur = usuario.getUseur();
        usuarioR.password = usuario.getPassword();
        usuarioR.role = usuario.getRole();
        return usuarioR;
    }
}
