package org.sklsft.generator.bc.backup.command.interfaces;

import javax.sql.DataSource;

import org.sklsft.generator.model.om.Table;
import org.sklsft.generator.repository.backup.command.interfaces.Command;

public interface JdbcCommandFactory {

	Command getCommand(DataSource dataSource, Table table);

}
