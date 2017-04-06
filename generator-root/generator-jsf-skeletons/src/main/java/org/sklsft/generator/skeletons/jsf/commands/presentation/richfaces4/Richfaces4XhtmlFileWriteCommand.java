package org.sklsft.generator.skeletons.jsf.commands.presentation.richfaces4;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.Property;
import org.sklsft.generator.model.metadata.DataType;
import org.sklsft.generator.skeletons.commands.impl.typed.XhtmlFileWriteCommand;

public abstract class Richfaces4XhtmlFileWriteCommand extends XhtmlFileWriteCommand {

	public Richfaces4XhtmlFileWriteCommand(String folderName, String fileName) {
		super(folderName, fileName);
	}

	protected void writeListComponent(Property property, Bean bean) {
		switch (property.dataType) {
		case BOOLEAN:
			if (property.nullable) {
				writeLine("<h:outputText value=" + CHAR_34 + "#{"
						+ bean.objectName + "." + property.name + "}"
						+ CHAR_34 + "/>");
			} else {
				writeLine("<h:selectBooleanCheckbox value=" + CHAR_34 + "#{"
						+ bean.objectName + "." + property.name + "}"
						+ CHAR_34 + " disabled=" + CHAR_34 + "true"
						+ CHAR_34 + "/>");
			}
			break;

		case DATETIME:
			writeLine("<h:outputText value=" + CHAR_34 + "#{"
					+ bean.objectName + "." + property.name + "}" + CHAR_34
					+ ">");

			switch (property.format) {
			case DATE:
				writeLine("<f:convertDateTime type=" + CHAR_34 + "date"
						+ CHAR_34 + " dateStyle=" + CHAR_34 + "medium"
						+ CHAR_34 + "/>");
				break;

			default:
				writeLine("<f:convertDateTime type=" + CHAR_34 + "both"
						+ CHAR_34 + " dateStyle=" + CHAR_34 + "medium"
						+ CHAR_34 + "/>");
				break;
			}
			writeLine("</h:outputText>");

			break;

		case DOUBLE:
			writeLine("<h:outputText value=" + CHAR_34 + "#{"
					+ bean.objectName + "." + property.name + "}" + CHAR_34
					+ ">");

			switch (property.format) {
			case TWO_DECIMALS:
				writeLine("<f:convertNumber pattern=" + CHAR_34 + "#,##0.00"
						+ CHAR_34 + "/>");
				break;

			case FOUR_DECIMALS:
				writeLine("<f:convertNumber pattern=" + CHAR_34
						+ "#,##0.0000" + CHAR_34 + "/>");
				break;

			default:
				writeLine("<f:convertNumber pattern=" + CHAR_34
						+ "#,##0.########" + CHAR_34 + "/>");
				break;
			}
			writeLine("</h:outputText>");
			break;

		case LONG:
			writeLine("<h:outputText value=" + CHAR_34 + "#{"
					+ bean.objectName + "." + property.name + "}" + CHAR_34
					+ ">");
			writeLine("<f:convertNumber pattern=" + CHAR_34 + "#,##0"
					+ CHAR_34 + "/>");
			writeLine("</h:outputText>");
			break;

		case STRING:
			writeLine("<h:outputText value=" + CHAR_34 + "#{"
					+ bean.objectName + "." + property.name + "}" + CHAR_34
					+ "/>");
			break;

		case TEXT:
			writeLine("<pre class=" + CHAR_34 + "truncated-text" + CHAR_34 + ">");
			writeLine("#{" + bean.objectName + "." + property.name + "}");
			writeLine("</pre>");
			writeLine("<rich:tooltip>");
			writeLine("<pre>");
			writeLine("#{" + bean.objectName + "." + property.name + "}");
			writeLine("</pre>");
			writeLine("</rich:tooltip>");
			break;
		}
	}
	
	
	protected void writeInput(Property property, Bean bean){
		writeInput("", property, bean);
	}
	
