package org.sklsft.generator.skeletons.jsf.commands.presentation.primefaces;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.ui.ViewProperty;

public class PrimefacesOneToManyComponentModalViewFileWriteCommand extends PrimefacesXhtmlFileWriteCommand {

	private OneToManyComponent oneToManyComponent;

	public PrimefacesOneToManyComponentModalViewFileWriteCommand(OneToManyComponent oneToManyComponent) {
		super(oneToManyComponent.referenceBean.myPackage.model.project.workspaceFolder + File.separator + oneToManyComponent.referenceBean.myPackage.model.project.projectName
				+ "-webapp" + File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "sections" + File.separator + oneToManyComponent.parentBean.myPackage.name + File.separator + oneToManyComponent.parentBean.className.toLowerCase(),
				oneToManyComponent.referenceBean.className + "Modal");

		this.oneToManyComponent = oneToManyComponent;
	}

	@Override
	protected void writeContent() throws IOException {

		Bean currentBean = oneToManyComponent.referenceBean;
		Bean parentBean = oneToManyComponent.parentBean;

		writeLine("<ui:composition xmlns=\"http://www.w3.org/1999/xhtml\"");
		writeLine("xmlns:ui=\"http://java.sun.com/jsf/facelets\"");
		writeLine("xmlns:f=\"http://java.sun.com/jsf/core\"");
		writeLine("xmlns:h=\"http://java.sun.com/jsf/html\"");
		writeLine("xmlns:p=\"http://primefaces.org/ui\">");
		skipLine();

		writeLine("<!-- -->");
		writeLine("<!-- auto generated jsf file -->");
		writeLine("<!-- write modifications between specific code marks -->");
		writeLine("<!-- processed by skeleton-generator -->");
		writeLine("<!-- -->");
		skipLine();
		
		writeLine("<div class=\"modal-header\">");
		writeLine("<h2>");
		writeLine("#{i18n." + currentBean.objectName + "Detail}");
		writeLine("</h2>");
		writeLine("</div>");
		skipLine();
		
		writeLine("<div class=\"modal-body\">");

		writeLine("<h:panelGroup id=\"" +  currentBean.objectName + "DetailPanelGroup\">");
		skipLine();

		writeLine("<ui:param name=\"view\" value=\"#{" + parentBean.detailViewObjectName + ".selected" + currentBean.className + "}\"/>");
		writeLine("<ui:param name=\"form\" value=\"#{view.form}\"/>");
		skipLine();

		writeLine("<div class=\"row\">");
		skipLine();

		for (ViewProperty property : currentBean.formBean.properties) {
			writeInput(property, currentBean);
		}

		writeLine("</div>");
		skipLine();
		
		
		if (this.oneToManyComponent.referenceBean.createEnabled) {
			writeLine("<p:commandButton value=\"#{i18n.save}\" action=\"#{" + parentBean.detailControllerObjectName + ".save" + currentBean.className + "}\"" 
					+ " rendered=\"#{empty view.id}\""
					+ " styleClass=\"btn btn-success\" process=\"@form:" + currentBean.objectName + "DetailPanelGroup\" update=\":messages, @form:" +  currentBean.objectName + "PanelGroup, @form:" + currentBean.objectName
					+ "DetailPanelGroup\" oncomplete=\"if (#{empty facesContext.maximumSeverity or facesContext.maximumSeverity.ordinal ==0}) $('#" + currentBean.objectName + "Modal').modal('hide')\"/>");
		}
		
		if (this.oneToManyComponent.referenceBean.updateEnabled) {
			writeLine("<p:commandButton value=\"#{i18n.update}\" action=\"#{" + parentBean.detailControllerObjectName + ".update" + currentBean.className+ "}\"" 
					+ " rendered=\"#{not empty view.id}\" disabled=\"#{not view.canUpdate}\""
					+ " styleClass=\"btn btn-success\""
					+ " process=\"@form:" + currentBean.objectName + "DetailPanelGroup + \" update=\":messages, @form:" +  currentBean.objectName + "PanelGroup, @form:" + currentBean.objectName + "DetailPanelGroup\" oncomplete=\""
					+ "if (#{empty facesContext.maximumSeverity or facesContext.maximumSeverity.ordinal==0}) $('#" + currentBean.objectName + "Modal').modal('hide')\"/>");
		}
		
		writeLine("<p:commandButton value=\"#{i18n.cancel}\" actionListener=\"#{" + parentBean.listControllerObjectName + ".resetForm}\" styleClass=\"btn btn-info\" immediate=\"true\" process=\"@this\" update=\":messages, @form:" +  currentBean.objectName
				+ "PanelGroup\" oncomplete=\"$('#" + currentBean.objectName + "Modal').modal('hide')\"/>");

		skipLine();

		this.writeNotOverridableContent();
		skipLine();

		writeLine("</h:panelGroup>");
		skipLine();
		
		writeLine("</div>");
		skipLine();

		writeLine("</ui:composition>");

	}
}
