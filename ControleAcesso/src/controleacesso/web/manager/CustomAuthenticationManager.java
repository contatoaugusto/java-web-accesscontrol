package controleacesso.web.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import controleacesso.web.dao.ControleAcessoUsuarioDao;
import controleacesso.web.modelo.ControleAcessoPerfil;
import controleacesso.web.modelo.ControleAcessoRecurso;
import controleacesso.web.modelo.ControleAcessoUsuario;


/**
 * Essa classe faz a implementa√ß√£o de autentica√ß√£o, o que permite acessar detalhes do usuario existente
 * no banco de dados e se o usuario e senha n√£o s√£o iguais. Entre outras coisas dispara uma,
 * {@link BadCredentialsException}
 *
 * @author Antonio Augusto
 * 
 */
public class CustomAuthenticationManager implements AuthenticationManager {

	private static final Log log = LogFactory
			.getLog(CustomAuthenticationManager.class);

	//private UsuarioDao usuarioDao = new UsuarioDao();

	private ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder();

	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {

		log.debug("Na classe CustomAuthenticationManager. Iniciando o mÈtodo authenticate()");

		ControleAcessoUsuario usuario = null;
		
		try {

			ControleAcessoUsuarioDao usuariohome = new ControleAcessoUsuarioDao();
			usuario = usuariohome.findUsuarioByName(auth.getName());

			if (usuario == null) {
				log.error("Usu·rio " + auth.getName() + " n„o encontrado!");
				throw new BadCredentialsException("Usu·rio " + auth.getName() + " n„o encontrado!");
			}
		} catch (NoResultException e) {
			log.error(e.getMessage());
			throw new BadCredentialsException(e.getMessage());
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new BadCredentialsException(e.getMessage());
		}
		

		// Comparar senhas
		// Assegura que decodifica a senha antes de comparar
		if (passwordEncoder.isPasswordValid(usuario.getDsSenha(),
				(String) auth.getCredentials(), null) == false) {
			log.error("Senha Iconrreta!");
			throw new BadCredentialsException("Senha Incorreta!");
		}

		// Here's the main logic of this custom authentication manager
		// Username and password must be the same to authenticate
//		if (!auth.getName().equals(auth.getCredentials())) {
//			log.debug("O usu·rio informado ou a senha incorretos!");
//			throw new BadCredentialsException("O usu·rio informado ou a senha incorretos!");

//		} else {
			// Em nossa abordagem, se chegou at√© aqui √© porque o usuario tem
			// acesso ao recurso solicitado e pode prosseguir.
			log.debug("Todos os dados do usu√°rio est√£o corretos e pronto pra prosseguir");
			//HttpSession session = auth. request.getSession(true);
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			attr.getRequest().getSession(true); 
			attr.getRequest().getSession().setAttribute("usuario", usuario);
			return new UsernamePasswordAuthenticationToken(auth.getName(),
					auth.getCredentials(), getAuthoritiesByUser(usuario));
//		}
	}

	/**
	 * Retorna tipo ROLE dependendo do nÌvel de acesso, o qual È definido por um Integer.
	 * 
	 * @param access
	 *            an integer value representing the access of the user
	 * @return collection of granted authorities
	 */
	public Collection<GrantedAuthority> getAuthorities(Integer access) {
		// Create a list of grants for this user
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(1);

		// All users are granted with ROLE_USER access
		// Therefore this user gets a ROLE_USER by default
		// logger.debug("Grant ROLE_USER to this user");

		// Check if this user has admin access
		// We interpret Integer(1) as an admin user
		if (access.compareTo(1) == 0) {
			log.debug("Dado ACESSO_PERMITIDO ao usuario.");
			authList.add(new GrantedAuthorityImpl("ACESSO_PERMITIDO"));
		} else {
			log.debug("Dado ACESSO_NEGADO ao usuario.");
			authList.add(new GrantedAuthorityImpl("ACESSO_NEGADO"));
		}
		// Return list of granted authorities
		return authList;
	}

	/**
	 * A partir de um link, ou recurso verifica se est√° configurado e adiciona a permiss√£o de acesso.
	 * 
	 * @param linkrecurso
	 * @return
	 */
	public Collection<GrantedAuthority> getAuthorities(String linkrecurso) {
		// Create a list of grants for this user
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(1);

		log.debug("Dado ACESSO_PERMITIDO ao usuario.");
		authList.add(new GrantedAuthorityImpl(linkrecurso.replace('/', ' ')
				.trim()));

		// Return granted authoritie
		return authList;
	}

	/**
	 * Preenche lista de autorizaÁıes conforme recursos a que o usuario tenha acesso.
	 * Retorna
	 * @param username
	 * @return Collection<GrantedAuthority>
	 */
	public Collection<GrantedAuthority> getAuthoritiesByUser(ControleAcessoUsuario usuario) {

		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();

		for (ControleAcessoPerfil perfil : usuario.getTbperfils()) {
			for (ControleAcessoRecurso recurso : perfil.getTbrecursos()) {
				authList.add(new GrantedAuthorityImpl(recurso.getLkLink()
						.replace('/', ' ').trim()));
			}
		}
		log.debug("Dado ACESSO_PERMITIDO ao usuario.");

		// Return granted authoritie
		return authList;
	}

}
