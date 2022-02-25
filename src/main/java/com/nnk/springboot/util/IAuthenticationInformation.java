/**
 * 
 */
package com.nnk.springboot.util;

import java.security.Principal;

/**
 * Authentication information interface.
 * @author tipikae
 * @version 1.0
 *
 */
public interface IAuthenticationInformation {

	/**
	 * Get username.
	 * @param principal
	 * @return String
	 */
	String getUsername(Principal principal);
}
