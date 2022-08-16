package org.sklsft.generator.skeletons.angular.commands.pages.details.menu;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.skeletons.angular.commands.pages.AngularHtmlFileWriteCommand;


public class HtmlMenuComponentFileWriteCommand extends AngularHtmlFileWriteCommand {

	private Bean bean;
	/*
	 * constructor
	 */
	public HtmlMenuComponentFileWriteCommand(Bean bean) {
        
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.tsUiArtefactName + File.separator + bean.myPackage.tsComponentsPath + File.separator + bean.urlPiece + File.separator + "menu", bean.urlPiece + "-menu.component");
		
		this.bean = bean;
		
	}
	
	
	
	@Override
	protected void writeContent() throws IOException {

		writeLine("<!-- -->");
		writeLine("<!-- auto generated menu component html file -->");
		writeLine("<!-- write modifications between specific code marks -->");
		writeLine("<!-- processed by skeleton generator -->");
		writeLine("<!-- -->");
		skipLine();
		
		writeLine("<nav mat-tab-nav-bar>");
		writeLine("<a mat-tab-link *ngFor=\"let link of links\"");
		writeLine("[active]=\"activePath == link.path\" [routerLink]=\"link.path\"> {{link.text}} </a>");
		writeLine("</nav>");
		
    }
}