	protected void writeInput(String prefix, Property property, Bean bean){
		
		writeLine("<div class=" + CHAR_34 + "col-xs-12" + CHAR_34 + ">");
		
		if (!property.dataType.equals(DataType.BOOLEAN)) {
            writeLine("<label>#{i18n." + bean.objectName + property.capName + "}</label>");
		}
		
		if (property.comboBoxBean != null) {
			writeCombobox(prefix, property, bean);
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
			writeLine("<h:message for=" + CHAR_34 + prefix + bean.objectName + property.capName + CHAR_34 + " styleClass=" + CHAR_34 + "detail-error-message" + CHAR_34 + "/>");
		}
		
		writeLine("</div>");
        skipLine();
	}
	
	private void writeCombobox(String prefix, Property property, Bean bean){
		
		write("<h:selectOneMenu id=" + CHAR_34 + prefix + bean.objectName
				+ property.capName + CHAR_34 + " styleClass=" + CHAR_34
				+ "form-control" + CHAR_34 + " value=" + CHAR_34
				+ "#{form." + property.name + "}"
				+ CHAR_34);
				
		if (!property.editable) {
			skipLine();
			write(" disabled=" + CHAR_34 + "true" + CHAR_34);
		}
		
		writeLine(">");

		switch (property.dataType) {
		case DATETIME:
			switch (property.format) {
			case DATE:
				writeLine("<f:convertDateTime type=" + CHAR_34
						+ "date" + CHAR_34 + " dateStyle="
						+ CHAR_34 + "long" + CHAR_34 + "/>");
				break;

			default:
				writeLine("<f:convertDateTime type=" + CHAR_34
						+ "both" + CHAR_34 + " dateStyle="
						+ CHAR_34 + "long" + CHAR_34 + "/>");
				break;

			}
			break;

		case DOUBLE:
			switch (property.format) {
			case TWO_DECIMALS:
				writeLine("<f:convertNumber pattern=" + CHAR_34
						+ "#,##0.00" + CHAR_34 + "/>");
				break;

			case FOUR_DECIMALS:
				writeLine("<f:convertNumber pattern=" + CHAR_34
						+ "#,##0.0000" + CHAR_34 + "/>");
				break;

			default:
				writeLine("<f:convertNumber pattern=" + CHAR_34
						+ "#,##0.########" + CHAR_34 + "/>");
				break;
			}
			break;

		case LONG:
			writeLine("<f:convertNumber integerOnly=" + CHAR_34 + "true" + CHAR_34 + " pattern=" + CHAR_34 + "#,##0"
					+ CHAR_34 + "/>");
			break;

		default:
			break;
		}

		writeLine("<f:selectItems value=" + CHAR_34
				+ "#{commonView."
				+ property.comboBoxBean.objectName
				+ "Options}" + CHAR_34 + "/>");
		writeLine("</h:selectOneMenu>");
	}
	
	
	private void writeStringInput(String prefix, Property property, Bean bean){
		write("<h:inputText id=" + CHAR_34 + prefix + bean.objectName
				+ property.capName + CHAR_34 + " styleClass="
				+ CHAR_34 + "form-control" + CHAR_34 + " value="
				+ CHAR_34 + "#{form."
				+ property.name + "}" + CHAR_34);
		
		if (!property.editable) {
			skipLine();
			write("disabled=" + CHAR_34 + "true" + CHAR_34);
		}
		writeLine("/>");
	}
	
	private void writeTextInput(String prefix, Property property, Bean bean){
		write("<h:inputTextarea id=" + CHAR_34 + prefix + bean.objectName
				+ property.capName + CHAR_34 + " styleClass="
				+ CHAR_34 + "form-control" + CHAR_34 + " rows="
				+ CHAR_34 + "10" + CHAR_34 + " value="
				+ CHAR_34 + "#{form."
				+ property.name + "}" + CHAR_34);
		
		if (!property.editable) {
			skipLine();
			write("disabled=" + CHAR_34 + "true" + CHAR_34);
		}
		writeLine("/>");
	}
	
