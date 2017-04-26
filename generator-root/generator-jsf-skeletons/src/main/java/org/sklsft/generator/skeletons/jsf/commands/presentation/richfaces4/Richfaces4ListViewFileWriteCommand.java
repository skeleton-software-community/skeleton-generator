package org.sklsft.generator.skeletons.jsf.commands.presentation.richfaces4;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.model.metadata.DetailMode;

public class Richfaces4ListViewFileWriteCommand extends Richfaces4XhtmlFileWriteCommand {

	private Bean bean;

	public Richfaces4ListViewFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-webapp" + File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "sections" + File.separator + bean.myPackage.name + File.separator
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
		
		writeLine("<h:form id=" + CHAR_34 + bean.objectName + "ListForm" + CHAR_34 + ">");
		writeLine("<a4j:jsFunction name=" + CHAR_34 + "load" + CHAR_34);
		writeLine("action=" + CHAR_34 + "#{" + this.bean.listControllerObjectName + ".load}" + CHAR_34);
		writeLine("render=" + CHAR_34 + this.bean.objectName + "PanelGroup" + CHAR_34);
		writeLine("oncomplete=" + CHAR_34 + "$('#processingPanel').modal('hide')" + CHAR_34 + "/>");
		
	    writeLine("<script>window.onload = function(){$('#processingPanel').modal('show');load();}</script>");
		
		writeLine("<h2>");
		writeLine("#{i18n." + bean.objectName + "List}");
		writeLine("</h2>");
			
		writeLine("<h:panelGroup id=" + CHAR_34 + this.bean.objectName + "PanelGroup" + CHAR_34 + ">");
		
		writeLine("<a4j:region>");
		skipLine();		
		
		writeLine("<ui:fragment rendered=" + CHAR_34 + "#{empty " + this.bean.listViewObjectName + "." + this.bean.objectName + "List}" + CHAR_34 + ">");
		writeLine("#{i18n.noDataFound}<br/>");
		writeLine("</ui:fragment>");
		skipLine();
		
		writeLine("<ui:fragment rendered=" + CHAR_34 + "#{not empty " + this.bean.listViewObjectName + "." + this.bean.objectName + "List}" + CHAR_34 + ">");
		
		writeLine("<div style=" + CHAR_34 + "overflow-x:scroll" + CHAR_34 + ">");
		skipLine();

		writeLine("<rich:dataTable rows=" + CHAR_34 + "10" + CHAR_34);
		writeLine("id=" + CHAR_34 + this.bean.objectName + "List" + CHAR_34 + " var=" + CHAR_34 + this.bean.objectName + CHAR_34 + " name=" + CHAR_34 + "datatable" + CHAR_34);
		writeLine("value=" + CHAR_34 + "#{" + this.bean.listViewObjectName + "." + this.bean.objectName + "List}" + CHAR_34
				+ " rowClasses=" + CHAR_34 + "datatableRow, datatableRowLight" + CHAR_34 + ">");
		skipLine();

		writeLine("<f:facet name=" + CHAR_34 + "header" + CHAR_34 + ">");
		writeLine("<rich:columnGroup>");

		
		writeLine("<rich:column>");
		
		writeLine("<div id=" + CHAR_34 + "dropList" + CHAR_34 + " class=" + CHAR_34 + "dropList" + CHAR_34 + ">");
		
		if (bean.deleteEnabled) {
			writeLine("<a4j:commandLink title=" + CHAR_34 + "#{i18n.deleteSelection}" + CHAR_34 + " action=" + CHAR_34 + "#{" + bean.listControllerObjectName + ".deleteList}" + CHAR_34);
			writeLine("onclick=" + CHAR_34 + "if (!confirm('#{i18n.confirmDeleteSelection}')) return false" + CHAR_34 + " execute=" + CHAR_34 + "@region" + CHAR_34+ " render=" + CHAR_34 + bean.objectName + "PanelGroup"
					+ CHAR_34 + ">");
			writeLine("<span class=" + CHAR_34 + "glyphicon glyphicon-trash" + CHAR_34 + "/>");
		
			writeLine("</a4j:commandLink>");
		}


