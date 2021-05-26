package tugasIndividu;

import java.nio.charset.IllegalCharsetNameException;

/**
 * Class for IncorrectNameException
 *
 * @author Ivan Widjanarko
 * @version 26-05-2021
 */
public class IncorrectNameException extends IllegalCharsetNameException {
	public IncorrectNameException() {
		super( "Name is Incorrect" );
	}
	public IncorrectNameException( String message ) {
		super( message );
	}
}