	private void writeBooleanInput(String prefix, Property property, Bean bean){
		writeLine("<div class=" + CHAR_34 + "checkbox" + CHAR_34 + ">");
		writeLine("<label>");
		writeLine("<h:selectBooleanCheckbox id=" + CHAR_34 + prefix 
				+ bean.objectName + property.capName
				+ CHAR_34 + " value=" + CHAR_34 + "#{form." + property.name + "}"
				+ CHAR_34);
		if (property.editable) {
			writeLine("readonly=" + CHAR_34 + "false" + CHAR_34
					+ " disabled=" + CHAR_34 + "false"
					+ CHAR_34 + "/>");
			
		} else {
			writeLine("readonly=" + CHAR_34 + "true" + CHAR_34
					+ " disabled=" + CHAR_34 + "true"
					+ CHAR_34 + "/>");
		}
		writeLine("#{i18n." + bean.objectName + property.capName + "}");
		writeLine("</label>");
		writeLine("</div>");
	}
	
	private void writeDoubleInput(String prefix, Property property, Bean bean){
		write("<h:inputText id=" + CHAR_34 + prefix + bean.objectName
				+ property.capName + CHAR_34 + " styleClass="
				+ CHAR_34 + "form-control" + CHAR_34 + " value="
				+ CHAR_34 + "#{form."
				+ property.name + "}" + CHAR_34);
		
		if (!property.editable) {
			skipLine();
			write("disabled=" + CHAR_34 + "true" + CHAR_34);
		}
		writeLine(">");

		switch (property.format) {
			case TWO_DECIMALS:
				writeLine("<f:convertNumber pattern=" + CHAR_34
						+ "#,##0.00" + CHAR_34 + "/>");
				break;
	
			case FOUR_DECIMALS:
				writeLine("<f:convertNumber pattern=" + CHAR_34
						+ "#,##0.0000" + CHAR_34 + "/>");
				break;
	
			default:
				writeLine("<f:convertNumber pattern=" + CHAR_34
						+ "#,##0.########" + CHAR_34 + "/>");
				break;
		}
		writeLine("</h:inputText>");
	}
	
	private void writeLongInput(String prefix, Property property, Bean bean){
		write("<h:inputText id=" + CHAR_34 + prefix + bean.objectName
				+ property.capName + CHAR_34 + " styleClass="
				+ CHAR_34 + "form-control" + CHAR_34 + " value="
				+ CHAR_34 + "#{form."
				+ property.name + "}" + CHAR_34);
				
		if (!property.editable) {
			skipLine();
			write("disabled=" + CHAR_34 + "true" + CHAR_34);
		}
		
		writeLine(">");
		writeLine("<f:convertNumber integerOnly=" + CHAR_34 + "true" + CHAR_34 + " pattern=" + CHAR_34 + "#,##0"
				+ CHAR_34 + "/>");
		writeLine("</h:inputText>");
	}
	
