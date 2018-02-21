package org.sklsft.generator.skeletons.jsf.commands.presentation.richfaces4;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.ui.ViewProperty;

public class Richfaces4OneToManyModalViewFileWriteCommand extends Richfaces4XhtmlFileWriteCommand {

	private OneToMany oneToMany;

	public Richfaces4OneToManyModalViewFileWriteCommand(OneToMany oneToMany) {
		super(oneToMany.referenceBean.myPackage.model.project.workspaceFolder + File.separator + oneToMany.referenceBean.myPackage.model.project.projectName
				+ "-webapp" + File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "sections" + File.separator + oneToMany.parentBean.myPackage.urlPiece + File.separator + oneToMany.parentBean.urlPiece + File.separator + oneToMany.referenceBean.urlPiece, "modal");

		this.oneToMany = oneToMany;
	}

	@Override
	protected void writeContent() throws IOException {

		Bean currentBean = oneToMany.referenceBean;
		Bean parentBean = oneToMany.parentBean;

		writeLine("<ui:composition xmlns=\"http://www.w3.org/1999/xhtml\"");
		writeLine("xmlns:ui=\"http://java.sun.com/jsf/facelets\"");
		writeLine("xmlns:f=\"http://java.sun.com/jsf/core\"");
		writeLine("xmlns:h=\"http://java.sun.com/jsf/html\"");
		writeLine("xmlns:rich=\"http://richfaces.org/rich\"");
		writeLine("xmlns:a4j=\"http://richfaces.org/a4j\">");
		skipLine();

		writeLine("<!-- -->");
		writeLine("<!-- auto generated jsf file -->");
		writeLine("<!-- write modifications between specific code marks -->");
		writeLine("<!-- processed by skeleton-generator -->");
		writeLine("<!-- -->");
		skipLine();
		
		writeLine("<div class=\"modal-header\">");
		writeLine("<h2>");
		writeLine("#{i18n." + currentBean.objectName + "Details}");
		writeLine("</h2>");
		writeLine("</div>");
		skipLine();
		
		writeLine("<div class=\"modal-body\">");

		writeLine("<h:panelGroup id=\"" + currentBean.objectName + "DetailPanelGroup\">");
		skipLine();

		writeLine("<a4j:region>");
		skipLine();

		writeLine("<ui:param name=\"view\" value=\"#{" + parentBean.detailViewObjectName + ".selected" + currentBean.className + "}\"/>");
		writeLine("<ui:param name=\"form\" value=\"#{view.form}\"/>");
		skipLine();

		writeLine("<div class=\"row\">");
		skipLine();

		for (ViewProperty property : oneToMany.formBean.properties) {
			writeInput(property, currentBean);
		}

		writeLine("</div>");
		skipLine();

		if (this.oneToMany.referenceBean.createEnabled) {
			writeLine("<a4j:commandButton value=\"#{i18n.save}\" action=\"#{" + parentBean.detailControllerObjectName + ".save" + currentBean.className + "}\"" 
					+ " rendered=\"#{empty view.id}\""
					+ " styleClass=\"btn btn-success\" execute=\"@region\" render=\"" + currentBean.objectName + "PanelGroup, " + currentBean.objectName
					+ "DetailPanelGroup\" oncomplete=\"if (#{empty facesContext.maximumSeverity or facesContext.maximumSeverity.ordinal ==0}) $('#" + currentBean.objectName + "Modal').modal('hide')\"/>");
		}
		
		if (this.oneToMany.referenceBean.updateEnabled) {
			writeLine("<a4j:commandButton value=\"#{i18n.update}\" action=\"#{" + parentBean.detailControllerObjectName + ".update" + currentBean.className + "}\""
					+ " rendered=\"#{not empty view.id}\" disabled=\"#{not view.canUpdate}\""
					+ " styleClass=\"btn btn-success\" execute=\"@region\" render=\"" + currentBean.objectName + "PanelGroup, " + currentBean.objectName
					+ "DetailPanelGroup\" oncomplete=\"if (#{empty facesContext.maximumSeverity or facesContext.maximumSeverity.ordinal ==0}) $('#" + currentBean.objectName + "Modal').modal('hide')\"/>");
		}
		
		writeLine("<a4j:commandButton value=\"#{i18n.cancel}\" actionListener=\"#{" + parentBean.listControllerObjectName + ".resetForm}\" styleClass=\"btn btn-info\" immediate=\"true\" render=\"" + currentBean.objectName
				+ "PanelGroup\" oncomplete=\"$('#" + currentBean.objectName + "Modal').modal('hide')\"/>");

		skipLine();

		this.writeNotOverridableContent();
		skipLine();

		writeLine("</a4j:region>");
		skipLine();

		writeLine("</h:panelGroup>");
		skipLine();
		
		writeLine("</div>");
		skipLine();

		writeLine("</ui:composition>");

	}
}
