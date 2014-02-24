package controleacesso.web.dao;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import controleacesso.web.facade.ControleAcessoAbstractFacade;
import controleacesso.web.modelo.ControleAcessoPerfil;
import controleacesso.web.modelo.ControleAcessoRecurso;


/**
 * Home object for domain model class Recurso.
 * @see ControleAcessoRecurso.Recurso
 * @author Antonio Augusto
 */
//@Stateless
public class ControleAcessoRecursoDao extends ControleAcessoAbstractFacade<ControleAcessoRecurso>  {

	public ControleAcessoRecursoDao() {
		super(ControleAcessoRecurso.class);
		// TODO Auto-generated constructor stub
	}
	
	private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em = createEntityManager();
    }

	private static final Log log = LogFactory.getLog(ControleAcessoRecursoDao.class);

	@Transactional
	public void save(ControleAcessoRecurso objDao) {
		log.debug("Salvando instância de Recurso");
		create(objDao);
	}
	
	@Transactional
	public void remove(ControleAcessoRecurso objDao) {
		log.debug("Removendo instância de Recurso");
		
		// Remover item na tabela de relacionamento tb_recurso_perfil
		getEntityManager();
		for (ControleAcessoPerfil perfil : objDao.getTbperfils()){
			em.createQuery("delete from dbo.tb_recurso_perfil where id_recurso = " + objDao.getIdRecurso() + 
					" and id_perfil = " + perfil.getIdPerfil()).executeUpdate();
		}		
		
		objDao.setTbperfils(null);
	}

	@Transactional
	public void update(ControleAcessoRecurso objDao) {
		log.debug("Atualizando instância de Recurso");
		edit(objDao);
	}

	public ControleAcessoRecurso getObjDao(int id) {
		return find(id);
	}
	
	@Transactional
	public List<ControleAcessoRecurso> list() {
		getEntityManager();
		List<ControleAcessoRecurso> lista = em.createNamedQuery("ControleAcessoRecurso.findAll").getResultList();
		return lista;
	}
	
	@Transactional
	public List<ControleAcessoRecurso> listByRecursoAtivo() {
		getEntityManager();
		List<ControleAcessoRecurso> lista = em.createNamedQuery("ControleAcessoRecurso.findByAtivo").setParameter("fgAtivo", true).getResultList();
		return lista;
	}
	
	 public List<SelectItem> getItemsPerfil(){  
		 ControleAcessoPerfilDao perfilDAO = new ControleAcessoPerfilDao();
		 List<SelectItem> statusItem = new ArrayList<SelectItem>();
		 List<ControleAcessoPerfil> listaSA = perfilDAO.list();  
	        for  (ControleAcessoPerfil cbx : listaSA){    
	              SelectItem  s = new SelectItem();    
	              s.setValue(cbx.getIdPerfil());    
	              s.setLabel(cbx.getDsPerfil());    
	              statusItem.add(s);    
	       }    
	       return statusItem;  
	   }
	 
	 public List<SelectItem> getItemsRecursoPai(){  
		 ControleAcessoRecursoDao recursoDAO = new ControleAcessoRecursoDao();
		 List<SelectItem> recursoItem = new ArrayList<SelectItem>();
		 List<ControleAcessoRecurso> listaSA = recursoDAO.list();  
	        for  (ControleAcessoRecurso cbx : listaSA){    
	              SelectItem  s = new SelectItem();    
	              s.setValue(cbx.getIdRecurso());    
	              s.setLabel(cbx.getNmRecurso());    
	              recursoItem.add(s);    
	       }    
	       return recursoItem;  
	   }
	 
	
}
