package org.sklsft.generator.bc.file.command.impl.presentation.jsf.richfaces4;

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
        
        writeLine("<a4j:region>");
        skipLine();
        
        writeLine("<ui:param name=" + CHAR_34 + currentBean.objectName + CHAR_34 + " value=" + CHAR_34 + "#{" + parentBean.detailViewObjectName + ".selected" + currentBean.className + "}" + CHAR_34 + "/>");
        skipLine();
        
        writeLine("<div class=" + CHAR_34 + "row" + CHAR_34 + ">");

        for (Property property : currentBean.getVisibleProperties())
        {
            if (property.visibility.isDetailVisible())
            {
                writeInput(property, currentBean);
            }
        }

        writeLine("</div>");
       
        writeLine("<br/>");
        writeLine("<br/>");
        skipLine();
        
        if (this.uniqueComponent.referenceBean.updateEnabled)
        {
            writeLine("<a4j:commandButton value=" + CHAR_34 + "#{i18n.update}" + CHAR_34 + " action=" + CHAR_34 + "#{" + parentBean.listControllerObjectName + ".update" + currentBean.className + "}" + CHAR_34 + " styleClass=" + CHAR_34 + "btn btn-success" + CHAR_34 + " execute=" + CHAR_34 + "@region" + CHAR_34 + " render=" + CHAR_34 + currentBean.objectName + "PanelGroup" + CHAR_34 + "/>");
        }
    
        skipLine();
        this.writeNotOverridableContent();
        skipLine();

        writeLine("</a4j:region>");
        skipLine();
        
        writeLine("</h:panelGroup>");
        skipLine();
        
        writeLine("</ui:composition>");

    }
}
