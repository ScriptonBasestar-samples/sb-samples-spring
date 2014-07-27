package org.beansugar.samples;

import org.beansugar.samples.model.Account;
import org.beansugar.samples.reposiroty.impl.AccountRepositoryImpl;
import org.beansugar.samples.service.AccountService;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
//		EntityManager em = factory.createEntityManager();
//		em.getTransaction().begin();

		AccountRepositoryImpl accountRepository = new AccountRepositoryImpl();
		accountRepository.setEmf(factory);

		AccountService accountService = new AccountService();
		accountService.setAccountRepository(accountRepository);

		//샘플데이터 입력
		Account account1 = new Account();
		account1.setUsername("user1");
		account1.setPhone("01000000001");
		account1.setEmail("a@a.com");
		account1.setActivatated(true);

		//데이터 보기
		Account result = accountService.showUser("user1");
		System.out.println(result);
	}
}
