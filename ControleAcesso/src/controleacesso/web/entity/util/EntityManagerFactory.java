package controleacesso.web.entity.util;

import java.io.FileNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import controleacesso.web.file.ManipulaProperties;

@WebListener
public class EntityManagerFactory implements ServletContextListener {
	
	private static javax.persistence.EntityManagerFactory emf;

    @Override
    public void contextInitialized(ServletContextEvent event) {
    	ManipulaProperties mProperties = new ManipulaProperties();
        try {
			emf = Persistence.createEntityManagerFactory(mProperties.readPropertiesInterno("nome_persistence_unit")); // "SGDQPU");
			emf.isOpen();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        emf.close();
    }

    public static EntityManager createEntityManager() {
        if (emf == null) {
            throw new IllegalStateException("Classe EntityManagerFactory. Context is not initialized yet.");
        }

        return emf.createEntityManager();
    }

}
