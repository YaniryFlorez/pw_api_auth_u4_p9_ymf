package uce.edu.web.api.interfaces;

import java.time.Instant;
import java.util.Set;

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import uce.edu.web.api.config.AuthConfig;
import uce.edu.web.api.application.UsuarioService;
import uce.edu.web.api.application.representation.UsuarioRepresentation;

import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AuthResource {
    @Inject
    AuthConfig authConfig;

    @Inject
    UsuarioService usuarioService;
//http://localhost:8082/matricula/api/v1.0/auth/token/useur/password?useur=admin&password=123
    @GET
    @Path("/token")
    public Response token(
            @QueryParam("useur") String useur,
            @QueryParam("password") String password) {
        UsuarioRepresentation usuarioRepresentation = usuarioService.validarUsuario(useur, password);

        if (usuarioRepresentation != null) {
            String issuer = authConfig.issuer();
            long ttl = authConfig.tokenTtl();

            Instant now = Instant.now();
            Instant exp = now.plusSeconds(ttl);

            String jwt = Jwt.issuer(issuer)
                    .subject(useur)
                    .groups(Set.of(usuarioRepresentation.role)) // roles: user / admin
                    .issuedAt(now)
                    .expiresAt(exp)
                    .sign();

            return Response.ok(new TokenResponse(jwt, exp.getEpochSecond(), usuarioRepresentation.role)).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Usuario o contraseña incorrectos").build();
        }
    }

    @RegisterForReflection
    public static class TokenResponse {
        public String accessToken;
        public long expiresAt;
        public String role;

        public TokenResponse() {
        }

        public TokenResponse(String accessToken, long expiresAt, String role) {
            this.accessToken = accessToken;
            this.expiresAt = expiresAt;
            this.role = role;
        }
    }
}