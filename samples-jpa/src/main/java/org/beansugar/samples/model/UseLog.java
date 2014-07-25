package org.beansugar.samples.model;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * @author archmagece
 * @since 2014. 7. 25.
 * beansguar.org
 */
@Entity
@Data
public class UseLog {
	@Id
	//자동증가숫자는 디비의 특성을 타는수가 있다
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Account account;

	@ManyToOne
	private Pc pc;

	//하이버네이트에서 기본데이터 타입으로 지원되지 않는 데이터 타입을 사용할 때
	//예전엔 직접 구현해서 썼는데 요즘은 그냥 라이브러리를 지원해준다
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	//업데이트방식을쓸지 누적을쓸지는 디비의 부하에 따라 다른데 피씨방정도는.. 업데이트방식도 괜찮고
	//서버의 로그인기록이라면 데이터쌓기방식을써야할 것 같다.
	//그냥 순차쓰기도 부담이 될 정도라면 디비를 고집해서는 안될 것 같고
	private DateTime fromTime;
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime toTime;
}
