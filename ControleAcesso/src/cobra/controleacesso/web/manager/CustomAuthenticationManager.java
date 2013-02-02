package cobra.controleacesso.web.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cobra.controleacesso.web.dao.UsuarioDao;
import cobra.controleacesso.web.dao.UsuarioDao;
import cobra.controleacesso.web.modelo.Perfil;
import cobra.controleacesso.web.modelo.Recurso;
import cobra.controleacesso.web.modelo.Usuario;
import cobra.controleacesso.web.util.Constantes;

/**
 * Essa classe faz a implementação de autenticação, o que permite acessar detalhes do usuario existente
 * no banco de dados e se o usuario e senha não são iguais. Entre outras coisas dispara uma,
 * {@link BadCredentialsException}
 *
 * @author Antonio Augusto
 * 
 */
public class CustomAuthenticationManager implements AuthenticationManager {

	private static final Log log = LogFactory
			.getLog(CustomAuthenticationManager.class);

	private UsuarioDao usuarioDao = new UsuarioDao();

	private ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder();

	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {

		log.debug("Na classe CustomAuthenticationManager. Iniciando o m�todo authenticate()");

		Usuario usuario = null;
		
		try {

			UsuarioDao usuariohome = new UsuarioDao();
			usuario = usuariohome.findUsuarioByName(auth.getName());

			if (usuario == null) {
				log.error("Usuário " + auth.getName() + " não encontrado!");
				throw new BadCredentialsException("Usuário " + auth.getName() + " não encontrado!");
			}
		} catch (Exception e) {
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
		if (auth.getName().equals(auth.getCredentials()) == true) {
			log.debug("O usuário informado e a senha são iguais!");
			throw new BadCredentialsException("O usuário informado e a senha são iguais!");

		} else {
			// Em nossa abordagem, se chegou até aqui é porque o usuario tem
			// acesso ao recurso solicitado e pode prosseguir.
			log.debug("Todos os dados do usuário estão corretos e pronto pra prosseguir");
			return new UsernamePasswordAuthenticationToken(auth.getName(),
					auth.getCredentials(), getAuthoritiesByUser(usuario));
		}
	}

	/**
	 * Retorna tipo ROLE dependendo do n�vel de acesso, o qual � definido por um Integer.
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
	 * A partir de um link, ou recurso verifica se está configurado e adiciona a permissão de acesso.
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
	 * Preenche lista de autoriza��es conforme recursos a que o usuario tenha acesso.
	 * Retorna
	 * @param username
	 * @return Collection<GrantedAuthority>
	 */
	public Collection<GrantedAuthority> getAuthoritiesByUser(Usuario usuario) {

		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();

		for (Perfil perfil : usuario.getTbperfils()) {
			for (Recurso recurso : perfil.getTbrecursos()) {
				authList.add(new GrantedAuthorityImpl(recurso.getLkLink()
						.replace('/', ' ').trim()));
			}
		}
		log.debug("Dado ACESSO_PERMITIDO ao usuario.");

		// Return granted authoritie
		return authList;
	}

}
