package Ex1;

public class InvalidInputException extends RuntimeException {

	public InvalidInputException(Exception e) {
		super(e);
	}
	
	public InvalidInputException(String input) {
		super("Invalid input: [" + input + "]");
	}
}