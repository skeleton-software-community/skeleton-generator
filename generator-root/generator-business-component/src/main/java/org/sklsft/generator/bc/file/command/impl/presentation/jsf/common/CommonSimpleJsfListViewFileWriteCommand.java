package org.sklsft.generator.bc.file.command.impl.presentation.jsf.common;

import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.presentation.jsf.JsfXhtmlFileWriteCommand;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.Property;

public class CommonSimpleJsfListViewFileWriteCommand extends JsfXhtmlFileWriteCommand {

	private Bean bean;

	public CommonSimpleJsfListViewFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + "\\" + bean.myPackage.model.project.projectName + "-webapp\\src\\main\\webapp\\sections\\" + bean.myPackage.name + "\\"
				+ bean.className.toLowerCase(), bean.className + "List");

		this.bean = bean;

	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("<ui:composition xmlns=" + (char) 34 + "http://www.w3.org/1999/xhtml" + (char) 34);
		writeLine("xmlns:ui=" + (char) 34 + "http://java.sun.com/jsf/facelets" + (char) 34);
		writeLine("xmlns:f = " + (char) 34 + "http://java.sun.com/jsf/core" + (char) 34);
		writeLine("xmlns:h = " + (char) 34 + "http://java.sun.com/jsf/html" + (char) 34);
		writeLine("xmlns:rich = " + (char) 34 + "http://richfaces.org/rich" + (char) 34);
		writeLine("xmlns:a4j = " + (char) 34 + "http://richfaces.org/a4j" + (char) 34);
		writeLine("xmlns:c=" + (char) 34 + "http://java.sun.com/jstl/core" + (char) 34);
		writeLine("xmlns:fn=" + (char) 34 + "http://java.sun.com/jsp/jstl/functions" + (char) 34);
		writeLine("template=" + (char) 34 + "/templates/template.xhtml" + (char) 34 + ">");
		skipLine();

		writeLine("<!-- -->");
		writeLine("<!-- auto generated jsf file -->");
		writeLine("<!-- write modifications between specific code marks -->");
		writeLine("<!-- processed by skeleton-generator -->");
		writeLine("<!-- -->");
		skipLine();

		writeLine("<ui:define name=" + (char) 34 + "content" + (char) 34 + ">");
		skipLine();

		writeLine("<br/>");
		writeLine("<rich:messages infoClass=" + (char) 34 + "infoMessage" + (char) 34 + " errorClass=" + (char) 34 + "errorMessage" + (char) 34);
		writeLine("globalOnly=" + (char) 34 + "true" + (char) 34 + "/>");
		writeLine("<br/>");
		skipLine();
		
		writeLine("<h2>");
		writeLine("#{i18n." + bean.objectName + "List}");
		writeLine("</h2>");
		skipLine();

		writeLine("<h:form id=" + (char) 34 + this.bean.objectName + "Form" + (char) 34 + ">");
		skipLine();


		writeLine("<h:panelGroup id=" + (char) 34 + this.bean.objectName + "PanelGroup" + (char) 34 + ">");
		writeLine("<h:panelGroup rendered=" + (char) 34 + "#{!" + this.bean.controllerObjectName + ".displaySuccessfull}" + (char) 34 + " styleClass=" + (char) 34 + "errorMessage" + (char) 34
				+ " layout=" + (char) 34 + "block" + (char) 34 + ">");
		writeLine("<h:outputText value=" + (char) 34 + "#{i18n.displayFailure}" + (char) 34 + "/>");
		writeLine("</h:panelGroup>");
		writeLine("<h:panelGroup rendered=" + (char) 34 + "#{" + this.bean.controllerObjectName + ".displaySuccessfull}" + (char) 34 + ">");
		skipLine();

		writeLine("<br/>");
		skipLine();

		writeLine("<h:outputText value=" + (char) 34 + "#{i18n.noDataFound}" + (char) 34);
		writeLine("rendered=" + (char) 34 + "#{empty " + this.bean.controllerObjectName + "." + this.bean.objectName + "List}" + (char) 34 + "/>");
		skipLine();
		
		writeLine("<h:panelGroup rendered=" + (char) 34 + "#{not empty " + this.bean.controllerObjectName + "." + this.bean.objectName
				+ "List}" + (char) 34 + ">");
		skipLine();
		
		writeLine("<div style=" + (char) 34 + "overflow-x:scroll" + (char) 34 + ">");
		skipLine();

		writeLine("<rich:dataTable rows=" + (char) 34 + "#{" + this.bean.controllerObjectName + ".numberOfRows}" + (char) 34);
		writeLine("id=" + (char) 34 + this.bean.objectName + "List" + (char) 34 + " var=" + (char) 34 + this.bean.objectName + (char) 34 + " name=" + (char) 34 + "datatable" + (char) 34);
		writeLine("value=" + (char) 34 + "#{" + this.bean.controllerObjectName + "." + this.bean.objectName + "List}" + (char) 34);
		writeLine("rowClasses=" + (char) 34 + "datatableRow, datatableRowLight" + (char) 34 + ">");
		skipLine();

		writeLine("<c:set var=" + (char) 34 + this.bean.objectName + (char) 34 + " value=" + (char) 34 + "#{" + this.bean.objectName + "}" + (char) 34);
		writeLine("scope=" + (char) 34 + "request" + (char) 34 + "/>");
		skipLine();

		writeLine("<f:facet name=" + (char) 34 + "header" + (char) 34 + ">");
		writeLine("<rich:columnGroup>");

		
		for (Property property : this.bean.getVisibleProperties()) {
			if (property.visibility.isListVisible()) {
				writeLine("<rich:column>");
				writeFilter(property, this.bean);
				writeLine("</rich:column>");
			}
		}

		writeLine("<rich:column>");
		
		writeLine("<a4j:commandLink action=" + (char)34 + "#{" + bean.controllerObjectName + ".reset" + bean.filterClassName + "}" + (char)34 + " reRender=" + (char)34 + bean.objectName + "List, " + bean.objectName + "Scroller" + (char)34 + ">");
		writeLine("<h:graphicImage url=" + (char)34 + "/resources/images/icons/refresh.png" + (char)34 + " styleClass=" + (char)34 + "imageIcon" + (char)34 + " title=" + (char)34 + "#{i18n.resetFilter}" + (char)34 + "/>");
		writeLine("</a4j:commandLink>");
		
		writeLine("</rich:column>");

		writeLine("</rich:columnGroup>");
		writeLine("</f:facet>");


		for (Property property : this.bean.getVisibleProperties()) {
			if (property.visibility.isListVisible()) {
				writeLine("<rich:column sortBy=" + (char) 34 + "#{" + this.bean.objectName + "." + property.name + "}" + (char) 34);
				writeFilterExpression(property, bean);
				writeLine("<f:facet name=" + (char) 34 + "header" + (char) 34 + ">");
				writeLine("<h:outputText value=" + (char) 34 + "#{i18n." + this.bean.objectName + property.capName + "}" + (char) 34 + " />");
				writeLine("</f:facet>");

				writeListComponent(property, this.bean);

				writeLine("</rich:column>");
				skipLine();
			}
		}

		writeLine("<rich:column>");
		writeLine("<f:facet name=" + (char) 34 + "header" + (char) 34 + ">");
		writeLine("<h:outputText value=" + (char) 34 + "Actions" + (char) 34 + " />");
		writeLine("</f:facet>");
		writeLine("<h:panelGrid columns=" + (char) 34 + "2" + (char) 34 + ">");
		writeLine("<a4j:commandLink action=" + (char) 34 + "#{" + bean.controllerObjectName + ".edit" + bean.className + "}" + (char) 34 + " actionListener=" + (char) 34 + "#{"
				+ bean.controllerObjectName + ".listenSelected" + bean.className + "Id}" + (char) 34 + " oncomplete=" + (char) 34 + "Richfaces.showModalPanel('" + bean.objectName + "ModalPanel')"
				+ (char) 34 + " reRender=" + (char) 34 + bean.objectName + "DetailPanelGroup" + (char) 34 + ">");
		writeLine("<f:attribute name=" + (char) 34 + "selected" + bean.className + "Id" + (char) 34 + " value=" + (char) 34 + "#{" + bean.objectName + ".id}" + (char) 34 + " />");
		writeLine("<h:graphicImage url=" + (char) 34 + "/resources/images/icons/edit.png" + (char) 34 + " styleClass=" + (char) 34 + "imageIcon" + (char) 34 + " title=" + (char) 34 + "#{i18n.edit}"
				+ (char) 34 + "/>");
		writeLine("</a4j:commandLink>");

		if (this.bean.deleteEnabled) {
			writeLine("<a4j:commandLink  action=" + (char) 34 + "#{" + this.bean.controllerObjectName + ".delete" + this.bean.className + "}" + (char) 34 + " actionListener=" + (char) 34
					+ "#{" + this.bean.controllerObjectName + ".listenSelected" + this.bean.className + "Id}" + (char) 34);
			writeLine("onclick=" + (char) 34 + "if (!confirm('#{i18n.confirmDrop}')) return false" + (char) 34 + " reRender=" + (char) 34 + this.bean.objectName + "PanelGroup" + (char) 34 + ">");
			writeLine("<f:attribute name=" + (char) 34 + "selected" + this.bean.className + "Id" + (char) 34 + " value=" + (char) 34 + "#{" + this.bean.objectName + ".id}" + (char) 34 + "/>");
			writeLine("<h:graphicImage url=" + (char) 34 + "/resources/images/icons/delete.png" + (char) 34 + " styleClass=" + (char) 34 + "imageIcon" + (char) 34 + " title=" + (char) 34
					+ "#{i18n.drop}" + (char) 34 + "/>");
			writeLine("</a4j:commandLink>");

		}
		writeLine("</h:panelGrid>");
		writeLine("</rich:column>");
		skipLine();

		writeLine("</rich:dataTable>");
		skipLine();
		
		writeLine("</div>");
		skipLine();
		
		writeLine("</h:panelGroup>");
		skipLine();

		writeLine("<rich:datascroller id=" + (char) 34 + bean.objectName + "Scroller" + (char) 34 + " maxPages=" + (char) 34 + "5" + (char) 34 + " renderIfSinglePage=" + (char) 34 + "false"
				+ (char) 34 + " for=" + (char) 34 + this.bean.objectName + "List" + (char) 34 + "/>");

		if (bean.createEnabled) {
			writeLine("<br/>");
			writeLine("<br/>");
			skipLine();

			writeLine("<a4j:commandButton value=" + (char) 34 + "#{i18n.create}" + (char) 34 + " action=" + (char) 34 + "#{" + bean.controllerObjectName + ".create" + bean.className + "}" + (char) 34
					+ " styleClass=" + (char) 34 + "simpleButton" + (char) 34 + " oncomplete=" + (char) 34 + "Richfaces.showModalPanel('" + bean.objectName + "ModalPanel')" + (char) 34 + " reRender="
					+ (char) 34 + bean.objectName + "DetailPanelGroup" + (char) 34 + "/>");
		}

		writeLine("<br/>");
		skipLine();

		writeLine("</h:panelGroup>");
		writeLine("</h:panelGroup>");
		skipLine();

		writeLine("</h:form>");
		skipLine();

		writeLine("<rich:modalPanel id=" + (char) 34 + bean.objectName + "ModalPanel" + (char) 34 + " autosized=" + (char) 34 + "true" + (char) 34 + " width=" + (char) 34 + "800" + (char) 34
				+ " left=" + (char) 34 + "auto" + (char) 34 + ">");
		writeLine("<ui:include src=" + (char) 34 + "/sections/" + this.bean.myPackage.name + "/" + this.bean.className.toLowerCase() + "/" + bean.className + "Details.xhtml" + (char) 34 + "/>");
		writeLine("</rich:modalPanel>");
		skipLine();

		writeLine("</ui:define>");
		writeLine("</ui:composition>");
	}
}
