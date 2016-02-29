package org.sklsft.generator.repository.backup.command;

import org.sklsft.generator.repository.backup.datasource.impl.BackupCommandArguments;

public interface Command {

	void execute(BackupCommandArguments arguments);
}
