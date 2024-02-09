package org.sklsft.generator.skeletons.angular.commands.pages.list;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.ui.FilterProperty;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.model.metadata.DetailMode;
import org.sklsft.generator.skeletons.angular.commands.pages.AngularHtmlFileWriteCommand;


public class HtmlListComponentFileWriteCommand extends AngularHtmlFileWriteCommand {

	private Bean bean;
	/*
	 * constructor
	 */
	public HtmlListComponentFileWriteCommand(Bean bean) {
        
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.tsUiArtefactName + File.separator + bean.myPackage.tsComponentsPath + File.separator + bean.urlPiece + File.separator + "list", bean.urlPiece + "-list.component");
		
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
		
		writeLine("<app-private-template>");
		writeLine("<h2>");
		writeLine(bean.listRendering + ": {{scrollView.count}} / {{scrollView.size}}");
		writeLine("</h2>");
		skipLine();
		
		writeLine("<form [formGroup]=\"filter\">");
		for (FilterProperty property : this.bean.basicViewBean.filter.properties) {
			writeFilter(property);
		}
		writeLine("</form>");
		
		writeLine("<mat-table [dataSource]=\"dataSource\" matSort>");

		for (ViewProperty property:bean.basicViewBean.properties) {
			writeListComponent(property);
		}
		
		writeLine("<ng-container matColumnDef=\"Actions\">");
		writeLine("<mat-header-cell *matHeaderCellDef class=\"table-header\">Actions</mat-header-cell>");
		writeLine("<mat-cell *matCellDef=\"let element\">");
		if (bean.detailMode.equals(DetailMode.PAGE)) {
			writeLine("<a class=\"margin-10\" href=\"{{'/" + bean.urlPiece + "/' + element.id}}\"><mat-icon aria-label=\"Edit\" svgIcon=\"table-edit\" class=\"text-success\"></mat-icon></a>");
		}
		writeLine("<a class=\"margin-10\" (click)=\"edit(element.id)\"><mat-icon aria-label=\"Edit\" svgIcon=\"pencil\" class=\"text-success\"></mat-icon></a>");
		if (bean.deleteEnabled) {
			writeLine("<a *ngIf=\"element.canDelete\" class=\"margin-10\" (click)=\"delete(element.id)\"><mat-icon aria-label=\"Delete\" svgIcon=\"delete\" class=\"text-warn\"></mat-icon></a>");
		}
		writeLine("</mat-cell>");
		writeLine("</ng-container>");

		writeLine("<mat-header-row *matHeaderRowDef=\"displayedColumns\"></mat-header-row>");
		writeLine("<mat-row *matRowDef=\"let row; columns: displayedColumns;\"></mat-row>");
		writeLine("</mat-table>");
		
		
		writeLine("<mat-paginator #paginator [length]=\"scrollView.count\"");
		writeLine("[pageSize]=\"scrollForm.elementsPerPage\"");
		writeLine("[showFirstLastButtons]=\"true\"");
		writeLine("[pageSizeOptions]=\"pageSizeOptions\">");
		writeLine("</mat-paginator>");
		skipLine();
		
		if (bean.createEnabled) {
			writeLine("<button mat-raised-button (click)=\"create()\" color=\"primary\">");
			writeLine("Create");
			writeLine("</button>");
		}

        writeNotOverridableContent();
        writeLine("</app-private-template>");

    }
}
