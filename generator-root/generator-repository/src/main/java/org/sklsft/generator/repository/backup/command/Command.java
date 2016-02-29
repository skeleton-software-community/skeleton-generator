package org.sklsft.generator.repository.backup.command;

import java.util.List;

public interface Command {

	void execute(List<Object[]> argsList);
}
