package org.sklsft.generator.repository.backup.command.interfaces;

/**
 * Used by the populator<br>
 * Once identified the path where to find what to do, this interface just defines what to do with the given path
 * @author Nicolas Thibault
 *
 */
public interface BackupCommand {

	public void execute(String path);
}
