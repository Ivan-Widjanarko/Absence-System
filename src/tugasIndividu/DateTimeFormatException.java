package tugasIndividu;

import java.time.DateTimeException;

public class DateTimeFormatException extends DateTimeException {
	public DateTimeFormatException() {
		super( "Date / Time Format is Incorrect" );
	}
	public DateTimeFormatException( String message ) {
		super( message );
	}
}
