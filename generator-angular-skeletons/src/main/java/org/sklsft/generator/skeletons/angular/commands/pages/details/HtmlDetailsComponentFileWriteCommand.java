package org.sklsft.generator.skeletons.angular.commands.pages.details;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.skeletons.angular.commands.pages.AngularHtmlFileWriteCommand;
import org.sklsft.generator.skeletons.commands.impl.typed.HtmlFileWriteCommand;


public class HtmlDetailsComponentFileWriteCommand extends AngularHtmlFileWriteCommand {

	private Bean bean;
	/*
	 * constructor
	 */
	public HtmlDetailsComponentFileWriteCommand(Bean bean) {
        
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.tsUiArtefactName + File.separator + bean.myPackage.model.tsSourcesFolder + File.separator + bean.myPackage.tsFeaturePath + File.separator + bean.urlPiece + File.separator + "details", bean.urlPiece + "-details.component");
		
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
		
		writeLine("<form #form=\"ngForm\" (ngSubmit)=\"update()\">");
		
		for (ViewProperty property:bean.formBean.properties) {
			writeInput(property, bean);
		}
		
		writeLine("<p>");
		writeLine("<button mat-raised-button color=\"accent\" type=\"submit\">Update</button>");
		writeLine("</p>");
		writeLine("</form>");

        writeNotOverridableContent();

    }
}
