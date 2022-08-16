package org.sklsft.generator.skeletons.angular.commands.pages.details;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.skeletons.angular.commands.pages.AngularHtmlFileWriteCommand;


public class HtmlDetailsComponentFileWriteCommand extends AngularHtmlFileWriteCommand {

	private Bean bean;
	/*
	 * constructor
	 */
	public HtmlDetailsComponentFileWriteCommand(Bean bean) {
        
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.tsUiArtefactName + File.separator + bean.myPackage.tsComponentsPath + File.separator + bean.urlPiece + File.separator + "details", bean.urlPiece + "-details.component");
		
		this.bean = bean;
		
	}
	
	
	
	@Override
	protected void writeContent() throws IOException {

        
		writeLine("<!-- -->");
		writeLine("<!-- auto generated details component html file -->");
		writeLine("<!-- write modifications between specific code marks -->");
		writeLine("<!-- processed by skeleton generator -->");
		writeLine("<!-- -->");
		skipLine();
		
		writeLine("<app-" + bean.urlPiece + "-menu  #menu [activePath]=\"activePath\" [id]=\"id\"></app-" + bean.urlPiece + "-menu>");
		skipLine();
		
		writeLine("<h2>");
		writeLine(bean.detailRendering);
		writeLine("</h2>");
		skipLine();
		
		writeLine("<div class=\"details-form\" >");
		writeLine("<form [formGroup]=\"form\" (ngSubmit)=\"update()\">");
		
		for (ViewProperty property:bean.formBean.properties) {
			writeInput(property);
		}
		
		if (bean.updateEnabled) {
			writeLine("<p>");
			writeLine("<button *ngIf=\"view.canUpdate\" mat-raised-button color=\"primary\" type=\"submit\" [disabled]=\"form.invalid\">Update</button>");
			writeLine("</p>");
		}
		writeLine("</form>");
		
        writeNotOverridableContent();
        
        writeLine("</div>");

    }
}
