package org.sklsft.generator.skeletons.jsf.commands.presentation.richfaces4;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.model.metadata.DetailMode;

public class Richfaces4ListViewFileWriteCommand extends Richfaces4XhtmlFileWriteCommand {

	private Bean bean;

	public Richfaces4ListViewFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-webapp" + File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "sections" + File.separator + bean.myPackage.urlPiece + File.separator + bean.urlPiece, "list");

		this.bean = bean;
		
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("<ui:composition xmlns=\"http://www.w3.org/1999/xhtml\"");
		writeLine("xmlns:ui=\"http://java.sun.com/jsf/facelets\"");
		writeLine("xmlns:f = \"http://java.sun.com/jsf/core\"");
		writeLine("xmlns:h = \"http://java.sun.com/jsf/html\"");
		writeLine("xmlns:rich = \"http://richfaces.org/rich\"");
		writeLine("xmlns:a4j = \"http://richfaces.org/a4j\"");
		writeLine("xmlns:cc=\"http://java.sun.com/jsf/composite/components\"");
		writeLine("xmlns:s=\"http://commons.sklsft.org/ui/components\"");
		writeLine("template=\"/templates/template.xhtml\">");
		skipLine();

		writeLine("<!-- -->");
		writeLine("<!-- auto generated jsf file -->");
		writeLine("<!-- write modifications between specific code marks -->");
		writeLine("<!-- processed by skeleton generator -->");
		writeLine("<!-- -->");
		skipLine();

		writeLine("<f:metadata>");
		writeLine("<f:viewAction action=\"#{" + this.bean.listControllerObjectName + ".load}\"/>");
		writeLine("</f:metadata>");
		
		writeLine("<ui:define name=\"content\">");
		skipLine();		
		
		writeLine("<h:form id=\"" + bean.objectName + "ListForm\">");		
			
		writeLine("<h:panelGroup id=\"" + this.bean.objectName + "PanelGroup\">");
		
		writeLine("<h2>");
		writeLine("#{i18n." + bean.objectName + "List} (#{" + bean.listViewObjectName + ".scrollView.size})");
		writeLine("</h2>");
		
		writeLine("<a4j:region>");
		skipLine();		
		
		writeLine("<div class=\"filter-panel\">");
		writeLine("<h3>");
		writeLine("#{i18n.filters}");
		
		writeLine("<a4j:commandLink action=\"#{" + bean.listControllerObjectName + ".reset}\" render=\"" + bean.objectName + "PanelGroup\">");
		writeLine("<h:graphicImage url=\"/resources/images/icons/refresh.png\" styleClass=\"imageIcon\" title=\"#{i18n.resetFilter}\"/>");
		writeLine("</a4j:commandLink>");
		
		writeLine("</h3>");
		
		writeLine("<div class=\"row row-eq-height\">");		
		
		for (ViewProperty property : this.bean.basicViewBean.properties) {			
			
			writeLine("<div class=\"col-xs-3\">");
			writeFilter(property, bean);
			writeLine("</div>");
			skipLine();
		}
		
		
		writeLine("</div>");
		writeLine("</div>");
		
		writeLine("<div class=\"results-panel\">");
		
		writeLine("<h:panelGroup id=\"resultsPanelGroup\">");		
		
		writeLine("<ui:fragment rendered=\"#{" + bean.listViewObjectName + ".scrollView.elements.size() == 0}\">");
		writeLine("#{i18n.noDataFound}<br/>");
		writeLine("</ui:fragment>");
		skipLine();
		
		writeLine("<ui:fragment rendered=\"#{" + bean.listViewObjectName + ".scrollView.elements.size() > 0}\">");
		
		writeLine("<s:tooltip for=\".truncated-text\"/>");
		skipLine();
		
		writeLine("<rich:dataTable rows=\"10\"");
		writeLine("id=\"" + this.bean.objectName + "List\" var=\"" + this.bean.objectName + "\" name=\"datatable\"");
		writeLine("value=\"#{" + this.bean.listViewObjectName + ".scrollView.elements}\""
				+ " headerClass=\"datatable-header\""
				+ " rowClasses=\"datatable-row, datatable-row-light\">");
		skipLine();
		
		writeLine("<rich:column>");
		writeLine("<f:facet name=\"header\">");
		writeLine("<h:selectBooleanCheckbox id=\"selectUnselectAll\" onclick=\"selectUnselectAll(this)\" value=\"false\"/>");
		writeLine("<script>$(function(){displaySelectUnselectAll();});</script>");
		writeLine("</f:facet>");
		writeLine("<h:selectBooleanCheckbox rendered=\"#{" + this.bean.objectName + ".canDelete}\" id=\"selectUnselect\" value=\"#{" + this.bean.objectName + ".selected}\" onclick=\""
				+ "selectUnselect('" + bean.objectName + "ListForm:" + bean.objectName + "List:selectUnselectAll')\"/>");
		writeLine("</rich:column>");
		skipLine();
		
		writeLine("<rich:column>");
		writeLine("<f:facet name=\"header\">");
		writeLine("<h:outputText value=\"Actions\" />");
		writeLine("<div class=\"drop-list\" id=\"drop-list\">");

