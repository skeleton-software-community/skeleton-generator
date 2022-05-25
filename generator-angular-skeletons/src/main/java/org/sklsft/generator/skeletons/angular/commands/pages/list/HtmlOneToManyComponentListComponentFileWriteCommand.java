package org.sklsft.generator.skeletons.angular.commands.pages.list;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.ui.FilterProperty;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.skeletons.angular.commands.pages.AngularHtmlFileWriteCommand;


public class HtmlOneToManyComponentListComponentFileWriteCommand extends AngularHtmlFileWriteCommand {

	private OneToManyComponent oneToManyComponent;
	private Bean referenceBean;
	
	/*
	 * constructor
	 */
	public HtmlOneToManyComponentListComponentFileWriteCommand(OneToManyComponent oneToManyComponent) {
        
		super(oneToManyComponent.referenceBean.myPackage.model.project.workspaceFolder + File.separator + oneToManyComponent.referenceBean.myPackage.model.tsUiArtefactName + File.separator + oneToManyComponent.parentBean.myPackage.model.tsSourcesFolder + File.separator + oneToManyComponent.parentBean.myPackage.tsFeaturePath + File.separator + oneToManyComponent.parentBean.urlPiece + File.separator + oneToManyComponent.referenceBean.urlPiece + File.separator + "list", oneToManyComponent.referenceBean.urlPiece + "-list.component");
		
		this.oneToManyComponent = oneToManyComponent;
		this.referenceBean = oneToManyComponent.referenceBean;
		
	}
	
	
	
	@Override
	protected void writeContent() throws IOException {

        
		writeLine("<!-- -->");
		writeLine("<!-- auto generated one to many component list component html file -->");
		writeLine("<!-- write modifications between specific code marks -->");
		writeLine("<!-- processed by skeleton generator -->");
		writeLine("<!-- -->");
		skipLine();
		
		writeLine("<nav mat-tab-nav-bar>");
		writeLine("<a mat-tab-link *ngFor=\"let link of links\"");
		writeLine("[active]=\"activePath == link.path\" [routerLink]=\"link.path\"> {{link.text}} </a>");
		writeLine("</nav>");
		
		writeLine("<h2>");
		writeLine(referenceBean.listRendering + ": {{scrollView.count}} / {{scrollView.size}}");
		writeLine("</h2>");
		skipLine();
		
		writeLine("<form [formGroup]=\"filter\">");
		for (FilterProperty property : referenceBean.basicViewBean.filter.properties) {
			writeFilter(property);
		}
		writeLine("</form>");
		
		writeLine("<mat-table [dataSource]=\"dataSource\" matSort>");

		for (ViewProperty property:referenceBean.basicViewBean.properties) {
			writeListComponent(property, referenceBean);
		}
		
		writeLine("<ng-container matColumnDef=\"Actions\">");
		writeLine("<mat-header-cell *matHeaderCellDef class=\"daisy-bg-on-primary-lighter\">Actions</mat-header-cell>");
		writeLine("<mat-cell *matCellDef=\"let element\">");
		
		writeLine("<a class=\"margin-10\" (click)=\"edit(element.id)\"><mat-icon aria-label=\"Edit\" svgIcon=\"pencil\" class=\"daisy-text-success\"></mat-icon></a>");
		writeLine("<a *ngIf=\"element.canDelete\" class=\"margin-10\" (click)=\"delete(element.id)\"><mat-icon aria-label=\"Delete\" svgIcon=\"delete\" class=\"daisy-text-warn\"></mat-icon></a>");

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

    }
}