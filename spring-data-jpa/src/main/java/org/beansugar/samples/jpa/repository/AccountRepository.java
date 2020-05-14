package org.beansugar.samples.jpa.repository;

import org.beansugar.samples.jpa.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author archmagece
 * @since 2014. 7. 27.
 * beansguar.org
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

	@Query("SELECT a FROM Account a WHERE a.email = :email")
	public List<Account> find(@Param("email") String email);

	public List<Account> findByUsername(String username);
}
