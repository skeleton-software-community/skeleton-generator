package org.sklsft.generator.repository.backup.command.interfaces;

import org.sklsft.generator.repository.backup.reader.model.BackupArguments;

public interface BackupArgumentsCommand {

	void execute(BackupArguments arguments);
}
