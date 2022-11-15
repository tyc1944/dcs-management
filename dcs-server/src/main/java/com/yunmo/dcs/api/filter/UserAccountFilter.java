package com.yunmo.dcs.api.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunmo.dcs.domain.UserAccount;
import com.yunmo.dcs.domain.repository.UserAccountRepository;
import com.yunmo.dcs.infrastruction.exception.LoginPasswordChangeException;
import com.yunmo.dcs.infrastruction.exception.UserAccountDeletedException;
import com.yunmo.domain.common.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Base64;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.oauth2.jwt.JwtClaimNames.IAT;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserAccountFilter extends OncePerRequestFilter {

  private final UserAccountRepository userAccountRepository;
  private final ObjectMapper objectMapper;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    if (request.getRequestURI().toLowerCase().startsWith("/api")) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      Tenant tenant = (Tenant) authentication.getPrincipal();

      UserAccount userAccount =
          userAccountRepository
              .findById(tenant.getId())
              .orElseThrow(
                  () -> {
                    throw new UserAccountDeletedException("账户不存在");
                  });
      if (!userAccount.isEnabled()) {
        throw new DisabledException("账户已禁用");
      }

      String token = request.getHeader(AUTHORIZATION);
      Object iat = objectMapper.readTree(decodeToken(token)).get(IAT);
      Instant iatInstant = Instant.ofEpochSecond(Long.parseLong(iat.toString()));

      if (userAccount.getChangePasswordTime() != null
          && iatInstant.isBefore(userAccount.getChangePasswordTime())) {
        throw new LoginPasswordChangeException("登录账号密码变更");
      }
    }
    filterChain.doFilter(request, response);
  }

  public String decodeToken(String token) {
    String[] chunks = token.split("\\.");
    Base64.Decoder decoder = Base64.getDecoder();
    return new String(decoder.decode(chunks[1]));
  }
}
