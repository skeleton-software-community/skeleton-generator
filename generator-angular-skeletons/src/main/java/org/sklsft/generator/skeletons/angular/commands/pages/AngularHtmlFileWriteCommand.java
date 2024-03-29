package org.sklsft.generator.skeletons.angular.commands.pages;

import org.sklsft.generator.model.domain.ui.FilterProperty;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.model.metadata.SelectionMode;
import org.sklsft.generator.skeletons.commands.impl.typed.HtmlFileWriteCommand;

public abstract class AngularHtmlFileWriteCommand extends HtmlFileWriteCommand {

	public AngularHtmlFileWriteCommand(String folderName, String fileName) {
		super(folderName, fileName);
	}

	protected void writeListComponent(ViewProperty property) {
		writeLine("<ng-container matColumnDef=\"" + property.name + "\">");
		writeLine("<mat-header-cell *matHeaderCellDef mat-sort-header class=\"table-header\">" + property.rendering + "</mat-header-cell>");
		
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
			writeLine("<mat-cell *matCellDef=\"let element\"> {{element." + property.name + "| date:'yyyy-MM-ddTHH:mm:ssZ':'GMT'}} </mat-cell>");

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
	
	
	protected void writeInput(ViewProperty property){
		
		if (property.selectableBean != null) {
			if (property.selectableBean.selectionBehavior.selectionMode.equals(SelectionMode.DROPDOWN_OPTIONS)) {
				writeCombobox(property);
			} else {
				writeAutocomplete(property);
			}
		} else {
		
			switch (property.dataType) {
				case BOOLEAN:
					writeBooleanInput(property);
					break;
				case DATE:
					writeDateInput(property);
					break;
				case DATETIME:
					writeDateTimeInput(property);
					break;
				case DOUBLE:
					writeBigDecimalInput(property);
					break;
				case BIG_DECIMAL:
					writeBigDecimalInput(property);
					break;
				case SHORT:
				case INTEGER:
				case LONG:
					writeLongInput(property);
					break;
				case STRING:
					writeStringInput(property);
					break;
				case TEXT:
					writeTextInput(property);
					break;
			}
		}
        skipLine();
	}
	
	private void writeCombobox(ViewProperty property){
		
		writeLine("<p>");
		writeLine("<mat-form-field appearance=\"outline\" floatLabel=\"always\">");
		writeLine("<mat-label>" + property.rendering + "</mat-label>");
		if (!property.editable) {
			writeLine("<input matInput placeholder=\"" + property.rendering + "\" formControlName=\"" + property.name + "\"/>");
		} else {
			writeLine("<mat-select placeholder=\"" + property.rendering + "\" formControlName=\"" + property.name + "\" >");
			writeLine("<mat-option [value]=\"null\"></mat-option>");
			writeLine("<mat-option *ngFor=\"let option of " + property.name + "Options\" [value]=\"option.key\">{{option.key}}</mat-option>");
			writeLine("</mat-select>");
		}
		writeLine("</mat-form-field>");
		writeLine("</p>");
	}
	
	private void writeAutocomplete(ViewProperty property){
		
		writeLine("<p>");
		writeLine("<mat-form-field appearance=\"outline\" floatLabel=\"always\" class=\"autocomplete\">");
		writeLine("<mat-label>" + property.rendering + "</mat-label>");
		if (!property.editable) {
			writeLine("<input matInput placeholder=\"" + property.rendering + "\" formControlName=\"" + property.name + "\"/>");
		} else {
			writeLine("<input matInput placeholder=\"" + property.rendering + "\" formControlName=\"" + property.name + "\" [matAutocomplete]=\"" + property.name + "AutoComplete\"/>");
			writeLine("<mat-autocomplete #" + property.name + "AutoComplete=\"matAutocomplete\">");
			writeLine("<mat-option *ngFor=\"let option of " + property.name + "Options | async\" [value]=\"option.key\">{{option.key}}</mat-option>");
			writeLine("</mat-autocomplete>");
		}
		writeLine("</mat-form-field>");
		writeLine("</p>");
	}
	
	
	private void writeStringInput(ViewProperty property){
		writeLine("<p>");
		writeLine("<mat-form-field appearance=\"outline\" floatLabel=\"always\">");
		writeLine("<mat-label>" + property.rendering + "</mat-label>");
		writeLine("<input matInput placeholder=\"" + property.rendering + "\" formControlName=\"" + property.name + "\"/>");
		writeLine("</mat-form-field>");
		writeLine("</p>");
	}
	
	private void writeTextInput(ViewProperty property){
		writeLine("<p>");
		writeLine("<mat-form-field appearance=\"outline\" floatLabel=\"always\">");
		writeLine("<mat-label>" + property.rendering + "</mat-label>");
		writeLine("<textarea matInput placeholder=\"" + property.rendering + "\" formControlName=\"" + property.name + "\" cdkTextareaAutosize></textarea>");
		writeLine("</mat-form-field>");
		writeLine("</p>");
	}
	
	private void writeBooleanInput(ViewProperty property){
		if (!property.nullable) {
			writeLine("<p>");
			writeLine("<mat-checkbox color=\"primary\" formControlName=\"" +  property.name + "\">");
			writeLine(property.rendering);
			writeLine("</mat-checkbox>");
			writeLine("</p>");
		} else {
			writeLine("<p>");
			writeLine("<mat-form-field appearance=\"outline\" floatLabel=\"always\">");
			writeLine("<mat-label>" + property.rendering + "</mat-label>");
			if (!property.editable) {
				writeLine("<input matInput placeholder=\"" + property.rendering + "\" formControlName=\"" + property.name + "\"/>");
			} else {
				writeLine("<mat-select placeholder=\"" + property.rendering + "\" formControlName=\"" + property.name + "\">");
				writeLine("<mat-option [value]=\"\"></mat-option>");
				writeLine("<mat-option [value]=\"true\">true</mat-option>");
				writeLine("<mat-option [value]=\"false\">false</mat-option>");
				writeLine("</mat-select>");
			}
			writeLine("</mat-form-field>");
			writeLine("<p>");
		}
	}
	
	private void writeBigDecimalInput(ViewProperty property){
		writeLine("<p>");
		writeLine("<mat-form-field appearance=\"outline\" floatLabel=\"always\">");
		writeLine("<mat-label>" + property.rendering + "</mat-label>");
		writeLine("<input matInput type=\"decimal\" placeholder=\"" + property.rendering + "\" formControlName=\"" + property.name + "\"/>");
		writeLine("</mat-form-field>");
		writeLine("</p>");
	}
	
	private void writeLongInput(ViewProperty property){
		writeLine("<p>");
		writeLine("<mat-form-field appearance=\"outline\" floatLabel=\"always\">");
		writeLine("<mat-label>" + property.rendering + "</mat-label>");
		writeLine("<input type=\"number\" matInput placeholder=\"" + property.rendering + "\" formControlName=\"" + property.name + "\"/>");
		writeLine("</mat-form-field>");
		writeLine("</p>");
	}
	
	private void writeDateInput(ViewProperty property){
		writeLine("<p>");
		writeLine("<mat-form-field appearance=\"outline\" floatLabel=\"always\">");
		writeLine("<mat-label>" + property.rendering + "</mat-label>");
		if (!property.editable) {
			writeLine("<input matInput placeholder=\"yyyy-MM-dd\" formControlName=\"" + property.name + "\"/>");
		} else {
			writeLine("<input matInput [matDatepicker]=\"" + property.name + "DatePicker\" placeholder=\"yyyy-MM-dd\" formControlName=\"" + property.name + "\"/>");
			writeLine("<mat-datepicker-toggle matSuffix [for]=\"" + property.name + "DatePicker\"></mat-datepicker-toggle>");
			writeLine("<mat-datepicker #" + property.name + "DatePicker></mat-datepicker>");
		}
		writeLine("</mat-form-field>");
		writeLine("</p>");
	}
	
	private void writeDateTimeInput(ViewProperty property){
		writeLine("<p>");
		writeLine("<mat-form-field appearance=\"outline\" floatLabel=\"always\">");
		writeLine("<mat-label>" + property.rendering + "</mat-label>");
		writeLine("<input matInput placeholder=\"yyyy-MM-ddTHH:mm:ssZ\" formControlName=\"" + property.name + "\"/>");
		writeLine("</mat-form-field>");
		writeLine("</p>");
	}
	
	
	protected void writeFilter(FilterProperty property) {
		
		switch (property.dataType) {
			case STRING:
			case TEXT:
				writeLine("<mat-form-field appearance=\"outline\" floatLabel=\"always\">");
				writeLine("<mat-label>" + property.rendering + "</mat-label>");
				writeLine("<input matInput placeholder=\"" + property.rendering + "\" formControlName=\"" + property.name + "\"/>");
				writeLine("</mat-form-field>");	
				break;
				
			case DATE:				
				writeLine("<mat-form-field appearance=\"outline\" floatLabel=\"always\">");
				writeLine("<mat-label>" + property.rendering + "</mat-label>");
				writeLine("<input matInput [matDatepicker]=\"" + property.name + "DatePicker\" placeholder=\"yyyy-MM-dd\" formControlName=\"" + property.name + "\"/>");
				writeLine("<mat-datepicker-toggle matSuffix [for]=\"" + property.name + "DatePicker\"></mat-datepicker-toggle>");
				writeLine("<mat-datepicker #" + property.name + "DatePicker></mat-datepicker>");
				writeLine("</mat-form-field>");
				break;
				
			case DATETIME:				
				writeLine("<mat-form-field appearance=\"outline\" floatLabel=\"always\">");
				writeLine("<mat-label>" + property.rendering + "</mat-label>");
				writeLine("<input matInput placeholder=\"yyyy-MM-ddTHH:mm:ssZ\" formControlName=\"" + property.name + "\"/>");
				writeLine("</mat-form-field>");
				break;
				
			case DOUBLE:
			case BIG_DECIMAL:
				writeLine("<mat-form-field appearance=\"outline\" floatLabel=\"always\">");
				writeLine("<mat-label>" + property.rendering + "</mat-label>");
				writeLine("<input type=\"number\" matInput placeholder=\"" + property.rendering + "\" formControlName=\"" + property.name + "\"/>");
				writeLine("</mat-form-field>");
				break;
			
			case SHORT:
			case INTEGER:
			case LONG:
				writeLine("<mat-form-field appearance=\"outline\" floatLabel=\"always\">");
				writeLine("<mat-label>" + property.rendering + "</mat-label>");
				writeLine("<input type=\"number\" matInput placeholder=\"" + property.rendering + "\" formControlName=\"" + property.name + "\"/>");
				writeLine("</mat-form-field>");
				break;
				
			case BOOLEAN:
				writeLine("<mat-form-field appearance=\"outline\" floatLabel=\"always\">");
				writeLine("<mat-label>" + property.rendering + "</mat-label>");
				writeLine("<mat-select placeholder=\"" + property.rendering + "\" formControlName=\"" + property.name + "\" >");
				writeLine("<mat-option [value]=\"\"></mat-option>");
				writeLine("<mat-option [value]=\"true\">true</mat-option>");
				writeLine("<mat-option [value]=\"false\">false</mat-option>");
				writeLine("</mat-select>");
				writeLine("</mat-form-field>");
				break;

		}
	}
}
