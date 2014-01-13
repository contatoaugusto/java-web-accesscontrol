package controleacesso.web.seguranca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import controleacesso.web.dao.UsuarioDao;
import controleacesso.web.modelo.Usuario;


public class AutorizacaoHandler {

	private static final Log log = LogFactory.getLog(AutorizacaoHandler.class);
	
	// Our custom DAO layer
	private UsuarioDao usuarioDao = new UsuarioDao();

	// We need an Md5 encoder since our passwords in the database are Md5
	// encoded.
	private Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

	public AutorizacaoHandler (){
		log.debug("Iniciando a classe AutorizacaoHandler.");
	}
	
	public Authentication authenticate(Authentication auth, String recursoRequisitado)
			throws AuthenticationException {

		Usuario usuario = null;

		try {
			if (recursoRequisitado != null)
				usuario = usuarioDao.searchDatabase(auth.getName(),
						recursoRequisitado);
			else {
				log.error("Não foi possível recupear a informação de qual é o recurso requisitado!");
				throw new BadCredentialsException(
						"Não foi possível recupear a informação de qual é o recurso requisitado!");
			}
		} catch (Exception e) {
			// log.error("Usuário " + auth.getName() + " não existe!");
			throw new BadCredentialsException(e.getMessage());
		}

		// Comparar senhas
		// Assegura decodificar a senha antes de comparar
		if (passwordEncoder.isPasswordValid(usuario.getDsSenha(),
				(String) auth.getCredentials(), null) == false) {
			log.error("Senha Errada!");
			throw new BadCredentialsException("Senha Errada!");
		}

		// Here's the main logic of this custom authentication manager
		// Username and password must be the same to authenticate
		if (auth.getName().equals(auth.getCredentials()) == true) {
			// logger.debug("Entered username and password are the same!");
			throw new BadCredentialsException(
					"Entered username and password are the same!");

		} else {
			// Na nossa abordagem, se chegou até aqui é porque o usuario tem
			// acesso ao recurso solicitado e pode prosseguir,
			// Ou seja, o acesso é sempre 1, como admin
			log.debug("Todos os dados do usuário estão corretos e pronto pra prosseguir");
			return new UsernamePasswordAuthenticationToken(auth.getName(),
					auth.getCredentials(), getAuthorities(recursoRequisitado));
		}
	}

	/**
	 * Retorna tipo ROLE dependendo do nível de acesso, onde o nível de acesso é
	 * um Integer.
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

	public Collection<GrantedAuthority> getAuthorities(String linkrecurso) {
		// Create a list of grants for this user
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(1);

		log.debug("Dado ACESSO_PERMITIDO ao usuario.");
		authList.add(new GrantedAuthorityImpl(linkrecurso));
		
		// Return granted authoritie
		return authList;
	}
}
