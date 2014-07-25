package org.beansugar.samples;

import org.beansugar.samples.model.Account;
import org.beansugar.samples.model.Pc;
import org.beansugar.samples.model.Price;
import org.beansugar.samples.service.SonNim;
import org.joda.time.DateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author archmagece
 * @since 2014. 7. 25.
 * beansguar.org
 */
public class Main {
	private static final String PERSISTENCE_UNIT_NAME = "pcbang";
	private static EntityManagerFactory factory;

	public static void main(String[] args) throws SQLException {

		Connection dbConnection = DriverManager.getConnection("jdbc:hsqldb:mem:pcbangdb;shutdown=true", "sa", null);

		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		//샘플데이터 입력
		for(int i=0;i<10;i++){
			Pc pc = new Pc();
//		pc.setId(0L);
			pc.setCpu("4870");
			pc.setVga("nvidia");
//			pc.setSince(DateTime.parse("20140101"));
			pc.setSince(DateTime.now());
			em.persist(pc);
		}

		Price price = new Price();
//		price.setId(0L);
		price.setPricePerHour(500);
		em.persist(price);

//		em.flush();
		em.getTransaction().commit();
		em.close();


		//손님 서비스 생성
		SonNim sonNim = new SonNim();
		sonNim.setEmf(factory);

		//새손님 왔다
		Account account = new Account();
		account.setActivatated(true);
		account.setEmail("user1@a.com");
		account.setPhone("0101010101");
		account.setUsername("user1");
		sonNim.newSonNim(account);
		//두명왔다
		Account account2 = new Account();
		account2.setActivatated(true);
		account2.setEmail("user2@a.com");
		account2.setPhone("0101010102");
		account2.setUsername("user2");
		sonNim.newSonNim(account2);

		//사용시작
		sonNim.onna("user1", 0L);
		sonNim.onna("user2", 1L);

		//사용종료
		sonNim.gana("user1");

		//도망간놈 종료
		sonNim.choono();

		//로그보기
		em = factory.createEntityManager();
		Query query = em.createQuery("select u from UseLog u");
		System.out.println("사용기록");
		for(Object object : query.getResultList()){
			System.out.println(object);
		}
	}
}
