package com.b2camp.simple_core_banking.component;

import com.b2camp.simple_core_banking.utils.JwtUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/*")
public class FilterInterceptor implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inisialisasi jika diperlukan
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Cek apakah URL adalah /login, jika ya, lewati filter
        if (httpRequest.getRequestURI().equals("/users/login")) {
            chain.doFilter(request, response);  // Lewati filter jika URL adalah /login
            return;
        }

        // Ambil token dari header Authorization
        String authorizationHeader = httpRequest.getHeader("x-authorization");

        // Cek apakah header ada dan dimulai dengan "Bearer"
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Missing or invalid Authorization header");
            return;
        }

        String token = authorizationHeader.substring(7); // Hapus "Bearer " dari header

        try {
            // Validasi token menggunakan utilitas JWT
            String username = JwtUtil.validateToken(token);

            // Simpan username di request attribute
            httpRequest.setAttribute("username", username);

        } catch (Exception e) {
            // Token tidak valid
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Invalid or expired token");
            return;
        }

        // Lanjutkan ke filter berikutnya
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup jika diperlukan
    }
}
