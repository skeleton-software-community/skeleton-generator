package org.sklsft.generator.bc.file.command.impl.conf.spring.test;

import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.model.om.Project;

public class SpringHibernateRichfacesSpringTestRepositoryFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringHibernateRichfacesSpringTestRepositoryFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-services/src/test/resources", "applicationContext-" + project.projectName + "-repository-test", FileType.XML, project);
	}

}
