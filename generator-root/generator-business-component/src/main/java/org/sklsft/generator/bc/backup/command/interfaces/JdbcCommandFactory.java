package org.sklsft.generator.bc.backup.command.interfaces;

import java.util.List;

import javax.sql.DataSource;

import org.sklsft.generator.model.om.Table;
import org.sklsft.generator.repository.backup.jdbc.interfaces.JdbcCommand;

public interface JdbcCommandFactory {

	JdbcCommand getCommand(DataSource dataSource, Table table, List<Object[]> argsList);

}
