package org.sklsft.generator.skeletons.jsf.commands.presentation.primefaces;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.model.metadata.DataType;
import org.sklsft.generator.model.metadata.SelectionMode;
import org.sklsft.generator.skeletons.commands.impl.typed.XhtmlFileWriteCommand;

public abstract class PrimefacesXhtmlFileWriteCommand extends XhtmlFileWriteCommand {

	public PrimefacesXhtmlFileWriteCommand(String folderName, String fileName) {
		super(folderName, fileName);
	}

	protected void writeListComponent(ViewProperty property, Bean bean) {
		switch (property.dataType) {
		case BOOLEAN:
			if (property.nullable) {
				writeLine("<h:outputText value=\"#{"+ bean.objectName + "." + property.name + "}\"/>");
			} else {
				writeLine("<h:selectBooleanCheckbox value=\"#{" + bean.objectName + "." + property.name + "}\" disabled=\"true\"/>");
			}
			break;

		case DATETIME:
			writeLine("<h:outputText value=\"#{" + bean.objectName + "." + property.name + "}\">");

			switch (property.format) {
			case DATE:
				writeLine("<f:convertDateTime type=\"date\" dateStyle=\"medium\"/>");
				break;

			default:
				writeLine("<f:convertDateTime type=\"both\" dateStyle=\"medium\"/>");
				break;
			}
			writeLine("</h:outputText>");

			break;

		case DOUBLE:
			writeLine("<h:outputText value=\"#{" + bean.objectName + "." + property.name + "}\">");

			switch (property.format) {
			case TWO_DECIMALS:
				writeLine("<f:convertNumber pattern=\"#,##0.00\"/>");
				break;

			case FOUR_DECIMALS:
				writeLine("<f:convertNumber pattern=\"#,##0.0000\"/>");
				break;

			default:
				writeLine("<f:convertNumber pattern=\"#,##0.########\"/>");
				break;
			}
			writeLine("</h:outputText>");
			break;

		case LONG:
			writeLine("<h:outputText value=\"#{" + bean.objectName + "." + property.name + "}\">");
			writeLine("<f:convertNumber pattern=\"#,##0\"/>");
			writeLine("</h:outputText>");
			break;

		case STRING:
			writeLine("<h:outputText value=\"#{" + bean.objectName + "." + property.name + "}\"/>");
			break;

		case TEXT:
			writeLine("<pre class=\"truncated-text\"");
			writeLine(" data-toggle=\"tooltip\"");
			writeLine(" data-html=\"true\"");
			writeLine(" data-title='&lt;pre&gt;#{" + bean.objectName + "." + property.name + "}&lt;/pre&gt;'");
			writeLine(" data-placement=\"bottom\"");
			writeLine(" data-trigger=\"click\">");
			writeLine("#{" + bean.objectName + "." + property.name + "}");
			writeLine("</pre>");
			break;
		}
	}
	
	
	protected void writeInput(ViewProperty property, Bean bean){
		writeInput("", property, bean);
	}
	
	protected void writeInput(String prefix, ViewProperty property, Bean bean){
		
		writeLine("<div class=\"col-xs-12\">");
		
		if (!property.dataType.equals(DataType.BOOLEAN)) {
            writeLine("<label>#{i18n." + bean.objectName + property.capName + "}</label>");
		}
		
		if (property.selectableBean != null) {
			if (property.selectableBean.selectionBehavior.selectionMode.equals(SelectionMode.DROPDOWN_OPTIONS)) {
				writeCombobox(prefix, property, bean);
			} else {
				writeAutocomplete(prefix, property, bean);
			}
		} else {
		
			switch (property.dataType) {
				case BOOLEAN:
					writeBooleanInput(prefix, property, bean);
					break;
				case DATETIME:
					writeDateInput(prefix, property, bean);
					break;
				case DOUBLE:
					writeDoubleInput(prefix, property, bean);
					break;
				case LONG:
					writeLongInput(prefix, property, bean);
					break;
				case STRING:
					writeStringInput(prefix, property, bean);
					break;
				case TEXT:
					writeTextInput(prefix, property, bean);
					break;
			}
		}
		
		if (!property.dataType.equals(DataType.BOOLEAN)) {
			writeLine("<h:message for=\"" + prefix + bean.objectName + property.capName + "\" styleClass=\"detail-error-message\"/>");
		}
		
		writeLine("</div>");
        skipLine();
	}
	
	private void writeCombobox(String prefix, ViewProperty property, Bean bean){
		
		write("<h:selectOneMenu id=\"" + prefix + bean.objectName
				+ property.capName + "\" styleClass=\"form-control\" value=\"#{form." + property.name + "}\"");
				
		if (!property.editable) {
			write(" disabled=\"true\"");
		}
		
		writeLine(">");

		writeLine("<f:selectItem itemValue=\"#{null}\" itemLabel=\"\"/>");
		writeLine("<f:selectItems value=\"#{commonView." + property.selectableBean.objectName + "Options}\"");
		writeLine("var=\"option\" itemValue=\"#{option.key}\" itemLabel=\"#{option.label}\"/>");
		writeLine("</h:selectOneMenu>");
	}
	
