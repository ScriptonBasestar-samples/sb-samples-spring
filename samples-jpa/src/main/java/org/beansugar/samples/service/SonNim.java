package org.beansugar.samples.service;

import lombok.Setter;
import org.beansugar.samples.model.Account;
import org.beansugar.samples.model.Pc;
import org.beansugar.samples.model.UseLog;
import org.hibernate.criterion.DetachedCriteria;
import org.joda.time.DateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

/**
 * @author archmagece
 * @since  2014. 7. 25
 *
 * 보통은 서비스와 리포지터리를 나눠서 복잡한 쿼리는 리포쪽에 넣고 트랜잭션을 거는데.. 이거 좀 귀찮아서 에러가 안나면 그냥 돌리는편..
 */
public class SonNim {

	@Setter
	private EntityManagerFactory emf;

	/**
	 * 새로온 손님 계정생성
	 * @param account
	 */
	public void newSonNim(Account account){
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		em.persist(account);

		em.flush();
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * 손님 게임시작
	 * 사용자정보by(username),Pc정보by(pcid) -> log체크by(username)
	 * @param username
	 */
	public boolean onna(String username, Long pcid){
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		boolean resultResult = false;
		//사용자정보
		CriteriaBuilder cb = em.getCriteriaBuilder();

//		CriteriaQuery cq = cb.createQuery();
//		Root e = cq.from(Account.class);
//		cq.where(cb.equal(e.get("username"), username));
//		Query query = em.createQuery(cq);
//		Account account = (Account)query.getSingleResult();

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
		Root<Account> from = criteriaQuery.from(Account.class);
		CriteriaQuery<Object> select = criteriaQuery.select(from).where(criteriaBuilder.equal(from.get("username"), username));
		TypedQuery<Object> typedQuery = em.createQuery(select);
		Account account = (Account)typedQuery.getSingleResult();

		//pc정보
		Pc pc = em.find(Pc.class, pcid);

		//로그확인후 사용중이 아니면 입력
		CriteriaBuilder cb2 = em.getCriteriaBuilder();

//		CriteriaQuery cq2 = cb.createQuery();
//		Root e2 = cq2.from(UseLog.class);
//		cq2.where(cb2.equal(e2.get("account"), account));
//		Query query2 = em.createQuery(cq2);
//		UseLog result2 = (UseLog)query2.getSingleResult();

		UseLog result2 = null;
		try{
			CriteriaBuilder criteriaBuilder2 = em.getCriteriaBuilder();
			CriteriaQuery<Object> criteriaQuery2 = criteriaBuilder2.createQuery();
			Root<UseLog> from2 = criteriaQuery2.from(UseLog.class);
			CriteriaQuery<Object> select2 = criteriaQuery2.select(from2).where(criteriaBuilder2.equal(from2.get("account"), account));
			TypedQuery<Object> typedQuery2 = em.createQuery(select2);
			result2 = (UseLog)typedQuery2.getSingleResult();
		}catch (Exception e){
//			e.printStackTrace();
		}

		if(result2==null){
			//리절트2가있는게 정상.입력하면 됨
			UseLog useLog = new UseLog();
			useLog.setAccount(account);
			useLog.setPc(pc);
			useLog.setFromTime(DateTime.now());
//			useLog.setTo(null);
			em.persist(useLog);
			em.flush();
			resultResult = true;
		}else{
			resultResult = false;
		}

		em.flush();
		em.getTransaction().commit();
		em.close();
		return resultResult;
	}

	/**
	 * 손님 게임종료
	 *
	 * 사용자정보by(username),Pc정보by(pcid) -> log체크by(username)
	 * 끔찍한 복붙발생...
	 * @param username
	 */
	public boolean gana(String username){
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		boolean resultResult = false;
		//사용자정보
		CriteriaBuilder cb = em.getCriteriaBuilder();

//		CriteriaQuery cq = cb.createQuery();
//		Root e = cq.from(Account.class);
//		cq.where(cb.equal(e.get("username"), username));
//		Query query = em.createQuery(cq);
//		Account account = (Account)query.getSingleResult();

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
		Root<Account> from = criteriaQuery.from(Account.class);
		CriteriaQuery<Object> select = criteriaQuery.select(from).where(criteriaBuilder.equal(from.get("username"), username));
		TypedQuery<Object> typedQuery = em.createQuery(select);
		Account account = (Account)typedQuery.getSingleResult();

		//로그확인후 사용중이 아니면 입력
		CriteriaBuilder cb2 = em.getCriteriaBuilder();

		UseLog result2 = null;
		try{
			CriteriaBuilder criteriaBuilder2 = em.getCriteriaBuilder();
			CriteriaQuery<Object> criteriaQuery2 = criteriaBuilder2.createQuery();
			Root<UseLog> from2 = criteriaQuery2.from(UseLog.class);
			CriteriaQuery<Object> select2 = criteriaQuery2.select(from2).where(criteriaBuilder2.equal(from2.get("account"), account));
			TypedQuery<Object> typedQuery2 = em.createQuery(select2);
			result2 = (UseLog)typedQuery2.getSingleResult();
		}catch (Exception e){
//			e.printStackTrace();
		}

		if(result2==null){
			//리절트2가 없으면 쓰지않았던 손님이니까 실패
			resultResult = false;
		}else{
			//리절트2가 있으면 로그기록 종료
			result2.setToTime(DateTime.now());
			em.persist(result2);
		}

		em.flush();
		em.getTransaction().commit();
		em.close();
		return resultResult;
	}

	/**
	 * 돈안내고 도망간 사용자 정리
	 */
	public void choono(){
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		TypedQuery<UseLog> query = 	em.createQuery("select log from UseLog log where log.toTime is null", UseLog.class);
		for(UseLog useLog : query.getResultList()){
			useLog.setToTime(DateTime.now());
			em.persist(useLog);
		}

		em.flush();
		em.getTransaction().commit();
		em.close();
	}
}
