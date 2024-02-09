package org.sklsft.generator.skeletons.angular.commands.pages.list;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.ui.FilterProperty;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.model.metadata.DetailMode;
import org.sklsft.generator.skeletons.angular.commands.pages.AngularHtmlFileWriteCommand;


public class HtmlOneToManyListComponentFileWriteCommand extends AngularHtmlFileWriteCommand {

	private OneToMany oneToMany;
	private Bean referenceBean;
	private Bean parentBean;
	
	/*
	 * constructor
	 */
	public HtmlOneToManyListComponentFileWriteCommand(OneToMany oneToMany) {
        
		super(oneToMany.parentBean.myPackage.model.project.workspaceFolder + File.separator + oneToMany.parentBean.myPackage.model.tsUiArtefactName + File.separator + oneToMany.parentBean.myPackage.tsComponentsPath + File.separator + oneToMany.parentBean.urlPiece + File.separator + oneToMany.referenceBean.urlPiece + File.separator + "list", oneToMany.referenceBean.urlPiece + "-list.component");
		
		this.oneToMany = oneToMany;
		this.referenceBean = oneToMany.referenceBean;
		this.parentBean = oneToMany.parentBean;
		
	}
	
	
	
	@Override
	protected void writeContent() throws IOException {

        
		writeLine("<!-- -->");
		writeLine("<!-- auto generated one to many list component html file -->");
		writeLine("<!-- write modifications between specific code marks -->");
		writeLine("<!-- processed by skeleton generator -->");
		writeLine("<!-- -->");
		skipLine();
		
		writeLine("<app-private-template>");
		writeLine("<app-" + parentBean.urlPiece + "-menu  #menu [activePath]=\"activePath\" [id]=\"id\"></app-" + parentBean.urlPiece + "-menu>");
		skipLine();
		
		writeLine("<h2>");
		writeLine(referenceBean.listRendering + ": {{scrollView.count}} / {{scrollView.size}}");
		writeLine("</h2>");
		skipLine();
		
		writeLine("<form [formGroup]=\"filter\">");
		for (FilterProperty property : oneToMany.basicViewBean.filter.properties) {
			writeFilter(property);
		}
		writeLine("</form>");
		
		writeLine("<mat-table [dataSource]=\"dataSource\" matSort>");

		for (ViewProperty property:oneToMany.basicViewBean.properties) {
			writeListComponent(property);
		}
		
		writeLine("<ng-container matColumnDef=\"Actions\">");
		writeLine("<mat-header-cell *matHeaderCellDef class=\"table-header\">Actions</mat-header-cell>");
		writeLine("<mat-cell *matCellDef=\"let element\">");
		
		if (referenceBean.detailMode.equals(DetailMode.PAGE)) {
			writeLine("<a class=\"margin-10\" href=\"{{'/" + referenceBean.urlPiece + "/' + element.id}}\"><mat-icon aria-label=\"Edit\" svgIcon=\"table-edit\" class=\"text-success\"></mat-icon></a>");
		}
		writeLine("<a class=\"margin-10\" (click)=\"edit(element.id)\"><mat-icon aria-label=\"Edit\" svgIcon=\"pencil\" class=\"text-success\"></mat-icon></a>");
		writeLine("<a *ngIf=\"element.canDelete\" class=\"margin-10\" (click)=\"delete(element.id)\"><mat-icon aria-label=\"Delete\" svgIcon=\"delete\" class=\"text-warn\"></mat-icon></a>");

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
		
		if (referenceBean.createEnabled) {
			writeLine("<button mat-raised-button (click)=\"create()\" color=\"primary\">");
			writeLine("Create");
			writeLine("</button>");
		}

        writeNotOverridableContent();
        writeLine("</app-private-template>");
    }
}
