package org.sklsft.generator.bash.launcher;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.sklsft.generator.bash.prompt.ArgumentsPrompter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This is the entry point of sklgen cli<br>
 * It uses a command resolver that will find a command launcher and get the missing inputs through the corresponding {@link ArgumentsPrompter}
 * @author Nicolas Thibault
 *
 */
public class MainLauncher {
	
	/*
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(MainLauncher.class);
	
	public static void main(String[] args) {

		if (args.length < 2) {
			HelpLauncher.main(null);
			return;
		}
		
		String command = args[0];
		Launchers launcher = LauncherResolver.resolveLauncher(command);
		
		String[] launcherArgs = null;
		try {
			if (launcher.getPrompterClass() != null) {
				ArgumentsPrompter prompter = launcher.getPrompterClass().newInstance();
				List<String> argList = prompter.promptForArguments();
				
				launcherArgs = new String[argList.size()+1];
				launcherArgs[0] = args[1];
				for (int i = 0;i<argList.size(); i++) {
					launcherArgs[i+1] = argList.get(i);
				}
			} else {
				launcherArgs = new String[args.length-1];
				for (int i = 0; i < args.length -1 ; i++) {
					launcherArgs[i] = args[i+1];
				}
			}
			launcher.getLauncherClass().getMethod("main", String[].class).invoke(null, new Object[]{launcherArgs});
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException | InstantiationException e) {
			logger.error("failed to launch command " + command + " : " + e.getMessage(), e);
		}
	}
}
