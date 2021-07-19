package org.sklsft.generator.skeletons.angular.commands.pages.list;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.ui.FilterProperty;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.skeletons.angular.commands.pages.AngularHtmlFileWriteCommand;


public class HtmlListComponentFileWriteCommand extends AngularHtmlFileWriteCommand {

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
		
		writeLine("<h2>");
		writeLine(bean.listRendering + ": {{view.scrollView.count}} / {{view.scrollView.size}}");
		writeLine("</h2>");
		skipLine();
		
		writeLine("<form [formGroup]=\"filter\">");
		for (FilterProperty property : this.bean.basicViewBean.filter.properties) {
			writeFilter(property);
		}
		writeLine("</form>");
		
		writeLine("<mat-table [dataSource]=\"dataSource\">");

		for (ViewProperty property:bean.basicViewBean.properties) {
			writeListComponent(property, bean);
		}
		
		writeLine("<ng-container matColumnDef=\"Actions\">");
		writeLine("<mat-header-cell *matHeaderCellDef>Actions</mat-header-cell>");
		writeLine("<mat-cell *matCellDef=\"let element\"><a href=\"{{'/" + bean.urlPiece + "/' + element.id}}\"><img src=\"/assets/images/edit.png\"/></a></mat-cell>");
		writeLine("</ng-container>");

		writeLine("<mat-header-row *matHeaderRowDef=\"displayedColumns\"></mat-header-row>");
		writeLine("<mat-row *matRowDef=\"let row; columns: displayedColumns;\"></mat-row>");
		writeLine("</mat-table>");
		
		
		writeLine("<mat-paginator #paginator [length]=\"view.scrollView.count\"");
		writeLine("[pageSize]=\"view.scrollForm.elementsPerPage\"");
		writeLine("[showFirstLastButtons]=\"true\"");
		writeLine("[pageSizeOptions]=\"pageSizeOptions\">");
		writeLine("</mat-paginator>");
		skipLine();

        writeNotOverridableContent();

    }
}
