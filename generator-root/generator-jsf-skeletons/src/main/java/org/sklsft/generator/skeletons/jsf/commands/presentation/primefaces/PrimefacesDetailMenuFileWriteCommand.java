package org.sklsft.generator.skeletons.jsf.commands.presentation.primefaces;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.OneToOneComponent;

public class PrimefacesDetailMenuFileWriteCommand extends PrimefacesXhtmlFileWriteCommand {

	private Bean bean;
	
	public PrimefacesDetailMenuFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-webapp" + File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "sections" + File.separator  + bean.myPackage.name + File.separator + bean.className.toLowerCase(), bean.className + "DetailsMenu");

		this.bean = bean;
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("<ui:composition xmlns=\"http://www.w3.org/1999/xhtml\"");
        writeLine("xmlns:ui=\"http://java.sun.com/jsf/facelets\"");
        writeLine("xmlns:f=\"http://java.sun.com/jsf/core\"");
        writeLine("xmlns:h=\"http://java.sun.com/jsf/html\">");
        skipLine();

        writeLine("<!-- -->");
        writeLine("<!-- auto generated jsf file -->");
        writeLine("<!-- write modifications between specific code marks -->");
        writeLine("<!-- processed by skeleton-generator -->");
        writeLine("<!-- -->");
        skipLine();

        writeLine("<ul class=\"nav nav-pills\">");
        
        writeLine("<li role=\"presentation\" id=\"" + bean.objectName + "DetailsMenu\">");
		writeLine("<h:link outcome=\"/sections/" + bean.myPackage.name + "/" + this.bean.className.toLowerCase() + "/" + bean.className + "Details.jsf\">");
		writeLine("<f:param name=\"id\" value=\"#{" + bean.detailViewObjectName + ".selected" + bean.className + ".id}\" />");
		writeLine("#{i18n." + bean.objectName + "Detail}");
		writeLine("</h:link>");
		writeLine("</li>");
        
		for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList)
        {
        	Bean currentBean = oneToOneComponent.referenceBean;

			writeLine("<li role=\"presentation\" id=\"" + currentBean.objectName + "DetailsMenu\">");
			writeLine("<h:link outcome=\"/sections/" + bean.myPackage.name + "/" + this.bean.className.toLowerCase() + "/" + currentBean.className + "Details.jsf\">");
			writeLine("<f:param name=\"id\" value=\"#{" + bean.detailViewObjectName + ".selected" + bean.className + ".id}\" />");
			writeLine("#{i18n." + currentBean.objectName + "Detail}");
			writeLine("</h:link>");
			writeLine("</li>");
        }
		
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
        	Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("<li role=\"presentation\" id=\"" + currentBean.objectName + "ListMenu\">");
			writeLine("<h:link outcome=\"/sections/" + bean.myPackage.name + "/" + this.bean.className.toLowerCase() + "/" + currentBean.className + "List.jsf\">");
			writeLine("<f:param name=\"id\" value=\"#{" + bean.detailViewObjectName + ".selected" + bean.className + ".id}\" />");
			writeLine("#{i18n." + currentBean.objectName + "List}");
			writeLine("</h:link>");
			writeLine("</li>");
        }
        
        for (OneToMany oneToMany : this.bean.oneToManyList)
        {
        	Bean currentBean = oneToMany.referenceBean;

			writeLine("<li role=\"presentation\" id=\"" + currentBean.objectName + "ListMenu\">");
			writeLine("<h:link outcome=\"/sections/" + bean.myPackage.name + "/" + this.bean.className.toLowerCase() + "/" + currentBean.className + "List.jsf\">");
			writeLine("<f:param name=\"id\" value=\"#{" + bean.detailViewObjectName + ".selected" + bean.className + ".id}\" />");
			writeLine("#{i18n." + currentBean.objectName + "List}");
			writeLine("</h:link>");
			writeLine("</li>");
        }
		

		writeLine("</ul>");
		
        writeLine("</ui:composition>");
    }
}
