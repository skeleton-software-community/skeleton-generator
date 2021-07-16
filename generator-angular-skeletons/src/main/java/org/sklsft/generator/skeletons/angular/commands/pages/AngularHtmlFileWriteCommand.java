package org.sklsft.generator.skeletons.angular.commands.pages;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.model.metadata.DataType;
import org.sklsft.generator.model.metadata.SelectionMode;
import org.sklsft.generator.skeletons.commands.impl.typed.HtmlFileWriteCommand;

public abstract class AngularHtmlFileWriteCommand extends HtmlFileWriteCommand {

	public AngularHtmlFileWriteCommand(String folderName, String fileName) {
		super(folderName, fileName);
	}

	protected void writeListComponent(ViewProperty property, Bean bean) {
		writeLine("<ng-container matColumnDef=\"" + property.name + "\">");
		writeLine("<mat-header-cell *matHeaderCellDef>" + property.rendering + "</mat-header-cell>");
		
		switch (property.dataType) {
		case BOOLEAN:
			if (property.nullable) {
				writeLine("<mat-cell *matCellDef=\"let element\"> {{element." + property.name + "}} </mat-cell>");
			} else {
				writeLine("<mat-cell *matCellDef=\"let element\"> {{element." + property.name + "}} </mat-cell>");
			}
			break;
			
		case DATE:
			writeLine("<mat-cell *matCellDef=\"let element\"> {{element." + property.name + "}} </mat-cell>");

			break;

		case DATETIME:
			writeLine("<mat-cell *matCellDef=\"let element\"> {{element." + property.name + "}} </mat-cell>");

			break;

		case DOUBLE:
			writeLine("<mat-cell *matCellDef=\"let element\"> {{element." + property.name + "}} </mat-cell>");
			break;
			
		case BIG_DECIMAL:
			writeLine("<mat-cell *matCellDef=\"let element\"> {{element." + property.name + "}} </mat-cell>");
			break;

		case SHORT:
		case INTEGER:
		case LONG:
			writeLine("<mat-cell *matCellDef=\"let element\"> {{element." + property.name + "}} </mat-cell>");
			break;

		case STRING:
			writeLine("<mat-cell *matCellDef=\"let element\"> {{element." + property.name + "}} </mat-cell>");
			break;

		case TEXT:
			writeLine("<mat-cell *matCellDef=\"let element\"> {{element." + property.name + "}} </mat-cell>");
			break;
		}
		
		writeLine("</ng-container>");
	}
	
	
	protected void writeInput(ViewProperty property, Bean bean){
		writeInput("", property, bean);
	}
	
	protected void writeInput(String prefix, ViewProperty property, Bean bean){
		
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
				case DATE:
					writeDateInput(prefix, property, bean);
					break;
				case DATETIME:
					writeDateTimeInput(prefix, property, bean);
					break;
				case DOUBLE:
					writeDoubleInput(prefix, property, bean);
					break;
				case BIG_DECIMAL:
					writeBigDecimalInput(prefix, property, bean);
					break;
				case SHORT:
				case INTEGER:
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
        skipLine();
	}
	
	private void writeCombobox(String prefix, ViewProperty property, Bean bean){
		
		writeLine("<p>");
		writeLine("<mat-form-field appearance=\"outline\">");
		writeLine("<mat-label>" + property.rendering + "</mat-label>");
		writeLine("<input matInput placeholder=\"" + property.rendering + "\" formControlName=\"" + property.name + "\"/>");
		writeLine("</mat-form-field>");
		writeLine("</p>");
	}
	
	private void writeAutocomplete(String prefix, ViewProperty property, Bean bean){
		
		writeLine("<p>");
		writeLine("<mat-form-field appearance=\"outline\">");
		writeLine("<mat-label>" + property.rendering + "</mat-label>");
		writeLine("<input matInput placeholder=\"" + property.rendering + "\" formControlName=\"" + property.name + "\"/>");
		writeLine("</mat-form-field>");
		writeLine("</p>");
	}
	
	
	private void writeStringInput(String prefix, ViewProperty property, Bean bean){
		writeLine("<p>");
		writeLine("<mat-form-field appearance=\"outline\">");
		writeLine("<mat-label>" + property.rendering + "</mat-label>");
		writeLine("<input matInput placeholder=\"" + property.rendering + "\" formControlName=\"" + property.name + "\"/>");
		writeLine("</mat-form-field>");
		writeLine("</p>");
	}
	
