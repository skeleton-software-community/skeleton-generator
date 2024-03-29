package org.sklsft.generator.skeletons.angular.commands.pages.modal;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.skeletons.angular.commands.pages.AngularHtmlFileWriteCommand;


public class HtmlModalComponentFileWriteCommand extends AngularHtmlFileWriteCommand {

	private Bean bean;
	/*
	 * constructor
	 */
	public HtmlModalComponentFileWriteCommand(Bean bean) {
        
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.tsUiArtefactName + File.separator + bean.myPackage.tsComponentsPath + File.separator + bean.urlPiece + File.separator + "modal", bean.urlPiece + "-modal.component");
		
		this.bean = bean;
		
	}
	
	
	
	@Override
	protected void writeContent() throws IOException {

        
		writeLine("<!-- -->");
		writeLine("<!-- auto generated modal component html file -->");
		writeLine("<!-- write modifications between specific code marks -->");
		writeLine("<!-- processed by skeleton generator -->");
		writeLine("<!-- -->");
		skipLine();
		
		writeLine("<div class=\"modal-form\" >");
		writeLine("<form [formGroup]=\"form\" (ngSubmit)=\"saveOrUpdate()\">");
		writeLine("<mat-dialog-content class=\"text-grey-darker\">");
		writeLine("<div>");
		writeLine("<button mat-icon-button mat-dialog-close disableRipple>");
		writeLine("<mat-icon class=\"text-grey\" svgIcon=\"close\"></mat-icon>");
		writeLine("</button>");
		writeLine("</div>");
		writeLine("<section>");
		for (ViewProperty property:bean.formBean.properties) {
			writeInput(property);
		}
		
		writeLine("</section>");
		writeLine("</mat-dialog-content>");
		writeLine("<mat-dialog-actions>");
		if (bean.createEnabled) {
			writeLine("<button mat-raised-button *ngIf=\"view.id == null\" color=\"primary\" type=\"submit\" [disabled]=\"form.invalid\">Save</button>");
		}
		if (bean.updateEnabled ) {
			writeLine("<button mat-raised-button *ngIf=\"view.id != null && view.canUpdate\" color=\"primary\" type=\"submit\" [disabled]=\"form.invalid\">Update</button>");
		}
		writeLine("</mat-dialog-actions>");
		writeLine("</form>");

        writeNotOverridableContent();

        writeLine("</div>");
    }
}
