package com.nnk.springboot.unit.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.nnk.springboot.util.AuthenticationInfoImpl;

@ExtendWith(MockitoExtension.class)
class AuthenticationInfoTest {
	
	@Mock
	private UsernamePasswordAuthenticationToken token;
	
	@Mock
	private OAuth2AuthenticationToken oauthToken;
	
	@Mock
	private OAuth2User oauth2User;
	
	@Mock
	private User user;
	
	@Mock
	private DefaultOidcUser oidcUser;
	
	@Mock
	private OidcIdToken idToken;
	
	private AuthenticationInfoImpl authInfo = new AuthenticationInfoImpl();

	@Test
	void getUsernameReturnsQuestionMarkWhenBadInstance() {
		assertEquals("?", authInfo.getUsername(mock(Principal.class)));
	}

	@Test
	void getUsernameReturnsUsernameWhenUsernamePasswordAuthenticated() {
		Principal principal = token;
		when(token.isAuthenticated()).thenReturn(true);
		when(token.getPrincipal()).thenReturn(user);
		when(user.getUsername()).thenReturn("username");
		assertEquals("username", authInfo.getUsername(principal));
	}

	@Test
	void getUsernameReturnsQuestionMarkWhenUsernamePasswordNotAuthenticated() {
		Principal principal = token;
		when(token.isAuthenticated()).thenReturn(false);
		assertEquals("?", authInfo.getUsername(principal));
	}

	@Test
	void getUsernameReturnsUsernameWhenOidcNotNull() {
		Principal user = oauthToken;
		oauth2User = oidcUser;
		when(oauthToken.getPrincipal()).thenReturn(oauth2User);
		when(oidcUser.getIdToken()).thenReturn(idToken);
		when(idToken.getEmail()).thenReturn("Email");
		assertEquals("Email", authInfo.getUsername(user));
	}

	@Test
	void getUsernameReturnsUsernameWhenOidcNullAndOAuth2Authenticated() {
		Principal user = oauthToken;
		when(oauthToken.getPrincipal()).thenReturn(oauth2User);
		when(oauthToken.isAuthenticated()).thenReturn(true);
		DefaultOAuth2User defaultOAuth2 = mock(DefaultOAuth2User.class);
		when(oauthToken.getPrincipal()).thenReturn(defaultOAuth2);
		Map<String, Object> atttributes = new HashMap<>();
		atttributes.put("login", "Login");
		when(defaultOAuth2.getAttributes()).thenReturn(atttributes);
		assertEquals("Login", authInfo.getUsername(user));
	}

	@Test
	void getUsernameReturnsQuestionMarkWhenOidcNullAndOAuth2NotAuthenticated() {
		Principal user = oauthToken;
		when(oauthToken.getPrincipal()).thenReturn(oauth2User);
		when(oauthToken.isAuthenticated()).thenReturn(false);
		assertEquals("?", authInfo.getUsername(user));
	}

}
