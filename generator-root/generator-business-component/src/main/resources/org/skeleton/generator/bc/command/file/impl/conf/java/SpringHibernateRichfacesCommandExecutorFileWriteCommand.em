package ${project.commandPackageName};

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ${project.testExceptionPackageName}.BuildFailureException;
import ${project.testExceptionPackageName}.CommandFailureException;

public class CommandExecutor {

	private static final Charset encoding = StandardCharsets.UTF_8;

	/*
	 * properties
	 */

	@Autowired
	private CommandBuilderFactory commandBuilderFactory;

	/*
	 * getters and setters
	 */

	public CommandBuilderFactory getCommandBuilderFactory() {
		return commandBuilderFactory;
	}

	public void setCommandBuilderFactory(
			CommandBuilderFactory commandBuilderFactory) {
		this.commandBuilderFactory = commandBuilderFactory;
	}

	/**
	 * executes all the commands contained in the specified file.
	 * 
	 * @param commandFilePath
	 * @throws BuildFailureException
	 * @throws CommandFailureException
	 * @throws IOException
	 */

	public void execute(String commandFilePath, Class<?> clazz)
			throws BuildFailureException, CommandFailureException, IOException {

		Path path = Paths.get(commandFilePath);
		List<String> lines = Files.readAllLines(path, encoding);

		for (String line : lines) {
			if (!StringUtils.isEmpty(line)) {
				CommandBuilder commandBuilder = commandBuilderFactory
						.createCommandBuilder(line, clazz);
				commandBuilder.buildCommand(line).execute();
			}
		}
	}
}