package org.harang.server.security.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.harang.server.constants.jwt.JwtProperties;
import org.harang.server.domain.enums.Type;
import org.harang.server.dto.type.ErrorMessage;
import org.harang.server.exception.CustomException;
import org.harang.server.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        final String token = getTokenFromHeader(request);

        log.debug(String.valueOf(request));

        if (StringUtils.hasText(token) && jwtUtil.verifyToken(token)) {
            Claims claims = jwtUtil.getClaim(token);
            Long memberId = claims.get(JwtProperties.MEMBER_ID_CLAIM_NAME, Long.class);
            Type role = claims.get(JwtProperties.MEMBER_ROLE_CLAIM_NAME, Type.class);
            if (role == null) {
                throw new CustomException(ErrorMessage.INVALID_TOKEN_TYPE);
            }
            // type 값을 GrantedAuthority 객체 콜렉션으로 변환
            List<GrantedAuthority> roles = Collections.singletonList(new SimpleGrantedAuthority(role.getValue()));
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(memberId, null, roles);

            // TODO: 왜 쓰는지 알아보기
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // security context에 authentication 객체 저장
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTH_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtProperties.BEARER)) {
            return bearerToken.substring(JwtProperties.BEARER.length());
        }
        log.debug("요청 헤더에 토큰이 존재하지 않습니다.");
        return null;
    }
}
