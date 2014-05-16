package org.skeleton.generator.bc.factory.reader;

import org.skeleton.generator.model.metadata.PersistenceMode;
import org.skeleton.generator.model.om.Table;
import org.skeleton.generator.repository.dao.datasource.impl.TextDelimitedFileBackupReader;
import org.skeleton.generator.repository.dao.datasource.impl.XmlFileBackupReader;
import org.skeleton.generator.repository.dao.datasource.interfaces.BackupArgumentReader;
import org.skeleton.generator.repository.dao.datasource.interfaces.InputSourceProvider;

public class BackupArgumentReaderFactory {

	public BackupArgumentReader getReaderFactory(PersistenceMode type, InputSourceProvider inputSourceProvider, Table table){
		switch(type){
		case CSV : return new TextDelimitedFileBackupReader(table.getInsertColumnList().size());
		case XML : return new XmlFileBackupReader(inputSourceProvider);
		default : throw new RuntimeException("Unknown persistence mode " + type); //TODO plus beau
		}
	}
}
