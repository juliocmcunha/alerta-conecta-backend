package net.devs404.alerta_conecta_backend.database.occurrence.microdata;

public class OccurrenceLocation 
{
	private double latitude = 0.0d;
	private double longitude = 0.0d;
	
	public OccurrenceLocation(double latitude, double longitude)
	{
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	///GETTERS
	public double getLatitude() {return this.latitude;}
	public double getLongitude() {return this.longitude;}
}
