package controleacesso.web.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import controleacesso.web.facade.ControleAcessoAbstractFacade;
import controleacesso.web.modelo.ControleAcessoPerfil;


//import cobra.sim.util.teste.Perfil;

/**
 * Home object for domain model class Perfil.
 * @see ControleAcessoPerfil.Perfil
 * @author Antonio Augusto
 */
//@Stateless
public class ControleAcessoPerfilDao extends ControleAcessoAbstractFacade<ControleAcessoPerfil>  {

	public ControleAcessoPerfilDao() {
		super(ControleAcessoPerfil.class);
		// TODO Auto-generated constructor stub
	}
	
	private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em = createEntityManager();
    }
	
	private static final Log log = LogFactory.getLog(ControleAcessoPerfilDao.class);

	public void save(ControleAcessoPerfil objDao) {
		log.debug("Salvando instância de Recurso");
		create(objDao);
	}
	
	public void remove(ControleAcessoPerfil objDao) {
		log.debug("Removendo instância de Recurso");
		remove(objDao);
	}

	public void update(ControleAcessoPerfil objDao) {
		log.debug("Atualizando instância de Recurso");
		edit(objDao);
	}
	
	public ControleAcessoPerfil getObjDao(int id) {
		return find(id);
	}
	
	public List<ControleAcessoPerfil> list() {
		List<ControleAcessoPerfil> lista = findAll();
		return lista;
	}
	
	public List<ControleAcessoPerfil> listByUsuario(int idUsuario) {
		getEntityManager();
		List<ControleAcessoPerfil> lista = em
				.createNamedQuery("ControleAcessoPerfil.findByUsuario")
				.setParameter("idUsuario", idUsuario).getResultList();
		return lista;
	}
}
