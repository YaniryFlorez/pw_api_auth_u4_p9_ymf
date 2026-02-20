package uce.edu.web.api.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Integer id;
     private String useur;
     private String password;
     private String role;
     
     public Integer getId() {
         return id;
     }
     public void setId(Integer id) {
         this.id = id;
     }
     public String getUseur() {
         return useur;
     }
     public void setUseur(String useur) {
         this.useur = useur;
     }
     public String getPassword() {
         return password;
     }
     public void setPassword(String password) {
         this.password = password;
     }
     public String getRole() {
         return role;
     }
     public void setRole(String role) {
         this.role = role;
     }

   
     
    
}
