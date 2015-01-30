package org.sklsft.generator.bc.file.command.impl.sql.update;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.sklsft.generator.bc.file.command.impl.sql.SqlFileWriteCommand;
import org.sklsft.generator.model.om.Model;
import org.sklsft.generator.model.om.Project;

public class UpdateScriptFileWriterCommand extends SqlFileWriteCommand {
	private List<String> scriptLines;
	
	public UpdateScriptFileWriterCommand(Model model, String modeName, String databaseName, List<String> scriptLines) {
		super(model.project.sourceFolder + File.separator + Project.BUILD_SCRIPT_FOLDER, "UPDATE_" + modeName + "_" + databaseName);
		this.scriptLines = scriptLines;
	}
	
	@Override
	protected void writeContent() throws IOException {
		for (String line : scriptLines) {
			writeLine(line);
		}		
	}
}
