package org.sklsft.generator.bash.launcher;

import java.util.HashMap;
import java.util.Map;

import org.sklsft.generator.bash.prompt.ArgumentsPrompter;
import org.sklsft.generator.bash.prompt.ProjectInitializerPrompter;

public enum Launchers {

	HELP("help", HelpLauncher.class, null),
	PROJECT_INITIALIZER("init", ProjectInitializerLauncher.class, ProjectInitializerPrompter.class),
	CODE_GENERATOR("generate", CodeGeneratorLauncher.class, null),
	DATABASE_BUILDER("builddb", DatabaseBuilderLauncher.class, null);
	
	
	private static final Map<String, Launchers> reverseMap = new HashMap<>();
	static{
		for(Launchers launcher : values()){
			reverseMap.put(launcher.command, launcher);
		}
	}
	
	
	private String command;
	private Class<?> LauncherClass;
	private Class<? extends ArgumentsPrompter> prompterClass;
	
	private Launchers(String command, Class<?> launcherClass,
			Class<? extends ArgumentsPrompter> prompterClass) {
		this.command = command;
		LauncherClass = launcherClass;
		this.prompterClass = prompterClass;
	}	
	
	
	public static Launchers byCommand(String value){
		Launchers launcher = reverseMap.get(value);
		if(launcher==null) {
			throw new IllegalArgumentException("Unkwown command " + value);
		}
		return launcher;
	}


	public Class<?> getLauncherClass() {
		return LauncherClass;
	}


	public Class<? extends ArgumentsPrompter> getPrompterClass() {
		return prompterClass;
	}
}
