package org.sklsft.generator.bc.file.command.impl.presentation.jsf;

import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.conf.XmlFileWriteCommand;
import org.sklsft.generator.model.metadata.RelationType;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.Package;
import org.sklsft.generator.model.om.Project;
import org.sklsft.generator.model.om.Property;

public class JsfFacesConfigFileWriteCommand extends XmlFileWriteCommand {

	private Project project;

	public JsfFacesConfigFileWriteCommand(Project project) {
		super(project.workspaceFolder + "\\" + project.projectName + "-webapp\\src\\main\\webapp\\WEB-INF", "faces-config");
		this.project = project;
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("<?xml version='1.0' encoding='UTF-8'?>");
		writeLine("<faces-config xmlns=" + (char) 34 + "http://java.sun.com/xml/ns/javaee" + (char) 34);
		writeLine("xmlns:xsi=" + (char) 34 + "http://www.w3.org/2001/XMLSchema-instance" + (char) 34);
		writeLine("xsi:schemaLocation=" + (char) 34 + "http://java.sun.com/xml/ns/javaee");
		writeLine("http://java.sun.com/xml/ns/javaee/web-facesconfig_2_0.xsd" + (char) 34);
		writeLine("version=" + (char) 34 + "2.0" + (char) 34 + ">");
		skipLine();

		writeLine("<!-- -->");
		writeLine("<!-- auto generated jsf faces config file -->");
		writeLine("<!-- write modifications between specific code marks -->");
		writeLine("<!-- processed by skeleton-generator -->");
		writeLine("<!-- -->");
		skipLine();

		writeLine("<application>");
		writeLine("<el-resolver>org.springframework.web.jsf.el.SpringBeanFacesELResolver</el-resolver>");
		writeLine("<message-bundle>i18n</message-bundle>");
		writeLine("<resource-bundle>");
		writeLine("<base-name>i18n</base-name>");
		writeLine("<var>i18n</var>");
		writeLine("</resource-bundle>");
		writeLine("<locale-config>");
		writeLine("<default-locale>fr</default-locale>");
		writeLine("</locale-config>");
		writeLine("</application>");
		skipLine();

		for (Package myPackage : project.model.packages) {
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					writeLine("<navigation-rule>");
					skipLine();

					writeLine("<navigation-case>");
					writeLine("<from-action>#{" + bean.controllerObjectName + ".load}</from-action>");
					writeLine("<from-outcome>success</from-outcome>");
					writeLine("<to-view-id>/sections/" + myPackage.name + "/" + bean.objectName.toLowerCase() + "/" + bean.className + "List" + ".xhtml</to-view-id>");
					writeLine("<redirect/>");
					writeLine("</navigation-case>");
					skipLine();

					writeLine("<navigation-case>");
					writeLine("<from-action>#{" + bean.controllerObjectName + ".display}</from-action>");
					writeLine("<from-outcome>success</from-outcome>");
					writeLine("<to-view-id>/sections/" + myPackage.name + "/" + bean.objectName.toLowerCase() + "/" + bean.className + "List" + ".xhtml</to-view-id>");
					writeLine("<redirect/>");
					writeLine("</navigation-case>");
					skipLine();

					for (Property property : bean.properties) {
						if (property.referenceBean != null && !property.relation.equals(RelationType.PROPERTY)) {
							writeLine("<navigation-case>");
							writeLine("<from-action>#{" + bean.controllerObjectName + ".loadFrom" + property.referenceBean.className + "}</from-action>");
							writeLine("<from-outcome>success</from-outcome>");
							writeLine("<to-view-id>/sections/" + myPackage.name + "/" + bean.objectName.toLowerCase() + "/" + bean.className + "List" + ".xhtml</to-view-id>");
							writeLine("<redirect/>");
							writeLine("</navigation-case>");
							skipLine();

							writeLine("<navigation-case>");
							writeLine("<from-action>#{" + bean.controllerObjectName + ".loadFrom" + property.referenceBean.className + "}</from-action>");
							writeLine("<from-outcome>failure</from-outcome>");
							writeLine("<to-view-id>/sections/" + property.referenceBean.myPackage.name + "/" + property.referenceBean.objectName.toLowerCase() + "/" + property.referenceBean.className
									+ "List" + ".xhtml</to-view-id>");
							writeLine("<redirect/>");
							writeLine("</navigation-case>");
							skipLine();
						}
					}

					if (!bean.isSimple()) {

						writeLine("<navigation-case>");
						writeLine("<from-action>#{" + bean.controllerObjectName + ".edit" + bean.className + "}</from-action>");
						writeLine("<from-outcome>success</from-outcome>");
						writeLine("<to-view-id>/sections/" + myPackage.name + "/" + bean.objectName.toLowerCase() + "/" + bean.className + "Details.xhtml</to-view-id>");
						writeLine("<redirect/>");
						writeLine("</navigation-case>");
						skipLine();

						writeLine("<navigation-case>");
						writeLine("<from-action>#{" + bean.controllerObjectName + ".create" + bean.className + "}</from-action>");
						writeLine("<from-outcome>success</from-outcome>");
						writeLine("<to-view-id>/sections/" + myPackage.name + "/" + bean.objectName.toLowerCase() + "/" + bean.className + "Details.xhtml</to-view-id>");
						writeLine("<redirect />");
						writeLine("</navigation-case>");
						skipLine();
					}

					writeLine("</navigation-rule>");
					skipLine();
				}
			}
		}

		this.writeNotOverridableContent();

		writeLine("</faces-config>");

	}
}
