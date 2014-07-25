package org.beansugar.samples.model;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.List;

/**
 * @author archmagece
 * @since  2014. 7. 25
 */
@Entity
@Data
public class Pc {
	@Id
	//자동증가숫자는 디비의 특성을 타는수가 있다
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;


	private String cpu;
	private String vga;

	//하이버네이트에서 기본데이터 타입으로 지원되지 않는 데이터 타입을 사용할 때
	//예전엔 직접 구현해서 썼는데 요즘은 그냥 라이브러리를 지원해준다
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime since;
}
