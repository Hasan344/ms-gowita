package com.gowita.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gowita.dto.response.ErrorResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse, AuthenticationException ex)
            throws IOException, ServletException {
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ErrorResponse response = new ErrorResponse("Unauthorized");
        byte[] responseToSend = new ObjectMapper().writeValueAsString(response).getBytes(
                StandardCharsets.UTF_8);
        httpServletResponse.getOutputStream().write(responseToSend);
    }
}

