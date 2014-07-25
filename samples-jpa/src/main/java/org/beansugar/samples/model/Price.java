package org.beansugar.samples.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author archmagece
 * @since  2014. 7. 25
 */
@Entity
@Data
public class Price {
	@Id
	//자동증가숫자는 디비의 특성을 타는수가 있다
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 분당 겜비
	 */
	private Integer pricePerHour = 500;
}