	private void writeAutocomplete(String prefix, ViewProperty property, Bean bean){
		
		writeLine("<rich:autocomplete id=\"" + prefix + bean.objectName
				+ property.capName + "\" styleClass=\"form-control\" value=\"#{form." + property.name + "}\"");
				
		if (!property.editable) {
			write(" disabled=\"true\"");
		}
		
		writeLine(" autocompleteMethod=\"#{commonController.search" + property.selectableBean.className + "Options}\"/>");
	}
	
	
	private void writeStringInput(String prefix, ViewProperty property, Bean bean){
		write("<h:inputText id=\"" + prefix + bean.objectName
				+ property.capName + "\" styleClass=\"form-control\" value=\"#{form." + property.name + "}\"");
		
		if (!property.editable) {
			write(" disabled=\"true\"");
		}
		writeLine("/>");
	}
	
	private void writeTextInput(String prefix, ViewProperty property, Bean bean){
		write("<h:inputTextarea id=\"" + prefix + bean.objectName
				+ property.capName + "\" styleClass=\"form-control\" rows=\"10\" value=\"#{form." + property.name + "}\"");
		
		if (!property.editable) {
			write(" disabled=\"true\"");
		}
		writeLine("/>");
	}
	
	private void writeBooleanInput(String prefix, ViewProperty property, Bean bean){
		writeLine("<div class=\"checkbox\">");
		writeLine("<label>");
		writeLine("<h:selectBooleanCheckbox id=\"" + prefix 
				+ bean.objectName + property.capName + "\" value=\"#{form." + property.name + "}\"");
		if (property.editable) {
			writeLine("readonly=\"false\" disabled=\"false\"/>");			
		} else {
			writeLine("readonly=\"true\" disabled=\"true\"/>");
		}
		writeLine("#{i18n." + bean.objectName + property.capName + "}");
		writeLine("</label>");
		writeLine("</div>");
	}
	
	private void writeDoubleInput(String prefix, ViewProperty property, Bean bean){
		write("<h:inputText id=\"" + prefix + bean.objectName
				+ property.capName + "\" styleClass=\"form-control\" value=\"#{form."
				+ property.name + "}\"");
		
		if (!property.editable) {
			write(" disabled=\"true\"");
		}
		writeLine(">");

		switch (property.format) {
			case TWO_DECIMALS:
				writeLine("<f:convertNumber pattern=\"#,##0.00\"/>");
				break;
	
			case FOUR_DECIMALS:
				writeLine("<f:convertNumber pattern=\"#,##0.0000\"/>");
				break;
	
			default:
				writeLine("<f:convertNumber pattern=\"#,##0.########\"/>");
				break;
		}
		writeLine("</h:inputText>");
	}
	
	private void writeLongInput(String prefix, ViewProperty property, Bean bean){
		write("<h:inputText id=\"" + prefix + bean.objectName
				+ property.capName + "\" styleClass=\"form-control\" value=\"#{form." + property.name + "}\"");
				
		if (!property.editable) {
			skipLine();
			write("disabled=\"true\"");
		}
		
		writeLine(">");
		writeLine("<f:convertNumber integerOnly=\"true\" pattern=\"#,##0\"/>");
		writeLine("</h:inputText>");
	}
	
