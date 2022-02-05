package org.by.bharadwaj.fundoo.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "USER_DETAILS")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "FIRST_NM",columnDefinition ="varchar(20)")
	private String firstName;
	
	private String lastName;
	
	@Column(name = "PHONE_NUM",nullable = false,unique = true)
	private String phoneNumber;
	
	@Column(name = "EMAIL",nullable = false,unique = true)
	private String email;

	@JsonIgnore
	@Column(name = "PASSWORD",nullable = false)
	private String password;
	
	private Boolean isEmailVerified;
	
	@CreationTimestamp
	private LocalDateTime createdTimestamp;
	
	@OneToMany(targetEntity = Note.class)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private List<Note> notes;

}
