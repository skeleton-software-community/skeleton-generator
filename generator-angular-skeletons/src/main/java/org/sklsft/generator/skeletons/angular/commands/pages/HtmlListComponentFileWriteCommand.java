package org.sklsft.generator.skeletons.angular.commands.pages;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.skeletons.commands.impl.typed.HtmlFileWriteCommand;


public class HtmlListComponentFileWriteCommand extends HtmlFileWriteCommand {

	private Bean bean;
	/*
	 * constructor
	 */
	public HtmlListComponentFileWriteCommand(Bean bean) {
        
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.tsUiArtefactName + File.separator + bean.myPackage.model.tsSourcesFolder + File.separator + bean.myPackage.tsFeaturePath + File.separator + bean.urlPiece + File.separator + "list", bean.urlPiece + "-list.component");
		
		this.bean = bean;
		
	}
	
	
	
	@Override
	protected void writeContent() throws IOException {

        
		writeLine("<!-- -->");
		writeLine("<!-- auto generated list component html file -->");
		writeLine("<!-- write modifications between specific code marks -->");
		writeLine("<!-- processed by skeleton generator -->");
		writeLine("<!-- -->");
		skipLine();
		
		writeLine("<mat-table [dataSource]=\"dataSource\">");

		for (ViewProperty property:bean.basicViewBean.properties) {
			writeLine("<ng-container matColumnDef=\"" + property.name + "\">");
			writeLine("<mat-header-cell *matHeaderCellDef>" + property.rendering + "</mat-header-cell>");
			writeLine("<mat-cell *matCellDef=\"let element\"> {{element." + property.name + "}} </mat-cell>");
			writeLine("</ng-container>");
		}

		writeLine("<mat-header-row *matHeaderRowDef=\"displayedColumns\"></mat-header-row>");
		writeLine("<mat-row *matRowDef=\"let row; columns: displayedColumns;\"></mat-row>");
		writeLine("</mat-table>");

        writeNotOverridableContent();

    }
}