		writeLine("</div>");
		
		writeLine("</rich:column>");
		skipLine();
		
		writeLine("<rich:column>");
		
		writeLine("<a4j:commandLink action=" + CHAR_34 + "#{" + bean.listControllerObjectName + ".reset" + bean.basicViewBean.filterClassName + "}" + CHAR_34 + " render=" + CHAR_34 + bean.objectName + "List, " + bean.objectName + "Scroller" + CHAR_34 + ">");
		writeLine("<h:graphicImage url=" + CHAR_34 + "/resources/images/icons/refresh.png" + CHAR_34 + " styleClass=" + CHAR_34 + "imageIcon" + CHAR_34 + " title=" + CHAR_34 + "#{i18n.resetFilter}" + CHAR_34 + "/>");
		writeLine("</a4j:commandLink>");
		
		writeLine("</rich:column>");
		skipLine();

		for (ViewProperty property : this.bean.basicViewBean.properties) {
			writeLine("<rich:column>");
			writeFilter(property, this.bean);
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
		writeLine("<h:selectBooleanCheckbox rendered="+ CHAR_34 +"#{" + this.bean.objectName + ".canDelete}" + CHAR_34 + " id=" + CHAR_34 + "selectUnselect" + CHAR_34 + " value=" + CHAR_34 + "#{" + this.bean.objectName + ".selected}" + CHAR_34 + " onclick=" + CHAR_34
				+ "selectUnselect('" + bean.objectName + "ListForm:" + bean.objectName + "List:selectUnselectAll')" + CHAR_34 + "/>");
		writeLine("</rich:column>");
		skipLine();
		
		writeLine("<rich:column>");
		writeLine("<f:facet name=" + CHAR_34 + "header" + CHAR_34 + ">");
		writeLine("<h:outputText value=" + CHAR_34 + "Actions" + CHAR_34 + " />");
		writeLine("</f:facet>");
		writeLine("<h:panelGrid columns=" + CHAR_34 + "2" + CHAR_34 + ">");
		
		if (bean.detailMode.equals(DetailMode.PAGE)) {
			writeLine("<h:link outcome=" + CHAR_34 + "/sections/" + bean.myPackage.name + "/" + this.bean.className.toLowerCase() + "/" + bean.className + "Details.jsf" + CHAR_34 + ">");
			writeLine("<h:graphicImage url=" + CHAR_34 + "/resources/images/icons/edit.png" + CHAR_34 + " styleClass=" + CHAR_34 + "imageIcon" + CHAR_34 + " title=" + CHAR_34 + "#{i18n.edit}" + CHAR_34 + "/>");
			writeLine("<f:param name=" + CHAR_34 + "id" + CHAR_34 + " value=" + CHAR_34 + "#{" + bean.objectName + ".id}" + CHAR_34 + " />");
			writeLine("</h:link>");
		} else {
			writeLine("<a4j:commandLink action=" + CHAR_34 + "#{" + this.bean.listControllerObjectName + ".edit" + this.bean.className + "(" + bean.objectName + ".id)"+ "}"
					+ CHAR_34 + " oncomplete=" + CHAR_34 + "$('#" + bean.objectName + "Modal').modal('show')"
					+ CHAR_34 + " render=" + CHAR_34 + bean.objectName + "DetailPanelGroup" + CHAR_34 + ">");
			writeLine("<h:graphicImage url=" + CHAR_34 + "/resources/images/icons/edit.png" + CHAR_34 + " styleClass=" + CHAR_34 + "imageIcon" + CHAR_34 + " title=" + CHAR_34 + "#{i18n.edit}" + CHAR_34 + "/>");
			writeLine("</a4j:commandLink>");
		}
		
		if (this.bean.deleteEnabled) {
			writeLine("<a4j:commandLink rendered="+ CHAR_34 +"#{" + this.bean.objectName + ".canDelete}"+ CHAR_34 +" action=" + CHAR_34 + "#{" + this.bean.listControllerObjectName + ".delete(" + this.bean.objectName + ".id)}" + CHAR_34);
			writeLine("onclick=" + CHAR_34 + "if (!confirm('#{i18n.confirmDelete}')) return false" + CHAR_34 + " render=" + CHAR_34 + this.bean.objectName + "PanelGroup" + CHAR_34 + ">");
			writeLine("<h:graphicImage url=" + CHAR_34 + "/resources/images/icons/delete.png" + CHAR_34 + " styleClass=" + CHAR_34 + "imageIcon" + CHAR_34 + " title=" + CHAR_34
					+ "#{i18n.delete}" + CHAR_34 + "/>");
			writeLine("</a4j:commandLink>");

		}
		writeLine("</h:panelGrid>");
		writeLine("</rich:column>");
		skipLine();


		for (ViewProperty property : this.bean.basicViewBean.properties) {
			writeLine("<rich:column sortBy=" + CHAR_34 + "#{" + this.bean.objectName + "." + property.name + "}" + CHAR_34);
			writeFilterExpression(property, bean);
			writeLine("<f:facet name=" + CHAR_34 + "header" + CHAR_34 + ">");
			writeLine("<h:outputText value=" + CHAR_34 + "#{i18n." + this.bean.objectName + property.capName + "}" + CHAR_34 + " />");
			writeLine("</f:facet>");

			writeListComponent(property, this.bean);

			writeLine("</rich:column>");
			skipLine();
		}

		writeLine("</rich:dataTable>");
		skipLine();
		
		writeLine("</div>");
		skipLine();
		writeLine("<div class=" + CHAR_34 + "scroller" + CHAR_34 + ">");
		writeLine("<rich:dataScroller id=" + CHAR_34 + bean.objectName + "Scroller" + CHAR_34 + " maxPages=" + CHAR_34 + "5" + CHAR_34 + " renderIfSinglePage=" + CHAR_34 + "false"
				+ CHAR_34 + " for=" + CHAR_34 + this.bean.objectName + "List" + CHAR_34 + "/>");
		writeLine("</div>");
		skipLine();
		
		writeLine("</ui:fragment>");
		skipLine();

		if (this.bean.createEnabled) {

			writeLine("<a4j:commandButton value=" + CHAR_34 + "#{i18n.create}" + CHAR_34 + " action=" + CHAR_34 + "#{" + this.bean.listControllerObjectName + ".create" + this.bean.className + "}"
					+ CHAR_34 + " styleClass=" + CHAR_34 + "btn btn-info" + CHAR_34 + " oncomplete=" + CHAR_34 + "if (#{empty facesContext.maximumSeverity or facesContext.maximumSeverity.ordinal ==0}) {$('#" + bean.objectName + "Modal').modal('show')}"
					+ CHAR_34 + " render=" + CHAR_34 + bean.objectName + "DetailPanelGroup" + CHAR_34 + "/>");

		}
		
		skipLine();

		
			
		this.writeNotOverridableContent();

		writeLine("</a4j:region>");
		skipLine();
		writeLine("</h:panelGroup>");
		skipLine();
		
        
        writeLine("<div class=" + CHAR_34 + "modal modal-default" + CHAR_34 + " id=" + CHAR_34 + bean.objectName + "Modal" + CHAR_34 + " tabindex=" + CHAR_34 + "-1" + CHAR_34 + " aria-hidden=" + CHAR_34 + "true" + CHAR_34 + ">");
        writeLine("<div class=" + CHAR_34 + "modal-dialog modal-lg" + CHAR_34 + ">");
        writeLine("<div class=" + CHAR_34 + "modal-content" + CHAR_34 + ">");
        writeLine("<ui:include src=" + CHAR_34 + "/sections/" + bean.myPackage.name + "/" + this.bean.className.toLowerCase() + "/" + bean.className + "Modal.xhtml" + CHAR_34 + "/>");
        writeLine("</div>");
        writeLine("</div>");
        writeLine("</div>");
        
        writeLine("</h:form>");

		writeLine("</ui:define>");
		writeLine("</ui:composition>");
	}
}
