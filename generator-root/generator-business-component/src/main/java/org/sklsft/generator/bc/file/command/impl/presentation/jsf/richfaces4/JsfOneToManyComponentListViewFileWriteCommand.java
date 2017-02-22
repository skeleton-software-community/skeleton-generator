package org.sklsft.generator.bc.file.command.impl.presentation.jsf.richfaces4;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.Property;

public class JsfOneToManyComponentListViewFileWriteCommand extends JsfXhtmlFileWriteCommand {

	private OneToManyComponent oneToManyComponent;

	public JsfOneToManyComponentListViewFileWriteCommand(OneToManyComponent oneToManyComponent) {
		super(oneToManyComponent.referenceBean.myPackage.model.project.workspaceFolder + File.separator + oneToManyComponent.referenceBean.myPackage.model.project.projectName
				+ "-webapp" + File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "sections" + File.separator + oneToManyComponent.parentBean.myPackage.name + File.separator + oneToManyComponent.parentBean.className.toLowerCase(),
				oneToManyComponent.referenceBean.className + "List");
		this.oneToManyComponent = oneToManyComponent;
	}

	@Override
	protected void writeContent() throws IOException {

		Bean currentBean = oneToManyComponent.referenceBean;
		Bean parentBean = oneToManyComponent.parentBean;

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
		writeLine("<f:viewAction action=" + CHAR_34 + "#{" + parentBean.detailControllerObjectName + ".load" + currentBean.className + "List}" + CHAR_34 + " />");
		writeLine("</f:metadata>");

        writeLine("<ui:define name=" + CHAR_34 + "content" + CHAR_34 + ">");
        skipLine();
        
        writeLine("<h:form id=" + CHAR_34 + currentBean.objectName + "ListForm" + CHAR_34 + ">");
        skipLine();
        
        writeLine("<ui:include src=" + CHAR_34 + "/sections/" + parentBean.myPackage.name + "/" + parentBean.className.toLowerCase() + "/" + parentBean.className + "DetailsMenu.xhtml" + CHAR_34 + "/>");
        skipLine();

        writeLine("<h2>");
		writeLine("#{i18n." + currentBean.objectName + "List}");
		writeLine("</h2>");
        
        writeLine("<h:panelGroup id=" + CHAR_34 + currentBean.objectName + "PanelGroup" + CHAR_34 + ">");
		skipLine();
		
		writeLine("<a4j:region>");
		skipLine();	

		writeLine("<ui:fragment rendered=" + CHAR_34 + "#{empty " + parentBean.detailViewObjectName + "." + currentBean.objectName + "List}" + CHAR_34 + ">");
		writeLine("#{i18n.noDataFound}<br/>");		
		writeLine("</ui:fragment>");
		skipLine();
		
		writeLine("<ui:fragment rendered=" + CHAR_34 + "#{not empty " + parentBean.detailViewObjectName + "." + currentBean.objectName + "List}" + CHAR_34 + ">");
		skipLine();
		
		writeLine("<div style=" + CHAR_34 + "overflow-x:scroll" + CHAR_34 + ">");
		skipLine();

		writeLine("<rich:dataTable rows=" + CHAR_34 + "10" + CHAR_34);
		writeLine("id=" + CHAR_34 + currentBean.objectName + "List" + CHAR_34 + " var=" + CHAR_34 + currentBean.objectName + CHAR_34 + " name=" + CHAR_34 + "datatable" + CHAR_34);
		writeLine("value=" + CHAR_34 + "#{" + parentBean.detailViewObjectName + "." + currentBean.objectName + "List}" + CHAR_34 + " headerClass=" + CHAR_34 + "datatableHeader" + CHAR_34
				+ " rowClasses=" + CHAR_34 + "datatableRow, datatableRowLight" + CHAR_34 + ">");
		skipLine();

		writeLine("<f:facet name=" + CHAR_34 + "header" + CHAR_34 + ">");
		writeLine("<rich:columnGroup>");

		
		writeLine("<rich:column>");
		
		writeLine("<div id=" + CHAR_34 + "dropList" + CHAR_34 + " class=" + CHAR_34 + "dropList" + CHAR_34 + ">");
		
		if (currentBean.deleteEnabled) {
			writeLine("<a4j:commandLink title=" + CHAR_34 + "#{i18n.deleteSelection}" + CHAR_34 + " action=" + CHAR_34 + "#{" + parentBean.detailControllerObjectName + ".delete" + currentBean.className + "List}" + CHAR_34);
			writeLine("onclick=" + CHAR_34 + "if (!confirm('#{i18n.confirmDeleteSelection}')) return false" + CHAR_34 + " execute=" + CHAR_34 + "@region" + CHAR_34+ " render=" + CHAR_34 + currentBean.objectName + "PanelGroup"
					+ CHAR_34 + ">");
			writeLine("<span class=" + CHAR_34 + "glyphicon glyphicon-trash" + CHAR_34 + "/>");
		
			writeLine("</a4j:commandLink>");
		}


		writeLine("</div>");		
		writeLine("</rich:column>");
		skipLine();
		
		writeLine("<rich:column>");
		
		writeLine("<a4j:commandLink action=" + CHAR_34 + "#{" + parentBean.detailControllerObjectName + ".reset" + currentBean.basicViewBean.filterClassName + "}" + CHAR_34 + " reRender=" + CHAR_34 + currentBean.objectName + "List, " + currentBean.objectName + "Scroller" + CHAR_34 + ">");
		writeLine("<h:graphicImage url=" + CHAR_34 + "/resources/images/icons/refresh.png" + CHAR_34 + " styleClass=" + CHAR_34 + "imageIcon" + CHAR_34 + " title=" + CHAR_34 + "#{i18n.resetFilter}" + CHAR_34 + "/>");
		writeLine("</a4j:commandLink>");
		
		writeLine("</rich:column>");
		skipLine();

		for (Property property : currentBean.basicViewBean.properties) {
			writeLine("<rich:column>");
			writeFilter(property, currentBean, parentBean);
			writeLine("</rich:column>");
			skipLine();
		}		

		writeLine("</rich:columnGroup>");
		writeLine("</f:facet>");

		
		writeLine("<rich:column>");
		writeLine("<f:facet name=" + CHAR_34 + "header" + CHAR_34 + ">");
		writeLine("<h:selectBooleanCheckbox id=" + CHAR_34 + "selectUnselectAll" + CHAR_34 + " onclick=" + CHAR_34 + "selectUnselectAll(this)" + CHAR_34 + " value=" + CHAR_34 + "false" + CHAR_34 + "/>");
		writeLine("<script>$(function(){displaySelectUnselectAll();});</script>");
		writeLine("</f:facet>");
		writeLine("<h:selectBooleanCheckbox rendered="+ CHAR_34 +"#{" + currentBean.objectName + ".canDelete}" + CHAR_34 + " id=" + CHAR_34 + "selectUnselect" + CHAR_34 + " value=" + CHAR_34 + "#{" + currentBean.objectName + ".selected}" + CHAR_34 + " onclick=" + CHAR_34
				+ "selectUnselect('" + currentBean.objectName + "ListForm:" + currentBean.objectName + "List:selectUnselectAll')" + CHAR_34 + "/>");
		writeLine("</rich:column>");
		skipLine();
		
		writeLine("<rich:column>");
		writeLine("<f:facet name=" + CHAR_34 + "header" + CHAR_34 + ">");
		writeLine("<h:outputText value=" + CHAR_34 + "Actions" + CHAR_34 + " />");
		writeLine("</f:facet>");
		writeLine("<h:panelGrid columns=" + CHAR_34 + "2" + CHAR_34 + ">");
		writeLine("<a4j:commandLink action=" + CHAR_34 + "#{" + parentBean.detailControllerObjectName + ".edit" + currentBean.className + "(" + currentBean.objectName + ".id)}" + CHAR_34 + " oncomplete=" + CHAR_34 + "if (#{empty facesContext.maximumSeverity or facesContext.maximumSeverity.ordinal ==0}) $('#" + currentBean.objectName
				+ "Modal').modal('show');" + CHAR_34 + " execute=" + CHAR_34 + "@this" + CHAR_34 + " render=" + CHAR_34 + currentBean.objectName + "DetailPanelGroup" + CHAR_34 + ">");
		writeLine("<h:graphicImage url=" + CHAR_34 + "/resources/images/icons/edit.png" + CHAR_34 + " styleClass=" + CHAR_34 + "imageIcon" + CHAR_34 + " title=" + CHAR_34 + "#{i18n.edit}"
				+ CHAR_34 + "/>");
		writeLine("</a4j:commandLink>");

		if (currentBean.deleteEnabled) {
			writeLine("<a4j:commandLink action=" + CHAR_34 + "#{" + parentBean.detailControllerObjectName + ".delete" + currentBean.className + "(" + currentBean.objectName + ".id)}" + CHAR_34);
			writeLine("rendered="+ CHAR_34 +"#{" + currentBean.objectName + ".canDelete}" + CHAR_34);
			writeLine("onclick=" + CHAR_34 + "if (!confirm('#{i18n.confirmDelete}')) return false" + CHAR_34 + " execute=" + CHAR_34 + "@this" + CHAR_34 + " render=" + CHAR_34 + currentBean.objectName + "PanelGroup" + CHAR_34 + ">");
			writeLine("<h:graphicImage url=" + CHAR_34 + "/resources/images/icons/delete.png" + CHAR_34 + " styleClass=" + CHAR_34 + "imageIcon" + CHAR_34 + " title=" + CHAR_34
					+ "#{i18n.delete}" + CHAR_34 + "/>");
			writeLine("</a4j:commandLink>");
		}
		writeLine("</h:panelGrid>");
		writeLine("</rich:column>");
		skipLine();


		for (Property property : currentBean.basicViewBean.properties) {
			writeLine("<rich:column sortBy=" + CHAR_34 + "#{" + currentBean.objectName + "." + property.name + "}" + CHAR_34);
			writeFilterExpression(property, currentBean, parentBean);
			writeLine("<f:facet name=" + CHAR_34 + "header" + CHAR_34 + ">");
			writeLine("<h:outputText value=" + CHAR_34 + "#{i18n." + currentBean.objectName + property.capName + "}" + CHAR_34 + " />");
			writeLine("</f:facet>");

			writeListComponent(property, currentBean);

			writeLine("</rich:column>");
			skipLine();
		}
		
		writeLine("</rich:dataTable>");
		skipLine();
		
		writeLine("</div>");
		skipLine();

		writeLine("<div class=" + CHAR_34 + "scroller" + CHAR_34 + ">");
		writeLine("<rich:dataScroller id=" + CHAR_34 + currentBean.objectName + "Scroller" + CHAR_34 + " maxPages=" + CHAR_34 + "5" + CHAR_34 + " renderIfSinglePage=" + CHAR_34 + "false" + CHAR_34 + " for=" + CHAR_34 + currentBean.objectName + "List"
				+ CHAR_34 + "/>");
		writeLine("</div>");
		skipLine();
		
		writeLine("</ui:fragment>");
		skipLine();

		
		skipLine();

		if (currentBean.createEnabled) {
			writeLine("<a4j:commandButton value=" + CHAR_34 + "#{i18n.create}" + CHAR_34 + " action=" + CHAR_34 + "#{" + parentBean.detailControllerObjectName + ".create" + currentBean.className
					+ "}" + CHAR_34 + " styleClass=" + CHAR_34 + "btn btn-success" + CHAR_34 + " oncomplete=" + CHAR_34 + "if (#{empty facesContext.maximumSeverity or facesContext.maximumSeverity.ordinal ==0}) $('#" + currentBean.objectName + "Modal').modal('show')"
					+ CHAR_34 + " execute=" + CHAR_34 + "@this" + CHAR_34 + " render=" + CHAR_34 + currentBean.objectName + "DetailPanelGroup" + CHAR_34 + "/>");
			skipLine();
		}
		
		this.writeNotOverridableContent();
		skipLine();
		
		writeLine("</a4j:region>");
		skipLine();	

		writeLine("</h:panelGroup>");
		skipLine();
		
		
		writeLine("<div class=" + CHAR_34 + "modal modal-default" + CHAR_34 + " id=" + CHAR_34 + currentBean.objectName + "Modal" + CHAR_34 + " tabindex=" + CHAR_34 + "-1" + CHAR_34 + " aria-hidden=" + CHAR_34 + "true" + CHAR_34 + ">");
		writeLine("<div class=" + CHAR_34 + "modal-dialog modal-lg" + CHAR_34 + ">");
		writeLine("<div class=" + CHAR_34 + "modal-content" + CHAR_34 + ">");
		writeLine("<ui:include src=" + CHAR_34 + "/sections/" + parentBean.myPackage.name + "/" + parentBean.className.toLowerCase() + "/" + currentBean.className + "Modal.xhtml" + CHAR_34 + "/>");
		writeLine("</div>");
		writeLine("</div>");
		writeLine("</div>");
		skipLine();
         
		
		writeLine("</h:form>");
        
        writeLine("<script>$('#" + currentBean.objectName + "ListMenu').addClass('active');</script>");
        

		writeLine("</ui:define>");
		writeLine("</ui:composition>");
	}
}
