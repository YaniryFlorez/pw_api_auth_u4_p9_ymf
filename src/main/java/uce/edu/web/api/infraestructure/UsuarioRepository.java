package uce.edu.web.api.infraestructure;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import uce.edu.web.api.domain.Usuario;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    public Usuario buscarPorCredenciales(String useur, String password) {
        return find("useur = ?1 and password = ?2", useur, password).firstResult();
    }
    
    public Usuario buscarPorUsername(String useur) {
        return find("useur = ?1", useur).firstResult();
    }
}