	private void writeDateInput(String prefix, Property property, Bean bean){
		writeLine("<rich:calendar id=" + CHAR_34 + prefix 
				+ bean.objectName + property.capName + CHAR_34
				+ " inputClass=" + CHAR_34 + "form-control"
				+ CHAR_34 + " value=" + CHAR_34 + "#{form." + property.name + "}"
				+ CHAR_34);

		switch (property.format) {
		case DATE:
			write(" datePattern=" + CHAR_34 + "dd MMMM yyyy"
					+ CHAR_34);
			break;

		default:
			write(" datePattern=" + CHAR_34
					+ "dd MMMM yyyy HH:mm" + CHAR_34);
			break;
		}
				
		if (!property.editable) {
			skipLine();
			write("disabled=" + CHAR_34 + "true" + CHAR_34);
		}
		writeLine("/>");
		
	}
	
	
	protected void writeFilter(Property property, Bean bean) {
		switch (property.dataType) {
		case STRING:
	
			writeLine("<h:inputText id=" + CHAR_34 + bean.objectName
					+ property.capName + "Filter" + CHAR_34);
			writeLine("value=" + CHAR_34 + "#{" + bean.listViewObjectName + "." + bean.basicViewBean.filterObjectName + "." + property.name + "}" + CHAR_34);
			writeLine("styleClass=" + CHAR_34 + "dataTableFilter" + CHAR_34 + ">");
			writeLine("<a4j:ajax event=" + CHAR_34 + "keyup" + CHAR_34 + " render=" + CHAR_34 + bean.objectName + "List, " + bean.objectName + "Scroller" + CHAR_34);
			writeLine("oncomplete=" + CHAR_34 + "setCaretToEnd(event);" + CHAR_34 + " >");
			writeLine("<a4j:attachQueue requestDelay=" + CHAR_34 + "500" + CHAR_34 + " />");
			writeLine("</a4j:ajax>");
			writeLine("</h:inputText>");
			break;
			
		
		default:
			
			writeLine("<h:inputText id=" + CHAR_34 + bean.objectName
					+ property.capName + "Filter" + CHAR_34);
			writeLine("value=" + CHAR_34 + "#{" + bean.listViewObjectName + "." + bean.basicViewBean.filterObjectName + "." + property.name + "}" + CHAR_34);
			writeLine("styleClass=" + CHAR_34 + "dataTableFilter" + CHAR_34 + ">");
			writeLine("<a4j:ajax event=" + CHAR_34 + "keyup" + CHAR_34 + " render=" + CHAR_34 + bean.objectName + "List, " + bean.objectName + "Scroller" + CHAR_34);
			writeLine("oncomplete=" + CHAR_34 + "setCaretToEnd(event);" + CHAR_34 + " >");
			writeLine("<a4j:attachQueue requestDelay=" + CHAR_34 + "500" + CHAR_34 + " />");
			writeLine("</a4j:ajax>");
			writeLine("</h:inputText>");
			break;
			
	
		}
	}
	
	protected void writeFilter(Property property, Bean currentBean, Bean parentBean) {
		switch (property.dataType) {
		case STRING:
	
			writeLine("<h:inputText id=" + CHAR_34 + currentBean.objectName
					+ property.capName + "Filter" + CHAR_34);
			writeLine("value=" + CHAR_34 + "#{" + parentBean.detailViewObjectName + "." + currentBean.basicViewBean.filterObjectName + "." + property.name + "}" + CHAR_34);
			writeLine("styleClass=" + CHAR_34 + "dataTableFilter" + CHAR_34 + ">");
			writeLine("<a4j:ajax event=" + CHAR_34 + "keyup" + CHAR_34 + " render=" + CHAR_34 + currentBean.objectName + "List, " + currentBean.objectName + "Scroller" + CHAR_34);
			writeLine("oncomplete=" + CHAR_34 + "setCaretToEnd(event);" + CHAR_34 + " >");
			writeLine("<a4j:attachQueue requestDelay=" + CHAR_34 + "500" + CHAR_34 + " />");
			writeLine("</a4j:ajax>");			
			writeLine("</h:inputText>");
			break;
			
		
		default:
			
			writeLine("<h:inputText id=" + CHAR_34 + currentBean.objectName
					+ property.capName + "Filter" + CHAR_34);
			writeLine("value=" + CHAR_34 + "#{" + parentBean.detailViewObjectName + "." + currentBean.basicViewBean.filterObjectName + "." + property.name + "}" + CHAR_34);
			writeLine("styleClass=" + CHAR_34 + "dataTableFilter" + CHAR_34 + ">");
			writeLine("<a4j:ajax event=" + CHAR_34 + "keyup" + CHAR_34 + " render=" + CHAR_34 + currentBean.objectName + "List, " + currentBean.objectName + "Scroller" + CHAR_34);
			writeLine("oncomplete=" + CHAR_34 + "setCaretToEnd(event);" + CHAR_34 + " >");
			writeLine("<a4j:attachQueue requestDelay=" + CHAR_34 + "500" + CHAR_34 + " />");
			writeLine("</a4j:ajax>");
			writeLine("</h:inputText>");
			break;
			
	
		}
	}
	
