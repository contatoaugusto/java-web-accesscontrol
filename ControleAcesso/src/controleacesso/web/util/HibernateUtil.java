/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controleacesso.web.util;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import controleacesso.web.modelo.Perfil;
import controleacesso.web.modelo.Pessoa;
import controleacesso.web.modelo.Recurso;
import controleacesso.web.modelo.Usuario;


/**
 * Hibernate Utility class with a convenient method to get Session Factory object.
 *
 * @author Antonio Augusto
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;

    private HibernateUtil() {
        
    }

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {
                // Create the SessionFactory from standard (hibernate.cfg.xml)
                // config file.
                AnnotationConfiguration ac = new AnnotationConfiguration();
                ac.addAnnotatedClass(Pessoa.class);
                ac.addAnnotatedClass(Usuario.class);
                ac.addAnnotatedClass(Recurso.class);
                ac.addAnnotatedClass(Perfil.class);

                
                sessionFactory = ac.configure().buildSessionFactory();
//                SchemaExport se = new SchemaExport(ac);
//                se.create(true, true);
                

            } catch (Throwable ex) {
                // Log the exception.
                System.err.println("Initial SessionFactory creation failed." + ex);
                throw new ExceptionInInitializerError(ex);
            }

            return sessionFactory;

        } else {
            return sessionFactory;
        }
        
    }

    
    public static void main(String[] args) {
        HibernateUtil.getSessionFactory();
    }

}
