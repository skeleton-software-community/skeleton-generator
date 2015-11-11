package org.sklsft.generator.bc.file.command.impl.presentation.jsf.richfaces4;

import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.Property;

public class JsfCreationViewFileWriteCommand extends JsfXhtmlFileWriteCommand {

	private Bean bean;
	
	public JsfCreationViewFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + "\\" + bean.myPackage.model.project.projectName + "-webapp\\src\\main\\webapp\\sections\\"  + bean.myPackage.name + "\\" + bean.className.toLowerCase(), bean.className + "Creation");

		this.bean = bean;
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("<ui:composition xmlns=" + CHAR_34 + "http://www.w3.org/1999/xhtml" + CHAR_34);
        writeLine("xmlns:ui=" + CHAR_34 + "http://java.sun.com/jsf/facelets" + CHAR_34);
        writeLine("xmlns:f=" + CHAR_34 + "http://java.sun.com/jsf/core" + CHAR_34);
        writeLine("xmlns:h=" + CHAR_34 + "http://java.sun.com/jsf/html" + CHAR_34);
        writeLine("xmlns:rich=" + CHAR_34 + "http://richfaces.org/rich" + CHAR_34);
        writeLine("xmlns:a4j=" + CHAR_34 + "http://richfaces.org/a4j" + CHAR_34);
        writeLine("xmlns:c=" + CHAR_34 + "http://java.sun.com/jstl/core" + CHAR_34 + ">");
        skipLine();

        writeLine("<!-- -->");
        writeLine("<!-- auto generated jsf file -->");
        writeLine("<!-- write modifications between specific code marks -->");
        writeLine("<!-- processed by skeleton-generator -->");
        writeLine("<!-- -->");
        skipLine();

        writeLine("<div class=" + CHAR_34 + "modal-header" + CHAR_34 + ">");
		writeLine("<h2>");
		writeLine("#{i18n." + bean.objectName + "Detail}");
		writeLine("</h2>");
		writeLine("</div>");
		skipLine();
		
		writeLine("<div class=" + CHAR_34 + "modal-body" + CHAR_34 + ">");
		skipLine();
		
		writeLine("<h:panelGroup id=" + CHAR_34 + this.bean.objectName + "CreationPanelGroup" + CHAR_34 + ">");
        
        writeLine("<a4j:region>");
        skipLine();

        writeLine("<ui:param name=" + CHAR_34 + this.bean.objectName + CHAR_34 + " value=" + CHAR_34 + "#{" + this.bean.listViewObjectName + ".new" + this.bean.className + "}" + CHAR_34 + " scope=" + CHAR_34 + "request" + CHAR_34 + "/>");
        skipLine();
        
        writeLine("<div class=" + CHAR_34 + "row" + CHAR_34 + ">");

        for (Property property : this.bean.fullViewBean.properties) {
        	writeInput(property, bean);
        }
        
        writeLine("</div>");

        skipLine();        
        
        writeLine("<a4j:commandButton value=" + CHAR_34 + "#{i18n.save}" + CHAR_34 + " action=" + CHAR_34 + "#{" + bean.listControllerObjectName + ".save" + this.bean.className + "}"
				+ CHAR_34 + " styleClass=" + CHAR_34 + "btn btn-success" + CHAR_34 + " execute=" + CHAR_34 + "@region" + CHAR_34 + " render=" + CHAR_34 + bean.objectName + "PanelGroup, " + bean.objectName
				+ "CreationPanelGroup" + CHAR_34 + " oncomplete=" + CHAR_34 + "if (#{empty facesContext.maximumSeverity or facesContext.maximumSeverity.ordinal ==0}) $('#" + bean.objectName + "CreationModalPanel').modal('hide');"
				+ CHAR_34 + "/>");
		writeLine("<a4j:commandButton value=" + CHAR_34 + "#{i18n.cancel}" + CHAR_34 + " actionListener=" + CHAR_34 + "#{" + bean.listControllerObjectName + ".resetForm}" + CHAR_34
				+ " styleClass=" + CHAR_34 + "btn btn-info" + CHAR_34 
				+ " immediate=" + CHAR_34 + "true" + CHAR_34 + " execute=" + CHAR_34 + "@region" + CHAR_34 + " render=" + CHAR_34 + bean.objectName + "PanelGroup" + CHAR_34 
				+ " oncomplete=" + CHAR_34 + "$('#" + bean.objectName + "CreationModalPanel').modal('hide')" + CHAR_34 + "/>");


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
