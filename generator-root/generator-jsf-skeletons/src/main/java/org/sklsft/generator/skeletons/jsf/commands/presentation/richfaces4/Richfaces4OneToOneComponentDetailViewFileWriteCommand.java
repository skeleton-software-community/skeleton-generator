package org.sklsft.generator.skeletons.jsf.commands.presentation.richfaces4;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.model.domain.business.Property;

public class Richfaces4OneToOneComponentDetailViewFileWriteCommand extends Richfaces4XhtmlFileWriteCommand {

	private OneToOneComponent oneToOneComponent;

	public Richfaces4OneToOneComponentDetailViewFileWriteCommand(OneToOneComponent oneToOneComponent) {
		super(oneToOneComponent.referenceBean.myPackage.model.project.workspaceFolder + File.separator + oneToOneComponent.referenceBean.myPackage.model.project.projectName
				+ "-webapp" + File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "sections" + File.separator + oneToOneComponent.referenceBean.myPackage.name + File.separator + oneToOneComponent.parentBean.className.toLowerCase(),
				oneToOneComponent.referenceBean.className + "Details");

		this.oneToOneComponent = oneToOneComponent;
	}

	@Override
	protected void writeContent() throws IOException {
		
		Bean currentBean = oneToOneComponent.referenceBean;
        Bean parentBean = oneToOneComponent.parentBean;

        writeLine("<ui:composition xmlns=" + CHAR_34 + "http://www.w3.org/1999/xhtml" + CHAR_34);
		writeLine("xmlns:ui = " + CHAR_34 + "http://java.sun.com/jsf/facelets" + CHAR_34);
		writeLine("xmlns:f = " + CHAR_34 + "http://java.sun.com/jsf/core" + CHAR_34);
		writeLine("xmlns:h = " + CHAR_34 + "http://java.sun.com/jsf/html" + CHAR_34);
		writeLine("xmlns:rich = " + CHAR_34 + "http://richfaces.org/rich" + CHAR_34);
		writeLine("xmlns:a4j = " + CHAR_34 + "http://richfaces.org/a4j" + CHAR_34);
		writeLine("xmlns:fn=" + CHAR_34 + "http://java.sun.com/jsp/jstl/functions" + CHAR_34);
		writeLine("xmlns:c=" + CHAR_34 + "http://java.sun.com/jstl/core" + CHAR_34);
        writeLine("template=" + CHAR_34 + "/templates/template.xhtml" + CHAR_34 + ">");
        skipLine();

        writeLine("<!-- -->");
        writeLine("<!-- auto generated jsf file -->");
        writeLine("<!-- write modifications between specific code marks -->");
        writeLine("<!-- processed by skeleton-generator -->");
        writeLine("<!-- -->");
        skipLine();
        
        writeLine("<f:metadata>");
		writeLine("<f:viewParam name=" + CHAR_34 + "id" + CHAR_34 + " value=" + CHAR_34 + "#{" + parentBean.detailViewObjectName + ".selected" + parentBean.className + ".id}" + CHAR_34 + " />");
		writeLine("<f:viewAction action=" + CHAR_34 + "#{" + parentBean.detailControllerObjectName + ".load" + currentBean.className + "}" + CHAR_34 + " />");
		writeLine("</f:metadata>");

        writeLine("<ui:define name=" + CHAR_34 + "content" + CHAR_34 + ">");
        skipLine();
        
        writeLine("<h:form>");
        skipLine();
        
        writeLine("<ui:include src=" + CHAR_34 + "/sections/" + parentBean.myPackage.name + "/" + parentBean.className.toLowerCase() + "/" + parentBean.className + "DetailsMenu.xhtml" + CHAR_34 + "/>");
        skipLine();

        writeLine("<h2>");
		writeLine("#{i18n." + currentBean.objectName + "Detail}");
		writeLine("</h2>");
        
        writeLine("<h:panelGroup id=" + CHAR_34 + currentBean.objectName + "DetailPanelGroup" + CHAR_34 + ">");
		skipLine();
		
		writeLine("<a4j:region>");
		skipLine();
        
		writeLine("<ui:param name=" + CHAR_34 + "view" + CHAR_34 + " value=" + CHAR_34 + "#{" + parentBean.detailViewObjectName + ".selected" + currentBean.className + "}" + CHAR_34 + "/>");
        writeLine("<ui:param name=" + CHAR_34 + "form" + CHAR_34 + " value=" + CHAR_34 + "#{view.form}" + CHAR_34 + "/>");
        skipLine();
        
        writeLine("<div class=" + CHAR_34 + "row" + CHAR_34 + ">");

		for (Property property : currentBean.formBean.properties) {
			if (property.visibility.isDetailVisible()) {
				writeInput(property, currentBean);
			}
		}

        writeLine("</div>");

        skipLine();
        
        if (this.oneToOneComponent.referenceBean.updateEnabled) {
            writeLine("<a4j:commandButton value=" + CHAR_34 + "#{i18n.update}" + CHAR_34 + 
            		" rendered=" + CHAR_34 + "#{not empty view.id}" + CHAR_34 + " disabled=" + CHAR_34 + "#{not view.canUpdate}" + CHAR_34 + 
            		" action=" + CHAR_34 + "#{" + parentBean.detailControllerObjectName + ".update" + currentBean.className + "}" + CHAR_34 + 
            		" styleClass=" + CHAR_34 + "btn btn-success" + CHAR_34 + " execute=" + CHAR_34 + "@region" + CHAR_34 + 
            		" render=" + CHAR_34 + currentBean.objectName + "DetailPanelGroup" + CHAR_34 + "/>");
        }
        
        if (this.oneToOneComponent.referenceBean.deleteEnabled) {
            writeLine("<a4j:commandButton value=" + CHAR_34 + "#{i18n.delete}" + CHAR_34 + 
            		" rendered=" + CHAR_34 + "#{not empty view.id}" + CHAR_34 +
            		" action=" + CHAR_34 + "#{" + parentBean.detailControllerObjectName + ".delete" + currentBean.className + "}" + CHAR_34 + 
            		" styleClass=" + CHAR_34 + "btn btn-warning" + CHAR_34 + " execute=" + CHAR_34 + "@region" + CHAR_34 + 
            		" render=" + CHAR_34 + currentBean.objectName + "DetailPanelGroup" + CHAR_34 + "/>");
        }
        
        if (this.oneToOneComponent.referenceBean.createEnabled) {
            writeLine("<a4j:commandButton value=" + CHAR_34 + "#{i18n.save}" + CHAR_34 + 
            		" rendered=" + CHAR_34 + "#{empty view.id}" + CHAR_34 +
            		" action=" + CHAR_34 + "#{" + parentBean.detailControllerObjectName + ".save" + currentBean.className + "}" + CHAR_34 + 
            		" styleClass=" + CHAR_34 + "btn btn-success" + CHAR_34 + " execute=" + CHAR_34 + "@region" + CHAR_34 + 
            		" render=" + CHAR_34 + currentBean.objectName + "DetailPanelGroup" + CHAR_34 + "/>");
        }
    
        skipLine();
        this.writeNotOverridableContent();
        skipLine();

        writeLine("</a4j:region>");
        skipLine();
        
        writeLine("</h:panelGroup>");
        skipLine();
        
        writeLine("</h:form>");
        
        writeLine("<script>$('#" + currentBean.objectName + "DetailsMenu').addClass('active');</script>");
        

		writeLine("</ui:define>");
		writeLine("</ui:composition>");

    }
}