	private void writeDateInput(String prefix, ViewProperty property, Bean bean){
		writeLine("<rich:calendar id=\"" + prefix + bean.objectName + property.capName +
				"\" inputClass=\"form-control\" value=\"#{form." + property.name + "}\"");

		switch (property.format) {
		case DATE:
			write(" datePattern=\"dd MMMM yyyy\"");
			break;

		default:
			write(" datePattern=\"dd MMMM yyyy HH:mm\"");
			break;
		}
				
		if (!property.editable) {
			skipLine();
			write("disabled=\"true\"");
		}
		writeLine("/>");
		
	}
	
	
	protected void writeFilter(ViewProperty property, Bean bean, Bean parentBean) {
		
		writeLine("<label>#{i18n." + bean.objectName + property.capName + "}</label>");
		
		String scrollForm = parentBean!=null?parentBean.detailViewObjectName + "." + bean.objectName + "ScrollForm":bean.listViewObjectName + ".scrollForm";
		String refreshMethod = parentBean!=null?parentBean.detailControllerObjectName + ".refresh" + bean.className + "List":bean.listControllerObjectName + ".refresh";
		
		switch (property.dataType) {
			case STRING:
			case TEXT:
				writeLine("<h:inputText");
				writeLine("value=\"#{" + scrollForm + ".filter." + property.name + "}\"");
				writeLine("styleClass=\"form-control\">");
				writeLine("<a4j:ajax event=\"keyup\" render=\"resultsPanelGroup\" listener=\"#{" + refreshMethod + "}\">");
				writeLine("<a4j:attachQueue requestDelay=\"500\"/>");
				writeLine("</a4j:ajax>");
				writeLine("</h:inputText>");			
				break;
				
			case DATETIME:				
				writeLine("<rich:calendar value=\"#{" + scrollForm + ".filter." + property.name + "MinValue}\"");
				writeLine("inputClass=\"form-control\"");
				switch (property.format) {
				case DATE:
					writeLine("datePattern=\"dd MMMM yyyy\">");
					break;

				default:
					writeLine("datePattern=\"dd MMMM yyyy HH:mm\">");
					break;
				}
				writeLine("<a4j:ajax event=\"change\" render=\"resultsPanelGroup\" listener=\"#{" + refreshMethod + "}\">");
				writeLine("</a4j:ajax>");
				writeLine("</rich:calendar>");
				writeLine("<rich:calendar value=\"#{" + scrollForm + ".filter." + property.name + "MaxValue}\"");
				writeLine("inputClass=\"form-control\"");
				switch (property.format) {
				case DATE:
					writeLine("datePattern=\"dd MMMM yyyy\">");
					break;

				default:
					writeLine("datePattern=\"dd MMMM yyyy HH:mm\">");
					break;
				}
				writeLine("<a4j:ajax event=\"change\" render=\"resultsPanelGroup\" listener=\"#{" + refreshMethod + "}\">");
				writeLine("</a4j:ajax>");
				writeLine("</rich:calendar>");
				break;
				
			case DOUBLE:
				
				writeLine("<h:inputText value=\"#{" + scrollForm + ".filter." + property.name + "MinValue}\"");
				writeLine("styleClass=\"form-control\">");
				writeLine("<a4j:ajax event=\"keyup\" render=\"resultsPanelGroup\" listener=\"#{" + refreshMethod + "}\">");
				writeLine("<a4j:attachQueue requestDelay=\"500\"/>");
				writeLine("</a4j:ajax>");
				writeLine("</h:inputText>");
				
				writeLine("<h:inputText value=\"#{" + scrollForm + ".filter." + property.name + "MaxValue}\"");
				writeLine("styleClass=\"form-control\">");
				writeLine("<a4j:ajax event=\"keyup\" render=\"resultsPanelGroup\" listener=\"#{" + refreshMethod + "}\">");
				writeLine("<a4j:attachQueue requestDelay=\"500\"/>");
				writeLine("</a4j:ajax>");
				writeLine("</h:inputText>");
				break;
			
			case LONG:
				writeLine("<h:inputText value=\"#{" + scrollForm + ".filter." + property.name + "MinValue}\"");
				writeLine("styleClass=\"form-control\">");
				writeLine("<f:convertNumber integerOnly=\"true\" pattern=\"#,##0\"/>");
				writeLine("<a4j:ajax event=\"keyup\" render=\"resultsPanelGroup\" listener=\"#{" + refreshMethod + "}\">");
				writeLine("<a4j:attachQueue requestDelay=\"500\"/>");
				writeLine("</a4j:ajax>");
				writeLine("</h:inputText>");
				
				writeLine("<h:inputText value=\"#{" + scrollForm + ".filter." + property.name + "MaxValue}\"");
				writeLine("styleClass=\"form-control\">");
				writeLine("<f:convertNumber integerOnly=\"true\" pattern=\"#,##0\"/>");
				writeLine("<a4j:ajax event=\"keyup\" render=\"resultsPanelGroup\" listener=\"#{" + refreshMethod + "}\">");
				writeLine("<a4j:attachQueue requestDelay=\"500\"/>");
				writeLine("</a4j:ajax>");
				writeLine("</h:inputText>");
				
				break;
				
			case BOOLEAN:
				
				writeLine("<h:selectOneMenu value=\"#{" + scrollForm + ".filter." + property.name + "}\" styleClass=\"form-control\">");
				writeLine("<f:selectItem itemLabel=\"\" itemValue=\"#{null}\"></f:selectItem>");
				writeLine("<f:selectItem itemLabel=\"#{i18n.trueLabel}\" itemValue=\"#{true}\"></f:selectItem>");
				writeLine("<f:selectItem itemLabel=\"#{i18n.falseLabel}\" itemValue=\"#{false}\"></f:selectItem>");
				writeLine("<a4j:ajax event=\"change\" render=\"resultsPanelGroup\" listener=\"#{" + refreshMethod + "}\">");
				writeLine("</a4j:ajax>");
				writeLine("</h:selectOneMenu>");
				
				break;
			
			default:				
	
		}
	}
	
	protected void writeFilter(ViewProperty property, Bean bean) {
		writeFilter(property, bean, null);
	}
}
