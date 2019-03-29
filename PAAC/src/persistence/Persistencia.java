package persistence;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/******************************************************************/ 
/* @version 1.0                                                   */ 
/* @author Puxka Acosta Domínguez Eduardo Rosas Rivera            */ 
/* @since 07/11/2018                                              */
/* Nombre de la clase PersistenciaCuentaInvitado                  */
/******************************************************************/
public class Persistencia {
    
    /**
     * Este metodo es para trabajar con las entidades de la base de datos 
     * @return El EntityManager 
     */ 
    public EntityManager administrarEntidades() {
        
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.user", "root");
        properties.put("javax.persistence.jdbc.password", "pass");
        EntityManagerFactory emf = javax.persistence.Persistence
                .createEntityManagerFactory("PAACPU", properties);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        return em;
    }
}
