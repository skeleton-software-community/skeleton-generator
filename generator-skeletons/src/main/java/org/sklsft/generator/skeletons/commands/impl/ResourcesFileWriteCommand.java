package org.sklsft.generator.skeletons.commands.impl;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.skeletons.commands.impl.templatized.TemplatizedReourceCopier;
import org.sklsft.generator.skeletons.commands.interfaces.FileWriteCommand;

/**
 * An implementation of the abstract file write command based on the copy of a resources
 * During the copy, velocity will do variables resolution
 * @author Nicolas Thibault
 *
 */
public class ResourcesFileWriteCommand implements FileWriteCommand {
	
	private Class<?> clazz;
	private String resourcesRoot;
	private String targetRootPath;
	private Project project;
	private TemplatizedReourceCopier copier;
	private static final String separator = "/";
	
	public ResourcesFileWriteCommand(Project project, Class<?> clazz, String resourcesRoot, String targetRootPath) {
		this.project = project;
		this.clazz = clazz;
		this.resourcesRoot = resourcesRoot;
		this.targetRootPath = targetRootPath;
		copier = new TemplatizedReourceCopier(project);
	}
	
	@Override
	public String getLabel() {
		return "resources copied to " + targetRootPath;
	}

	@Override
	public void execute() throws IOException, URISyntaxException {
		
		URL url = clazz.getResource(resourcesRoot);
		Path targetPath = Paths.get(project.workspaceFolder + File.separator + targetRootPath);
		
		if (url.getProtocol().equals("file")){
			copyRecursively(resourcesRoot, Paths.get(url.toURI()), targetPath);
		} else {
			
			File jar;
			try {
				jar = new File(clazz.getProtectionDomain().getCodeSource().getLocation().toURI());
			} catch (URISyntaxException e) {
				throw new IOException("failed to jar path", e);
			}

			try (FileSystem jarMount = FileSystems.newFileSystem(jar.toPath(), null)) {
				Path resourcesPath = jarMount.getPath(resourcesRoot);
				copyRecursively(resourcesRoot, resourcesPath, targetPath);
			}
		}
	}

	private void copyRecursively(String root, Path resourcesPath, Path targetPath) throws IOException {
		
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(resourcesPath)) {
			Iterator<Path> iterator = stream.iterator();
			while(iterator.hasNext()) {
				Path childResourcesPath = iterator.next();
				String childResourceFileName = childResourcesPath.toFile().getName();
				String childRelativeLocation = root + separator + childResourceFileName;
				
				String targetFileName = getTargetFileName(childResourceFileName);
				File childTargetFile = new File(targetPath.toFile().getPath() + File.separator + targetFileName);
				Path childTargetPath = childTargetFile.toPath();
				
				if (Files.isDirectory(childResourcesPath)) {
					childTargetPath = Files.createDirectories(childTargetPath);
					copyRecursively(childRelativeLocation, childResourcesPath, childTargetPath);
				} else {
					if (isTemplatized(childResourceFileName)) {
						copier.resolveAndCopy(childRelativeLocation, childTargetPath);
					} else {
						Files.copy(childResourcesPath, childTargetPath);
					}
				}
			}
		}
	}
	
	private boolean isTemplatized(String resourceFileName) {
		return resourceFileName.endsWith(".vm");
	}

	private String getTargetFileName(String resourceFileName) {
		
		if (resourceFileName.endsWith(".vm")) {
			return resourceFileName.substring(0, resourceFileName.length()-3);
		}
		return resourceFileName;
	}

	@Override
	public int getRowCount() {
		return 0;
	}

}
