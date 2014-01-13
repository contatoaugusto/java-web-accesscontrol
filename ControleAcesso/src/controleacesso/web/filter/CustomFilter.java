package controleacesso.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import controleacesso.web.util.Constantes;


/**
 * A custom filter that denies access if the given username is equal to
 * <b>mike</b>. This filter extends the {@link OncePerRequestFilter} to
 * guarantee that this filter is executed just once.
 * <p>
 * When the user enters this filter, he is already authenticated. This
 * filters acts like an intercept-url where you can customize access levels
 * per user
 * 
 * Na nossa abordagem essa classe serve basicamente para guardar a pagina requisitada, pois não havia um forma mais
 *
 */
public class CustomFilter extends OncePerRequestFilter {

	//protected static Logger logger = Logger.getLogger("filter");
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		logger.debug("Rodando o filtro CustomFilter.");
		
		// Retrieve user details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HttpSession session = request.getSession(true);
        String recursoRequisitado = request.getServletPath();
        
   	 	// O recurso que foi interceptado pelo xml em intercept-url representa sempre a primeira requisição filtrada por esta classe
        if (session.getAttribute(Constantes.SESSAO_ATRIBUTO_RECURSOREQUISITADO) == null)
   	 		session.setAttribute(Constantes.SESSAO_ATRIBUTO_RECURSOREQUISITADO, recursoRequisitado);
        
        //response.sendRedirect("/login.jsf");
        
   	 	// Filter only if user details is not empty; otherwise there's nothing to filter
//        if (authentication == null) {
//        	 
//        	// If the username is equal to mike, deny access
//	        if (authentication.getName().equals("mike") == true ) {
//	        	logger.error("Username and password match. Access denied!");
//	            throw new AccessDeniedException("Username and password match. Access denied!");
//	        }
//	        
//        }
        
        // User details are not empty
        logger.debug("Continua com o restante da cadeia de filtros");
        filterChain.doFilter(request, response);
	}

}
