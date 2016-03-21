package org.beansugar.sample;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author archmagece@gmail.com
 * @since 2014-02-05
 * 디비두개이상을 한개 데이터소스에서 관리하는 경우.. 풀지원
 */
public class BSMultiBasicDataSource extends BasicDataSource {

	private List<ConnectionInfo> connInfos;

	public BSMultiBasicDataSource(List<ConnectionInfo> connInfo) {
		this.connInfos = new LinkedList<>();
		this.connInfos.addAll(connInfo);
	}

	@Override
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		int sidoHeoutSsoo = 0;
		while (true) {
			//접속정보 재설정
			try {
				conn = createDataSource().getConnection();
				break;
//			}catch(org.springframework.jdbc.CannotGetJdbcConnectionException e){
			} catch (Exception e) {
//			}catch(SQLException e){
//				e.printStackTrace();
				dataSource = null;
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

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
