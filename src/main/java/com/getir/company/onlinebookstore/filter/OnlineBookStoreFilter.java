package com.getir.company.onlinebookstore.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.getir.company.onlinebookstore.config.BookStoreConfig;
import com.getir.company.onlinebookstore.constant.OnlineBookStoreConstant;
import com.getir.company.onlinebookstore.core.pojo.TokenDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * 
 * OnlineBookStoreFilter - Security Filter to secure the endpoints.
 * 
 * @author Saravanan Perumal
 *
 */
@Component
public class OnlineBookStoreFilter extends GenericFilterBean {

	@Autowired
	private BookStoreConfig config;

	@Autowired
	private Environment env;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		final HttpServletResponse response = (HttpServletResponse) servletResponse;

		if (isExcludeUrl(httpServletRequest.getRequestURI())) {
			String sso_jwt = getToken(httpServletRequest);
			String apiKey = httpServletRequest.getHeader("x-api-key");
			String amId = httpServletRequest.getHeader("am_id");

			if (StringUtils.isNotEmpty(sso_jwt) || StringUtils.isNotEmpty(apiKey) || StringUtils.isNotEmpty(amId)) {

				if (!apiKey.equals(config.getApikey())) {
					throw new RuntimeException("Unauthorized Access", new Throwable("Invalid Identifiers"));
				}

				// SSO_JWT TOKEN VALIDATION

				String userId = "";

				if (config.getJwtEnabled() != null
						&& OnlineBookStoreConstant.JWT_ENABLED.equalsIgnoreCase(config.getJwtEnabled())) {
					TokenDto decodeToken = decodeToken(sso_jwt);

					if (StringUtils.isEmpty(decodeToken.getUserId()))
						throw new RuntimeException("Unauthorized Access");

					userId = decodeToken.getUserId();
				} else {
					userId = config.getDevUser();
				}

				response.setHeader("userId", userId);
				httpServletRequest.setAttribute("userId", userId);

			} else {
				throw new RuntimeException("Unauthorized Access");
			}
		}

		chain.doFilter(httpServletRequest, servletResponse);

	}

	private TokenDto decodeToken(String ssoJwt) {

		Claims claim = Jwts.parser().setSigningKey(config.getSigningkey()).parseClaimsJws(ssoJwt.trim()).getBody();

		return TokenDto.builder().userId(String.valueOf(claim.get("userId"))).build();

	}

	private String getToken(HttpServletRequest httpServletRequest) {
		String authToken = httpServletRequest.getHeader(OnlineBookStoreConstant.AUTHORISATION);

		if (authToken == null || !authToken.startsWith(OnlineBookStoreConstant.TOKEN_PREFIX))
			throw new RuntimeException("Unauthorized Access");

		return authToken.replace(OnlineBookStoreConstant.TOKEN_PREFIX, "");
	}

	private boolean isExcludeUrl(String requestUri) {
		String excludeUrl = env.getProperty("getir.authentication.url.exclude");
		String[] excludeUrls = excludeUrl.split(",");

		for (int i = 0; i < excludeUrls.length; i++) {
			if (requestUri.contains(excludeUrls[i]))
				return false;
		}

		return true;
	}
}
