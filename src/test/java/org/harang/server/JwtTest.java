package org.harang.server;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.Principal;
import org.harang.server.domain.enums.Type;
import org.harang.server.dto.response.JwtTokenResponse;
import org.harang.server.interceptor.pre.MemberIdArgumentResolver;
import org.harang.server.util.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;

@SpringBootTest
public class JwtTest {

    // TODO: 다시 보기

    @Autowired
    JwtUtil jwtUtil;

    @Test
    @DisplayName("토큰이 생성되는지 확인하는 테스트")
    void generateTokenTest() {
        JwtTokenResponse tokens = jwtUtil.generateTokens(Long.valueOf(1), Type.SPROUT);
        System.out.println(tokens.toString());
    }

    @Test
    @DisplayName("memberIdResolver 테스트")
    public void memberIdResolveTest() throws Exception {
        NativeWebRequest webRequest = mock(NativeWebRequest.class);
        Principal principal = () -> "123";
        when(webRequest.getUserPrincipal()).thenReturn(principal);

        MethodParameter methodParameter = mock(MethodParameter.class);

        MemberIdArgumentResolver resolver = new MemberIdArgumentResolver();

        Long resolvedMemberId = (Long) resolver.resolveArgument(methodParameter, null, webRequest, null);

        assertEquals(Long.valueOf(123), resolvedMemberId);
    }

}
