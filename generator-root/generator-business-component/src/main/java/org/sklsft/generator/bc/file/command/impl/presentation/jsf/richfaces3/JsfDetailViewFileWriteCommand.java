package org.sklsft.generator.bc.file.command.impl.presentation.jsf.richfaces3;

import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.model.domain.business.Property;

public class JsfDetailViewFileWriteCommand extends JsfXhtmlFileWriteCommand {

	private Bean bean;
	
	public JsfDetailViewFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + "\\" + bean.myPackage.model.project.projectName + "-webapp\\src\\main\\webapp\\sections\\"  + bean.myPackage.name + "\\" + bean.className.toLowerCase(), bean.className + "Details");

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
        writeLine("xmlns:c=" + CHAR_34 + "http://java.sun.com/jstl/core" + CHAR_34);
        writeLine("template=" + CHAR_34 + "/templates/template.xhtml" + CHAR_34 + ">");
        skipLine();

        writeLine("<!-- -->");
        writeLine("<!-- auto generated jsf file -->");
        writeLine("<!-- write modifications between specific code marks -->");
        writeLine("<!-- processed by skeleton-generator -->");
        writeLine("<!-- -->");
        skipLine();

        writeLine("<ui:define name=" + CHAR_34 + "content" + CHAR_34 + ">");
        skipLine();

        writeLine("<br/>");
        writeLine("<rich:messages infoClass=" + CHAR_34 + "infoMessage" + CHAR_34 + " errorClass=" + CHAR_34 + "errorMessage" + CHAR_34 + " globalOnly=" + CHAR_34 + "true" + CHAR_34 + "/>");
        writeLine("<br/>");
        skipLine();
        
        writeLine("<rich:tabPanel id=" + CHAR_34 + this.bean.objectName + "TabPanel" + CHAR_34 + " switchType=" + CHAR_34 + "ajax" + CHAR_34 + ">");
        
        writeLine("<rich:tab id=" + CHAR_34 + this.bean.objectName + "Tab" + CHAR_34 + " label=" + CHAR_34 + "#{i18n." + this.bean.objectName + "Detail}" + CHAR_34);
        writeLine("action=" + CHAR_34 + "#{" + this.bean.detailControllerObjectName + ".load}" + CHAR_34 + " reRender=" + CHAR_34 + this.bean.objectName + "DetailPanelGroup" + CHAR_34 + ">");
        skipLine();
        
        writeLine("<h:panelGroup id=" + CHAR_34 + this.bean.objectName + "DetailPanelGroup" + CHAR_34 + ">");
        skipLine();
        
        writeLine("<h:form>");
        skipLine();

        writeLine("<c:set var=" + CHAR_34 + this.bean.objectName + CHAR_34 + " value=" + CHAR_34 + "#{" + this.bean.detailControllerObjectName + "." +  this.bean.detailViewObjectName + ".selected" + this.bean.className + "}" + CHAR_34 + " scope=" + CHAR_34 + "request" + CHAR_34 + "/>");
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
        
        if (this.bean.updateEnabled)
        {
            writeLine("<a4j:commandButton value=" + CHAR_34 + "#{i18n.update}" + CHAR_34 + " action=" + CHAR_34 + "#{" + this.bean.detailControllerObjectName + ".update" + this.bean.className + "}" + CHAR_34 + " styleClass=" + CHAR_34 + "simpleButton" + CHAR_34 + " reRender=" + CHAR_34 + this.bean.objectName + "DetailPanelGroup" + CHAR_34 + "/>"); 
        }
        
        writeLine("</h:panelGrid>");
        skipLine();

        this.writeNotOverridableContent();
        skipLine();

        writeLine("</h:form>");
        skipLine();
        
        writeLine("</h:panelGroup>");
        skipLine();
        
        writeLine("</rich:tab>");
        skipLine();
        

        for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList)
        {
        	Bean currentBean = oneToOneComponent.referenceBean;
        	
            writeLine("<rich:tab label=" + CHAR_34 + "#{i18n." + currentBean.objectName + "Detail}" + CHAR_34);
            writeLine("action=" + CHAR_34 + "#{" + this.bean.detailControllerObjectName + ".load" + currentBean.className + "}" + CHAR_34 + " reRender=" + CHAR_34 + currentBean.objectName + "DetailPanelGroup" + CHAR_34 + ">");
            writeLine("<ui:include src=" + CHAR_34 + "/sections/" + currentBean.myPackage.name + "/" + this.bean.className.toLowerCase() + "/" + currentBean.className + "Details.xhtml" + CHAR_34 + "/>");
            writeLine("</rich:tab>");
            skipLine();
        }

        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
        	Bean currentBean = oneToManyComponent.referenceBean;
        	
            writeLine("<rich:tab label=" + CHAR_34 + "#{i18n." + currentBean.objectName + "List}" + CHAR_34);
            writeLine("action=" + CHAR_34 + "#{" + this.bean.detailControllerObjectName + ".load" + currentBean.className + "List}" + CHAR_34);
            writeLine("ontabenter=" + CHAR_34 + "Richfaces.showModalPanel('processingPanel')" + CHAR_34);
            writeLine("oncomplete=" + CHAR_34 + "Richfaces.hideModalPanel('processingPanel')" + CHAR_34);
            writeLine("reRender=" + CHAR_34 + currentBean.objectName + "PanelGroup" + CHAR_34 + ">");
            writeLine("<ui:include src=" + CHAR_34 + "/sections/" + currentBean.myPackage.name + "/" + this.bean.className.toLowerCase() + "/" + currentBean.className + "List" + ".xhtml" + CHAR_34 + "/>");
            writeLine("</rich:tab>");
            skipLine();
        }
        
        for (OneToMany oneToMany : this.bean.oneToManyList) {
        	
        	Bean currentBean = oneToMany.referenceBean;
        	
            writeLine("<rich:tab label=" + CHAR_34 + "#{i18n." + currentBean.objectName + "List}" + CHAR_34);
            writeLine("action=" + CHAR_34 + "#{" + this.bean.detailControllerObjectName + ".load" + currentBean.className + "List}" + CHAR_34);       
            writeLine("ontabenter=" + CHAR_34 + "Richfaces.showModalPanel('processingPanel')" + CHAR_34);
            writeLine("oncomplete=" + CHAR_34 + "Richfaces.hideModalPanel('processingPanel')" + CHAR_34);
            writeLine("reRender=" + CHAR_34 + currentBean.objectName + "PanelGroup" + CHAR_34 + ">");
            writeLine("<ui:include src=" + CHAR_34 + "/sections/" + currentBean.myPackage.name + "/" + this.bean.className.toLowerCase() + "/" + currentBean.className + "List" + ".xhtml" + CHAR_34 + "/>");
            writeLine("</rich:tab>");
            skipLine();
        }


        writeLine("</rich:tabPanel>");
        skipLine();
    

        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
        	
        	Bean currentBean = oneToManyComponent.referenceBean;
        	
            writeLine("<rich:modalPanel id=" + CHAR_34 + currentBean.objectName + "Modal" + CHAR_34 + " autosized=" + CHAR_34 + "true" + CHAR_34 + " width=" + CHAR_34 + "800" + CHAR_34 + " left=" + CHAR_34 + "100" + CHAR_34 + ">");
            writeLine("<ui:include src=" + CHAR_34 + "/sections/" + currentBean.myPackage.name + "/" + this.bean.className.toLowerCase() + "/" + currentBean.className + "Modal.xhtml" + CHAR_34 + "/>");
            writeLine("</rich:modalPanel>");
            skipLine();
        }
        
        for (OneToMany oneToMany : this.bean.oneToManyList) {
        	
        	Bean currentBean = oneToMany.referenceBean;
            
            writeLine("<rich:modalPanel id=" + CHAR_34 + currentBean.objectName + "Modal" + CHAR_34 + " autosized=" + CHAR_34 + "true" + CHAR_34 + " width=" + CHAR_34 + "800" + CHAR_34 + " left=" + CHAR_34 + "100" + CHAR_34 + ">");
            writeLine("<ui:include src=" + CHAR_34 + "/sections/" + currentBean.myPackage.name + "/" + this.bean.className.toLowerCase() + "/" + currentBean.className + "Modal.xhtml" + CHAR_34 + "/>");
            writeLine("</rich:modalPanel>");
            skipLine();
        }
        
        writeLine("</ui:define>");
        writeLine("</ui:composition>");
    }
}
