package net.devs404.alerta_conecta_backend.util;

import java.sql.Timestamp;
import java.time.Instant;

public class TimestampPlus extends Timestamp
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TimestampPlus(long time) {
		super(time);
		// TODO Auto-generated constructor stub
	}

	/// This method works only with this Date structure: '2025-12-08T16:11:00Z'
	public static Timestamp valueOf(String date)
	{
		return Timestamp.from(Instant.parse(date.replace(" ", "T") + "Z"));
	}
}
