package org.beansugar.sample;

/**
 * @Author: archmagece
 * @Since: 2013-12-09 11:00
 *
 * http://www.epsg.org/
 */
public enum GeoDictionary {
//	EPSG4004(""),
//	EPSG4019(""),
//	EPSG4326("WGS84"),
//	EPSG5178("UTM-K_BESSEL"),
//	EPSG5179("UTM-K_GRS80");
	EPSG4236("GEO"),//WGS84
	KATEC("KATEC"),
	TM("TM"),
	EPSG5181("GRS80"),
	EPSG5179("UTMK"),//UTM-K_GRS80
	EPSG5178("UTM-K_BESSEL");

	private String val;

	GeoDictionary(String val){
		this.val = val;
	}
	@Override
	public String toString() {
		return super.toString();
	}
}
