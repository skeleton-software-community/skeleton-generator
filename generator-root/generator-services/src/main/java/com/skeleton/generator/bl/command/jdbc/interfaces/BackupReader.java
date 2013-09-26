package com.skeleton.generator.bl.command.jdbc.interfaces;

import java.util.List;

import com.skeleton.generator.exception.ReadBackupFailureException;

public interface BackupReader {

	List<Object[]> readBackupArgs() throws ReadBackupFailureException;
}
