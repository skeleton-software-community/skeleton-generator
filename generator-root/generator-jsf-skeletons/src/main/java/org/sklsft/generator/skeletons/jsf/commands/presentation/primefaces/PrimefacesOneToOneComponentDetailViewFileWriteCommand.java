package org.sklsft.generator.skeletons.jsf.commands.presentation.primefaces;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.model.domain.ui.ViewProperty;

public class PrimefacesOneToOneComponentDetailViewFileWriteCommand extends PrimefacesXhtmlFileWriteCommand {

	private OneToOneComponent oneToOneComponent;

	public PrimefacesOneToOneComponentDetailViewFileWriteCommand(OneToOneComponent oneToOneComponent) {
		super(oneToOneComponent.referenceBean.myPackage.model.project.workspaceFolder + File.separator + oneToOneComponent.referenceBean.myPackage.model.project.projectName
				+ "-webapp" + File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "sections" + File.separator + oneToOneComponent.referenceBean.myPackage.name + File.separator + oneToOneComponent.parentBean.className.toLowerCase(),
				oneToOneComponent.referenceBean.className + "Details");

		this.oneToOneComponent = oneToOneComponent;
	}

	@Override
	protected void writeContent() throws IOException {
		
		Bean currentBean = oneToOneComponent.referenceBean;
        Bean parentBean = oneToOneComponent.parentBean;

        writeLine("<ui:composition xmlns=\"http://www.w3.org/1999/xhtml\"");
		writeLine("xmlns:ui = \"http://java.sun.com/jsf/facelets\"");
		writeLine("xmlns:f = \"http://java.sun.com/jsf/core\"");
		writeLine("xmlns:h = \"http://java.sun.com/jsf/html\"");
		writeLine("xmlns:p=\"http://primefaces.org/ui\"");
        writeLine("template=\"/templates/template.xhtml\">");
        skipLine();

        writeLine("<!-- -->");
        writeLine("<!-- auto generated jsf file -->");
        writeLine("<!-- write modifications between specific code marks -->");
        writeLine("<!-- processed by skeleton-generator -->");
        writeLine("<!-- -->");
        skipLine();
        
        writeLine("<f:metadata>");
		writeLine("<f:viewParam name=\"id\" value=\"#{" + parentBean.detailViewObjectName + ".selected" + parentBean.className + ".id}\" />");
		writeLine("<f:viewAction action=\"#{" + parentBean.detailControllerObjectName + ".load" + currentBean.className + "}\" />");
		writeLine("</f:metadata>");

        writeLine("<ui:define name=\"content\">");
        skipLine();
        
        writeLine("<h:form>");
        skipLine();
        
        writeLine("<ui:include src=\"/sections/" + parentBean.myPackage.name + "/" + parentBean.className.toLowerCase() + "/" + parentBean.className + "DetailsMenu.xhtml\"/>");
        skipLine();

        writeLine("<h2>");
		writeLine("#{i18n." + currentBean.objectName + "Detail}");
		writeLine("</h2>");
        
        writeLine("<h:panelGroup id=\"" + currentBean.objectName + "DetailPanelGroup\">");
		skipLine();
        
		writeLine("<ui:param name=\"view\" value=\"#{" + parentBean.detailViewObjectName + ".selected" + currentBean.className + "}\"/>");
        writeLine("<ui:param name=\"form\" value=\"#{view.form}\"/>");
        skipLine();
        
        writeLine("<div class=\"row\">");

		for (ViewProperty property : currentBean.formBean.properties) {
			if (property.visibility.isDetailVisible()) {
				writeInput(property, currentBean);
			}
		}

        writeLine("</div>");

        skipLine();
        
        if (this.oneToOneComponent.referenceBean.updateEnabled) {
            writeLine("<p:commandButton value=\"#{i18n.update}\"" 
            		+ " rendered=\"#{not empty view.id}\" disabled=\"#{not view.canUpdate}\""
            		+ " action=\"#{" + parentBean.detailControllerObjectName + ".update" + currentBean.className + "}\""
            		+ " styleClass=\"btn btn-success\" process=\"@form\""
            		+ " update=\":messages, @form:" + currentBean.objectName + "DetailPanelGroup\"/>");
        }
        
        if (this.oneToOneComponent.referenceBean.deleteEnabled) {
            writeLine("<p:commandButton value=\"#{i18n.delete}\"" 
            		+ " rendered=\"#{not empty view.id}\""
            		+ " action=\"#{" + parentBean.detailControllerObjectName + ".delete" + currentBean.className + "}\""
            		+ " styleClass=\"btn btn-warning\" process=\"@this\""
            		+ " update=\":messages, @form:" + currentBean.objectName + "DetailPanelGroup\"/>");
        }
        
        if (this.oneToOneComponent.referenceBean.createEnabled) {
            writeLine("<p:commandButton value=\"#{i18n.save}\""
            		+ " rendered=\"#{empty view.id}\""
            		+ " action=\"#{" + parentBean.detailControllerObjectName + ".save" + currentBean.className + "}\""
            		+ " styleClass=\"btn btn-success\" process=\"@form\""
            		+ " update=\":messages, @form:" + currentBean.objectName + "DetailPanelGroup\"/>");
        }
    
        skipLine();
        this.writeNotOverridableContent();
        skipLine();
        
        writeLine("</h:panelGroup>");
        skipLine();
        
        writeLine("</h:form>");
        
        writeLine("<script>$('#" + currentBean.objectName + "DetailsMenu').addClass('active');</script>");
        

		writeLine("</ui:define>");
		writeLine("</ui:composition>");

    }
}
