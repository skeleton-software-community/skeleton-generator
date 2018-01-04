package org.sklsft.generator.skeletons.core.commands.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.model.domain.business.Property;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.model.metadata.RelationType;
import org.sklsft.generator.model.metadata.SelectionMode;
import org.sklsft.generator.skeletons.commands.impl.typed.JavaFileWriteCommand;
import org.sklsft.generator.util.naming.JavaClassNaming;


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

		javaImports.add("import static org.sklsft.commons.model.patterns.HibernateCriteriaUtils.*;");
		javaImports.add("import java.util.Date;");
		javaImports.add("import java.math.BigDecimal;");
		javaImports.add("import java.util.List;");
		javaImports.add("import org.hibernate.criterion.Projections;");
		javaImports.add("import org.hibernate.criterion.Restrictions;");
		javaImports.add("import org.hibernate.Criteria;");
		javaImports.add("import org.hibernate.criterion.Order;");
		javaImports.add("import org.sklsft.commons.api.model.OrderType;");
		javaImports.add("import org.hibernate.sql.JoinType;");
		javaImports.add("import org.hibernate.FetchMode;");
		javaImports.add("import org.sklsft.commons.api.exception.repository.ObjectNotFoundException;");
		javaImports.add("import org.springframework.stereotype.Repository;");
		javaImports.add("import org.sklsft.commons.model.patterns.BaseDaoImpl;");
		javaImports.add("import " + this.bean.myPackage.omPackageName + "." + this.bean.className + ";");
		javaImports.add("import " + bean.myPackage.filtersPackageName + "." + bean.basicViewBean.filterClassName + ";");
		javaImports.add("import " + bean.myPackage.sortingsPackageName + "." + bean.basicViewBean.sortingClassName + ";");
		javaImports.add("import " + this.bean.myPackage.baseDAOInterfacePackageName + "." + this.bean.baseDaoInterfaceName + ";");
	
		for (OneToManyComponent oneToManyComponent:bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.filtersPackageName + "." + currentBean.basicViewBean.filterClassName + ";");
			javaImports.add("import " + currentBean.myPackage.sortingsPackageName + "." + currentBean.basicViewBean.sortingClassName + ";");
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
		createCount();		
		createScroll();
		createLoadOneToManyComponentList();
		createCountOneToManyComponent();		
		createScrollOneToManyComponent();
		createLoadOneToManyComponent();
		
		if (bean.cardinality>0) {
			createExistsObject();
			createFindObject();
		}
		if (bean.selectable) {
			if (bean.selectionBehavior.selectionMode.equals(SelectionMode.AUTO_COMPLETE)) {
				createSearch();
			}
		}
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
			if (property.visibility.isListVisible() && property.referenceBean != null) {
				writeLine("criteria.setFetchMode(" + CHAR_34 + property.name + CHAR_34 + ",FetchMode.JOIN);");

				for (Alias alias : getAliases(property)) {
					writeLine("criteria.setFetchMode(" + CHAR_34 + property.name + "." + alias.propertyPath + CHAR_34 + ",FetchMode.JOIN);");

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
							writeLine("criteria.setFetchMode(" + CHAR_34 + prop.name + "." + alias.propertyPath + CHAR_34 + ",FetchMode.JOIN);");
						}
					}
				}

				writeLine("return criteria.list();");
				writeLine("}");
				skipLine();

			}
		}
	}
	
	
	private void createCount() {
		
		writeLine("/**");
		writeLine(" * count filtered object list");
		writeLine(" */");
		writeLine("@Override");
		writeLine("public Long count(" + bean.basicViewBean.filterClassName + " filter) {");
		
		writeLine("Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(" + this.bean.className + ".class).setProjection(Projections.rowCount());");

		List<Alias> aliases = getAllAliases(bean);
		for (Alias alias : aliases) {
			writeLine("Criteria " + alias.name + "Criteria = " + (alias.parentName!=null?(alias.parentName + "Criteria"):"criteria") + ".createCriteria(" + CHAR_34 + alias.propertyName + CHAR_34 + ", JoinType.LEFT_OUTER_JOIN);");
		}
		
		for (ViewProperty property : this.bean.basicViewBean.properties) {
			writeRestriction(property);
		}

		writeLine("return (Long) criteria.uniqueResult();");
		writeLine("}");
		skipLine();
		
		for (Property property : this.bean.properties) {
			if (property.referenceBean != null && property.relation.equals(RelationType.MANY_TO_ONE)) {

				writeLine("/**");
				writeLine(" * count object list from " + property.referenceBean.objectName); 
				writeLine(" */");
				writeLine("public Long countFrom" + property.capName + "(Long " + property.name + "Id) {");
				
				writeLine("Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(" + this.bean.className + ".class).setProjection(Projections.rowCount());");

				writeLine("if (" + property.name + "Id == null){");
				writeLine("criteria.add(Restrictions.isNull(" + CHAR_34 + property.name + ".id" + CHAR_34 + "));");
				writeLine("} else {");
				writeLine("criteria.add(Restrictions.eq(" + CHAR_34 + property.name + ".id" + CHAR_34 + ", " + property.name + "Id));");
				writeLine("}");
				
				writeLine("return (Long) criteria.uniqueResult();");
				writeLine("}");
				skipLine();
				
				
				writeLine("/**");
				writeLine(" * count filtered object list from " + property.referenceBean.objectName); 
				writeLine(" */");
				writeLine("public Long countFrom" + property.capName + "(Long " + property.name + "Id, " + bean.basicViewBean.filterClassName + " filter) {");
				
				writeLine("Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(" + this.bean.className + ".class).setProjection(Projections.rowCount());");

				writeLine("if (" + property.name + "Id == null){");
				writeLine("criteria.add(Restrictions.isNull(" + CHAR_34 + property.name + ".id" + CHAR_34 + "));");
				writeLine("} else {");
				writeLine("criteria.add(Restrictions.eq(" + CHAR_34 + property.name + ".id" + CHAR_34 + ", " + property.name + "Id));");
				writeLine("}");
				
				aliases = getAllAliases(bean);
				for (Alias alias : aliases) {
					writeLine("Criteria " + alias.name + "Criteria = " + (alias.parentName!=null?(alias.parentName + "Criteria"):"criteria") + ".createCriteria(" + CHAR_34 + alias.propertyName + CHAR_34 + ", JoinType.LEFT_OUTER_JOIN);");
				}
				
				for (ViewProperty viewProperty : this.bean.basicViewBean.properties) {
					writeRestriction(viewProperty);
				}

				writeLine("return (Long) criteria.uniqueResult();");
				writeLine("}");
				skipLine();
			}
		}
	}
	
	private void createScroll() {
		
		writeLine("/**");
		writeLine(" * scroll filtered object list");
		writeLine(" */");
		writeLine("@Override");
		writeLine("@SuppressWarnings(" + CHAR_34 + "unchecked" + CHAR_34 + ")");
		writeLine("public List<" + this.bean.className + "> scroll(" + bean.basicViewBean.filterClassName + " filter, " + bean.basicViewBean.sortingClassName + " sorting, Long firstResult, Long maxResults) {");
		
		writeLine("Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(" + this.bean.className + ".class);");

		List<Alias> aliases = getAllAliases(bean);
		for (Alias alias : aliases) {
			writeLine("Criteria " + alias.name + "Criteria = " + (alias.parentName!=null?(alias.parentName + "Criteria"):"criteria") + ".createCriteria(" + CHAR_34 + alias.propertyName + CHAR_34 + ", JoinType.LEFT_OUTER_JOIN);");
		}
		
		for (ViewProperty property : this.bean.basicViewBean.properties) {
			writeRestriction(property);
		}
		
		for (ViewProperty property : this.bean.basicViewBean.properties) {
			String propertyCriteria = StringUtils.isEmpty(property.joinedAliasName)?"criteria":property.joinedAliasName + "Criteria";
			writeLine("addOrder(" + propertyCriteria + ", " + CHAR_34 + property.lastPropertyName + CHAR_34 + ", sorting.get" + property.capName + "OrderType());");
		}
		
		writeLine("if (firstResult != null){");
		writeLine("criteria.setFirstResult(firstResult.intValue());");
		writeLine("}");
		writeLine("if (maxResults != null){");
		writeLine("criteria.setMaxResults(maxResults.intValue());");
		writeLine("}");

		writeLine("return criteria.list();");
		writeLine("}");
		skipLine();
		
		for (Property property : this.bean.properties) {
			if (property.referenceBean != null && property.relation.equals(RelationType.MANY_TO_ONE)) {

				writeLine("/**");
				writeLine(" * scroll filtered object list from " + property.referenceBean.objectName); 
				writeLine(" */");
				writeLine("@Override");
				writeLine("@SuppressWarnings(" + CHAR_34 + "unchecked" + CHAR_34 + ")");
				writeLine("public List<" + this.bean.className + "> scrollFrom" + property.capName + "(Long " + property.name + "Id, " + bean.basicViewBean.filterClassName + " filter, " + bean.basicViewBean.sortingClassName + " sorting, Long firstResult, Long maxResults) {");
				
				writeLine("Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(" + this.bean.className + ".class);");

				writeLine("if (" + property.name + "Id == null){");
				writeLine("criteria.add(Restrictions.isNull(" + CHAR_34 + property.name + ".id" + CHAR_34 + "));");
				writeLine("} else {");
				writeLine("criteria.add(Restrictions.eq(" + CHAR_34 + property.name + ".id" + CHAR_34 + ", " + property.name + "Id));");
				writeLine("}");
				
				aliases = getAllAliases(bean);
				for (Alias alias : aliases) {
					writeLine("Criteria " + alias.name + "Criteria = " + (alias.parentName!=null?(alias.parentName + "Criteria"):"criteria") + ".createCriteria(" + CHAR_34 + alias.propertyName + CHAR_34 + ", JoinType.LEFT_OUTER_JOIN);");
				}
				
				for (ViewProperty viewProperty : this.bean.basicViewBean.properties) {
					writeRestriction(viewProperty);
				}
				
				for (ViewProperty viewProperty : this.bean.basicViewBean.properties) {
					String propertyCriteria = StringUtils.isEmpty(viewProperty.joinedAliasName)?"criteria":viewProperty.joinedAliasName + "Criteria";
					writeLine("addOrder(" + propertyCriteria + ", " + CHAR_34 + viewProperty.lastPropertyName + CHAR_34 + ", sorting.get" + viewProperty.capName + "OrderType());");
				}
				
				writeLine("if (firstResult != null){");
				writeLine("criteria.setFirstResult(firstResult.intValue());");
				writeLine("}");
				writeLine("if (maxResults != null){");
				writeLine("criteria.setMaxResults(maxResults.intValue());");
				writeLine("}");

				writeLine("return criteria.list();");
				writeLine("}");
				skipLine();
				
			}
		}
	}
	
	
	private void writeRestriction(ViewProperty property) {
		switch (property.dataType) {
			case BOOLEAN :
				writeBooleanRestriction(property);
				return;
			case LONG :
				writeLongRestriction(property);
				return;
			case DATE :
				writeDateRestriction(property);
				return;
			case DATETIME :
				writeDateTimeRestriction(property);
				return;
			case DOUBLE :
				writeDoubleRestriction(property);
				return;
			case BIG_DECIMAL :
				writeBigDecimalRestriction(property);
				return;
			case STRING :
			case TEXT :
				writeTextRestriction(property);
				return;
		}
	}
	
	private void writeTextRestriction(ViewProperty property) {
		String propertyPath =  CHAR_34 + "{alias}." + property.lastColumnName + CHAR_34;
		String propertyCriteria = StringUtils.isEmpty(property.joinedAliasName)?"criteria":property.joinedAliasName + "Criteria";
		writeLine("addStringContainsRestriction(" + propertyCriteria + ", " + propertyPath + ", filter.get" + property.capName + "());");
	}
	
	private void writeBooleanRestriction(ViewProperty property) {
		String propertyPath =  CHAR_34 + property.lastPropertyName + CHAR_34;
		String propertyCriteria = StringUtils.isEmpty(property.joinedAliasName)?"criteria":property.joinedAliasName + "Criteria";
		writeLine("addBooleanRestriction(" + propertyCriteria + ", " + propertyPath + ", filter.get" + property.capName + "());");
	}
	
	private void writeDoubleRestriction(ViewProperty property) {
		writeComparableRestriction(property);
	}
	
	private void writeBigDecimalRestriction(ViewProperty property) {
		writeComparableRestriction(property);
	}
	
	private void writeDateRestriction(ViewProperty property) {
		writeComparableRestriction(property);
	}
	
	private void writeDateTimeRestriction(ViewProperty property) {
		writeComparableRestriction(property);
	}
	
	private void writeLongRestriction(ViewProperty property) {
		writeComparableRestriction(property);
	}
	
	private void writeComparableRestriction(ViewProperty property) {
		String propertyPath =  CHAR_34 + property.lastPropertyName + CHAR_34;
		String propertyCriteria = StringUtils.isEmpty(property.joinedAliasName)?"criteria":property.joinedAliasName + "Criteria";
		writeLine("addBetweenRestriction(" + propertyCriteria + ", " + propertyPath + ", filter.get" + property.capName + "MinValue(), filter.get" + property.capName + "MaxValue());");
	}
	
	
	private void createLoadOneToManyComponentList() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
            Bean currentBean = oneToManyComponent.referenceBean;
            writeLine("/**");
            writeLine(" * load one to many component " + currentBean.className + " list");
            writeLine(" */");
            writeLine("@Override");
    		writeLine("@SuppressWarnings(" + CHAR_34 + "unchecked" + CHAR_34 + ")");
			writeLine("public List<" + currentBean.className + "> load" + currentBean.className + "List(Long " + bean.objectName + "Id) {");
			writeLine("Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(" + currentBean.className + ".class);");
			writeLine("if (" + bean.objectName + "Id == null){");
			writeLine("criteria.add(Restrictions.isNull(" + CHAR_34 + oneToManyComponent.referenceProperty.name + ".id" + CHAR_34 + "));");
			writeLine("} else {");
			writeLine("criteria.add(Restrictions.eq(" + CHAR_34 + oneToManyComponent.referenceProperty.name + ".id" + CHAR_34 + ", " + bean.objectName + "Id));");
			writeLine("}");
			writeLine("return criteria.list();");
			writeLine("}");
			skipLine();
        }
	}
	
	
	private void createCountOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * count one to many component " + currentBean.className); 
			writeLine(" */");
			writeLine("@Override");
			writeLine("public Long count" + currentBean.className + "(Long " + bean.objectName + "Id) {");
						
			writeLine("Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(" + currentBean.className + ".class).setProjection(Projections.rowCount());");

			writeLine("if (" + bean.objectName + "Id == null){");
			writeLine("criteria.add(Restrictions.isNull(" + CHAR_34 + oneToManyComponent.referenceProperty.name + ".id" + CHAR_34 + "));");
			writeLine("} else {");
			writeLine("criteria.add(Restrictions.eq(" + CHAR_34 + oneToManyComponent.referenceProperty.name + ".id" + CHAR_34 + ", " + bean.objectName + "Id));");
			writeLine("}");
			
			writeLine("return (Long) criteria.uniqueResult();");
			
			writeLine("}");
			skipLine();
			
			writeLine("/**");
			writeLine("@Override");
			writeLine(" * count filtered one to many component " + currentBean.className);
			writeLine(" */");
			writeLine("public Long count" + currentBean.className + "(Long " + bean.objectName + "Id, " + currentBean.basicViewBean.filterClassName + " filter) {");
			
			writeLine("Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(" + currentBean.className + ".class).setProjection(Projections.rowCount());");

			writeLine("if (" + bean.objectName + "Id == null){");
			writeLine("criteria.add(Restrictions.isNull(" + CHAR_34 + oneToManyComponent.referenceProperty.name + ".id" + CHAR_34 + "));");
			writeLine("} else {");
			writeLine("criteria.add(Restrictions.eq(" + CHAR_34 + oneToManyComponent.referenceProperty.name + ".id" + CHAR_34 + ", " + bean.objectName + "Id));");
			writeLine("}");
			
			List<Alias> aliases = getAllAliases(currentBean);
			for (Alias alias : aliases) {
				writeLine("Criteria " + alias.name + "Criteria = " + (alias.parentName!=null?(alias.parentName + "Criteria"):"criteria") + ".createCriteria(" + CHAR_34 + alias.propertyName + CHAR_34 + ", JoinType.LEFT_OUTER_JOIN);");
			}
			
			for (ViewProperty viewProperty : currentBean.basicViewBean.properties) {
				writeRestriction(viewProperty);
			}
			
			writeLine("return (Long) criteria.uniqueResult();");
			
			writeLine("}");
			skipLine();
		}
	}
	
	

	private void createScrollOneToManyComponent() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * scroll filtered one to many component " + currentBean.className); 
			writeLine(" */");
			writeLine("@Override");
			writeLine("@SuppressWarnings(" + CHAR_34 + "unchecked" + CHAR_34 + ")");
			writeLine("public List<" + currentBean.className + "> scroll" + currentBean.className + "(Long " + bean.objectName + "Id, " + currentBean.basicViewBean.filterClassName + " filter, " + currentBean.basicViewBean.sortingClassName + " sorting, Long firstResult, Long maxResults) {");
			
			writeLine("Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(" + currentBean.className + ".class);");

			writeLine("if (" + bean.objectName + "Id == null){");
			writeLine("criteria.add(Restrictions.isNull(" + CHAR_34 + oneToManyComponent.referenceProperty.name + ".id" + CHAR_34 + "));");
			writeLine("} else {");
			writeLine("criteria.add(Restrictions.eq(" + CHAR_34 + oneToManyComponent.referenceProperty.name + ".id" + CHAR_34 + ", " + bean.objectName + "Id));");
			writeLine("}");
			
			List<Alias> aliases = getAllAliases(currentBean);
			for (Alias alias : aliases) {
				writeLine("Criteria " + alias.name + "Criteria = " + (alias.parentName!=null?(alias.parentName + "Criteria"):"criteria") + ".createCriteria(" + CHAR_34 + alias.propertyName + CHAR_34 + ", JoinType.LEFT_OUTER_JOIN);");
			}
			
			for (ViewProperty viewProperty : currentBean.basicViewBean.properties) {
				writeRestriction(viewProperty);
			}
			
			for (ViewProperty viewProperty : currentBean.basicViewBean.properties) {
				String propertyCriteria = StringUtils.isEmpty(viewProperty.joinedAliasName)?"criteria":viewProperty.joinedAliasName + "Criteria";
				writeLine("addOrder(" + propertyCriteria + ", " + CHAR_34 + viewProperty.lastPropertyName + CHAR_34 + ", sorting.get" + viewProperty.capName + "OrderType());");
			}
			
			writeLine("if (firstResult != null){");
			writeLine("criteria.setFirstResult(firstResult.intValue());");
			writeLine("}");
			writeLine("if (maxResults != null){");
			writeLine("criteria.setMaxResults(maxResults.intValue());");
			writeLine("}");

			writeLine("return criteria.list();");
			writeLine("}");
			skipLine();
		}
	}

	

	private void createLoadOneToManyComponent() {
		
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
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
        }
	}

	private void createExistsObject() {
		boolean start = true;

		writeLine("/**");
		writeLine(" * exists object");
		writeLine(" */");
		writeLine("@Override");
		write("public boolean exists(");
		for (ViewProperty property:this.bean.referenceViewProperties) {
			if (start) start = false; else write(", ");
			write(property.beanDataType + " " + property.name);
		}
		writeLine(") {");

		start = true;
		write("if (");
		for (ViewProperty property:this.bean.referenceViewProperties) {
			if (start) start = false; else write(" && ");
			write(property.name + " == null");
		}
		writeLine(") {");

		writeLine("return false;");

		writeLine("}");

		write(this.bean.className + " " + this.bean.objectName + " = (" + this.bean.className + ")this.sessionFactory.getCurrentSession().createCriteria(");
		writeLine(this.bean.className + ".class)");

		for (Alias alias : getReferenceAliases(bean)) {
			writeLine(".createAlias(" + CHAR_34 + alias.propertyPath + CHAR_34 + "," + CHAR_34 + alias.name + CHAR_34 + ")");

		}
		for (ViewProperty property : this.bean.referenceViewProperties) {
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

		for (Alias alias : getReferenceAliases(bean)) {
			writeLine(".createAlias(" + CHAR_34 + alias.propertyPath + CHAR_34 + "," + CHAR_34 + alias.name + CHAR_34 + ")");

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
	
	
	private void createSearch() {
		
		Property targetProperty = bean.selectionBehavior.targetProperty;
		
		writeLine("/**");
		writeLine(" * search");
		writeLine(" */");
		writeLine("@Override");
		writeLine("public List<" + this.bean.className + "> search(String arg) {");
		writeLine("Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(" + this.bean.className + ".class);");
		String propertyPath =  CHAR_34 + "{alias}." + targetProperty.column.name + CHAR_34;
		writeLine("addStringContainsRestriction(criteria, " + propertyPath + ", arg);");
		writeLine("criteria.setMaxResults(20);");
		writeLine("return criteria.list();");
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
	
	
	
	
	
	private class Alias {

		public String propertyPath;
		public String propertyName;
	    public String name;
	    public String parentName;
	}
	
	

	
	private List<Alias> getReferenceAliases(Bean bean) {
		return getAliases(bean, bean.cardinality);
	}
	
	private List<Alias> getAllAliases(Bean bean) {
		return getAliases(bean, bean.properties.size());
	}
	
	private List<Alias> getAliases(Bean bean, int size) {
		List<Alias> aliasList = new ArrayList<Alias>();
		List<Alias> tempAliasList = new ArrayList<Alias>();

		for (int i = 0; i < size; i++) {
			Property currentProperty = bean.properties.get(i);
			if (currentProperty.visibility.isListVisible() && currentProperty.referenceBean != null && !currentProperty.relation.isComponentLink()) {
				Alias alias = new Alias();
				alias.propertyPath = currentProperty.name;
				alias.propertyName = currentProperty.name;
				alias.name = currentProperty.name;
				alias.parentName = null;
				aliasList.add(alias);
				
				tempAliasList = getAliases(currentProperty);
				for (int j = 0; j < tempAliasList.size(); j++) {
					Alias tempAlias = tempAliasList.get(j);
					Alias newAlias = new Alias();
					newAlias.propertyPath = alias.propertyPath + "." + tempAlias.propertyPath;
					newAlias.propertyName = tempAlias.propertyName;
					newAlias.name = alias.name + JavaClassNaming.getClassNameFromObjectName(tempAlias.name);
					newAlias.parentName = tempAlias.parentName == null?alias.name:alias.name + JavaClassNaming.getClassNameFromObjectName(tempAlias.parentName);
					aliasList.add(newAlias);
				}
			}
		}

		return aliasList;
	}
	
	private List<Alias> getAliases(Property property) {
		if (property.embedded) {
			return getAllAliases(property.referenceBean);
		} else {
			return getReferenceAliases(property.referenceBean);
		}
	}
}
