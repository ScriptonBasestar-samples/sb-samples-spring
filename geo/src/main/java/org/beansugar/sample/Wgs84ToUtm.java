package org.beansugar.sample;

/**
 * @Author: archmagece
 * @Since: 2013-11-28 17:23
 */
public class Wgs84ToUtm {
//	Const fe = 500000 'Vars for the utmToLatlon
//	Const ok = 0.9996
//	Const PI = 3.14159265
//	Const deg2rad = PI / 180
//	Const rad2deg = 1 / deg2rad
//	Private cArray As Char() = {"C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X"} 'endutmtolatlon
//	Private blnWGS As Boolean = True
//	' ************************************* Utm to Lat / Lon conversion *********************
//	Private Function CalculateESquared(ByVal a As Double, ByVal b As Double)
//
//	CalculateESquared = ((a * a) - (b * b)) / (a * a)
//
//	End Function
//
//
//	Private Function CalculateE2Squared(ByVal a As Double, ByVal b As Double)
//
//	CalculateE2Squared = ((a * a) - (b * b)) / (b * b)
//
//	End Function
//
//
//	Private Function denom(ByVal es As Double, ByVal sphi As Double)
//	Dim sinSphi As Double
//	sinSphi = Sin(sphi)
//	denom = (1.0# - es * (sinSphi * sinSphi)) ^ 0.5
//	End Function
//
//	Private Function sphsr(ByVal a As Double, ByVal es As Double, ByVal sphi As Double)
//	Dim dn As Double
//	dn = denom(es, sphi)
//	sphsr = a * (1.0# - es) / (dn * dn * dn)
//	End Function
//
//
//	Private Function sphsn(ByVal a As Double, ByVal es As Double, ByVal sphi As Double)
//	Dim sinSphi As Double
//	sinSphi = Sin(sphi)
//	sphsn = a / (1.0# - es * (sinSphi * sinSphi)) ^ 0.5
//
//	End Function
//
//	Private Function sphtmd(ByVal ap As Double, ByVal bp As Double, ByVal cp As Double, ByVal dp As Double, ByVal ep As Double, _
//			ByVal sphi As Double)
//	sphtmd = (ap * sphi) - (bp * Sin(2.0# * sphi)) + (cp * Sin(4.0# * sphi)) _
//	- (dp * Sin(6.0# * sphi)) + (ep * Sin(8.0# * sphi))
//
//	End Function
//	Private Function FutmYzone(ByVal lat As Double) As Short
//	Dim y As String
//	If (lat < 84 And lat >= 72) Then
//	'// Special case: zone X is 12 degrees from north to south, not 8.
//	y = cArray(19)
//	Else
//			y = cArray(Fix((lat + 80) / 8))
//	End If
//	If (lat >= 84 Or lat < -80) Then
//	'// Invalid coordinate; the vertical zone is set to the invalid
//			'// character.
//	y = "*"
//	End If
//	Return Asc(y)
//	End Function
//
//	'//=======================================================================
//			'// Purpose:
//			'//  This function converts the specified lat/lon coordinate to a UTM
//			'//  coordinate.
//			'// Parameters:
//			'//  double a:
//			'//      Ellipsoid semi-major axis, in meters. (For WGS84 datum, use 6378137.0)
//			'//  double f:
//			'//      Ellipsoid flattening. (For WGS84 datum, use 1 / 298.257223563)
//			'//  int& utmXZone:
//			'//      Upon exit, this parameter will contain the hotizontal zone number of
//			'//      the UTM coordinate.  The returned value for this parameter is a number
//			'//      within the range 1 to 60, inclusive.
//			'//  char& utmYZone:
//			'//      Upon exit, this parameter will contain the zone letter of the UTM
//			'//      coordinate.  The returned value for this parameter will be one of:
//			'//      CDEFGHJKLMNPQRSTUVWX.
//			'//  double& easting:
//			'//      Upon exit, this parameter will contain the UTM easting, in meters.
//			'//  double& northing:
//			'//      Upon exit, this parameter will contain the UTM northing, in meters.
//			'//  double lat, double lon:
//			'//      The lat/lon coordinate to convert.
//			'// Notes:
//			'//  - The code in this function is a C conversion of some of the source code
//			'//    from the Mapping Datum Transformation Software (MADTRAN) program,
//			'//    written in PowerBasic.  To access the source code for MADTRAN, go to:
//			'//
//			'//      http://164.214.2.59/publications/guides/MADTRAN/index.html
//			'//
//			'//    and download MADTRAN.ZIP
//			'//  - If the UTM zone is out of range, the y-zone character is set to the
//			'//    asterisk character ('*').
//			'//=======================================================================
//	Public Function WGS84LatLonToUTM(ByVal Lat As Double, ByVal Lon As Double) As Double()
//	Return LatLonToUTM(6378137, 0.003352810665, lat, lon)
//	End Function
//	Public Function WGS84UTMTOLatLon(ByVal UtmXZone As Int16, ByVal Easting As Double, ByVal NorthHemisphere As Boolean, ByVal Northing As Double) As Double()
//	Return UTMToLatLon(6378137, 0.003352810665, UtmXZone, Easting, NorthHemisphere, Northing)
//	End Function
//
//	Public Function LatLonToUTM(ByVal a As Double, ByVal f As Double, ByVal Lat As Double, ByVal Lon As Double) As Double()
//	On Error GoTo ErrHandler
//	Dim recf As Double
//	Dim b As Double
//	Dim eSquared As Double
//	Dim e2Squared As Double
//	Dim tn As Double
//	Dim ap As Double
//	Dim bp As Double
//	Dim cp As Double
//	Dim dp As Double
//	Dim ep As Double
//	Dim olam As Double
//	Dim dlam As Double
//	Dim s As Double
//	Dim c As Double
//	Dim t As Double
//	Dim eta As Double
//	Dim sn As Double
//	Dim tmd As Double
//	Dim t1 As Double
//	Dim t2 As Double
//	Dim t3 As Double
//	Dim t6 As Double
//	Dim t7 As Double
//	Dim nfn As Double
//	Dim easting, northing, utmXZone, utmYzone As Double
//	If (Lon <= 0) Then
//	utmXZone = 30 + Fix(Lon / 6)
//	Else
//			utmXZone = 31 + Fix(Lon / 6)
//	End If
//	utmYzone = FutmYzone(Lat)
//	Dim latRad As Double
//	latRad = Lat * deg2rad
//	Dim lonRad As Double
//	lonRad = Lon * deg2rad
//			recf = 1 / f
//	b = a * (recf - 1.0#) / recf
//			eSquared = CalculateESquared(a, b)
//	e2Squared = CalculateE2Squared(a, b)
//	tn = (a - b) / (a + b)
//	ap = a * (1.0# - tn + 5.0# * ((tn * tn) - (tn * tn * tn)) / 4.0# + 81.0# * _
//			((tn * tn * tn * tn) - (tn * tn * tn * tn * tn)) / 64.0#)
//	bp = 3.0# * a * (tn - (tn * tn) + 7.0# * ((tn * tn * tn) _
//	- (tn * tn * tn * tn)) / 8.0# + 55.0# * (tn * tn * tn * tn * tn) / 64.0#) _
//	/ 2.0#
//	cp = 15.0# * a * ((tn * tn) - (tn * tn * tn) + 3.0# * ((tn * tn * tn * tn) _
//	- (tn * tn * tn * tn * tn)) / 4.0#) / 16.0#
//	dp = 35.0# * a * ((tn * tn * tn) - (tn * tn * tn * tn) + 11.0# _
//	* (tn * tn * tn * tn * tn) / 16.0#) / 48.0#
//	ep = 315.0# * a * ((tn * tn * tn * tn) - (tn * tn * tn * tn * tn)) / 512.0#
//	olam = (utmXZone * 6 - 183) * deg2rad
//			dlam = lonRad - olam
//	s = Sin(latRad)
//	c = Cos(latRad)
//	t = s / c
//			eta = e2Squared * (c * c)
//	sn = sphsn(a, eSquared, latRad)
//	tmd = sphtmd(ap, bp, cp, dp, ep, latRad)
//	t1 = tmd * ok
//			t2 = sn * s * c * ok / 2.0#
//	t3 = sn * s * (c * c * c) * ok * (5.0# - (t * t) + 9.0# * eta + 4.0# _
//	* (eta * eta)) / 24.0#
//	If (latRad < 0.0#) Then nfn = 10000000.0# Else nfn = 0
//	northing = nfn + t1 + (dlam * dlam) * t2 + (dlam * dlam * dlam _
//	* dlam) * t3 + (dlam * dlam * dlam * dlam * dlam * dlam) + 0.5
//	t6 = sn * c * ok
//			t7 = sn * (c * c * c) * (1.0# - (t * t) + eta) / 6.0#
//	easting = fe + dlam * t6 + (dlam * dlam * dlam) * t7 + 0.5
//	If (northing >= 9999999.0#) Then northing = 9999999.0#
//	Return New Double() {utmXZone, easting, utmYzone, northing}
//	ErrHandler:
//	End Function
//
//
//
//	'//=======================================================================
//			'// Purpose:
//			'//  This function converts the specified UTM coordinate to a lat/lon
//			'//  coordinate.
//			'// Pre:
//			'//  - utmXZone must be between 1 and 60, inclusive.
//			'//  - utmYZone must be one of: CDEFGHJKLMNPQRSTUVWX
//			'// Parameters:
//			'//  double a:
//			'//      Ellipsoid semi-major axis, in meters. (For WGS84 datum, use 6378137.0)
//			'//  double f:
//			'//      Ellipsoid flattening. (For WGS84 datum, use 1 / 298.257223563)
//			'//  int utmXZone:
//			'//      The horizontal zone number of the UTM coordinate.
//			'//  char utmYZone:
//			'//      The vertical zone letter of the UTM coordinate.
//			'//  double easting, double northing:
//			'//      The UTM coordinate to convert.
//			'//  double& lat:
//			'//      Upon exit, lat contains the latitude.
//			'//  double& lon:
//			'//      Upon exit, lon contains the longitude.
//			'// Notes:
//			'//  The code in this function is a C conversion of some of the source code
//			'//  from the Mapping Datum Transformation Software (MADTRAN) program, written
//			'//  in PowerBasic.  To access the source code for MADTRAN, go to:
//			'//
//			'//    http://164.214.2.59/publications/guides/MADTRAN/index.html
//			'//
//			'//  and download MADTRAN.ZIP
//			'//=======================================================================
//
//	Public Function UTMToLatLon(ByVal a As Double, ByVal f As Double, ByVal UtmXZone As Integer, ByVal Easting As Double _
//			, ByVal NorthHemisphere As Boolean, ByVal Northing As Double) As Double()
//
//	Dim recf As Double
//	Dim b As Double
//	Dim eSquared As Double
//	Dim e2Squared As Double
//	Dim tn As Double
//	Dim ap As Double
//	Dim bp As Double
//	Dim cp As Double
//	Dim dp As Double
//	Dim ep As Double
//	Dim olam As Double
//	Dim nfn As Double
//	Dim tmd As Double
//	Dim sr As Double
//	Dim sn As Double
//	Dim ftphi As Double
//	Dim s As Double
//	Dim c As Double
//	Dim t As Double
//	Dim eta As Double
//	Dim de As Double
//	Dim dlam As Double
//	Dim lat, lon As Double
//	recf = 1.0# / f
//			b = a * (recf - 1) / recf
//	eSquared = CalculateESquared(a, b)
//	e2Squared = CalculateE2Squared(a, b)
//	tn = (a - b) / (a + b)
//	ap = a * (1.0# - tn + 5.0# * ((tn * tn) - (tn * tn * tn)) / 4.0# + 81.0# * _
//			((tn * tn * tn * tn) - (tn * tn * tn * tn * tn)) / 64.0#)
//	bp = 3.0# * a * (tn - (tn * tn) + 7.0# * ((tn * tn * tn) _
//	- (tn * tn * tn * tn)) / 8.0# + 55.0# * (tn * tn * tn * tn * tn) / 64.0#) _
//	/ 2.0#
//	cp = 15.0# * a * ((tn * tn) - (tn * tn * tn) + 3.0# * ((tn * tn * tn * tn) _
//	- (tn * tn * tn * tn * tn)) / 4.0#) / 16.0#
//	dp = 35.0# * a * ((tn * tn * tn) - (tn * tn * tn * tn) + 11.0# _
//	* (tn * tn * tn * tn * tn) / 16.0#) / 48.0#
//	ep = 315.0# * a * ((tn * tn * tn * tn) - (tn * tn * tn * tn * tn)) / 512.0#
//	If Not NorthHemisphere Then
//	nfn = 10000000.0#
//	Else
//			nfn = 0
//	End If
//	tmd = (Northing - nfn) / ok
//			sr = sphsr(a, eSquared, 0.0#)
//	ftphi = tmd / sr
//	Dim t10 As Double, t11 As Double, t14 As Double, t15 As Double
//	Dim Index As Integer
//	For Index = 0 To 4
//	t10 = sphtmd(ap, bp, cp, dp, ep, ftphi)
//	sr = sphsr(a, eSquared, ftphi)
//	ftphi = ftphi + (tmd - t10) / sr
//			Next
//	sr = sphsr(a, eSquared, ftphi)
//	sn = sphsn(a, eSquared, ftphi)
//	s = Sin(ftphi)
//	c = Cos(ftphi)
//	t = s / c
//			eta = e2Squared * (c * c)
//	de = Easting - fe
//			t10 = t / (2.0# * sr * sn * (ok * ok))
//	t11 = t * (5.0# + 3.0# * (t * t) + eta - 4.0# * (eta * eta) - 9.0# * (t * t) _
//	* eta) / (24.0# * sr * (sn * sn * sn) * (ok * ok * ok * ok))
//	lat = ftphi - (de * de) * t10 + (de * de * de * de) * t11
//			t14 = 1.0# / (sn * c * ok)
//	t15 = (1.0# + 2.0# * (t * t) + eta) / (6 * (sn * sn * sn) * c _
//	* (ok * ok * ok))
//	dlam = de * t14 - (de * de * de) * t15
//			olam = (UtmXZone * 6 - 183.0#) * deg2rad
//			lon = olam + dlam
//	lon = lon * rad2deg
//			lat = lat * rad2deg
//	Return New Double() {lat, lon}
//	End Function
//	' * transform
//			'*
//			'* Parameters:
//			'*     from:     The geodetic position to be translated.
//			'*     from_a:   The semi-major axis of the "from" ellipsoid.
//			'*     from_f:   Flattening of the "from" ellipsoid.
//			'*     from_esq: Eccentricity-squared of the "from" ellipsoid.
//			'*     da:       Change in semi-major axis length (meters); "to" minus "from"
//			' *     df:       Change in flattening; "to" minus "from"
//			'*     dx:       Change in x between "from" and "to" datum.
//			'*     dy:       Change in y between "from" and "to" datum.
//			'*     dz:       Change in z between "from" and "to" datum.
//			'*/
//
//	Public Function ChangeDatum(ByVal Lat As Double, ByVal Lon As Double, _
//			ByVal From_a As Double, ByVal From_f As Double, ByVal To_a As Double, ByVal To_f As Double, _
//										ByVal dx As Double, ByVal dy As Double, ByVal dz As Double) As Double()
//
//	Lat = Lat / 180 * Math.PI
//			Lon = Lon / 180 * Math.PI
//	Dim slat As Double = Math.Sin(Lat)
//	Dim clat As Double = Math.Cos(Lat)
//	Dim slon As Double = Math.Sin(Lon)
//	Dim clon As Double = Math.Cos(Lon)
//	Dim ssqlat As Double = slat * slat
//	Dim adb As Double = 1.0 / (1.0 - From_f) '  // "a divided by b"
//	Dim dlat, dlon, dh As Double
//	Dim from_esq As Double = 2 * From_f - From_f ^ 2
//	Dim rn As Double = From_a / Math.Sqrt(1.0 - from_esq * ssqlat)
//	Dim rm As Double = From_a * (1.0 - from_esq) / Math.Pow((1.0 - from_esq * ssqlat), 1.5)
//	Dim res(2) As Double
//	Dim da As Double = To_a - From_a
//	Dim df As Double = To_f - From_f
//	dlat = (((((-dx * slat * clon - dy * slat * slon) + dz * clat) _
//	+ (da * ((rn * from_esq * slat * clat) / From_a))) _
//	+ (df * (rm * adb + rn / adb) * slat * clat))) _
//	/ (rm + 0)
//
//	dlon = (-dx * slon + dy * clon) / ((rn + 0) * clat)
//
//	dh = (dx * clat * clon) + (dy * clat * slon) + (dz * slat) _
//	- (da * (From_a / rn)) + ((df * rn * ssqlat) / adb)
//	res(0) = Lat + dlat
//				   res(1) = Lon + dlon
//								  res(0) = res(0) / Math.PI * 180
//	res(1) = res(1) / Math.PI * 180
//	Return res
//	End Function
}
