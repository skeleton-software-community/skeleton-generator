package org.sklsft.generator.skeletons.core.commands.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.sklsft.generator.model.domain.business.Alias;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.model.domain.business.Property;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.model.metadata.RelationType;
import org.sklsft.generator.skeletons.commands.impl.typed.JavaFileWriteCommand;


public class BaseDaoHibernateImplFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	/*
	 * constructor
	 */
	public BaseDaoHibernateImplFileWriteCommand(Bean bean) {

		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-repository" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
				+ bean.myPackage.baseDAOImplPackageName.replace(".", File.separator), bean.baseDaoClassName);

		this.bean = bean;

	}

	@Override
	protected void fetchSpecificImports() {

		javaImports.add("import java.util.List;");
		javaImports.add("import java.util.Date;");
		javaImports.add("import org.hibernate.criterion.Restrictions;");
		javaImports.add("import org.hibernate.Criteria;");
		javaImports.add("import org.hibernate.FetchMode;");
		javaImports.add("import org.sklsft.commons.api.exception.repository.ObjectNotFoundException;");
		javaImports.add("import org.springframework.stereotype.Repository;");
		javaImports.add("import org.sklsft.commons.model.patterns.BaseDaoImpl;");
		javaImports.add("import " + this.bean.myPackage.omPackageName + "." + this.bean.className + ";");
		javaImports.add("import " + this.bean.myPackage.baseDAOInterfacePackageName + "." + this.bean.baseDaoInterfaceName + ";");
	
		for (OneToManyComponent oneToManyComponent:bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
		}
		
		for (OneToOneComponent oneToOneComponent:bean.oneToOneComponentList) {
			Bean currentBean = oneToOneComponent.referenceBean;
			javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
		}
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + bean.myPackage.baseDAOImplPackageName + ";");
		skipLine();

		writeImports();
		skipLine();

		writeLine("/**");
		writeLine(" * auto generated base dao class file");
		writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		writeLine("public class " + this.bean.baseDaoClassName  + " extends BaseDaoImpl<" + this.bean.className + ", Long> implements " + this.bean.baseDaoInterfaceName + " {");
		skipLine();
		
		writeLine("/**");
		writeLine(" * constructor");
		writeLine(" */");
		writeLine("public " + this.bean.baseDaoClassName + "() {");
		writeLine("super(" + this.bean.className + ".class);");
		write("}");
		skipLine();

		createLoadObjectList();
		createLoadOneToManyComponent();
		createExistsObject();
		createFindObject();
		createSaveComponent();
		createDeleteComponent();

		write("}");

	}

	private void createLoadObjectList() {

		writeLine("/**");
		writeLine(" * load object list eagerly");
		writeLine(" */");
		writeLine("@Override");
		writeLine("@SuppressWarnings(" + CHAR_34 + "unchecked" + CHAR_34 + ")");
		writeLine("public List<" + this.bean.className + "> loadListEagerly() {");
		writeLine("Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(" + this.bean.className + ".class);");

		for (Property property : this.bean.properties) {
			if (property.referenceBean != null) {
				writeLine("criteria.setFetchMode(" + CHAR_34 + property.name + CHAR_34 + ",FetchMode.JOIN);");

				for (Alias alias : getReferenceAliases(property.referenceBean)) {
					writeLine("criteria.setFetchMode(" + CHAR_34 + property.name + "." + alias.propertyName + CHAR_34 + ",FetchMode.JOIN);");

				}
			}
		}

		writeLine("return criteria.list();");

		writeLine("}");
		skipLine();

		for (Property property : this.bean.properties) {
			if (property.referenceBean != null && property.relation.equals(RelationType.MANY_TO_ONE)) {
				writeLine("/**");
				writeLine(" * load object list from " + property.name);
				writeLine(" */");
				writeLine("@Override");
				writeLine("@SuppressWarnings(" + CHAR_34 + "unchecked" + CHAR_34 + ")");
				writeLine("public List<" + this.bean.className + "> loadListFrom" + property.capName + "(Long " + property.name + "Id) {");
				writeLine("Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(" + this.bean.className + ".class);");
				writeLine("if (" + property.name + "Id == null){");
				writeLine("criteria.add(Restrictions.isNull(" + CHAR_34 + property.name + ".id" + CHAR_34 + "));");
				writeLine("} else {");
				writeLine("criteria.add(Restrictions.eq(" + CHAR_34 + property.name + ".id" + CHAR_34 + ", " + property.name + "Id));");
				writeLine("}");
				writeLine("return criteria.list();");
				writeLine("}");
				skipLine();

				writeLine("/**");
				writeLine(" * load object list eagerly from " + property.name);
				writeLine(" */");
				writeLine("@Override");
				writeLine("@SuppressWarnings(" + CHAR_34 + "unchecked" + CHAR_34 + ")");
				writeLine("public List<" + this.bean.className + "> loadListEagerlyFrom" + property.capName + "(Long " + property.name + "Id) {");
				writeLine("Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(" + this.bean.className + ".class);");
				writeLine("if (" + property.name + "Id == null){");
				writeLine("criteria.add(Restrictions.isNull(" + CHAR_34 + property.name + ".id" + CHAR_34 + "));");
				writeLine("} else {");
				writeLine("criteria.add(Restrictions.eq(" + CHAR_34 + property.name + ".id" + CHAR_34 + ", " + property.name + "Id));");
				writeLine("}");

				for (Property prop : this.bean.properties) {
					if (prop.referenceBean != null) {
						writeLine("criteria.setFetchMode(" + CHAR_34 + prop.name + CHAR_34 + ",FetchMode.JOIN);");

						for (Alias alias : getReferenceAliases(prop.referenceBean)) {
							writeLine("criteria.setFetchMode(" + CHAR_34 + prop.name + "." + alias.propertyName + CHAR_34 + ",FetchMode.JOIN);");

						}
					}
				}

				writeLine("return criteria.list();");
				writeLine("}");
				skipLine();

			}
		}
	}

	
	private void createLoadOneToManyComponent() {
		
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList)
        {
            Bean currentBean = oneToManyComponent.referenceBean;
            writeLine("/**");
            writeLine(" * load one to many component " + currentBean.className);
            writeLine(" */");
            writeLine("@Override");
			writeLine("public " + currentBean.className + " load" + currentBean.className + "(Long id) {");
			writeLine(currentBean.className + " " + currentBean.objectName + " = (" + currentBean.className + ")this.sessionFactory.getCurrentSession().get(" + currentBean.className + ".class,id);");
			writeLine("if (" + currentBean.objectName + " == null) {");
			writeLine("throw new ObjectNotFoundException(" + CHAR_34 + currentBean.className + ".notFound" + CHAR_34 + ");");
			writeLine("} else {");
			writeLine("return " + currentBean.objectName + ";");
			writeLine("}");
			writeLine("}");
			skipLine();
			skipLine();
        }
	}

	private void createExistsObject() {
		List<ViewProperty> findPropertyList = this.bean.referenceViewProperties;
		List<Alias> findAliasList = getReferenceAliases(bean);
		boolean start = true;

		writeLine("/**");
		writeLine(" * exists object");
		writeLine(" */");
		writeLine("@Override");
		write("public boolean exists(");
		for (ViewProperty property:findPropertyList) {
			if (start) start = false; else write(", ");
			write(property.beanDataType + " " + property.name);
		}
		writeLine(") {");

		start = true;
		write("if (");
		for (ViewProperty property:findPropertyList) {
			if (start) start = false; else write(" && ");
			write(property.name + " == null");
		}
		writeLine(") {");

		writeLine("return false;");

		writeLine("}");

		write(this.bean.className + " " + this.bean.objectName + " = (" + this.bean.className + ")this.sessionFactory.getCurrentSession().createCriteria(");
		writeLine(this.bean.className + ".class)");

		for (Alias alias : findAliasList) {
			writeLine(".createAlias(" + CHAR_34 + alias.propertyName + CHAR_34 + "," + CHAR_34 + alias.name + CHAR_34 + ")");

		}
		for (ViewProperty property : findPropertyList) {
			if (StringUtils.isEmpty(property.joinedAliasName)) {
				writeLine(".add(Restrictions.eq(" + CHAR_34 + property.lastPropertyName + CHAR_34 + "," + property.name + "))");

			} else {
				writeLine(".add(Restrictions.eq(" + CHAR_34 + property.joinedAliasName + "." + property.lastPropertyName + CHAR_34 + "," + property.name + "))");

			}
		}

		writeLine(".uniqueResult();");
		writeLine("return " + this.bean.objectName + " != null;");
		writeLine("}");
		skipLine();
	}

	private void createFindObject() {
		boolean start = true;
		List<ViewProperty> findPropertyList = this.bean.referenceViewProperties;
		List<Alias> findAliasList = getReferenceAliases(bean);

		writeLine("/**");
		writeLine(" * find object or null");
		writeLine(" */");
		writeLine("@Override");
		write("public " + this.bean.className + " findOrNull(");
		for (ViewProperty property:findPropertyList) {
			if (start) start = false; else write(", ");
			write(property.beanDataType + " " + property.name);
		}
		writeLine(") {");

		write(this.bean.className + " " + this.bean.objectName + " = (" + this.bean.className + ")this.sessionFactory.getCurrentSession().createCriteria(");
		writeLine(this.bean.className + ".class)");

		for (Alias alias : findAliasList) {
			writeLine(".createAlias(" + CHAR_34 + alias.propertyName + CHAR_34 + "," + CHAR_34 + alias.name + CHAR_34 + ")");

		}
		for (ViewProperty property : findPropertyList) {
			if (StringUtils.isEmpty(property.joinedAliasName)) {
				writeLine(".add(Restrictions.eq(" + CHAR_34 + property.lastPropertyName + CHAR_34 + "," + property.name + "))");

			} else {
				writeLine(".add(Restrictions.eq(" + CHAR_34 + property.joinedAliasName + "." + property.lastPropertyName + CHAR_34 + "," + property.name + "))");

			}
		}

		writeLine(".uniqueResult();");
		writeLine("return " + this.bean.objectName + ";");
		writeLine("}");
		skipLine();
		
		start = true;
		writeLine("/**");
		writeLine(" * find object");
		writeLine(" */");
		writeLine("@Override");
		write("public " + this.bean.className + " find(");
		for (ViewProperty property:findPropertyList) {
			if (start) start = false; else write(", ");
			write(property.beanDataType + " " + property.name);
		}
		writeLine(") {");

		start = true;
		write("if (");
		for (ViewProperty property:findPropertyList) {
			if (start) start = false; else write(" && ");
			write(property.name + " == null");
		}
		writeLine(") {");

		writeLine("return null;");
		writeLine("}");

		start = true;
		write(this.bean.className + " " + this.bean.objectName + " = findOrNull(");
		for (ViewProperty property:findPropertyList) {
			if (start) start = false; else write(", ");
			write(property.name);
		}
		writeLine(");");
		writeLine("if (" + this.bean.objectName + " == null) {");
		writeLine("throw new ObjectNotFoundException(" + CHAR_34 + bean.className + ".notFound" + CHAR_34 + ");");
		writeLine("} else {");
		writeLine("return " + this.bean.objectName + ";");
		writeLine("}");
		writeLine("}");
		skipLine();
	}

	private void createSaveComponent() {
		
		for (OneToManyComponent oneToManyComponent:bean.oneToManyComponentList){
			Bean currentBean = oneToManyComponent.referenceBean;
			writeLine("/**");
			writeLine(" * save one to many component " + currentBean.className);
			writeLine(" */");
			writeLine("@Override");
			writeLine("public void save" + currentBean.className + "(" + this.bean.className + " " + this.bean.objectName + ", " + currentBean.className + " " + currentBean.objectName + ") {");
			writeLine(currentBean.objectName + "." + oneToManyComponent.referenceProperty.setterName + "(" + bean.objectName + ");");
			writeLine("this.sessionFactory.getCurrentSession().save(" + currentBean.objectName + ");");
			writeLine("}");
			skipLine();
		}
		
		for (OneToOneComponent oneToOneComponent:bean.oneToOneComponentList){
			Bean currentBean = oneToOneComponent.referenceBean;
			writeLine("/**");
			writeLine(" * save one to one component " + currentBean.className);
			writeLine(" */");
			writeLine("@Override");
			writeLine("public void save" + currentBean.className + "(" + this.bean.className + " " + this.bean.objectName + ", " + currentBean.className + " " + currentBean.objectName + ") {");
			writeLine(currentBean.objectName + ".set" + bean.className + "(" + bean.objectName + ");");
			writeLine("this.sessionFactory.getCurrentSession().save(" + currentBean.objectName + ");");
			writeLine("}");
			skipLine();
		}
	}


	private void createDeleteComponent() {
			
		for (OneToManyComponent oneToManyComponent:bean.oneToManyComponentList){
			Bean currentBean = oneToManyComponent.referenceBean;
			writeLine("/**");
			writeLine(" * delete one to many component " + currentBean.className);
			writeLine(" */");
			writeLine("@Override");
			writeLine("public void delete" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ") {");
			writeLine(currentBean.objectName + "." + oneToManyComponent.referenceProperty.getterName + "().get" + currentBean.className + "Collection().remove(" + currentBean.objectName + ");");
			writeLine("this.sessionFactory.getCurrentSession().delete(" + currentBean.objectName + ");");
			writeLine("}");
			skipLine();
		}
		
		for (OneToOneComponent oneToOneComponent:bean.oneToOneComponentList){
			Bean currentBean = oneToOneComponent.referenceBean;
			writeLine("/**");
			writeLine(" * delete one to one component " + currentBean.className);
			writeLine(" */");
			writeLine("@Override");
			writeLine("public void delete" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ") {");
			
			writeLine(bean.className + " " +  bean.objectName + " = " + currentBean.objectName + ".get" + bean.className + "();");
			writeLine(bean.objectName + ".set" + currentBean.className + "(null);");			
			writeLine("this.sessionFactory.getCurrentSession().delete(" + currentBean.objectName + ");");
			writeLine("}");
			skipLine();
		}
	}
	
	
	
	/**
	 * get the list of aliases that will be used to build queries
	 * 
	 * @return
	 */
	public List<Alias> getReferenceAliases(Bean bean) {
		List<Alias> aliasList = new ArrayList<Alias>();
		List<Alias> tempAliasList = new ArrayList<Alias>();

		for (int i = 0; i < bean.cardinality; i++) {
			Property currentProperty = bean.properties.get(i);
			if (currentProperty.referenceBean != null) {
				Alias alias = new Alias();
				alias.propertyName = currentProperty.name;
				alias.name = currentProperty.capName;
				aliasList.add(alias);

				tempAliasList = getReferenceAliases(currentProperty.referenceBean);
				for (int j = 0; j < tempAliasList.size(); j++) {
					Alias currentAlias = tempAliasList.get(j);
					Alias tempAlias = new Alias();
					tempAlias.propertyName = alias.propertyName + "." + currentAlias.propertyName;
					tempAlias.name = alias.name + currentAlias.name;
					aliasList.add(tempAlias);
				}
			}
		}

		return aliasList;
	}

}
