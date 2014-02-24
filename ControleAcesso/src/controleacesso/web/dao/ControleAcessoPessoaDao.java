package controleacesso.web.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import controleacesso.web.facade.ControleAcessoAbstractFacade;
import controleacesso.web.modelo.ControleAcessoPessoa;


/**
 * Home object for domain model class Pessoa.
 * @see controleacesso.web.modelo.ControleAcessoPessoa
 * @author Antonio Augusto
 */
public class ControleAcessoPessoaDao extends ControleAcessoAbstractFacade<ControleAcessoPessoa> {

	
	public ControleAcessoPessoaDao() {
		super(ControleAcessoPessoa.class);
		// TODO Auto-generated constructor stub
	}
	
	private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em = createEntityManager();
    }
    
	private static final Log log = LogFactory.getLog(ControleAcessoPessoaDao.class);

	public void save(ControleAcessoPessoa objDao) {
		log.debug("Salvando instância de Pessoa");
		
		create(objDao);
	}
	
	
	public void remove(ControleAcessoPessoa objDao) {
		log.debug("Removendo instância de Pessoa");
		remove(objDao);
	}

	
	public void update(ControleAcessoPessoa objDao) {
		log.debug("Atualizando instância de Pessoa");
		edit(objDao);
	}

	public ControleAcessoPessoa getObjDao(int id) {
		ControleAcessoPessoa pessoa =  (ControleAcessoPessoa) find(id);
		return pessoa;
	}
	
	public List<ControleAcessoPessoa> list() {
		getEntityManager();
		List<ControleAcessoPessoa> lista = em.createNamedQuery("ControleAcessoPessoa.findAll").getResultList();
		return lista;
	}

}
