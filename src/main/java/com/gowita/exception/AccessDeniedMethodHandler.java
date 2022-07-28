package com.gowita.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gowita.dto.response.ErrorResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class AccessDeniedMethodHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException accessDeniedException) throws IOException {
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ErrorResponse response = new ErrorResponse("Access denied");
        byte[] responseToSend =
                new ObjectMapper().writeValueAsString(response).getBytes(StandardCharsets.UTF_8);
        httpServletResponse.getOutputStream().write(responseToSend);
    }
}
