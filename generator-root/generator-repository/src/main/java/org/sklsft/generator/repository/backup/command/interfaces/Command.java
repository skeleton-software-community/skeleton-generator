package org.sklsft.generator.repository.backup.command.interfaces;

import java.util.List;

public interface Command {

	void execute(List<Object[]> argsList);
}
