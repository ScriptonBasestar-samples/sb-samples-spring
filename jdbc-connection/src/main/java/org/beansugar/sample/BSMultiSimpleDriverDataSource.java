package org.beansugar.sample;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.util.Assert;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * @author archmagece@gmail.com
 * @since 2014-02-05
 * 디비두개이상을 한개 데이터소스에서 관리하는 경우.. 풀낫지원
 */
public class BSMultiSimpleDriverDataSource extends SimpleDriverDataSource {

	private List<ConnectionInfo> connInfos;

	public BSMultiSimpleDriverDataSource(List<ConnectionInfo> connInfo) {
		this.connInfos = new LinkedList<>();
		this.connInfos.addAll(connInfo);
	}

	@Override
	protected Connection getConnectionFromDriver(Properties props) throws SQLException {
		Connection conn = null;
		int sidoHeoutSsoo = 0;
		while (true) {
			//접속정보 재설정
			Driver driver = getDriver();
			String url = getUrl();
			props.setProperty("user", getUsername());
			props.setProperty("password", getPassword());
			Assert.notNull(driver, "Driver must not be null");

			if (logger.isDebugEnabled()) {
				logger.debug("Creating new JDBC Driver Connection to [" + url + "]");
			}
			try {
				conn = driver.connect(url, props);
				break;
//			}catch(org.springframework.jdbc.CannotGetJdbcConnectionException e){
			} catch (Exception e) {
//				e.printStackTrace();
//				System.out.println(sidoHeoutSsoo+"  "+connInfos.size());
				if (sidoHeoutSsoo >= connInfos.size()) {
//					break;
					throw new SQLException("캔낫코넥스데이라베이스놈버" + sidoHeoutSsoo);
				}
				ConnectionInfo connInfo = connInfos.get(sidoHeoutSsoo++);
				setUrl(connInfo.getUrl());
				setUsername(connInfo.getUsername());
				setPassword(connInfo.getPassword());
				continue;
			}
		}
		return conn;
	}
}
