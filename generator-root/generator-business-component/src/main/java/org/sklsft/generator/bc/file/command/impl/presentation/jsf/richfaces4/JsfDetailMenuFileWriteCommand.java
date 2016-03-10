package org.sklsft.generator.bc.file.command.impl.presentation.jsf.richfaces4;

import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.OneToOneComponent;

public class JsfDetailMenuFileWriteCommand extends JsfXhtmlFileWriteCommand {

	private Bean bean;
	
	public JsfDetailMenuFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + "\\" + bean.myPackage.model.project.projectName + "-webapp\\src\\main\\webapp\\sections\\"  + bean.myPackage.name + "\\" + bean.className.toLowerCase(), bean.className + "DetailsMenu");

		this.bean = bean;
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("<ui:composition xmlns=" + CHAR_34 + "http://www.w3.org/1999/xhtml" + CHAR_34);
        writeLine("xmlns:ui=" + CHAR_34 + "http://java.sun.com/jsf/facelets" + CHAR_34);
        writeLine("xmlns:f=" + CHAR_34 + "http://java.sun.com/jsf/core" + CHAR_34);
        writeLine("xmlns:h=" + CHAR_34 + "http://java.sun.com/jsf/html" + CHAR_34);
        writeLine("xmlns:rich=" + CHAR_34 + "http://richfaces.org/rich" + CHAR_34);
        writeLine("xmlns:a4j=" + CHAR_34 + "http://richfaces.org/a4j" + CHAR_34);
        writeLine("xmlns:c=" + CHAR_34 + "http://java.sun.com/jstl/core" + CHAR_34 + ">");
        skipLine();

        writeLine("<!-- -->");
        writeLine("<!-- auto generated jsf file -->");
        writeLine("<!-- write modifications between specific code marks -->");
        writeLine("<!-- processed by skeleton-generator -->");
        writeLine("<!-- -->");
        skipLine();

        writeLine("<ul class=" + CHAR_34 + "nav nav-pills" + CHAR_34 + ">");
        
        writeLine("<li role=" + CHAR_34 + "presentation" + CHAR_34 + " id=" + CHAR_34 + bean.objectName + "DetailsMenu" + CHAR_34 + ">");
		writeLine("<h:link outcome=" + CHAR_34 + "/sections/" + bean.myPackage.name + "/" + this.bean.className.toLowerCase() + "/" + bean.className + "Details.jsf" + CHAR_34 + ">");
		writeLine("<f:param name=" + CHAR_34 + "id" + CHAR_34 + " value=" + CHAR_34 + "#{" + bean.detailViewObjectName + ".selected" + bean.className + ".id}" + CHAR_34 + " />");
		writeLine("#{i18n." + bean.objectName + "Detail}");
		writeLine("</h:link>");
		writeLine("</li>");
        
		for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList)
        {
        	Bean currentBean = oneToOneComponent.referenceBean;

			writeLine("<li role=" + CHAR_34 + "presentation" + CHAR_34 + " id=" + CHAR_34 + currentBean.objectName + "DetailsMenu" + CHAR_34 + ">");
			writeLine("<h:link outcome=" + CHAR_34 + "/sections/" + bean.myPackage.name + "/" + this.bean.className.toLowerCase() + "/" + currentBean.className + "Details.jsf" + CHAR_34 + ">");
			writeLine("<f:param name=" + CHAR_34 + "id" + CHAR_34 + " value=" + CHAR_34 + "#{" + bean.detailViewObjectName + ".selected" + bean.className + ".id}" + CHAR_34 + " />");
			writeLine("#{i18n." + currentBean.objectName + "Detail}");
			writeLine("</h:link>");
			writeLine("</li>");
        }
		
        for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
        	Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("<li role=" + CHAR_34 + "presentation" + CHAR_34 + " id=" + CHAR_34 + currentBean.objectName + "ListMenu" + CHAR_34 + ">");
			writeLine("<h:link outcome=" + CHAR_34 + "/sections/" + bean.myPackage.name + "/" + this.bean.className.toLowerCase() + "/" + currentBean.className + "List.jsf" + CHAR_34 + ">");
			writeLine("<f:param name=" + CHAR_34 + "id" + CHAR_34 + " value=" + CHAR_34 + "#{" + bean.detailViewObjectName + ".selected" + bean.className + ".id}" + CHAR_34 + " />");
			writeLine("#{i18n." + currentBean.objectName + "List}");
			writeLine("</h:link>");
			writeLine("</li>");
        }
        
        for (OneToMany oneToMany : this.bean.oneToManyList)
        {
        	Bean currentBean = oneToMany.referenceBean;

			writeLine("<li role=" + CHAR_34 + "presentation" + CHAR_34 + " id=" + CHAR_34 + currentBean.objectName + "ListMenu" + CHAR_34 + ">");
			writeLine("<h:link outcome=" + CHAR_34 + "/sections/" + bean.myPackage.name + "/" + this.bean.className.toLowerCase() + "/" + currentBean.className + "List.jsf" + CHAR_34 + ">");
			writeLine("<f:param name=" + CHAR_34 + "id" + CHAR_34 + " value=" + CHAR_34 + "#{" + bean.detailViewObjectName + ".selected" + bean.className + ".id}" + CHAR_34 + " />");
			writeLine("#{i18n." + currentBean.objectName + "List}");
			writeLine("</h:link>");
			writeLine("</li>");
        }
		

		writeLine("</ul>");
		
        writeLine("</ui:composition>");
    }
}
