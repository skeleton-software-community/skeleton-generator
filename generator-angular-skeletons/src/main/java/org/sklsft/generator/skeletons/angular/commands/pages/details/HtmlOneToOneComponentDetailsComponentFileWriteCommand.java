package org.sklsft.generator.skeletons.angular.commands.pages.details;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.skeletons.angular.commands.pages.AngularHtmlFileWriteCommand;


public class HtmlOneToOneComponentDetailsComponentFileWriteCommand extends AngularHtmlFileWriteCommand {

	private OneToOneComponent oneToOneComponent;
	private Bean parentBean;
	private Bean referenceBean;
	
	/*
	 * constructor
	 */
	public HtmlOneToOneComponentDetailsComponentFileWriteCommand(OneToOneComponent oneToOneComponent) {
        
		super(oneToOneComponent.parentBean.myPackage.model.project.workspaceFolder + File.separator + oneToOneComponent.parentBean.myPackage.model.tsUiArtefactName + File.separator + oneToOneComponent.parentBean.myPackage.tsComponentsPath + File.separator + oneToOneComponent.parentBean.urlPiece + File.separator + oneToOneComponent.referenceBean.urlPiece + File.separator + "details", oneToOneComponent.referenceBean.urlPiece + "-details.component");
		
		this.oneToOneComponent = oneToOneComponent;
		this.parentBean = oneToOneComponent.parentBean;
		this.referenceBean = oneToOneComponent.referenceBean;
		
	}
	
	
	
	@Override
	protected void writeContent() throws IOException {

        
		writeLine("<!-- -->");
		writeLine("<!-- auto generated one to one component details component html file -->");
		writeLine("<!-- write modifications between specific code marks -->");
		writeLine("<!-- processed by skeleton generator -->");
		writeLine("<!-- -->");
		skipLine();
		
		writeLine("<app-" + parentBean.urlPiece + "-menu  #menu [activePath]=\"activePath\" [id]=\"id\"></app-" + parentBean.urlPiece + "-menu>");
		skipLine();
		
		writeLine("<h2>");
		writeLine(referenceBean.detailRendering);
		writeLine("</h2>");
		skipLine();
		
		writeLine("<div class=\"details-form\" >");
		writeLine("<form [formGroup]=\"form\" (ngSubmit)=\"saveOrUpdate()\">");
		
		for (ViewProperty property:referenceBean.formBean.properties) {
			writeInput(property);
		}
		
		writeLine("<mat-dialog-actions>");
		if (referenceBean.createEnabled) {
			writeLine("<button mat-raised-button *ngIf=\"view.id == null\" color=\"primary\" type=\"submit\" [disabled]=\"form.invalid\">Save</button>");
		}
		if (referenceBean.updateEnabled ) {
			writeLine("<button mat-raised-button *ngIf=\"view.id != null && view.canUpdate\" color=\"primary\" type=\"submit\" [disabled]=\"form.invalid\">Update</button>");
		}
		if (referenceBean.deleteEnabled ) {
			writeLine("<button mat-raised-button *ngIf=\"view.id != null && view.canDelete\" color=\"warn\" type=\"button\" (click)=\"delete()\">Delete</button>");
		}
		writeLine("</mat-dialog-actions>");
		writeLine("</form>");
		
        writeNotOverridableContent();
        
        writeLine("</div>");

    }
}
