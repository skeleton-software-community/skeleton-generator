package org.sklsft.generator.bc.file.command.impl.presentation.jsf.richfaces3;

import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.Property;

public class JsfModalViewFileWriteCommand extends JsfXhtmlFileWriteCommand {

	private Bean bean;
	
	public JsfModalViewFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + "\\" + bean.myPackage.model.project.projectName + "-webapp\\src\\main\\webapp\\sections\\"  + bean.myPackage.name + "\\" + bean.className.toLowerCase(), bean.className + "Modal");

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

        writeLine("<br/>");
        writeLine("<rich:messages infoClass=" + CHAR_34 + "infoMessage" + CHAR_34 + " errorClass=" + CHAR_34 + "errorMessage" + CHAR_34 + " globalOnly=" + CHAR_34 + "true" + CHAR_34 + "/>");
        writeLine("<br/>");
        skipLine();
        
        
        writeLine("<h:panelGroup id=" + CHAR_34 + this.bean.objectName + "DetailPanelGroup" + CHAR_34 + ">");
        
        writeLine("<h:form>");
        skipLine();

        writeLine("<c:set var=" + CHAR_34 + this.bean.objectName + CHAR_34 + " value=" + CHAR_34 + "#{" + this.bean.listViewObjectName + ".selected" + this.bean.className + "}" + CHAR_34 + " scope=" + CHAR_34 + "request" + CHAR_34 + "/>");
        skipLine();
        
        writeLine("<h:panelGrid columns=" + CHAR_34 + "3" + CHAR_34 + ">");
        skipLine();

        for (Property property : this.bean.fullViewBean.properties)
        {
            writeLine("<h:outputText value=" + CHAR_34 + "#{i18n." + this.bean.objectName + property.capName + "} : " + CHAR_34 + "/>");
            
            writeDetailComponent(property, bean);

            writeLine("<h:message for=" + CHAR_34 + this.bean.objectName + property.capName + CHAR_34 + " styleClass=" + CHAR_34 + "detailErrorMessage" + CHAR_34 + "/>");
            skipLine();
        }

        writeLine("</h:panelGrid>");
        skipLine();
        
        writeLine("<br/>");
        writeLine("<br/>");
        skipLine();
        
        writeLine("<h:panelGrid columns=" + CHAR_34 + "2" + CHAR_34 + ">");
        
        if (bean.createEnabled) {
	        writeLine("<a4j:commandButton value=" + CHAR_34 + "#{i18n.save}" + CHAR_34 + " action=" + CHAR_34 + "#{" + bean.listControllerObjectName + ".save" + this.bean.className + "}"+ CHAR_34
	        		+ " rendered=" + CHAR_34 + "#{empty " + bean.objectName + ".id}" + CHAR_34
	        		+ " styleClass=" + CHAR_34 + "simpleButton" + CHAR_34 + " reRender=" + CHAR_34 + bean.objectName + "PanelGroup, " + bean.objectName
					+ "DetailPanelGroup" + CHAR_34 + " oncomplete=" + CHAR_34 + "if (#{empty facesContext.maximumSeverity or facesContext.maximumSeverity.ordinal ==0}) Richfaces.hideModalPanel('" + bean.objectName + "Modal')"
					+ CHAR_34 + "/>");
        }
        
        if (bean.updateEnabled) {        
        	writeLine("<a4j:commandButton value=" + CHAR_34 + "#{i18n.update}" + CHAR_34 + " action=" + CHAR_34 + "#{" + bean.listControllerObjectName + ".update" + this.bean.className + "}" + CHAR_34 
        			+ " rendered=" + CHAR_34 + "#{not empty " + bean.objectName + ".id}" + CHAR_34
        			+ " styleClass=" + CHAR_34 + "simpleButton" + CHAR_34 + " reRender=" + CHAR_34 + bean.objectName + "PanelGroup, " + bean.objectName
					+ "DetailPanelGroup" + CHAR_34 + " oncomplete=" + CHAR_34 + "if (#{empty facesContext.maximumSeverity or facesContext.maximumSeverity.ordinal ==0}) Richfaces.hideModalPanel('" + bean.objectName + "Modal')"
					+ CHAR_34 + "/>");
        }
        
        writeLine("<a4j:commandButton value=" + CHAR_34 + "#{i18n.cancel}" + CHAR_34 + " actionListener=" + CHAR_34 + "#{" + bean.listControllerObjectName + ".resetForm}" + CHAR_34
        		+ " styleClass=" + CHAR_34 + "simpleButton" + CHAR_34 + " immediate=" + CHAR_34 + "true" + CHAR_34 + " reRender=" + CHAR_34 + bean.objectName
				+ "PanelGroup" + CHAR_34 + " oncomplete=" + CHAR_34 + "Richfaces.hideModalPanel('" + bean.objectName + "Modal')" + CHAR_34 + "/>");

        
        writeLine("</h:panelGrid>");
        skipLine();

        this.writeNotOverridableContent();
        skipLine();

        writeLine("</h:form>");
        skipLine();
        
        writeLine("</h:panelGroup>");

        skipLine();
        
        writeLine("</ui:composition>");
    }
}
