package org.sklsft.generator.skeletons.angular.commands.pages.modal;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.skeletons.angular.commands.pages.AngularHtmlFileWriteCommand;


public class HtmlOneToManyModalComponentFileWriteCommand extends AngularHtmlFileWriteCommand {

	private OneToMany oneToMany;
	private Bean referenceBean;
	private Bean parentBean;

	
	/*
	 * constructor
	 */
	public HtmlOneToManyModalComponentFileWriteCommand(OneToMany oneToMany) {
        
		super(oneToMany.parentBean.myPackage.model.project.workspaceFolder + File.separator + oneToMany.parentBean.myPackage.model.tsUiArtefactName + File.separator + oneToMany.parentBean.myPackage.tsComponentsPath + File.separator + oneToMany.parentBean.urlPiece + File.separator + oneToMany.referenceBean.urlPiece + File.separator + "modal", oneToMany.referenceBean.urlPiece + "-modal.component");
		
		this.oneToMany = oneToMany;
		this.referenceBean = oneToMany.referenceBean;
		this.parentBean = oneToMany.parentBean;
	}
	
	
	
	@Override
	protected void writeContent() throws IOException {

        
		writeLine("<!-- -->");
		writeLine("<!-- auto generated one to many modal component html file -->");
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
		for (ViewProperty property:oneToMany.formBean.properties) {
			writeInput(property);
		}
		
		writeLine("</section>");
		writeLine("</mat-dialog-content>");
		writeLine("<mat-dialog-actions>");
		if (referenceBean.createEnabled) {
			writeLine("<button mat-raised-button *ngIf=\"view.id == null\" color=\"primary\" type=\"submit\" [disabled]=\"form.invalid\">Save</button>");
		}
		if (referenceBean.updateEnabled ) {
			writeLine("<button mat-raised-button *ngIf=\"view.id != null && view.canUpdate\" color=\"primary\" type=\"submit\" [disabled]=\"form.invalid\">Update</button>");
		}
		writeLine("</mat-dialog-actions>");
		writeLine("</form>");

        writeNotOverridableContent();

        writeLine("</div>");
    }
}
