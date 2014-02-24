/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controleacesso.web.util;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import controleacesso.web.modelo.ControleAcessoPerfil;
import controleacesso.web.modelo.ControleAcessoPessoa;
import controleacesso.web.modelo.ControleAcessoRecurso;
import controleacesso.web.modelo.ControleAcessoUsuario;


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
                ac.addAnnotatedClass(ControleAcessoPessoa.class);
                ac.addAnnotatedClass(ControleAcessoUsuario.class);
                ac.addAnnotatedClass(ControleAcessoRecurso.class);
                ac.addAnnotatedClass(ControleAcessoPerfil.class);

                
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
