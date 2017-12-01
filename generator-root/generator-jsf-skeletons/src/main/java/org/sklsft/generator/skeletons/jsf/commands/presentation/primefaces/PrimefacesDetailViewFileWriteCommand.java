package org.sklsft.generator.skeletons.jsf.commands.presentation.primefaces;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.ui.ViewProperty;

public class PrimefacesDetailViewFileWriteCommand extends PrimefacesXhtmlFileWriteCommand {

	private Bean bean;
	
	public PrimefacesDetailViewFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-webapp" + File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "sections" + File.separator  + bean.myPackage.name + File.separator + bean.className.toLowerCase(), bean.className + "Details");

		this.bean = bean;
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("<ui:composition xmlns=\"http://www.w3.org/1999/xhtml\"");
        writeLine("xmlns:ui=\"http://java.sun.com/jsf/facelets\"");
        writeLine("xmlns:f=\"http://java.sun.com/jsf/core\"");
        writeLine("xmlns:h=\"http://java.sun.com/jsf/html\"");
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
		writeLine("<f:viewParam name=\"id\" value=\"#{" + bean.detailViewObjectName + ".selected" + bean.className + ".id}\" />");
		writeLine("<f:viewAction action=\"#{" + bean.detailControllerObjectName + ".load}\" />");
		writeLine("</f:metadata>");

        writeLine("<ui:define name=\"content\">");
        skipLine();
        
        writeLine("<h:form>");
        skipLine();

        writeLine("<ui:include src=\"/sections/" + bean.myPackage.name + "/" + this.bean.className.toLowerCase() + "/" + bean.className + "DetailsMenu.xhtml\"/>");
        skipLine();
        
        writeLine("<h2>#{i18n." + bean.objectName + "Detail}</h2>");
        
        writeLine("<h:panelGroup id=\"" + this.bean.objectName + "DetailPanelGroup\">");
        skipLine();

        writeLine("<ui:param name=\"view\" value=\"#{" + this.bean.detailViewObjectName + ".selected" + this.bean.className + "}\"/>");
        writeLine("<ui:param name=\"form\" value=\"#{view.form}\"/>");
        skipLine();
        
        writeLine("<div class=\"row\">");

        for (ViewProperty property : this.bean.formBean.properties)
        {
            writeInput(property, bean);
        }

        writeLine("</div>");
        
        skipLine();
        
       
        if (this.bean.updateEnabled)
        {
            writeLine("<p:commandButton value=\"#{i18n.update}\" disabled=\"#{not view.canUpdate}\" action=\"#{" + this.bean.detailControllerObjectName + ".update}\" styleClass=\"btn btn-success\" process=\"@form\" update=\":messages, @form:" + this.bean.objectName + "DetailPanelGroup\"/>"); 
        }

        this.writeNotOverridableContent();
        skipLine();
        
        writeLine("</h:panelGroup>");
        skipLine();
        
        writeLine("</h:form>");
        
        writeLine("<script>$('#" + bean.objectName + "DetailsMenu').addClass('active');</script>");
        
        writeLine("</ui:define>");
        writeLine("</ui:composition>");
    }
}
