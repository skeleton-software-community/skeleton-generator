package org.sklsft.generator.persistence.backup.command.interfaces;

import org.sklsft.generator.persistence.backup.reader.model.BackupArguments;

public interface BackupArgumentsCommand {

	void execute(BackupArguments arguments);
}
