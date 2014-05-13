package org.skeleton.generator.bc.command.file.impl.templatized;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.skeleton.generator.bc.command.file.interfaces.FileWriteCommand;
import org.skeleton.generator.model.om.Project;

/**
 * An implementation of the abstract file write command based on the copy of a resources
 * @author Nicolas Thibault
 *
 */
public class ResourcesFileWriteCommand implements FileWriteCommand {

	private String resourcesRootPath;
	private String targetRootPath;
	private Project project;
	
	public ResourcesFileWriteCommand(Project project, String resourcesRootPath, String targetRootPath) {
		this.project = project;
		this.resourcesRootPath = resourcesRootPath;
		this.targetRootPath = targetRootPath;
	}

	@Override
	public void execute() throws IOException {
		
		try {
			File resourcesFolder = Paths.get(getClass().getResource(resourcesRootPath).toURI()).toFile();
			
			File targetFolder = new File(project.workspaceFolder + File.separator + targetRootPath);
			
			FileUtils.copyDirectoryToDirectory(resourcesFolder, targetFolder);
			
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	@Override
	public int getRowCount() {
		return 0;
	}

}
