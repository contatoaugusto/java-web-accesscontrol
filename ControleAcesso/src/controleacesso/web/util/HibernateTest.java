package controleacesso.web.util;


import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import controleacesso.web.criptografia.EncripDecriptUtil;



public class HibernateTest {

    public static void main(String[] args) throws SQLException {

    	EncripDecriptUtil criptografa = new EncripDecriptUtil();
    	String senha = criptografa.encripta("teste", "MD5");
    	
//        Livro livro = new Livro();
//
        Session session = HibernateUtil.getSessionFactory().openSession();

//        Usuario user = new Usuario 	(5, "carlos","teste",true,null, null);
//        Perfil perfil = new Perfil(4,  "Acesso Dois",user.getIdUsuario(), true, null, null, null);
//        UsuarioPerfil tp = new UsuarioPerfil(new UsuarioPerfilId(user.getIdUsuario(),perfil.getIdPerfil()),user,perfil);	
//        
//        Recurso recurso = new Recurso(
//        		1,perfil,"Pagina Inicial", new Date(), 1, null, true, "Essa é a pagina inicial", "index.jsp",null);
//        
//        RecursoPerfil rp = new RecursoPerfil (new RecursoPerfilId(recurso.getIdRecurso(), perfil.getIdPerfil()), recurso, perfil);
//        
        Transaction t = session.beginTransaction();
  try{
//        session.save(user);
//        session.save(perfil);
//        session.save(tp);
//        session.save(recurso);
//        session.save(rp);
        
        t.commit();
        session.close();
  } catch (Throwable ex) {
      // Log the exception.
      System.err.println("Erro ao persistir objetos." + ex);
      System.err.println("Erro ao persistir objetos." + ex.getMessage());
      //throw new ExceptionInInitializerError(ex);
  } 
  }
}
