package org.example.todo.SecurityConfig;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;


@RequiredArgsConstructor
@Component
//OncePerRequestFilter make filter for everytime receive request
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTService jwtservice;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
          @Nonnull  HttpServletRequest request,
          @Nonnull HttpServletResponse response,
          @Nonnull  FilterChain filterChain

    ) throws ServletException, IOException {

        final String AuthHeader=request.getHeader("Authorization");

        final String JWT;


        if(AuthHeader==null ||!AuthHeader.startsWith("Bearer ") ){
            filterChain.doFilter(request,response);
            return;
        }

        JWT=AuthHeader.substring(7);

        final String userEmail=jwtservice.extractUsername(JWT);

        if(userEmail!=null && SecurityContextHolder.getContext().getAuthentication()==null){

            UserDetails userDetails=this.userDetailsService.loadUserByUsername(userEmail);

            if (jwtservice.isTokenValid(JWT,userDetails)){

                UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}