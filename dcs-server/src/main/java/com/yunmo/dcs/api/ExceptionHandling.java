package com.yunmo.dcs.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;

import static org.zalando.problem.Status.UNAUTHORIZED;

@ControllerAdvice
public class ExceptionHandling extends com.yunmo.boot.web.ExceptionHandling {

  @ExceptionHandler(InvalidBearerTokenException.class)
  public ResponseEntity<Problem> handleInvalidBearerTokenException(
      final InvalidBearerTokenException exception, final NativeWebRequest request) {
    return ResponseEntity.status(UNAUTHORIZED.getStatusCode())
        .body(
            Problem.builder()
                .withStatus(UNAUTHORIZED)
                .withTitle("鉴权失败")
                .withDetail("无效的token信息")
                .build());
  }
}
