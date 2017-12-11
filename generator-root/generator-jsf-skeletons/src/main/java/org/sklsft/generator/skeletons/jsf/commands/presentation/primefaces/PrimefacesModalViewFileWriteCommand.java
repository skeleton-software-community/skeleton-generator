package org.sklsft.generator.skeletons.jsf.commands.presentation.primefaces;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.ui.ViewProperty;

public class PrimefacesModalViewFileWriteCommand extends PrimefacesXhtmlFileWriteCommand {

	private Bean bean;
	
	public PrimefacesModalViewFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-webapp" + File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "sections" + File.separator  + bean.myPackage.name + File.separator + bean.className.toLowerCase(), bean.className + "Modal");

		this.bean = bean;
	}

	@Override
	protected void writeContent() throws IOException {

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
		writeLine("#{i18n." + bean.objectName + "Detail}");
		writeLine("</h2>");
		writeLine("</div>");
		skipLine();
		
		writeLine("<div class=\"modal-body\">");
		skipLine();
		
		writeLine("<h:panelGroup id=\"" + this.bean.objectName + "DetailPanelGroup\">");

        writeLine("<ui:param name=\"view\" value=\"#{" + this.bean.listViewObjectName + ".selected" + this.bean.className + "}\"/>");
        writeLine("<ui:param name=\"form\" value=\"#{view.form}\"/>");
        skipLine();
        
        writeLine("<div class=\"row\">");

        for (ViewProperty property : this.bean.formBean.properties) {
        	writeInput(property, bean);
        }
        
        writeLine("</div>");

        skipLine();        
        
        if (bean.createEnabled) {
	        writeLine("<p:commandButton value=\"#{i18n.save}\" action=\"#{" + bean.listControllerObjectName + ".save}\""
	        		+ " rendered=\"#{empty view.id}\""
	        		+ " styleClass=\"btn btn-success\" process=\"@form:" + bean.objectName + "DetailPanelGroup\" update=\":messages, @form:" + bean.objectName + "PanelGroup, @form:" + bean.objectName
					+ "DetailPanelGroup\" oncomplete=\"onSuccess(args, function(){$('#" + bean.objectName + "Modal').modal('hide')});\"/>");
        }
        
        if (bean.updateEnabled) {
	        writeLine("<p:commandButton value=\"#{i18n.update}\" action=\"#{" + bean.listControllerObjectName + ".update}\""
					+ " rendered=\"#{not empty view.id}\" disabled=\"#{not view.canUpdate}\""
					+ " styleClass=\"btn btn-success\" process=\"@form:" + bean.objectName + "DetailPanelGroup\" update=\":messages, @form:" + bean.objectName + "PanelGroup, @form:" + bean.objectName
					+ "DetailPanelGroup\" oncomplete=\"onSuccess(args, function(){$('#" + bean.objectName + "Modal').modal('hide')});\"/>");
        }
        
		writeLine("<p:commandButton value=\"#{i18n.cancel}\" actionListener=\"#{" + bean.listControllerObjectName + ".resetForm}\""
				+ " styleClass=\"btn btn-info\""
				+ " immediate=\"true\" process=\"@this\" update=\"@form:" + bean.objectName + "PanelGroup\""
				+ " oncomplete=\"$('#" + bean.objectName + "Modal').modal('hide')\"/>");


        this.writeNotOverridableContent();
        skipLine();
        
        writeLine("</h:panelGroup>");
        skipLine();
        
        writeLine("</div>");
        skipLine();
        
        writeLine("</ui:composition>");
    }
}
