package projeto.rest;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

@SpringBootApplication
public class BackendApplication {
	
	@Bean
	public FilterRegistrationBean filterJwt() {
		FilterRegistrationBean filterRb = new FilterRegistrationBean();
		filterRb.setFilter(new TokenFilter());
		filterRb.addUrlPatterns("/private");
		return filterRb;
	}
	
	@Bean
	public FilterRegistrationBean corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(0);
		return bean;
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	
	public class TokenFilter extends GenericFilterBean {

	    @Override
	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	            throws IOException, ServletException {
	        
	        HttpServletRequest req = (HttpServletRequest) request;
	        
	        String header = req.getHeader("Authorization");
	        
	        if(header == null || !header.startsWith("Bearer ")) {
	            throw new ServletException("Token inexistente ou mal formatado!");
	        }
	        
	        // Extraindo apenas o token do cabecalho.
	        String token = header.substring(7);
	        
	        try {
	            Jwts.parser().setSigningKey("melancia").parseClaimsJws(token).getBody();
	        }catch(SignatureException e) {
	            throw new ServletException("Token inválido ou expirado!");
	        }        
	        chain.doFilter(request, response);
	    }
	}
}
