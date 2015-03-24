package org.sklsft.generator.bc.file.command.impl.presentation.jsf.richfaces3;

import java.io.IOException;

import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.Property;

public class JsfListViewFileWriteCommand extends JsfXhtmlFileWriteCommand {

	private Bean bean;

	public JsfListViewFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + "\\" + bean.myPackage.model.project.projectName + "-webapp\\src\\main\\webapp\\sections\\" + bean.myPackage.name + "\\"
				+ bean.className.toLowerCase(), bean.className + "List");

		this.bean = bean;
		
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("<ui:composition xmlns=" + CHAR_34 + "http://www.w3.org/1999/xhtml" + CHAR_34);
		writeLine("xmlns:ui=" + CHAR_34 + "http://java.sun.com/jsf/facelets" + CHAR_34);
		writeLine("xmlns:f = " + CHAR_34 + "http://java.sun.com/jsf/core" + CHAR_34);
		writeLine("xmlns:h = " + CHAR_34 + "http://java.sun.com/jsf/html" + CHAR_34);
		writeLine("xmlns:rich = " + CHAR_34 + "http://richfaces.org/rich" + CHAR_34);
		writeLine("xmlns:a4j = " + CHAR_34 + "http://richfaces.org/a4j" + CHAR_34);
		writeLine("xmlns:c=" + CHAR_34 + "http://java.sun.com/jstl/core" + CHAR_34);
		writeLine("xmlns:fn=" + CHAR_34 + "http://java.sun.com/jsp/jstl/functions" + CHAR_34);
		writeLine("template=" + CHAR_34 + "/templates/template.xhtml" + CHAR_34 + ">");
		skipLine();

		writeLine("<!-- -->");
		writeLine("<!-- auto generated jsf file -->");
		writeLine("<!-- write modifications between specific code marks -->");
		writeLine("<!-- processed by skeleton generator -->");
		writeLine("<!-- -->");
		skipLine();

		writeLine("<ui:define name=" + CHAR_34 + "content" + CHAR_34 + ">");
		skipLine();
		
		writeLine("<h:form>");
		writeLine("<a4j:jsFunction name=" + CHAR_34 + "load" + CHAR_34);
		writeLine("action=" + CHAR_34 + "#{" + this.bean.listControllerObjectName + ".load}" + CHAR_34);
		writeLine("reRender=" + CHAR_34 + this.bean.objectName + "PanelGroup" + CHAR_34);
		writeLine("oncomplete=" + CHAR_34 + "$('processingPanel').component.hide()" + CHAR_34 + "/>");
		
		writeLine("</h:form>");
	    writeLine("<script>window.onload = function(){$('processingPanel').component.show();load();}</script>");

		writeLine("<br/>");
		writeLine("<rich:messages infoClass=" + CHAR_34 + "infoMessage" + CHAR_34 + " errorClass=" + CHAR_34 + "errorMessage" + CHAR_34 + " globalOnly=" + CHAR_34 + "true" + CHAR_34
				+ "/>");
		writeLine("<br/>");
		
		writeLine("<h2>");
		writeLine("#{i18n." + bean.objectName + "List}");
		writeLine("</h2>");	
			
		writeLine("<h:panelGroup id=" + CHAR_34 + this.bean.objectName + "PanelGroup" + CHAR_34 + ">");
		
		writeLine("<h:outputText value=" + CHAR_34 + "#{i18n.noDataFound}" + CHAR_34 + " rendered=" + CHAR_34 + "#{empty " + this.bean.listViewObjectName + "." + this.bean.objectName
				+ "List}" + CHAR_34 + "/>");
		skipLine();
		
		writeLine("<h:form id=" + CHAR_34 + bean.objectName + "ListForm" + CHAR_34 + ">");
		skipLine();
		
		writeLine("<h:panelGroup rendered=" + CHAR_34 + "#{not empty " + this.bean.listViewObjectName + "." + this.bean.objectName
				+ "List}" + CHAR_34 + ">");
		skipLine();
		
		writeLine("<div style=" + CHAR_34 + "overflow-x:scroll" + CHAR_34 + ">");
		skipLine();

		writeLine("<rich:dataTable rows=" + CHAR_34 + "#{" + this.bean.listControllerObjectName + ".numberOfRows}" + CHAR_34);
		writeLine("id=" + CHAR_34 + this.bean.objectName + "List" + CHAR_34 + " var=" + CHAR_34 + this.bean.objectName + CHAR_34 + " name=" + CHAR_34 + "datatable" + CHAR_34);
		writeLine("value=" + CHAR_34 + "#{" + this.bean.listViewObjectName + "." + this.bean.objectName + "List}" + CHAR_34
				+ " rowClasses=" + CHAR_34 + "datatableRow, datatableRowLight" + CHAR_34 + ">");
		skipLine();

		writeLine("<f:facet name=" + CHAR_34 + "header" + CHAR_34 + ">");
		writeLine("<rich:columnGroup>");

		
		writeLine("<rich:column>");
		writeLine("</rich:column>");

		for (Property property : this.bean.getVisibleProperties()) {
			if (property.visibility.isListVisible()) {
				writeLine("<rich:column>");
				writeFilter(property, this.bean);
				writeLine("</rich:column>");
			}
		}

		writeLine("<rich:column>");
		
		writeLine("<a4j:commandLink action=" + CHAR_34 + "#{" + bean.listControllerObjectName + ".reset" + bean.filterClassName + "}" + CHAR_34 + " reRender=" + CHAR_34 + bean.objectName + "List, " + bean.objectName + "Scroller" + CHAR_34 + ">");
		writeLine("<h:graphicImage url=" + CHAR_34 + "/resources/images/icons/refresh.png" + CHAR_34 + " styleClass=" + CHAR_34 + "imageIcon" + CHAR_34 + " title=" + CHAR_34 + "#{i18n.resetFilter}" + CHAR_34 + "/>");
		writeLine("</a4j:commandLink>");
		
		writeLine("</rich:column>");

		writeLine("</rich:columnGroup>");
		writeLine("</f:facet>");


		writeLine("<rich:column>");
		writeLine("<f:facet name=" + CHAR_34 + "header" + CHAR_34 + ">");
		writeLine("<h:selectBooleanCheckbox id=" + CHAR_34 + "selectUnselectAll" + CHAR_34 + " onclick=" + CHAR_34 + "selectUnselectAll(this)" + CHAR_34 + " value=" + CHAR_34 + "false" + CHAR_34 + "/>");
		writeLine("</f:facet>");
		writeLine("<h:selectBooleanCheckbox id=" + CHAR_34 + "selected" + CHAR_34 + " value=" + CHAR_34 + "#{" + this.bean.objectName + ".selected}" + CHAR_34 + " onclick=" + CHAR_34
				+ "selectUnselect('" + bean.objectName + "ListForm:" + bean.objectName + "List:selectUnselectAll')" + CHAR_34 + "/>");
		writeLine("</rich:column>");
		skipLine();


		for (Property property : this.bean.getVisibleProperties()) {
			if (property.visibility.isListVisible()) {
				writeLine("<rich:column sortBy=" + CHAR_34 + "#{" + this.bean.objectName + "." + property.name + "}" + CHAR_34);
				writeFilterExpression(property, bean);
				writeLine("<f:facet name=" + CHAR_34 + "header" + CHAR_34 + ">");
				writeLine("<h:outputText value=" + CHAR_34 + "#{i18n." + this.bean.objectName + property.capName + "}" + CHAR_34 + " />");
				writeLine("</f:facet>");

				writeListComponent(property, this.bean);

				writeLine("</rich:column>");
				skipLine();
			}
		}

		writeLine("<rich:column>");
		writeLine("<f:facet name=" + CHAR_34 + "header" + CHAR_34 + ">");
		writeLine("<h:outputText value=" + CHAR_34 + "Actions" + CHAR_34 + " />");
		writeLine("</f:facet>");
		writeLine("<h:panelGrid columns=" + CHAR_34 + "2" + CHAR_34 + ">");
		
		writeLine("<h:outputLink value=" + CHAR_34 + "#{application.contextPath}/sections/" + bean.myPackage.name + "/" + this.bean.className.toLowerCase() + "/" + bean.className + "Details.jsf" + CHAR_34 + ">");
		writeLine("<h:graphicImage url=" + CHAR_34 + "/resources/images/icons/edit.png" + CHAR_34 + " styleClass=" + CHAR_34 + "imageIcon" + CHAR_34 + " title=" + CHAR_34 + "#{i18n.edit}" + CHAR_34 + "/>");
		writeLine("<f:param name=" + CHAR_34 + "id" + CHAR_34 + " value=" + CHAR_34 + "#{" + bean.objectName + ".id}" + CHAR_34 + " />");
		writeLine("</h:outputLink>");
		
		if (this.bean.deleteEnabled) {
			writeLine("<a4j:commandLink  action=" + CHAR_34 + "#{" + this.bean.listControllerObjectName + ".delete" + this.bean.className + "(" + this.bean.objectName + ".id)}" + CHAR_34);
			writeLine("onclick=" + CHAR_34 + "if (!confirm('#{i18n.confirmDrop}')) return false" + CHAR_34 + " reRender=" + CHAR_34 + this.bean.objectName + "PanelGroup" + CHAR_34 + ">");
			writeLine("<h:graphicImage url=" + CHAR_34 + "/resources/images/icons/delete.png" + CHAR_34 + " styleClass=" + CHAR_34 + "imageIcon" + CHAR_34 + " title=" + CHAR_34
					+ "#{i18n.drop}" + CHAR_34 + "/>");
			writeLine("</a4j:commandLink>");

		}
		writeLine("</h:panelGrid>");
		writeLine("</rich:column>");
		skipLine();

		writeLine("</rich:dataTable>");
		skipLine();
		
		writeLine("</div>");
		skipLine();
		
		writeLine("<rich:datascroller id=" + CHAR_34 + bean.objectName + "Scroller" + CHAR_34 + " maxPages=" + CHAR_34 + "5" + CHAR_34 + " renderIfSinglePage=" + CHAR_34 + "false"
				+ CHAR_34 + " for=" + CHAR_34 + this.bean.objectName + "List" + CHAR_34 + "/>");
		skipLine();
		
		writeLine("</h:panelGroup>");
		skipLine();

		if (this.bean.createEnabled) {
			writeLine("<br/>");
			writeLine("<br/>");
			skipLine();

			writeLine("<a4j:commandButton value=" + CHAR_34 + "#{i18n.create}" + CHAR_34 + " action=" + CHAR_34 + "#{" + this.bean.listControllerObjectName + ".create" + this.bean.className + "}"
					+ CHAR_34 + " styleClass=" + CHAR_34 + "simpleButton" + CHAR_34 + " oncomplete=" + CHAR_34 + "Richfaces.showModalPanel('" + bean.objectName + "CreationModalPanel')"
					+ CHAR_34 + " reRender=" + CHAR_34 + bean.objectName + "CreationPanelGroup" + CHAR_34 + "/>");

		}
		
		writeLine("<br/>");
		skipLine();

		
		writeLine("<div id=" + CHAR_34 + "actions" + CHAR_34 + " style=" + CHAR_34 + "display:none;margin:2px;" + CHAR_34 + ">");
		writeLine("#{i18n.actionsOnSelection} :");
		writeLine("<br/>");

		writeLine("<h:panelGrid columns=" + CHAR_34 + "1" + CHAR_34 + ">");

		if (bean.deleteEnabled) {
			writeLine("<a4j:commandButton value=" + CHAR_34 + "#{i18n.dropSelection}" + CHAR_34 + " action=" + CHAR_34 + "#{" + bean.listControllerObjectName + ".delete" + bean.className + "List}" + CHAR_34
					+ " styleClass=" + CHAR_34 + "simpleButton" + CHAR_34);
			writeLine("onclick=" + CHAR_34 + "if (!confirm('#{i18n.confirmDropSelection}')) return false" + CHAR_34 + " reRender=" + CHAR_34 + bean.objectName + "PanelGroup"
					+ CHAR_34 + "/>");
		}
		writeLine("</h:panelGrid>");
		skipLine();

		this.writeNotOverridableContent();
		skipLine();

		writeLine("</div>");
		skipLine();

		writeLine("</h:form>");
		skipLine();
		writeLine("</h:panelGroup>");
		skipLine();
		
		
		writeLine("<rich:modalPanel id=" + CHAR_34 + bean.objectName + "CreationModalPanel" + CHAR_34 + " autosized=" + CHAR_34 + "true" + CHAR_34 + " width=" + CHAR_34 + "800" + CHAR_34 + " left=" + CHAR_34 + "100" + CHAR_34 + ">");
        writeLine("<ui:include src=" + CHAR_34 + "/sections/" + bean.myPackage.name + "/" + this.bean.className.toLowerCase() + "/" + bean.className + "Creation.xhtml" + CHAR_34 + "/>");
        writeLine("</rich:modalPanel>");
        skipLine();

		writeLine("</ui:define>");
		writeLine("</ui:composition>");
	}
}
