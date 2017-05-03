package org.sklsft.generator.skeletons.jsf.commands.presentation;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.skeletons.commands.impl.typed.PropertiesFileWriteCommand;

public class I18nFileWriteCommand extends PropertiesFileWriteCommand {

	private Project project;

	public I18nFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-webapp" + File.separator + "src" + File.separator + "main" + File.separator + "resources", "i18n");
		this.project = project;
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("#");
		writeLine("# auto generated i18n file");
		writeLine("# write modifications between specific code marks");
		writeLine("# processed by skeleton-generator");
		writeLine("#");
		skipLine();

		for (Package myPackage : project.model.packages) {
			for (Bean bean : myPackage.beans) {
				writeLine("#" + bean.className);
				writeLine(bean.objectName + "List=" + bean.listRendering);
				writeLine(bean.objectName + "Detail=" + bean.detailRendering);

				for (ViewProperty property : bean.formBean.properties) {					
					writeLine(bean.objectName + property.capName + "=" + property.rendering);
				}
				skipLine();
			}
		}

		this.writeNotOverridableContent();
	}
}
