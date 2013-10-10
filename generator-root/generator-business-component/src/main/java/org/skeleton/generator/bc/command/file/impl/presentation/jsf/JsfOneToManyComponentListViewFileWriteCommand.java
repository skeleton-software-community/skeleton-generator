package org.skeleton.generator.bc.command.file.impl.presentation.jsf;

import java.io.IOException;

import org.skeleton.generator.model.om.Bean;
import org.skeleton.generator.model.om.OneToManyComponent;
import org.skeleton.generator.model.om.Property;

public class JsfOneToManyComponentListViewFileWriteCommand extends JsfXhtmlFileWriteCommand {

	private OneToManyComponent oneToManyComponent;

	public JsfOneToManyComponentListViewFileWriteCommand(OneToManyComponent oneToManyComponent) {
		super(oneToManyComponent.referenceBean.myPackage.model.project.workspaceFolder + "\\" + oneToManyComponent.referenceBean.myPackage.model.project.projectName
				+ "-webapp\\src\\main\\webapp\\sections\\" + oneToManyComponent.referenceBean.myPackage.name + "\\" + oneToManyComponent.parentBean.className.toLowerCase(),
				oneToManyComponent.referenceBean.className + "List");
		this.oneToManyComponent = oneToManyComponent;

	}

	@Override
	protected void writeContent() throws IOException {

		Bean currentBean = oneToManyComponent.referenceBean;
		Bean parentBean = oneToManyComponent.parentBean;

		writeLine("<ui:composition xmlns=" + (char) 34 + "http://www.w3.org/1999/xhtml" + (char) 34);
		writeLine("xmlns:ui = " + (char) 34 + "http://java.sun.com/jsf/facelets" + (char) 34);
		writeLine("xmlns:f = " + (char) 34 + "http://java.sun.com/jsf/core" + (char) 34);
		writeLine("xmlns:h = " + (char) 34 + "http://java.sun.com/jsf/html" + (char) 34);
		writeLine("xmlns:rich = " + (char) 34 + "http://richfaces.org/rich" + (char) 34);
		writeLine("xmlns:a4j = " + (char) 34 + "http://richfaces.org/a4j" + (char) 34);
		writeLine("xmlns:c=" + (char) 34 + "http://java.sun.com/jstl/core" + (char) 34 + ">");
		skipLine();

		writeLine("<!-- -->");
		writeLine("<!-- auto generated jsf file -->");
		writeLine("<!-- write modifications between specific code marks -->");
		writeLine("<!-- processed by skeleton generator -->");
		writeLine("<!-- -->");
		skipLine();

		writeLine("<script language=" + (char) 34 + "javascript" + (char) 34 + ">");
		writeLine("<!--");
		skipLine();

		writeLine("function selectUnselectAll(checkbox){");
		writeLine("var elements = checkbox.form.elements;");
		writeLine("for (var i = 0; i < elements.length; i++) {");
		writeLine("var element = elements[i];");
		writeLine("if (/" + currentBean.objectName + "Selected$/.test(element.id)) {");
		writeLine("element.checked = checkbox.checked;");
		writeLine("}");
		writeLine("}");
		writeLine("if (checkbox.checked==true){");
		writeLine("showActions();");
		writeLine("}else{");
		writeLine("hideActions();");
		writeLine("}");
		writeLine("}");
		skipLine();

		writeLine("function selectBox() {");
		writeLine("checkbox = document.getElementById(" + (char) 34 + currentBean.objectName + "Form:" + currentBean.objectName + "List:selectUnselectAll" + (char) 34 + ");");
		writeLine("var elements = checkbox.form.elements;");
		writeLine("var allSelected=true;");
		writeLine("var selectedItemNumber=0;");
		writeLine("for (var elementIndex = 0; elementIndex < elements.length; elementIndex++) {");
		writeLine("var element = elements[elementIndex];");
		writeLine("if (/" + currentBean.objectName + "Selected$/.test(element.id)) {");
		writeLine("if (!element.checked){");
		writeLine("allSelected=false;");
		writeLine("}else{");
		writeLine("selectedItemNumber++;");
		writeLine("}");
		writeLine("}");
		writeLine("}");
		writeLine("checkbox.checked=allSelected;");
		writeLine("if (selectedItemNumber>0){");
		writeLine("showActions();");
		writeLine("}else{");
		writeLine("hideActions();");
		writeLine("}");
		writeLine("}");
		skipLine();

		writeLine("function showActions(){");
		writeLine("document.getElementById('" + currentBean.objectName + "Actions').style.display=" + (char) 34 + "block" + (char) 34 + ";");
		writeLine("}");
		skipLine();

		writeLine("function hideActions(){");
		writeLine("document.getElementById('" + currentBean.objectName + "Actions').style.display=" + (char) 34 + "none" + (char) 34 + ";");
		writeLine("}");
		skipLine();

		writeLine("-->");
		writeLine("</script>");
		skipLine();

		String header = "#{i18n." + currentBean.objectName + "List}";

		for (Property property : parentBean.getFindPropertyList()) {
			header = header + " " + "#{" + parentBean.controllerObjectName + ".selected" + parentBean.className + "." + property.name + "}";
		}

		writeLine("<h:panelGroup id=" + (char) 34 + currentBean.objectName + "PanelGroup" + (char) 34 + ">");
		writeLine("<h:panelGroup rendered=" + (char) 34 + "#{!" + parentBean.controllerObjectName + ".displaySuccessfull}" + (char) 34 + " styleClass=" + (char) 34 + "errorMessage" + (char) 34
				+ " layout=" + (char) 34 + "block" + (char) 34 + ">");
		writeLine("<h:outputText value=" + (char) 34 + "#{i18n.dislayFailure}" + (char) 34 + "/>");
		writeLine("</h:panelGroup>");
		writeLine("<h:panelGroup rendered=" + (char) 34 + "#{" + parentBean.controllerObjectName + ".displaySuccessfull}" + (char) 34 + ">");
		skipLine();

		writeLine("<h:form id=" + (char) 34 + currentBean.objectName + "Form" + (char) 34 + ">");
		skipLine();

		writeLine("<rich:dataTable rows=" + (char) 34 + "#{" + parentBean.controllerObjectName + ".numberOfRows}" + (char) 34 + " rendered=" + (char) 34 + "#{" + parentBean.controllerObjectName
				+ ".displaySuccessfull}" + (char) 34);
		writeLine("id=" + (char) 34 + currentBean.objectName + "List" + (char) 34 + " var=" + (char) 34 + currentBean.objectName + (char) 34 + " name=" + (char) 34 + "datatable" + (char) 34);
		writeLine("value=" + (char) 34 + "#{" + parentBean.controllerObjectName + "." + currentBean.objectName + "List}" + (char) 34 + " headerClass=" + (char) 34 + "datatableHeader" + (char) 34
				+ " rowClasses=" + (char) 34 + "datatableRow, datatableRowLight" + (char) 34 + ">");
		skipLine();

		writeLine("<c:set var=" + (char) 34 + currentBean.objectName + (char) 34 + " value=" + (char) 34 + "#{" + currentBean.objectName + "}" + (char) 34 + " scope=" + (char) 34 + "request"
				+ (char) 34 + "/>");
		skipLine();

		writeLine("<rich:column>");
		writeLine("<f:facet name=" + (char) 34 + "header" + (char) 34 + ">");
		writeLine("<h:selectBooleanCheckbox id=" + (char) 34 + "selectUnselectAll" + (char) 34 + " name=" + (char) 34 + "selectUnselectAll" + (char) 34 + " forceId=" + (char) 34 + "true" + (char) 34
				+ " onclick=" + (char) 34 + "selectUnselectAll(this)" + (char) 34 + " value=" + (char) 34 + "false" + (char) 34 + "/>");
		writeLine("</f:facet>");
		writeLine("<h:selectBooleanCheckbox id=" + (char) 34 + currentBean.objectName + "Selected" + (char) 34 + " value=" + (char) 34 + "#{" + currentBean.objectName + ".selected}" + (char) 34
				+ " onclick=" + (char) 34 + "selectBox()" + (char) 34 + "/>");
		writeLine("</rich:column>");
		skipLine();

		for (Property property : currentBean.getVisiblePropertyList()) {
			if (property.visibility.isListVisible()) {
				writeLine("<rich:column sortBy=" + (char) 34 + "#{" + currentBean.objectName + "." + property.name + "}" + (char) 34 + " filterBy=" + (char) 34 + "#{" + currentBean.objectName + "."
						+ property.name + "}" + (char) 34 + " filterEvent=" + (char) 34 + "onkeyup" + (char) 34 + ">");
				writeLine("<f:facet name=" + (char) 34 + "header" + (char) 34 + ">");
				writeLine("<h:outputText value=" + (char) 34 + "#{i18n." + currentBean.objectName + property.capName + "}" + (char) 34 + " />");
				writeLine("</f:facet>");

				writeListComponent(property, currentBean);

				writeLine("</rich:column>");
				skipLine();
			}
		}

		writeLine("<rich:column>");
		writeLine("<f:facet name=" + (char) 34 + "header" + (char) 34 + ">");
		writeLine("<h:outputText value=" + (char) 34 + "Actions" + (char) 34 + " />");
		writeLine("</f:facet>");
		writeLine("<h:panelGrid columns=" + (char) 34 + "2" + (char) 34 + ">");
		writeLine("<a4j:commandLink action=" + (char) 34 + "#{" + parentBean.controllerObjectName + ".edit" + currentBean.className + "}" + (char) 34 + " actionListener=" + (char) 34 + "#{"
				+ parentBean.controllerObjectName + ".listenSelected" + currentBean.className + "Id}" + (char) 34 + " oncomplete=" + (char) 34 + "Richfaces.showModalPanel('" + currentBean.objectName
				+ "ModalPanel')" + (char) 34 + " reRender=" + (char) 34 + currentBean.objectName + "DetailPanelGroup" + (char) 34 + ">");
		writeLine("<f:attribute name=" + (char) 34 + "selected" + currentBean.className + "Id" + (char) 34 + " value=" + (char) 34 + "#{" + currentBean.objectName + ".id}" + (char) 34 + " />");
		writeLine("<h:graphicImage url=" + (char) 34 + "/resources/images/icons/edit.png" + (char) 34 + " styleClass=" + (char) 34 + "imageIcon" + (char) 34 + " title=" + (char) 34 + "#{i18n.edit}"
				+ (char) 34 + "/>");
		writeLine("</a4j:commandLink>");

		if (this.oneToManyComponent.referenceBean.deleteEnabled) {
			writeLine("<a4j:commandLink action=" + (char) 34 + "#{" + parentBean.controllerObjectName + ".delete" + currentBean.className + "}" + (char) 34 + " actionListener=" + (char) 34 + "#{"
					+ parentBean.controllerObjectName + ".listenSelected" + currentBean.className + "Id}" + (char) 34);
			writeLine("onclick=" + (char) 34 + "if (!confirm('#{i18n.confirmDrop}')) return false" + (char) 34 + " reRender=" + (char) 34 + currentBean.objectName + "PanelGroup" + (char) 34 + ">");
			writeLine("<f:attribute name=" + (char) 34 + "selected" + currentBean.className + "Id" + (char) 34 + " value=" + (char) 34 + "#{" + currentBean.objectName + ".id}" + (char) 34 + "/>");
			writeLine("<h:graphicImage url=" + (char) 34 + "/resources/images/icons/delete.png" + (char) 34 + " styleClass=" + (char) 34 + "imageIcon" + (char) 34 + " title=" + (char) 34
					+ "#{i18n.delete}" + (char) 34 + "/>");
			writeLine("</a4j:commandLink>");
		}
		writeLine("</h:panelGrid>");
		writeLine("</rich:column>");
		skipLine();

		writeLine("<f:facet name=" + (char) 34 + "footer" + (char) 34 + ">");
		writeLine("<rich:datascroller maxPages=" + (char) 34 + "5" + (char) 34 + "/>");
		writeLine("</f:facet>");
		writeLine("</rich:dataTable>");
		skipLine();

		writeLine("<br/>");
		writeLine("<br/>");
		skipLine();

		if (this.oneToManyComponent.referenceBean.createEnabled) {
			writeLine("<a4j:commandButton value=" + (char) 34 + "#{i18n.create}" + (char) 34 + " action=" + (char) 34 + "#{" + parentBean.controllerObjectName + ".create" + currentBean.className
					+ "}" + (char) 34 + " styleClass=" + (char) 34 + "simpleButton" + (char) 34 + " oncomplete=" + (char) 34 + "Richfaces.showModalPanel('" + currentBean.objectName + "ModalPanel')"
					+ (char) 34 + " reRender=" + (char) 34 + currentBean.objectName + "DetailPanelGroup" + (char) 34 + "/>");
			skipLine();
		}

		writeLine("<br/>");
		skipLine();

		writeLine("<div id=" + (char) 34 + currentBean.objectName + "Actions" + (char) 34 + " style=" + (char) 34 + "display:none;margin:2px;" + (char) 34 + ">");
		writeLine("Actions on selection:");
		writeLine("<br/>");

		writeLine("<h:panelGrid columns=" + (char) 34 + "1" + (char) 34 + ">");

		if (this.oneToManyComponent.referenceBean.deleteEnabled) {
			writeLine("<a4j:commandButton value=" + (char) 34 + "#{i18n.dropSelection}" + (char) 34 + " actionListener=" + (char) 34 + "#{" + parentBean.controllerObjectName + ".listenSelected"
					+ currentBean.className + "IdList}" + (char) 34 + " action=" + (char) 34 + "#{" + parentBean.controllerObjectName + ".delete" + currentBean.className + "List}" + (char) 34
					+ " styleClass=" + (char) 34 + "simpleButton" + (char) 34);
			writeLine("onclick=" + (char) 34 + "if (!confirm('#{i18.confirmDropSelection}')) return false" + (char) 34 + " reRender=" + (char) 34 + currentBean.objectName + "PanelGroup" + (char) 34
					+ "/>");
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
		writeLine("</h:panelGroup>");
		skipLine();

		writeLine("</ui:composition>");
	}
}
