package org.sklsft.generator.skeletons.angular.commands.pages.list.modal;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.skeletons.angular.commands.pages.AngularHtmlFileWriteCommand;


public class HtmlOneToManyComponentModalComponentFileWriteCommand extends AngularHtmlFileWriteCommand {

	private OneToManyComponent oneToManyComponent;
	private Bean referenceBean;
	private Bean parentBean;

	
	/*
	 * constructor
	 */
	public HtmlOneToManyComponentModalComponentFileWriteCommand(OneToManyComponent oneToManyComponent) {
        
		super(oneToManyComponent.referenceBean.myPackage.model.project.workspaceFolder + File.separator + oneToManyComponent.referenceBean.myPackage.model.tsUiArtefactName + File.separator + oneToManyComponent.parentBean.myPackage.model.tsSourcesFolder + File.separator + oneToManyComponent.parentBean.myPackage.tsFeaturePath + File.separator + oneToManyComponent.parentBean.urlPiece + File.separator + oneToManyComponent.referenceBean.urlPiece + File.separator + "list" + File.separator + "modal", oneToManyComponent.referenceBean.urlPiece + "-modal.component");
		
		this.oneToManyComponent = oneToManyComponent;
		this.referenceBean = oneToManyComponent.referenceBean;
		this.parentBean = oneToManyComponent.parentBean;
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
		writeLine("<mat-dialog-content class=\"daisy-text-grey-darker\">");
		writeLine("<div class=\"d-flex mx-n3 mt-n2\">");
		writeLine("<button mat-icon-button mat-dialog-close disableRipple class=\"ml-auto\">");
		writeLine("<mat-icon class=\"daisy-text-grey\">close</mat-icon>");
		writeLine("</button>");
		writeLine("</div>");
		writeLine("<section class=\"d-flex flex-column my-3\">");
		for (ViewProperty property:referenceBean.formBean.properties) {
			writeInput(property, referenceBean);
		}
		
		writeLine("</section>");
		writeLine("</mat-dialog-content>");
		writeLine("<mat-dialog-actions class=\"d-flex flex-column pb-4\">");
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
