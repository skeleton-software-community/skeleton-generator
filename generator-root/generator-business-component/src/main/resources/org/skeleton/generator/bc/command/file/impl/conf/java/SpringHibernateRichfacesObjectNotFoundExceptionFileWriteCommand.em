package ${project.daoExceptionPackageName};

public class ObjectNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException(String message) {
		super(message);

	}

	public ObjectNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}