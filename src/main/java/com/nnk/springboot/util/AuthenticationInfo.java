/**
 * 
 */
package com.nnk.springboot.util;

import java.security.Principal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

/**
 * Authentication informations.
 * @author tipikae
 * @version 1.0
 *
 */
@Component
public class AuthenticationInfo implements IAuthenticationInformation {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationInfo.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUsername(Principal principal) {
		LOGGER.debug("getUsername");
		if(principal instanceof UsernamePasswordAuthenticationToken){
			LOGGER.debug("getUsername: UsernamePasswordAuthenticationToken");
			return getUsernamePasswordLoginInfo(principal);
		} else if(principal instanceof OAuth2AuthenticationToken){
			LOGGER.debug("getUsername: OAuth2AuthenticationToken");
			 return getOauth2LoginInfo(principal);
        }
		
		return null;
	}

	private String getUsernamePasswordLoginInfo(Principal user) {
		LOGGER.debug("getUsernamePasswordLoginInfo");
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) user;
		if (token.isAuthenticated()) {
			LOGGER.debug("getUsernamePasswordLoginInfo: authenticated");
			User u = (User) token.getPrincipal();
			return u.getUsername();
		} else {
			LOGGER.debug("getUsernamePasswordLoginInfo: return null");
			return "?";
		}
	}

	private String getOauth2LoginInfo(Principal user) {
		LOGGER.debug("getOauth2LoginInfo");
		OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) user;
		OAuth2User principal = authToken.getPrincipal();
		
		OidcIdToken idToken = getIdToken(principal);
		if(idToken != null) {
			LOGGER.debug("getOauth2LoginInfo: idToken != null");
			return idToken.getEmail();
		} else if(authToken.isAuthenticated()) {
			LOGGER.debug("getOauth2LoginInfo: authenticated");
			Map<String,Object> userAttributes = ((DefaultOAuth2User) authToken.getPrincipal()).getAttributes();
			return (String) userAttributes.get("login");
		}
		return "?";
	}
	
	private OidcIdToken getIdToken(OAuth2User principal){
		LOGGER.debug("getIdToken");
		if(principal instanceof DefaultOidcUser) {
			LOGGER.debug("getIdToken: instance of DefaultOidcUser");
			DefaultOidcUser oidcUser = (DefaultOidcUser)principal;
			return oidcUser.getIdToken();
		}
		return null;
	}
}
