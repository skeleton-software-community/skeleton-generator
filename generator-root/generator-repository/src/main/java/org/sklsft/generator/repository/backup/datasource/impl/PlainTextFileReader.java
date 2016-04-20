package org.sklsft.generator.repository.backup.datasource.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.sklsft.generator.exception.ReadBackupFailureException;
import org.sklsft.generator.repository.backup.datasource.interfaces.BackupArgumentReader;

public class PlainTextFileReader implements BackupArgumentReader {

	@Override
	public BackupCommandArguments readBackupArgs(String backupFilePath) throws ReadBackupFailureException {
		try (BufferedReader br = new BufferedReader(new FileReader(backupFilePath))) {
			String line = br.readLine();
			BackupCommandArguments backup = new BackupCommandArguments();
			List<Object[]> arguments = new ArrayList<>(1);
			Object[] objects = new Object[1];
			objects[0] = line;
			arguments.add(objects);
			backup.setArguments(arguments);
			return backup;
		} catch (IOException e) {
			throw new ReadBackupFailureException("failed to read backup", e);
		}
	}

}
