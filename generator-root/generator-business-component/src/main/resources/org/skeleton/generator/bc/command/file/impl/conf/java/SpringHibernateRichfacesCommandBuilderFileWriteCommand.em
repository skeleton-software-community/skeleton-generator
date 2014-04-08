package ${project.commandPackageName};

import ${project.testExceptionPackageName}.BuildFailureException;



public interface CommandBuilder {

	public Command buildCommand(String line) throws BuildFailureException;
}
