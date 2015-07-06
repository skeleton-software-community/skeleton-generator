package org.sklsft.generator.bc.file.command.impl.presentation.jsf.common;

import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.presentation.jsf.JsfXhtmlFileWriteCommand;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.Property;

public class CommonSimpleJsfDetailViewFileWriteCommand extends JsfXhtmlFileWriteCommand {

	private Bean bean;
	
	public CommonSimpleJsfDetailViewFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + "\\" + bean.myPackage.model.project.projectName + "-webapp\\src\\main\\webapp\\sections\\"  + bean.myPackage.name + "\\" + bean.className.toLowerCase(), bean.className + "Details");

		this.bean = bean;
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("<ui:composition xmlns=" + (char)34 + "http://www.w3.org/1999/xhtml" + (char)34);
        writeLine("xmlns:ui=" + (char)34 + "http://java.sun.com/jsf/facelets" + (char)34);
        writeLine("xmlns:f=" + (char)34 + "http://java.sun.com/jsf/core" + (char)34);
        writeLine("xmlns:h=" + (char)34 + "http://java.sun.com/jsf/html" + (char)34);
        writeLine("xmlns:rich=" + (char)34 + "http://richfaces.org/rich" + (char)34);
        writeLine("xmlns:a4j=" + (char)34 + "http://richfaces.org/a4j" + (char)34);
        writeLine("xmlns:c=" + (char)34 + "http://java.sun.com/jstl/core" + (char)34 + ">");
        skipLine();

        writeLine("<!-- -->");
        writeLine("<!-- auto generated jsf file -->");
        writeLine("<!-- write modifications between specific code marks -->");
        writeLine("<!-- processed by skeleton-generator -->");
        writeLine("<!-- -->");
        skipLine();

        writeLine("<br/>");
        writeLine("<rich:messages infoClass=" + (char)34 + "infoMessage" + (char)34 + " errorClass=" + (char)34 + "errorMessage" + (char)34 + " globalOnly=" + (char)34 + "true" + (char)34 + "/>");
        writeLine("<br/>");
        
        writeLine("<h2>");
        writeLine("#{i18n." + bean.objectName + "Detail}");
        writeLine("</h2>");
        skipLine();
        
        writeLine("<br/>");
        skipLine();
        
        writeLine("<h:panelGroup id=" + (char)34 + bean.objectName + "DetailPanelGroup" + (char)34 + ">");
        writeLine("<h:panelGroup rendered=" + (char)34 + "#{!" + this.bean.controllerObjectName + ".displaySuccessfull}" + (char)34 + " styleClass=" + (char)34 + "errorMessage" + (char)34 + " layout=" + (char)34 + "block" + (char)34 + ">");
        writeLine("<h:outputText value=" + (char)34 + "#{i18n.displayFailure}" + (char)34 + "/>");
        writeLine("</h:panelGroup>");
        writeLine("<h:panelGroup rendered=" + (char)34 + "#{" + bean.controllerObjectName + ".displaySuccessfull}" + (char)34 + ">");
        skipLine();
        
        writeLine("<h:form>");
        skipLine();
        
        writeLine("<c:set var=" + (char)34 + bean.objectName + (char)34 + " value=" + (char)34 + "#{" + bean.controllerObjectName + ".selected" + bean.className + "}" + (char)34 + " scope=" + (char)34 + "request" + (char)34 + "/>");
        skipLine();
        
        writeLine("<h:panelGrid columns=" + (char)34 + "3" + (char)34 + ">");
        skipLine();

        for (Property property : bean.getVisibleProperties())
        {
            if (property.visibility.isDetailVisible())
            {
                writeLine("<h:outputText value=" + (char)34 + "#{i18n." + bean.objectName + property.capName + "} : " + (char)34 + "/>");

                writeDetailComponent(property, bean);

                writeLine("<h:message for=" + (char)34 + bean.objectName + property.capName + (char)34 + " styleClass=" + (char)34 + "detailErrorMessage" + (char)34 + "/>");
                skipLine();
            }
        }

        writeLine("</h:panelGrid>");
        skipLine();
        
        writeLine("<br/>");
        writeLine("<br/>");
        skipLine();
        
        writeLine("<h:panelGrid columns=" + (char)34 + "3" + (char)34 + ">");
        
        if (this.bean.updateEnabled)
        {
            writeLine("<a4j:commandButton value=" + (char)34 + "#{i18n.update}" + (char)34 + " action=" + (char)34 + "#{" + bean.controllerObjectName + ".update" + bean.className + "}" + (char)34 + " rendered=" + (char)34 + "#{!" + bean.controllerObjectName + ".creationTag}" + (char)34 + " styleClass=" + (char)34 + "simpleButton" + (char)34 + " reRender=" + (char)34 + bean.objectName + "PanelGroup, " + bean.objectName + "DetailPanelGroup" + (char)34 + " oncomplete=" + (char)34 + "if (#{facesContext.maximumSeverity.ordinal==0}) Richfaces.hideModalPanel('" + bean.objectName + "ModalPanel')" + (char)34 + "/>");
        }
        writeLine("<a4j:commandButton value=" + (char)34 + "#{i18n.save}" + (char)34 + " action=" + (char)34 + "#{" + bean.controllerObjectName + ".save" + bean.className + "}" + (char)34 + " rendered=" + (char)34 + "#{" + bean.controllerObjectName + ".creationTag}" + (char)34 + " disabled=" + (char)34 + "#{!" + bean.controllerObjectName + ".creationTag}" + (char)34 + " styleClass=" + (char)34 + "simpleButton" + (char)34 + " reRender=" + (char)34 + bean.objectName + "PanelGroup, " + bean.objectName + "DetailPanelGroup" + (char)34 + " oncomplete=" + (char)34 + "if (#{facesContext.maximumSeverity.ordinal==0}) Richfaces.hideModalPanel('" + bean.objectName + "ModalPanel')" + (char)34 + "/>");
        writeLine("<a4j:commandButton value=" + (char)34 + "#{i18n.cancel}" + (char)34 + " actionListener=" + (char)34 + "#{" + bean.controllerObjectName + ".resetForm}" + (char)34 + " action=" + (char)34 + "#{" + bean.controllerObjectName + ".refresh}" + (char)34 + " styleClass=" + (char)34 + "simpleButton" + (char)34 + " immediate=" + (char)34 + "true" + (char)34 + " reRender=" + (char)34 + bean.objectName + "PanelGroup" + (char)34 + " oncomplete=" + (char)34 + "Richfaces.hideModalPanel('" + bean.objectName + "ModalPanel')" + (char)34 + "/>");
        writeLine("</h:panelGrid>");
        skipLine();

        this.writeNotOverridableContent();
        skipLine();

        writeLine("</h:form>");
        skipLine();
        
        writeLine("</h:panelGroup>");
        writeLine("</h:panelGroup>");
        skipLine();
        
        writeLine("</ui:composition>");
    }
}
