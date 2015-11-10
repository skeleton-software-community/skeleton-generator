package org.sklsft.generator.bc.file.command.impl;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.sklsft.generator.bc.file.command.interfaces.FileWriteCommand;
import org.sklsft.generator.model.domain.Project;

/**
 * An implementation of the abstract file write command based on the copy of a resources
 * @author Nicolas Thibault
 *
 */
public class ResourcesFileWriteCommand implements FileWriteCommand {
	
	private static String separator = "/";
	private String resourcesRootPath;
	private String targetRootPath;
	private Project project;
	
	public ResourcesFileWriteCommand(Project project, String resourcesRootPath, String targetRootPath) {
		this.project = project;
		this.resourcesRootPath = resourcesRootPath;
		this.targetRootPath = targetRootPath;
	}
	
	@Override
	public String getLabel() {
		return "resources copied to " + targetRootPath;
	}

	@Override
	public void execute() throws IOException {
		
		URL url = getClass().getResource(resourcesRootPath);
		File targetFolder = new File(project.workspaceFolder + File.separator + targetRootPath);
		File resourcesFolder = null;
		
		if (url.getProtocol().equals("file")){
			resourcesFolder = new File(url.getPath());
			FileUtils.copyDirectoryToDirectory(resourcesFolder, targetFolder);
		} else {
			
			File jar;
			try {
				jar = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
			} catch (URISyntaxException e) {
				throw new IOException("failed to jar path", e);
			}

			try (FileSystem jarMount = FileSystems.newFileSystem(jar.toPath(), null)) {
				Path resourcesPath = jarMount.getPath(resourcesRootPath);
				Path targetPath = targetFolder.toPath();
				copyRecursively(resourcesPath, targetPath);
			}
		}
	}

	private void copyRecursively(Path resourcesPath, Path targetPath) throws IOException {
		
		File childTargetFile = new File(targetPath.toFile().getPath() + separator + resourcesPath.getFileName());
		Path childTargetPath = childTargetFile.toPath();
		
		if (Files.isDirectory(resourcesPath)) {
			childTargetPath = Files.createDirectories(childTargetPath);
			try (DirectoryStream<Path> stream = Files.newDirectoryStream(resourcesPath)) {
				Iterator<Path> iterator = stream.iterator();
				while(iterator.hasNext()) {
					Path childResourcesPath = iterator.next();
					copyRecursively(childResourcesPath, childTargetPath);
				}
			}
		} else {
			Files.copy(resourcesPath, childTargetPath, StandardCopyOption.REPLACE_EXISTING);
		}
	}

	@Override
	public int getRowCount() {
		return 0;
	}

}
