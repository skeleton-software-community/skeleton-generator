package org.sklsft.generator.bash.prompt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.sklsft.generator.bash.arguments.BasicFreeChoicesHelper;
import org.sklsft.generator.bash.arguments.ChoicesHelper;
import org.sklsft.generator.bash.arguments.DatabaseEngineChoicesHelper;
import org.sklsft.generator.bash.arguments.SkeletonChoicesHelper;
import org.sklsft.generator.bash.arguments.TrueFalseChoicesHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This implementation of {@link ArgumentsPrompter} is used fo initialization command<br>
 * It consists of getting your inputs through a list of questions
 * @author Nicolas Thibault
 *
 */
public class ProjectInitializerPrompter implements ArgumentsPrompter {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectInitializerPrompter.class);

	private List<ChoicesHelper> choicesHelpers = new ArrayList<>();
	
	
	
	public ProjectInitializerPrompter() {
		super();
		choicesHelpers.add(new BasicFreeChoicesHelper("Enter your domain name  (ex:org.sklsft) : "));
		choicesHelpers.add(new BasicFreeChoicesHelper("Enter your project name (ex:demo) : "));
		choicesHelpers.add(new SkeletonChoicesHelper());
		choicesHelpers.add(new DatabaseEngineChoicesHelper());
		choicesHelpers.add(new BasicFreeChoicesHelper("Enter your database name (ex:DEMO) : "));
		choicesHelpers.add(new BasicFreeChoicesHelper("Enter your database host name (ex:localhost) : "));
		choicesHelpers.add(new BasicFreeChoicesHelper("Enter your database port (ex:5432) : "));
		choicesHelpers.add(new BasicFreeChoicesHelper("Enter your database username (ex:postgres) : "));
		choicesHelpers.add(new BasicFreeChoicesHelper("Enter your database password (ex:postgres) : "));
		choicesHelpers.add(new TrueFalseChoicesHelper("Do you want to activate Hibernate envers fonctionality "));
	}

	public List<String> promptForArguments() {		
		
		String input;
		String argument = null;
		List<String> arguments = new ArrayList<>();
		
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));) {
		
			for (ChoicesHelper helper:choicesHelpers) {
				argument = null;
				while(argument == null) {
					System.out.println(helper.getFullMessage());
					input = bufferedReader.readLine();
					argument = helper.getChoice(input);
				}
				arguments.add(argument);
			}
			
		
			System.out.println("domain name : " + arguments.get(0));
			System.out.println("project name : " + arguments.get(1));
			System.out.println("skeleton type : " + arguments.get(2));
			System.out.println("database engine : " + arguments.get(3));
			System.out.println("database name : " + arguments.get(4));
			System.out.println("database host name : " + arguments.get(5));
			System.out.println(" port : " + arguments.get(6));
			System.out.println("database username : " + arguments.get(7));
			System.out.println("database password : " + arguments.get(8));
			System.out.println("hibernate envers activated : " + arguments.get(9));
			System.out.println("Please confirm your choice (Y/n)");
			input = bufferedReader.readLine();
			if (!"Y".equals(input)) {
				System.exit(0);
			}
		
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}	
		
		return arguments;
	}
}