	public void writeFilterExpression(Property property, Bean bean) {
		
		switch (property.dataType) {
		case STRING:
	
			writeLine("filterType=" + CHAR_34 + "custom" + CHAR_34  + " filterExpression=" + CHAR_34 + "#{fn:containsIgnoreCase(" + bean.objectName + "." + property.name + ", " + bean.listViewObjectName + "." + bean.basicViewBean.filterObjectName + "." + property.name + ")}" + CHAR_34 + ">");
			break;
		
		
		case TEXT:
			
			writeLine("filterType=" + CHAR_34 + "custom" + CHAR_34  + " filterExpression=" + CHAR_34 + "#{fn:containsIgnoreCase(" + bean.objectName + "." + property.name + ", " + bean.listViewObjectName + "." + bean.basicViewBean.filterObjectName + "." + property.name + ")}" + CHAR_34 + ">");
			break;
			
		
		case DATETIME:
			
			writeLine("filterType=" + CHAR_34 + "custom" + CHAR_34  + " filterExpression=" + CHAR_34 + "#{customFilter.filterDate(" + bean.objectName + "." + property.name + ", " + bean.listViewObjectName + "." + bean.basicViewBean.filterObjectName + "." + property.name + ")}" + CHAR_34 + ">");
			break;
			
			
		case BOOLEAN:
			
			writeLine("filterType=" + CHAR_34 + "custom" + CHAR_34  + " filterExpression=" + CHAR_34 + "#{customFilter.filterBoolean(" + bean.objectName + "." + property.name + ", " + bean.listViewObjectName + "." + bean.basicViewBean.filterObjectName + "." + property.name + ")}" + CHAR_34 + ">");
			break;
			
		
		default:
			
			writeLine("filterType=" + CHAR_34 + "custom" + CHAR_34  + " filterExpression=" + CHAR_34 + "#{fn:containsIgnoreCase(" + bean.objectName + "." + property.name + ", " + bean.listViewObjectName + "." + bean.basicViewBean.filterObjectName + "." + property.name + ")}" + CHAR_34 + ">");
			break;
		}
	}
	
	public void writeFilterExpression(Property property, Bean currentBean, Bean parentBean) {
		
		switch (property.dataType) {
		case STRING:
	
			writeLine("filterType=" + CHAR_34 + "custom" + CHAR_34  + " filterExpression=" + CHAR_34 + "#{fn:containsIgnoreCase(" + currentBean.objectName + "." + property.name + ", " + parentBean.detailViewObjectName + "." + currentBean.basicViewBean.filterObjectName + "." + property.name + ")}" + CHAR_34 + ">");
			break;
		
		
		case TEXT:
			
			writeLine("filterType=" + CHAR_34 + "custom" + CHAR_34  + " filterExpression=" + CHAR_34 + "#{fn:containsIgnoreCase(" + currentBean.objectName + "." + property.name + ", " + parentBean.detailViewObjectName + "." + currentBean.basicViewBean.filterObjectName + "." + property.name + ")}" + CHAR_34 + ">");
			break;
			
		
		case DATETIME:
			
			writeLine("filterType=" + CHAR_34 + "custom" + CHAR_34  + " filterExpression=" + CHAR_34 + "#{customFilter.filterDate(" + currentBean.objectName + "." + property.name + ", " + parentBean.detailViewObjectName + "." + currentBean.basicViewBean.filterObjectName + "." + property.name + ")}" + CHAR_34 + ">");
			break;
			
			
		case BOOLEAN:
			
			writeLine("filterType=" + CHAR_34 + "custom" + CHAR_34  + " filterExpression=" + CHAR_34 + "#{customFilter.filterBoolean(" + currentBean.objectName + "." + property.name + ", " + parentBean.detailViewObjectName + "." + currentBean.basicViewBean.filterObjectName + "." + property.name + ")}" + CHAR_34 + ">");
			break;
			
		
		default:
			
			writeLine("filterType=" + CHAR_34 + "custom" + CHAR_34  + " filterExpression=" + CHAR_34 + "#{fn:containsIgnoreCase(" + currentBean.objectName + "." + property.name + ", " + parentBean.detailViewObjectName + "." + currentBean.basicViewBean.filterObjectName + "." + property.name + ")}" + CHAR_34 + ">");
			break;
		}
	}
}
