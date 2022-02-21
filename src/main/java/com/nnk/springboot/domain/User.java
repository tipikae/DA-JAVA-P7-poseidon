package com.nnk.springboot.domain;

import javax.persistence.*;

import lombok.Data;

/**
 * User entity.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
@Entity
@Table(name = "users")
public class User {
	
	/**
	 * Id.
	 */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "TINYINT")
    private Integer id;
    
    /**
     * Username.
     */
    @Column(name = "username")
    private String username;
    
    /**
     * Password.
     */
    @Column(name = "password")
    private String password;
    
    /**
     * Fullname.
     */
    @Column(name = "fullname")
    private String fullname;
    
    /**
     * Role.
     */
    @Column(name = "role")
    private String role;
}
