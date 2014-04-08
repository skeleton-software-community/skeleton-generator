package ${project.commandPackageName};

import ${project.testExceptionPackageName}.CommandFailureException;



/**
 * interface used to create an insert command
 */

public interface Command {

	/**
	 * method that executes an insert in database from view object
	 * @throws CommandFailureException
	 */
	
	void execute() throws CommandFailureException;
}