	private void writeTextInput(String prefix, ViewProperty property, Bean bean){
		writeLine("<p>");
		writeLine("<mat-form-field appearance=\"outline\">");
		writeLine("<mat-label>" + property.rendering + "</mat-label>");
		writeLine("<input matInput placeholder=\"" + property.rendering + "\" formControlName=\"" + property.name + "\"/>");
		writeLine("</mat-form-field>");
		writeLine("</p>");
	}
	
	private void writeBooleanInput(String prefix, ViewProperty property, Bean bean){
		writeLine("<p>");
		writeLine("<mat-checkbox color=\"accent\" formControlName=\"" +  property.name + "\">");
		writeLine(property.rendering);
		writeLine("</mat-checkbox>");
		writeLine("</p>");
	}
	
	private void writeDoubleInput(String prefix, ViewProperty property, Bean bean){
		writeLine("<p>");
		writeLine("<mat-form-field appearance=\"outline\">");
		writeLine("<mat-label>" + property.rendering + "</mat-label>");
		writeLine("<input matInput placeholder=\"" + property.rendering + "\" formControlName=\"" + property.name + "\"/>");
		writeLine("</mat-form-field>");
		writeLine("</p>");
	}
	
	private void writeBigDecimalInput(String prefix, ViewProperty property, Bean bean){
		writeLine("<p>");
		writeLine("<mat-form-field appearance=\"outline\">");
		writeLine("<mat-label>" + property.rendering + "</mat-label>");
		writeLine("<input matInput placeholder=\"" + property.rendering + "\" formControlName=\"" + property.name + "\"/>");
		writeLine("</mat-form-field>");
		writeLine("</p>");
	}
	
	private void writeLongInput(String prefix, ViewProperty property, Bean bean){
		writeLine("<p>");
		writeLine("<mat-form-field appearance=\"outline\">");
		writeLine("<mat-label>" + property.rendering + "</mat-label>");
		writeLine("<input matInput placeholder=\"" + property.rendering + "\" formControlName=\"" + property.name + "\"/>");
		writeLine("</mat-form-field>");
		writeLine("</p>");
	}
	
	private void writeDateInput(String prefix, ViewProperty property, Bean bean){
		writeLine("<p>");
		writeLine("<mat-form-field appearance=\"outline\" floatLabel=\"always\">");
		writeLine("<mat-label>" + property.rendering + "</mat-label>");
		writeLine("<input matInput [matDatepicker]=\"picker\" placeholder=\"" + property.rendering + "\" formControlName=\"" + property.name + "\"/>");
		writeLine("<mat-datepicker-toggle matSuffix [for]=\"picker\"></mat-datepicker-toggle>");
		writeLine("<mat-datepicker #picker></mat-datepicker>");
		writeLine("</mat-form-field>");
		writeLine("</p>");
	}
	
