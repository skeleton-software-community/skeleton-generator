package org.sklsft.generator.bc.file.command.impl.presentation.jsf;

import java.io.IOException;
import java.util.List;

import org.sklsft.generator.bc.file.command.impl.conf.PropertiesFileWriteCommand;
import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.Property;

public class I18nFileWriteCommand extends PropertiesFileWriteCommand {

	private Project project;

	public I18nFileWriteCommand(Project project) {
		super(project.workspaceFolder + "\\" + project.projectName + "-webapp\\src\\main\\resources", "i18n");
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

				for (int i = 1; i< bean.properties.size();i++) {
					Property property = bean.properties.get(i);
					if (property.referenceBean != null) {
						List<Property> findPropertyList = property.getReferencePropertyList();
						for (Property findProperty : findPropertyList) {
							writeLine(bean.objectName + property.capName + findProperty.capName + "=" + findProperty.rendering);
						}
					} else {
						writeLine(bean.objectName + property.capName + "=" + property.rendering);
					}
				}
				skipLine();
			}
		}

		this.writeNotOverridableContent();
	}
}
