package org.beansugar.sample.converter.model;

import lombok.Data;

@Data
public class GeographicPoint {
	private double longitude;
	private double latitude;

	public GeographicPoint(double d, double e) {
		setLatitude( d );
		setLongitude( e );
	}

}
