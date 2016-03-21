package org.beansugar.sample;

import lombok.Data;

@Data
public class ConnectionInfo {
	private String url;
	private String username;
	private String password;
}