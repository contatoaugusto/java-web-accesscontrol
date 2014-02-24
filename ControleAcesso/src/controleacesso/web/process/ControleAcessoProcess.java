package controleacesso.web.process;

import org.springframework.security.core.authority.GrantedAuthorityImpl;

import controleacesso.web.dao.ControleAcessoUsuarioDao;
import controleacesso.web.modelo.ControleAcessoPerfil;
import controleacesso.web.modelo.ControleAcessoRecurso;
import controleacesso.web.modelo.ControleAcessoUsuario;


/**
 * @author Antonio Augusto
 *
 */
public class ControleAcessoProcess {

	/**
	 * Verifica se o nome de login passado como parâmetro possui acesso no recurso.
	 *
	 */
	public boolean verificaRecursoComAcesso (String nmLogin, String nmRecurso){
		
		ControleAcessoUsuarioDao usuarioDAO = new ControleAcessoUsuarioDao();
		ControleAcessoUsuario usuario = usuarioDAO.findUsuarioByName(nmLogin);
		
		for (ControleAcessoPerfil perfil : usuario.getTbperfils()) {
			for (ControleAcessoRecurso recurso : perfil.getTbrecursos()) {
				if (recurso.getLkLink().replace('/', ' ').trim().equalsIgnoreCase(nmRecurso.replace('/', ' ').trim()) ){
					return true;
				}
			}
		}
		
		return false;
	}
	
	
}
