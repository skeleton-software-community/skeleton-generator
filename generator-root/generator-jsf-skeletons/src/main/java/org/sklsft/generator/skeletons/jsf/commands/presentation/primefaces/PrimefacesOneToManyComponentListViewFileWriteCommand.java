package org.sklsft.generator.skeletons.jsf.commands.presentation.primefaces;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.model.metadata.DetailMode;

public class PrimefacesOneToManyComponentListViewFileWriteCommand extends PrimefacesXhtmlFileWriteCommand {

	private OneToManyComponent oneToManyComponent;

	public PrimefacesOneToManyComponentListViewFileWriteCommand(OneToManyComponent oneToManyComponent) {
		super(oneToManyComponent.referenceBean.myPackage.model.project.workspaceFolder + File.separator + oneToManyComponent.referenceBean.myPackage.model.project.projectName
				+ "-webapp" + File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "sections" + File.separator + oneToManyComponent.parentBean.myPackage.name + File.separator + oneToManyComponent.parentBean.className.toLowerCase(),
				oneToManyComponent.referenceBean.className + "List");
		this.oneToManyComponent = oneToManyComponent;
	}

	@Override
	protected void writeContent() throws IOException {

		Bean currentBean = oneToManyComponent.referenceBean;
		Bean parentBean = oneToManyComponent.parentBean;

		writeLine("<ui:composition xmlns=\"http://www.w3.org/1999/xhtml\"");
		writeLine("xmlns:ui = \"http://java.sun.com/jsf/facelets\"");
		writeLine("xmlns:f = \"http://java.sun.com/jsf/core\"");
		writeLine("xmlns:h = \"http://java.sun.com/jsf/html\"");
		writeLine("xmlns:p=\"http://primefaces.org/ui\"");
		writeLine("xmlns:cc=\"http://java.sun.com/jsf/composite/components\"");
		writeLine("xmlns:s=\"http://commons.sklsft.org/ui/components\"");
		writeLine("template=\"/templates/template.xhtml\">");
        skipLine();

        writeLine("<!-- -->");
        writeLine("<!-- auto generated jsf file -->");
        writeLine("<!-- write modifications between specific code marks -->");
        writeLine("<!-- processed by skeleton-generator -->");
        writeLine("<!-- -->");
        skipLine();
        
        writeLine("<f:metadata>");
		writeLine("<f:viewParam name=\"id\" value=\"#{" + parentBean.detailViewObjectName + ".selected" + parentBean.className + ".id}\" />");
		writeLine("<f:viewAction action=\"#{" + parentBean.detailControllerObjectName + ".load" + currentBean.className + "List}\" />");
		writeLine("</f:metadata>");

        writeLine("<ui:define name=\"content\">");
        skipLine();
        
        writeLine("<h:form id=\"" +  currentBean.objectName + "ListForm\">");
        skipLine();
        
        writeLine("<ui:include src=\"/sections/" + parentBean.myPackage.name + "/" + parentBean.className.toLowerCase() + "/" + parentBean.className + "DetailsMenu.xhtml\"/>");
        skipLine();
        
        writeLine("<h:panelGroup id=\"" +  currentBean.objectName + "PanelGroup\">");
		skipLine();

        writeLine("<h2>");
		writeLine("#{i18n." + currentBean.objectName + "List} (#{" + parentBean.detailViewObjectName + "." + currentBean.objectName + "ScrollView.size})");
		writeLine("</h2>");
		
		writeLine("<div class=\"filter-panel\">");
		writeLine("<h3>");
		writeLine("#{i18n.filters}");
		
		writeLine("<p:commandLink action=\"#{" + parentBean.detailControllerObjectName + ".reset" + currentBean.className + "Filter}\""
				+ " process=\"@this\" update=\"@form:" +  currentBean.objectName + "PanelGroup\">");
		writeLine("<h:graphicImage url=\"/resources/images/icons/refresh.png\" styleClass=\"imageIcon\" title=\"#{i18n.resetFilter}\"/>");
		writeLine("</p:commandLink>");
		
		writeLine("</h3>");
		
		writeLine("<div class=\"row row-eq-height\">");
		
		for (ViewProperty property : currentBean.basicViewBean.properties) {			
			
			writeLine("<div class=\"col-xs-3\">");
			writeFilter(property, currentBean, parentBean);
			writeLine("</div>");
			skipLine();
		}
		
		
		writeLine("</div>");
		writeLine("</div>");
		
		writeLine("<div class=\"results-panel\">");
		
		writeLine("<h:panelGroup id=\"resultsPanelGroup\">");		
		
		writeLine("<ui:fragment rendered=\"#{" + parentBean.detailViewObjectName + "." + currentBean.objectName + "ScrollView.size == 0}\">");
		writeLine("#{i18n.noDataFound}<br/>");
		writeLine("</ui:fragment>");
		skipLine();
		
		writeLine("<ui:fragment rendered=\"#{" + parentBean.detailViewObjectName + "." + currentBean.objectName + "ScrollView.size > 0}\">");
		skipLine();
		
		writeLine("<s:tooltip for=\".truncated-text\"/>");
		skipLine();
		
		writeLine("<p:dataTable rows=\"10\"");
		writeLine("id=\"" +  currentBean.objectName + "List\" var=\"" +  currentBean.objectName + "\" name=\"datatable\"");
		writeLine("value=\"#{" + parentBean.detailViewObjectName + "." + currentBean.objectName + "ScrollView.elements}\""
				+ " headerClass=\"datatable-header\""
				+ " rowClasses=\"datatable-row, datatable-row-light\">");
		skipLine();
		
		writeLine("<p:column>");
		writeLine("<f:facet name=\"header\">");
		writeLine("<h:selectBooleanCheckbox id=\"selectUnselectAll\" onclick=\"selectUnselectAll(this)\" value=\"false\"/>");
		writeLine("<script>$(function(){displaySelectUnselectAll();});</script>");
		writeLine("</f:facet>");
		writeLine("<h:selectBooleanCheckbox  rendered=\"#{" + currentBean.objectName + ".canDelete}\" id=\"selectUnselect\" value=\"#{" + currentBean.objectName + ".selected}\""
				+ " onclick=\"selectUnselect('" + currentBean.objectName + "ListForm:" + currentBean.objectName + "List:selectUnselectAll')\"/>");
		writeLine("</p:column>");
		skipLine();
		
		writeLine("<p:column>");
		writeLine("<f:facet name=\"header\">");
		writeLine("<h:outputText value=\"Actions\" />");
		
		writeLine("<div id=\"drop-list\" class=\"drop-list\">");
		
		if (currentBean.deleteEnabled) {
			writeLine("<p:commandLink title=\"#{i18n.deleteSelection}\" action=\"#{" + parentBean.detailControllerObjectName + ".delete" + currentBean.className + "List}\"");
			writeLine("onclick=\"if (!confirm('#{i18n.confirmDeleteSelection}')) return false\" process=\"@form:" +  currentBean.objectName + "PanelGroup\" update=\":messages, @form:" +  currentBean.objectName + "PanelGroup\">");
			writeLine("<span class=\"glyphicon glyphicon-trash\"/>");
		
			writeLine("</p:commandLink>");
		}

		writeLine("</div>");
		skipLine();
		
		writeLine("</f:facet>");
		writeLine("<h:panelGrid columns=\"2\">");

		if (currentBean.detailMode.equals(DetailMode.PAGE)) {
			writeLine("<h:link outcome=\"/sections/" + currentBean.myPackage.name + "/" + currentBean.className.toLowerCase() + "/" + currentBean.className + "Details.jsf\">");
			writeLine("<h:graphicImage url=\"/resources/images/icons/edit.png\" styleClass=\"imageIcon\" title=\"#{i18n.edit}\"/>");
			writeLine("<f:param name=\"id\" value=\"#{" + currentBean.objectName + ".id}\" />");
			writeLine("</h:link>");
		} else {
			writeLine("<p:commandLink action=\"#{" + parentBean.detailControllerObjectName + ".edit" + currentBean.className + "(" + currentBean.objectName + ".id)}\"" 
					+ " oncomplete=\"$('#" + currentBean.objectName + "Modal').modal('show')\""
					+ " update=\"@form:" +  currentBean.objectName + "DetailPanelGroup\">");
			writeLine("<h:graphicImage url=\"/resources/images/icons/edit.png\" styleClass=\"imageIcon\" title=\"#{i18n.edit}\"/>");
			writeLine("</p:commandLink>");
		}

		if (currentBean.deleteEnabled) {
			writeLine("<p:commandLink action=\"#{" + parentBean.detailControllerObjectName + ".delete" + currentBean.className + "(" + currentBean.objectName + ".id)}\"");
			writeLine("rendered=\"#{" + currentBean.objectName + ".canDelete}\"");
			writeLine("onclick=\"if (!confirm('#{i18n.confirmDelete}')) return false\" process=\"@this\" update=\":messages, @form:" +  currentBean.objectName + "PanelGroup\">");
			writeLine("<h:graphicImage url=\"/resources/images/icons/delete.png\" styleClass=\"imageIcon\" title=\"#{i18n.delete}\"/>");
			writeLine("</p:commandLink>");
		}
		writeLine("</h:panelGrid>");
		writeLine("</p:column>");
		skipLine();

		for (ViewProperty property : currentBean.basicViewBean.properties) {
			writeLine("<p:column>");
			
			writeLine("<f:facet name=\"header\">");
			
			writeLine("<cc:datatableHeader");
			writeLine("label=\"#{i18n." + currentBean.objectName + property.capName + "}\"");
			writeLine("orderType=\"#{" + parentBean.detailViewObjectName + "." + currentBean.objectName + "ScrollForm.sorting." + property.name + "OrderType}\"");
			writeLine("action=\"#{" + parentBean.detailControllerObjectName + ".refresh" + currentBean.className + "List}\"");
			writeLine("render=\"@form:" +  currentBean.objectName + "PanelGroup\"/>");

			writeLine("</f:facet>");

			writeListComponent(property, currentBean);

			writeLine("</p:column>");
			skipLine();
		}
		
		writeLine("</p:dataTable>");
		skipLine();		
		
		writeLine("<cc:datatableScroller");
		writeLine("page=\"#{" + parentBean.detailViewObjectName + "." + currentBean.objectName + "ScrollForm.page}\"");
		writeLine("numberOfPages=\"#{" + parentBean.detailViewObjectName + "." + currentBean.objectName + "ScrollView.numberOfPages}\"");
		writeLine("action=\"#{" + parentBean.detailControllerObjectName + ".refresh" + currentBean.className + "List}\"");
		writeLine("render=\"@form:" +  currentBean.objectName + "PanelGroup\"/>");
		skipLine();
		
		writeLine("</ui:fragment>");
		skipLine();		
		writeLine("</h:panelGroup>");
		skipLine();
		writeLine("</div>");
		skipLine();

		if (currentBean.createEnabled) {
			writeLine("<p:commandButton value=\"#{i18n.create}\" action=\"#{" + parentBean.detailControllerObjectName + ".create" + currentBean.className
					+ "}\" styleClass=\"btn btn-info\" oncomplete=\"if (#{empty facesContext.maximumSeverity or facesContext.maximumSeverity.ordinal ==0}) $('#" + currentBean.objectName + "Modal').modal('show')\""
					+ " process=\"@this\" update=\"@form:" +  currentBean.objectName + "DetailPanelGroup\"/>");
			skipLine();
		}
		
		this.writeNotOverridableContent();
		skipLine();
		
		writeLine("</h:panelGroup>");
		skipLine();
		
		
		 writeLine("<div class=\"modal modal-default\" id=\"" +  currentBean.objectName + "Modal\" tabindex=\"-1\" aria-hidden=\"true\">");
         writeLine("<div class=\"modal-dialog modal-lg\">");
         writeLine("<div class=\"modal-content\">");
         writeLine("<ui:include src=\"/sections/" + parentBean.myPackage.name + "/" + parentBean.className.toLowerCase() + "/" + currentBean.className + "Modal.xhtml\"/>");
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