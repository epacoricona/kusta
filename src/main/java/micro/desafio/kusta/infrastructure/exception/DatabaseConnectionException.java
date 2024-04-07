package micro.desafio.kusta.infrastructure.exception;

public class DatabaseConnectionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8395537946942066904L;

	public DatabaseConnectionException(String message) {
		super(message);
	}
}
