package org.sklsft.generator.command;

import org.sklsft.generator.bc.file.executor.FileWriteCommandTree;
import org.sklsft.generator.bl.services.interfaces.CodeGenerator;
import org.sklsft.generator.bl.services.interfaces.ProjectLoader;
import org.sklsft.generator.bl.services.interfaces.ProjectMetaDataService;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.ProjectMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * This class can be launched to execute code writing<br/>
 * Argument required : the workspace folder where the "data-model" folder will
 * be detected<br/>
 * Depending on the meta data that is going to be read, the main method will :
 * <li>load the project representation <li>build a tree of files that are going
 * to be written <li>launch a cascading writing of the files in the tree
 * 
 * @author Nicolas Thibault
 * 
 */
public class CodeGeneratorLauncher {

	/*
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(CodeGeneratorLauncher.class);

	/**
	 * main method to be executed
	 * 
	 * @param args
	 *            0->the workspace folder where the "data-model" folder will be
	 *            detected
	 */
	public static void main(String[] args) {

		if (args.length < 1) {
			throw new IllegalArgumentException("Path is Mandatory");
		}
		String folderPath = args[0];

		try (FileSystemXmlApplicationContext appContext = new FileSystemXmlApplicationContext("classpath:applicationContext-generator-command.xml");) {
			logger.info("Context loaded");

			Project project;

			try {
				logger.info("start loading project");

				ProjectMetaDataService projectMetaDataService = appContext.getBean(ProjectMetaDataService.class);
				ProjectLoader projectLoader = appContext.getBean(ProjectLoader.class);

				ProjectMetaData projectMetaData = projectMetaDataService.loadProjectMetaData(folderPath);
				project = projectLoader.loadProject(projectMetaData);

				logger.info("loading project " + project.projectName + " completed");

			} catch (Exception e) {
				logger.error("failed", e);
				return;
			}

			try {
				logger.info("start executing code writing");

				CodeGenerator codeGenerator = appContext.getBean(CodeGenerator.class);
				FileWriteCommandTree tree = codeGenerator.buildFileWriteCommandTree(project);
				codeGenerator.generateCode(tree);

				logger.info("executing code writing completed");

			} catch (Exception e) {
				logger.error("failed", e);
				return;
			}
		}
	}
}
