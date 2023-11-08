package net.ausiasmarch.compraventa.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.ausiasmarch.compraventa.helper.JWTHelper;

@Component
public class JWTFilter implements Filter {

    @Override
    public void init(FilterConfig oFilterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest oServletRequest, ServletResponse oServletResponse, FilterChain oFilterChain)
    throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) oServletRequest;
        HttpServletResponse response = (HttpServletResponse) oServletResponse;

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            oFilterChain.doFilter(oServletRequest, oServletResponse);
        } else {
            String auth = request.getHeader("Authorization");

            if ( auth == null || !auth.startsWith("Bearer ")) {

            } else {
                String token = auth.substring(7);
                try {
                    String username = JWTHelper.validateJWT(token);
                    request.setAttribute("username", username);
                } catch (Exception e) {
                    throw new ServletException("invalid token");
                }
            }
            oFilterChain.doFilter(oServletRequest, oServletResponse);
        }

    }

    @Override
    public void destroy() {
        
    }
    
}
