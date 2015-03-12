package org.sklsft.generator.bc.file.command.impl.conf.java.mvc.aspect;

import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.model.om.Project;

public class SpringHibernateRichfacesAjaxMethodAspectFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringHibernateRichfacesAjaxMethodAspectFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-webapp/src/main/java/" + project.model.mvcAspectPackageName.replace(".", File.separator) + File.separator, "AjaxMethodAspect", FileType.JAVA, project);
	}

}
