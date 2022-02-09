package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * User entity.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
@Entity
@Table(name = "Users")
public class User {
	
	/**
	 * Id.
	 */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "TINYINT")
    private Integer id;
    
    /**
     * Username.
     */
    @NotBlank(message = "Username is mandatory")
    @Column(name = "username")
    private String username;
    
    /**
     * Password.
     */
    @NotBlank(message = "Password is mandatory")
    @Column(name = "password")
    private String password;
    
    /**
     * Fullname.
     */
    @NotBlank(message = "FullName is mandatory")
    @Column(name = "fullname")
    private String fullname;
    
    /**
     * Role.
     */
    @NotBlank(message = "Role is mandatory")
    @Column(name = "role")
    private String role;
}
