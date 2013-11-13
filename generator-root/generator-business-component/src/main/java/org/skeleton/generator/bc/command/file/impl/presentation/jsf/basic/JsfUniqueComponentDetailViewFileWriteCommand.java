package org.skeleton.generator.bc.command.file.impl.presentation.jsf.basic;

import java.io.IOException;

import org.skeleton.generator.bc.command.file.impl.presentation.jsf.JsfXhtmlFileWriteCommand;
import org.skeleton.generator.model.om.Bean;
import org.skeleton.generator.model.om.Property;
import org.skeleton.generator.model.om.UniqueComponent;

public class JsfUniqueComponentDetailViewFileWriteCommand extends JsfXhtmlFileWriteCommand {

	private UniqueComponent uniqueComponent;

	public JsfUniqueComponentDetailViewFileWriteCommand(UniqueComponent uniqueComponent) {
		super(uniqueComponent.referenceBean.myPackage.model.project.workspaceFolder + "\\" + uniqueComponent.referenceBean.myPackage.model.project.projectName
				+ "-webapp\\src\\main\\webapp\\sections\\" + uniqueComponent.referenceBean.myPackage.name + "\\" + uniqueComponent.parentBean.className.toLowerCase(),
				uniqueComponent.referenceBean.className + "Details");

		this.uniqueComponent = uniqueComponent;
	}

	@Override
	protected void writeContent() throws IOException {
		
		Bean currentBean = uniqueComponent.referenceBean;
        Bean parentBean = uniqueComponent.parentBean;

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

        writeLine("<h:panelGroup id=" + (char)34 + currentBean.objectName + "PanelGroup" + (char)34 + ">");
        writeLine("<h:panelGroup rendered=" + (char)34 + "#{!" + parentBean.controllerObjectName + ".displaySuccessfull}" + (char)34 + " styleClass=" + (char)34 + "errorMessage" + (char)34 + " layout=" + (char)34 + "block" + (char)34 + ">");
        writeLine("<h:outputText value=" + (char)34 + "#{i18n.displayFailure}" + (char)34 + "/>");
        writeLine("</h:panelGroup>");
        writeLine("<h:panelGroup rendered=" + (char)34 + "#{" + parentBean.controllerObjectName + ".displaySuccessfull}" + (char)34 + ">");
        skipLine();
        
        writeLine("<h:form>");
        skipLine();
        
        writeLine("<c:set var=" + (char)34 + currentBean.objectName + (char)34 + " value=" + (char)34 + "#{" + parentBean.controllerObjectName + ".selected" + currentBean.className + "}" + (char)34 + " scope=" + (char)34 + "request" + (char)34 + "/>");
        skipLine();
        
        writeLine("<h:panelGrid columns=" + (char)34 + "3" + (char)34 + ">");
        skipLine();

        for (Property property : currentBean.getVisiblePropertyList())
        {
            if (property.visibility.isDetailVisible())
            {
                writeLine("<h:outputText value=" + (char)34 + "#{i18n." + currentBean.objectName + property.capName + "} : " + (char)34 + "/>");
                writeDetailComponent(property, currentBean);
                writeLine("<h:message for=" + (char)34 + currentBean.objectName + property.capName + (char)34 + " styleClass=" + (char)34 + "detailErrorMessage" + (char)34 + "/>");
                skipLine();
            }
        }

        writeLine("</h:panelGrid>");
        skipLine();
        
        writeLine("<br/>");
        writeLine("<br/>");
        skipLine();
        
        if (this.uniqueComponent.referenceBean.updateEnabled)
        {
            writeLine("<a4j:commandButton value=" + (char)34 + "#{i18n.update}" + (char)34 + " action=" + (char)34 + "#{" + parentBean.controllerObjectName + ".update" + currentBean.className + "}" + (char)34 + " styleClass=" + (char)34 + "simpleButton" + (char)34 + " reRender=" + (char)34 + currentBean.objectName + "PanelGroup" + (char)34 + "/>");
        }
    
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
