package org.beansugar.samples.reposiroty;

import org.beansugar.samples.model.Account;

import java.util.List;

/**
 * @author archmagece
 * @since 2014. 7. 27.
 * beansguar.org
 */
public interface AccountRepository {

	void saveAndFlush(Account account);

	List<Account> findByUsername(String username);

}