		if (bean.deleteEnabled) {
			writeLine("<a4j:commandLink title=\"#{i18n.deleteSelection}\" action=\"#{" + bean.listControllerObjectName + ".deleteList}\"");
			writeLine("onclick=\"if (!confirm('#{i18n.confirmDeleteSelection}')) return false\""
					+ " execute=\"@region\" render=\"" + bean.objectName
					+ "PanelGroup\">");
			writeLine("<span class=\"glyphicon glyphicon-trash\"/>");

			writeLine("</a4j:commandLink>");
		}

		writeLine("</div>");
		writeLine("</f:facet>");
		writeLine("<h:panelGrid columns=\"2\">");
		
		if (bean.detailMode.equals(DetailMode.PAGE)) {
			writeLine("<h:link outcome=\"/sections/" + bean.myPackage.urlPiece + "/" + this.bean.urlPiece + "/details.jsf\">");
			writeLine("<h:graphicImage url=\"/resources/images/icons/edit.png\" styleClass=\"imageIcon\" title=\"#{i18n.edit}\"/>");
			writeLine("<f:param name=\"id\" value=\"#{" + bean.objectName + ".id}\" />");
			writeLine("</h:link>");
		} else {
			writeLine("<a4j:commandLink action=\"#{" + this.bean.listControllerObjectName + ".edit" + this.bean.className + "(" + bean.objectName + ".id)"+ "}\""
					+ " oncomplete=\"$('#" + bean.objectName + "Modal').modal('show')\""
					+ " render=\"" + bean.objectName + "DetailPanelGroup\">");
			writeLine("<h:graphicImage url=\"/resources/images/icons/edit.png\" styleClass=\"imageIcon\" title=\"#{i18n.edit}\"/>");
			writeLine("</a4j:commandLink>");
		}
		
		if (this.bean.deleteEnabled) {
			writeLine("<a4j:commandLink rendered=\"#{" + this.bean.objectName + ".canDelete}\" action=\"#{" + this.bean.listControllerObjectName + ".delete(" + this.bean.objectName + ".id)}\"");
			writeLine("onclick=\"if (!confirm('#{i18n.confirmDelete}')) return false\" render=\"" + this.bean.objectName + "PanelGroup\">");
			writeLine("<h:graphicImage url=\"/resources/images/icons/delete.png\" styleClass=\"imageIcon\" title=\""
					+ "#{i18n.delete}\"/>");
			writeLine("</a4j:commandLink>");

		}
		writeLine("</h:panelGrid>");
		writeLine("</rich:column>");
		skipLine();


		for (ViewProperty property : this.bean.basicViewBean.properties) {
			writeLine("<rich:column>");
			writeLine("<f:facet name=\"header\">");
			
			writeLine("<cc:datatableHeader");
			writeLine("label=\"#{i18n." + this.bean.objectName + property.capName + "}\"");
			writeLine("orderType=\"#{" + bean.listViewObjectName + ".scrollForm.sorting." + property.name + "OrderType}\"");
			writeLine("action=\"#{" + bean.listControllerObjectName + ".refresh}\"");
			writeLine("render=\"" + bean.objectName + "PanelGroup\"/>");

			writeLine("</f:facet>");

			writeListComponent(property, this.bean);

			writeLine("</rich:column>");
			skipLine();
		}

		writeLine("</rich:dataTable>");
		skipLine();
		
		
		writeLine("<cc:datatableScroller");
		writeLine("page=\"#{" + bean.listViewObjectName + ".scrollForm.page}\"");
		writeLine("numberOfPages=\"#{" + bean.listViewObjectName + ".scrollView.numberOfPages}\"");
		writeLine("action=\"#{" + bean.listControllerObjectName + ".refresh}\"");
		writeLine("render=\"" + bean.objectName + "PanelGroup\"/>");		
		skipLine();
		
		writeLine("</ui:fragment>");
		skipLine();
		
		writeLine("</h:panelGroup>");
		skipLine();
		writeLine("</div>");
		skipLine();

		if (this.bean.createEnabled) {
			writeLine("<a4j:commandButton value=\"#{i18n.create}\" action=\"#{" + this.bean.listControllerObjectName + ".create" + this.bean.className + "}\""
					+ " styleClass=\"btn btn-info\" oncomplete=\"if (#{empty facesContext.maximumSeverity or facesContext.maximumSeverity.ordinal ==0}) {$('#" + bean.objectName + "Modal').modal('show')}\""
					+ " render=\"" + bean.objectName + "DetailPanelGroup\"/>");

			skipLine();
		}		
			
		this.writeNotOverridableContent();

		writeLine("</a4j:region>");
		skipLine();
		writeLine("</h:panelGroup>");
		skipLine();
		
		
        
        writeLine("<div class=\"modal modal-default\" id=\"" + bean.objectName + "Modal\" tabindex=\"-1\" aria-hidden=\"true\">");
        writeLine("<div class=\"modal-dialog modal-lg\">");
        writeLine("<div class=\"modal-content\">");
        writeLine("<ui:include src=\"/sections/" + bean.myPackage.urlPiece + "/" + this.bean.urlPiece + "/modal.xhtml\"/>");
        writeLine("</div>");
        writeLine("</div>");
        writeLine("</div>");
        
        writeLine("</h:form>");

		writeLine("</ui:define>");
		writeLine("</ui:composition>");
	}
}
