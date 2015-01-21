package org.sklsft.generator.bc.file.command.impl.presentation.jsf.richfaces3;

import java.io.IOException;

import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.Property;
import org.sklsft.generator.model.om.UniqueComponent;

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

        writeLine("<h:panelGroup id=" + CHAR_34 + currentBean.objectName + "PanelGroup" + CHAR_34 + ">");
        skipLine();
        
        writeLine("<h:form>");
        skipLine();
        
        writeLine("<c:set var=" + CHAR_34 + currentBean.objectName + CHAR_34 + " value=" + CHAR_34 + "#{" + parentBean.detailViewObjectName + ".selected" + currentBean.className + "}" + CHAR_34 + " scope=" + CHAR_34 + "request" + CHAR_34 + "/>");
        skipLine();
        
        writeLine("<h:panelGrid columns=" + CHAR_34 + "3" + CHAR_34 + ">");
        skipLine();

        for (Property property : currentBean.getVisibleProperties())
        {
            if (property.visibility.isDetailVisible())
            {
                writeLine("<h:outputText value=" + CHAR_34 + "#{i18n." + currentBean.objectName + property.capName + "} : " + CHAR_34 + "/>");
                writeDetailComponent(property, currentBean);
                writeLine("<h:message for=" + CHAR_34 + currentBean.objectName + property.capName + CHAR_34 + " styleClass=" + CHAR_34 + "detailErrorMessage" + CHAR_34 + "/>");
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
            writeLine("<a4j:commandButton value=" + CHAR_34 + "#{i18n.update}" + CHAR_34 + " action=" + CHAR_34 + "#{" + parentBean.listControllerObjectName + ".update" + currentBean.className + "}" + CHAR_34 + " styleClass=" + CHAR_34 + "simpleButton" + CHAR_34 + " reRender=" + CHAR_34 + currentBean.objectName + "PanelGroup" + CHAR_34 + "/>");
        }
    
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
