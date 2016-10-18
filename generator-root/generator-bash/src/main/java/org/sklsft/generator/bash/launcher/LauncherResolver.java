package org.sklsft.generator.bash.launcher;

/**
 * 
 * This class aims at resolving what launcher to be used depending on the command passed<br>
 * The resolution is based on an enumeration of available commands
 * @author Nicolas Thibault
 *
 */
public class LauncherResolver {

	public static Launchers resolveLauncher (String key) {
		return Launchers.byCommand(key);
	}
}
