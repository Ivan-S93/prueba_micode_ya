package zzz.com.micodeya.backend.core.util.security;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import zzz.com.micodeya.backend.core.util.JeResponse;

@Slf4j
// @Service
public class KuaaJwtFilter extends GenericFilterBean {

    // @Value("${jwt.secret}")
    private String secret;

    private String servletContextPath;

    // private Environment environment;

    public KuaaJwtFilter(String secret, String servletContextPath) {
        this.secret = secret;
        this.servletContextPath = servletContextPath;
        // this.environment = environment;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String authHeader = request.getHeader("Authorization");

        String requestUrl = request.getRequestURL().toString();
        String requestUri = request.getRequestURI();

        JeResponse jeResponse = new JeResponse("Jwt Filter exitoso", "Error grave filtro de token.");

        try {

            if (jeResponse.sinErrorValidacion()) {

                // usuarioService.eliminarPorId(id);

            }

            /* AUTOGENERADO _KGC_ */
            jeResponse.prepararRetornoMap();

            /*
             * JwtFilter Request URL:
             * http://localhost:8080/kuaageneratorcore/api/v2/auth/login
             * JwtFilter Request URI: /kuaageneratorcore/api/v2/auth/login
             */
            // System.out.println("JwtFilter Request URL: " + requestUrl);
            System.out.println("JwtFilter Request URI: " + requestUri + ", context: " + servletContextPath);

            // Omitir URLs
            if (requestUri.equals(servletContextPath + "/")
                    || requestUri.startsWith(servletContextPath + "/assets/")
                    || requestUri.startsWith(servletContextPath + "/static/")
                    || requestUri.endsWith("auth/login")
                    || requestUri.endsWith("server/status")
                    || requestUri.contains("/gen/generatorCloud/")
                    || requestUri.contains("/api/public/")

            ) {

                //igual intentar agregar si tiene
                try {
                    String tokenRecuperado = authHeader.substring(7);
                    Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(tokenRecuperado).getBody();
                    request.setAttribute("claims", claims);
                    log.warn("token recuperado en url omitida: "+requestUri);
                } catch (Exception e) {
                    
                }

                filterChain.doFilter(request, response);
                return;
            }

            String tokenRecuperado = null;

            // EL OPTIONS es como una verificacion previa que hacen algunos browsers o
            // clientes
            // https://developer.mozilla.org/en-US/docs/Glossary/Preflight_request
            if ("OPTIONS".equals(request.getMethod())) {
                response.setStatus(HttpServletResponse.SC_OK);
                filterChain.doFilter(request, response);
                return;
            } else if ("GET".equals(request.getMethod())) {
                if (authHeader == null) {
                    tokenRecuperado = request.getParameter("t") == null ? null : request.getParameter("t").toString();
                    if (tokenRecuperado == null) {
                        throw new ServletException("Error al recuperar token en metodo GET: "+requestUri);
                    }

                    request.setAttribute("authorizationtoken", "Bearer " + tokenRecuperado);
                } else {
                    tokenRecuperado = authHeader.substring(7);
                }

            } else {
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    throw new ServletException("An exception occurred");
                }
                tokenRecuperado = authHeader.substring(7);
            }

            final String token = tokenRecuperado;

            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

            request.setAttribute("claims", claims);
            filterChain.doFilter(request, response);

            return;
        } catch (Exception e) {
            jeResponse.prepararRetornoErrorMap(e);
            log.error(jeResponse.getErrorForLog(), e);
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json"); // Establecer el tipo de contenido de la respuesta

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(jeResponse.getRetornoMap());

        PrintWriter out = response.getWriter();
        out.println(json);
        // filterChain.doFilter(request, response);
    }
}