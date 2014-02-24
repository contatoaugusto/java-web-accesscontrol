package controleacesso.web.dao;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.BadCredentialsException;

import controleacesso.web.criptografia.EncripDecriptUtil;
import controleacesso.web.facade.ControleAcessoAbstractFacade;
import controleacesso.web.modelo.ControleAcessoPerfil;
import controleacesso.web.modelo.ControleAcessoPessoa;
import controleacesso.web.modelo.ControleAcessoRecurso;
import controleacesso.web.modelo.ControleAcessoUsuario;
import controleacesso.web.util.Constantes;


/**
 * Home object for domain model class Usuario.
 * 
 * @see ControleAcessoUsuario.Usuario
 * @author Antonio Augusto
 */
public class ControleAcessoUsuarioDao extends ControleAcessoAbstractFacade<ControleAcessoUsuario> {

	public ControleAcessoUsuarioDao() {
		super(ControleAcessoUsuario.class);
		// TODO Auto-generated constructor stub
	}
	
	private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em = createEntityManager();
    }
	
	private static final Log log = LogFactory.getLog(ControleAcessoUsuarioDao.class);

	public void save(ControleAcessoUsuario objDao) {
		log.debug("Salvando instância de Usuario");
		
		EncripDecriptUtil criptografia = new EncripDecriptUtil();
		objDao.setDsSenha(criptografia.encripta(objDao.getDsSenha(), Constantes.CRIPTOGRAFIA_TIPO_ALGORITMO));
		
		create(objDao);
	}
	public void remove(ControleAcessoUsuario objDao) {
		log.debug("Removendo instância de Usuario");
		
		getEntityManager();
		for (ControleAcessoPerfil perfil : objDao.getTbperfils()) {
			
			em.getTransaction().begin();
	    	em.createQuery("delete from dbo.tbusuario_perfil where id_usuario = "
					+ objDao.getIdUsuario() + " and id_perfil = "
					+ perfil.getIdPerfil())
					.executeUpdate();
//	    	int numberDeleted = entityManager.createQuery("DELETE FROM GroupUser gu WHERE gu.group.id=:id").setParameter("id",group.getId()).executeUpdate();
		}
	}

	public void update(ControleAcessoUsuario objDao, boolean criptografar) {
		log.debug("Atualizando instância de Usuario");
		
		if (criptografar){
			EncripDecriptUtil criptografia = new EncripDecriptUtil();
			objDao.setDsSenha(criptografia.encripta(objDao.getDsSenha(), Constantes.CRIPTOGRAFIA_TIPO_ALGORITMO));
		
		}
		
		edit(objDao);
	}

	public ControleAcessoUsuario getObjDao(int id) {
		ControleAcessoUsuario pessoa = find(id);
		return pessoa;
	}

	public List<ControleAcessoUsuario> list() {
		List<ControleAcessoUsuario> lista = findAll();
		return lista;
	}

	public ControleAcessoUsuario findUsuarioByName(String nmLogin) throws NoResultException{
		log.debug("Obtendo Usuario com o nome: " + nmLogin);
		
		getEntityManager();
		ControleAcessoUsuario usuario;
		try {
			usuario = (ControleAcessoUsuario) em.createNamedQuery("ControleAcessoUsuario.findByLogin")
					.setParameter("nmLogin", nmLogin).getSingleResult();
		}catch (NoResultException e){
			throw new NoResultException("Usuário "+ nmLogin + " não encontrado");
		}
		return usuario;
	}

	public List<ControleAcessoPessoa> getItemsPessoa() {
		ControleAcessoPessoaDao pessoaDAO = new ControleAcessoPessoaDao();
		List<SelectItem> statusItem = new ArrayList<SelectItem>();
//		List<ControleAcessoPessoa> listaSA = pessoaDAO.list();
//		for (ControleAcessoPessoa cbx : listaSA) {
//			SelectItem s = new SelectItem();
//			s.setValue(cbx.getIdPessoa());
//			s.setLabel(cbx.getNmPessoa());
//			statusItem.add(s);
//		}
//		return statusItem;
		
		return pessoaDAO.list();
	}

	/***
	 * Verifica se o usuário existe e se este possui perfil de acesso definido.
	 * 
	 * @param username
	 * @return Usuario
	 */
	public ControleAcessoUsuario searchDatabase(String username, String linkrecurso) {
		// Retrieve all users from the database
		ControleAcessoUsuarioDao usuariohome = new ControleAcessoUsuarioDao();
		ControleAcessoUsuario usuario = usuariohome.findUsuarioByName(username);

		if (usuario != null) {

			log.debug("Usuário encontrado.");

			// Verifica se o usuário tem perfil definifo e se sim, verifica se
			// este possui o recurso requisitado
			String recursoEntity = "";
			linkrecurso = linkrecurso.replace('/', ' ').trim();
			linkrecurso = linkrecurso.contains(".") ? linkrecurso.replace(".",
					"::").split("::")[0] : linkrecurso;
			for (ControleAcessoPerfil perfil : usuario.getTbperfils()) {
				for (ControleAcessoRecurso recurso : perfil.getTbrecursos()) {
					recursoEntity = recurso.getLkLink().contains(".") ? recurso
							.getLkLink().replace(".", "::").split("::")[0]
							: recurso.getLkLink();
					if (recursoEntity.equals(linkrecurso))
						return usuario;
				}
				log.error("Usuário sem acesso ao recurso: " + linkrecurso);
				throw new RuntimeException("Usuário sem acesso ao recurso: "
						+ linkrecurso);
			}
			log.error("Usuário sem perfil de acesso definido!");
			throw new RuntimeException("Usuário sem perfil de acesso definido!");
		}

		log.error("Usuário " + username + " não existe!");
		throw new RuntimeException("Usuário " + username + " não existe!");
	}

}
