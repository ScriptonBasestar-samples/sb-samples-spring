package org.beansugar.samples.reposiroty.impl;

import lombok.Setter;
import org.beansugar.samples.model.Account;
import org.beansugar.samples.reposiroty.AccountRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author archmagece
 * @since 2014. 7. 27.
 * beansguar.org
 */
public class AccountRepositoryImpl implements AccountRepository{

	@Setter
	private EntityManagerFactory emf;

	@Override
	public void saveAndFlush(Account account) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		em.persist(account);

		em.flush();
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public List<Account> findByUsername(String username) {
		EntityManager em = emf.createEntityManager();

		TypedQuery<Account> query = em.createQuery("select a from Account a where a.username = :username", Account.class);
		query.setParameter("username", username);
		return query.getResultList();
	}

}