	private void writeDateTimeInput(String prefix, ViewProperty property, Bean bean){
		writeLine("<p>");
		writeLine("<mat-form-field appearance=\"outline\">");
		writeLine("<mat-label>" + property.rendering + "</mat-label>");
		writeLine("<input matInput placeholder=\"" + property.rendering + "\" formControlName=\"" + property.name + "\"/>");
		writeLine("</mat-form-field>");
		writeLine("</p>");
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
				writeLine("<p:ajax event=\"keyup\" update=\"resultsPanelGroup, sizePanelGroup\" listener=\"#{" + refreshMethod + "}\"/>");
				writeLine("</h:inputText>");			
				break;
				
			case DATE:				
				writeLine("<p:calendar value=\"#{" + scrollForm + ".filter." + property.name + "MinValue}\"");
				writeLine("class=\"form-control date-picker\"");
				writeLine("pattern=\"yyyy-MM-dd\" mask=\"true\" navigator=\"true\">");
				writeLine("<p:ajax event=\"dateSelect\" update=\"resultsPanelGroup, sizePanelGroup\" listener=\"#{" + refreshMethod + "}\"/>");
				writeLine("</p:calendar>");
				writeLine("<p:calendar value=\"#{" + scrollForm + ".filter." + property.name + "MaxValue}\"");
				writeLine("class=\"form-control date-picker\"");
				writeLine("pattern=\"yyyy-MM-dd\" mask=\"true\" navigator=\"true\">");
				writeLine("<p:ajax event=\"dateSelect\" update=\"resultsPanelGroup, sizePanelGroup\" listener=\"#{" + refreshMethod + "}\"/>");
				writeLine("</p:calendar>");
				break;
				
			case DATETIME:				
				writeLine("<p:calendar value=\"#{" + scrollForm + ".filter." + property.name + "MinValue}\"");
				writeLine("class=\"form-control date-picker\"");
				writeLine("pattern=\"yyyy-MM-dd HH:mm:ss\" mask=\"true\" navigator=\"true\">");
				writeLine("<p:ajax event=\"dateSelect\" update=\"resultsPanelGroup, sizePanelGroup\" listener=\"#{" + refreshMethod + "}\"/>");
				writeLine("</p:calendar>");
				writeLine("<p:calendar value=\"#{" + scrollForm + ".filter." + property.name + "MaxValue}\"");
				writeLine("class=\"form-control date-picker\"");
				writeLine("pattern=\"yyyy-MM-dd HH:mm:ss\" mask=\"true\" navigator=\"true\">");
				writeLine("<p:ajax event=\"dateSelect\" update=\"resultsPanelGroup, sizePanelGroup\" listener=\"#{" + refreshMethod + "}\"/>");
				writeLine("</p:calendar>");
				break;
				
			case DOUBLE:
				
				writeLine("<h:inputText value=\"#{" + scrollForm + ".filter." + property.name + "MinValue}\"");
				writeLine("styleClass=\"form-control\">");
				writeLine("<p:ajax event=\"keyup\" update=\"resultsPanelGroup, sizePanelGroup\" listener=\"#{" + refreshMethod + "}\"/>");
				writeLine("</h:inputText>");
				
				writeLine("<h:inputText value=\"#{" + scrollForm + ".filter." + property.name + "MaxValue}\"");
				writeLine("styleClass=\"form-control\">");
				writeLine("<p:ajax event=\"keyup\" update=\"resultsPanelGroup, sizePanelGroup\" listener=\"#{" + refreshMethod + "}\"/>");
				writeLine("</h:inputText>");
				break;
				
			case BIG_DECIMAL:
				
				writeLine("<h:inputText value=\"#{" + scrollForm + ".filter." + property.name + "MinValue}\"");
				writeLine("styleClass=\"form-control\">");
				writeLine("<p:ajax event=\"keyup\" update=\"resultsPanelGroup, sizePanelGroup\" listener=\"#{" + refreshMethod + "}\"/>");
				writeLine("</h:inputText>");
				
				writeLine("<h:inputText value=\"#{" + scrollForm + ".filter." + property.name + "MaxValue}\"");
				writeLine("styleClass=\"form-control\">");
				writeLine("<p:ajax event=\"keyup\" update=\"resultsPanelGroup, sizePanelGroup\" listener=\"#{" + refreshMethod + "}\"/>");
				writeLine("</h:inputText>");
				break;
			
			case SHORT:
			case INTEGER:
			case LONG:
				writeLine("<h:inputText value=\"#{" + scrollForm + ".filter." + property.name + "MinValue}\"");
				writeLine("styleClass=\"form-control\">");
				writeLine("<f:convertNumber integerOnly=\"true\" pattern=\"#,##0\"/>");
				writeLine("<p:ajax event=\"keyup\" update=\"resultsPanelGroup, sizePanelGroup\" listener=\"#{" + refreshMethod + "}\"/>");
				writeLine("</h:inputText>");
				
				writeLine("<h:inputText value=\"#{" + scrollForm + ".filter." + property.name + "MaxValue}\"");
				writeLine("styleClass=\"form-control\">");
				writeLine("<f:convertNumber integerOnly=\"true\" pattern=\"#,##0\"/>");
				writeLine("<p:ajax event=\"keyup\" update=\"resultsPanelGroup, sizePanelGroup\" listener=\"#{" + refreshMethod + "}\"/>");
				writeLine("</h:inputText>");
				
				break;
				
			case BOOLEAN:
				
				writeLine("<h:selectOneMenu value=\"#{" + scrollForm + ".filter." + property.name + "}\" styleClass=\"form-control\">");
				writeLine("<f:selectItem itemLabel=\"\" itemValue=\"#{null}\"></f:selectItem>");
				writeLine("<f:selectItem itemLabel=\"#{i18n.trueLabel}\" itemValue=\"#{true}\"></f:selectItem>");
				writeLine("<f:selectItem itemLabel=\"#{i18n.falseLabel}\" itemValue=\"#{false}\"></f:selectItem>");
				writeLine("<p:ajax event=\"change\" update=\"resultsPanelGroup, sizePanelGroup\" listener=\"#{" + refreshMethod + "}\"/>");
				writeLine("</h:selectOneMenu>");
				
				break;

		}
	}
	
	protected void writeFilter(ViewProperty property, Bean bean) {
		writeFilter(property, bean, null);
	}
}
