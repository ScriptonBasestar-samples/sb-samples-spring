package org.beansugar.samples.model;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.util.List;

/**
 * @author archmagece
 * @since  2014. 7. 25
 */
@Entity
@Data
public class Account {
	@Id
	//자동증가숫자는 디비의 특성을 타는수가 있다
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

//	@Index(unique = true, columnList = "username", name = "idx_username")
	@Column
	private String username;
	@Email
	private String email;
	private String phone;

	private boolean activatated;
}
