package controleacesso.web.process;

import org.springframework.security.core.authority.GrantedAuthorityImpl;

import controleacesso.web.dao.UsuarioDao;
import controleacesso.web.modelo.Perfil;
import controleacesso.web.modelo.Recurso;
import controleacesso.web.modelo.Usuario;


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
		
		UsuarioDao usuarioDAO = new UsuarioDao();
		Usuario usuario = usuarioDAO.findUsuarioByName(nmLogin);
		
		for (Perfil perfil : usuario.getTbperfils()) {
			for (Recurso recurso : perfil.getTbrecursos()) {
				if (recurso.getLkLink().replace('/', ' ').trim().equalsIgnoreCase(nmRecurso.replace('/', ' ').trim()) ){
					return true;
				}
			}
		}
		
		return false;
	}
	
	
}
