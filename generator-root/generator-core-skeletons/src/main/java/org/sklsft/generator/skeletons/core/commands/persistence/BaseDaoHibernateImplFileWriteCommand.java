package org.sklsft.generator.skeletons.core.commands.persistence;

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

		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.persistenceArtefactName + File.separator + bean.myPackage.model.javaSourcesFolder + File.separator
				+ bean.myPackage.baseDAOImplPackageName.replace(".", File.separator), bean.baseDaoClassName);

		this.bean = bean;

	}

	@Override
	protected void fetchSpecificImports() {

		
		javaImports.add("import java.util.Date;");
		javaImports.add("import java.math.BigDecimal;");
		javaImports.add("import java.util.List;");
		javaImports.add("import java.util.ArrayList;");
		
		javaImports.add("import javax.persistence.criteria.CriteriaBuilder;");
		javaImports.add("import javax.persistence.criteria.CriteriaQuery;");
		javaImports.add("import javax.persistence.criteria.Fetch;");
		javaImports.add("import javax.persistence.criteria.Join;");
		javaImports.add("import javax.persistence.criteria.Order;");
		javaImports.add("import javax.persistence.criteria.Predicate;");
		javaImports.add("import javax.persistence.criteria.Root;");
		javaImports.add("import org.hibernate.Session;");
		javaImports.add("import org.hibernate.query.Query;");
		
		javaImports.add("import static org.sklsft.commons.model.patterns.JpaCriteriaUtils.*;");
		javaImports.add("import org.sklsft.commons.api.model.OrderType;");

		javaImports.add("import org.sklsft.commons.api.exception.repository.ObjectNotFoundException;");
		javaImports.add("import org.sklsft.commons.model.patterns.BaseDaoImpl;");
		javaImports.add("import " + this.bean.myPackage.omPackageName + "." + this.bean.className + ";");
		for (ViewProperty viewProperty:this.bean.basicViewBean.properties) {
			if (viewProperty.referenceBean!=null) {
				Bean currentBean = viewProperty.referenceBean;
				javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
			}
		}
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
		writeLine("public class " + this.bean.baseDaoClassName  + " extends BaseDaoImpl<" + this.bean.className + ", " + bean.idType + "> implements " + this.bean.baseDaoInterfaceName + " {");
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
		writeLine("@SuppressWarnings(\"unused\")");
		writeLine("public List<" + this.bean.className + "> loadListEagerly() {");
		
		writeLine("Session session = this.sessionFactory.getCurrentSession();");
		writeLine("CriteriaBuilder builder = session.getCriteriaBuilder();");
		writeLine("CriteriaQuery<" + this.bean.className + "> criteria = builder.createQuery(" + this.bean.className + ".class);");
		skipLine();
		
		writeLine("Root<" + this.bean.className + "> root = criteria.from(" + this.bean.className + ".class);");	
		for (Alias alias : getAllAliases(bean, null)) {
			writeLine("Fetch<" + alias.beanDataType + ", " + alias.parentBeanDataType + "> " + alias.name + " = " + alias.parentName + ".fetch(\"" + alias.propertyName + "\");");
		}
		skipLine();
		
		writeLine("criteria.select(root);");
		writeLine("List<Order> orders = new ArrayList<>();");		
		writeLine("addOrder(builder, orders, root.get(\"id\"), OrderType.DESC);");
		writeLine("criteria.orderBy(orders);");
		skipLine();
		writeLine("return session.createQuery(criteria).getResultList();");
		writeLine("}");
		skipLine();
		

		for (Property property : this.bean.properties) {
			if (property.referenceBean != null && property.relation.equals(RelationType.MANY_TO_ONE)) {
				writeLine("/**");
				writeLine(" * load object list from " + property.name);
				writeLine(" */");
				writeLine("@Override");
				writeLine("public List<" + this.bean.className + "> loadListFrom" + property.capName + "(" + property.referenceBean.idType + " " + property.name + "Id) {");
				
				writeLine("Session session = this.sessionFactory.getCurrentSession();");
				writeLine("CriteriaBuilder builder = session.getCriteriaBuilder();");
				writeLine("CriteriaQuery<" + this.bean.className + "> criteria = builder.createQuery(" + this.bean.className + ".class);");
				skipLine();
				
				writeLine("Root<" + this.bean.className + "> root = criteria.from(" + this.bean.className + ".class);");
				writeLine("Join<" + property.beanDataType + ", " + bean.className + "> " + property.name + " = root.join(\"" + property.name + "\");");
				skipLine();
				
				writeLine("if (" + property.name + "Id == null){");
				writeLine("criteria.where(builder.isNull(" + property.name + ".get(\"id\")));");
				writeLine("} else {");
				writeLine("criteria.where(builder.equal(" + property.name + ".get(\"id\"), " + property.name + "Id));");
				writeLine("}");				
				skipLine();
				
				writeLine("criteria.select(root);");
				writeLine("List<Order> orders = new ArrayList<>();");		
				writeLine("addOrder(builder, orders, root.get(\"id\"), OrderType.DESC);");
				writeLine("criteria.orderBy(orders);");
				skipLine();
				writeLine("return session.createQuery(criteria).getResultList();");
				writeLine("}");
				skipLine();


				writeLine("/**");
				writeLine(" * load object list eagerly from " + property.name);
				writeLine(" */");
				writeLine("@Override");
				writeLine("@SuppressWarnings({\"unused\",\"unchecked\"})");
				writeLine("public List<" + this.bean.className + "> loadListEagerlyFrom" + property.capName + "(" + property.referenceBean.idType + " " + property.name + "Id) {");
				
				writeLine("Session session = this.sessionFactory.getCurrentSession();");
				writeLine("CriteriaBuilder builder = session.getCriteriaBuilder();");
				writeLine("CriteriaQuery<" + this.bean.className + "> criteria = builder.createQuery(" + this.bean.className + ".class);");
				skipLine();
				
				writeLine("Root<" + this.bean.className + "> root = criteria.from(" + this.bean.className + ".class);");
				for (Alias alias : getAllAliases(bean, null)) {
					writeLine("Fetch<" + alias.beanDataType + ", " + alias.parentBeanDataType + "> " + alias.name + " = " + alias.parentName + ".fetch(\"" + alias.propertyName + "\");");
				}
				skipLine();
				
				writeLine("if (" + property.name + "Id == null){");
				writeLine("criteria.where(builder.isNull(((Join<" + property.beanDataType + "," + bean.className + ">)" + property.name + ").get(\"id\")));");
				writeLine("} else {");
				writeLine("criteria.where(builder.equal(((Join<" + property.beanDataType + "," + bean.className + ">)" + property.name + ").get(\"id\"), " + property.name + "Id));");
				writeLine("}");				
				skipLine();
				
				writeLine("criteria.select(root);");
				writeLine("List<Order> orders = new ArrayList<>();");		
				writeLine("addOrder(builder, orders, root.get(\"id\"), OrderType.DESC);");
				writeLine("criteria.orderBy(orders);");
				skipLine();
				writeLine("return session.createQuery(criteria).getResultList();");
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
		
		writeLine("Session session = this.sessionFactory.getCurrentSession();");
		writeLine("CriteriaBuilder builder = session.getCriteriaBuilder();");
		writeLine("CriteriaQuery<Long> criteria = builder.createQuery(Long.class);");
		skipLine();
		
		writeLine("Root<" + this.bean.className + "> root = criteria.from(" + this.bean.className + ".class);");
		for (Alias alias : getAllAliases(bean, null)) {
			writeLine("Join<" + alias.beanDataType + ", " + alias.parentBeanDataType + "> " + alias.name + " = " + alias.parentName + ".join(\"" + alias.propertyName + "\");");
		}
		skipLine();
		
		writeLine("List<Predicate> predicates = new ArrayList<>();");		
		for (ViewProperty property : this.bean.basicViewBean.properties) {
			writeRestriction(property);
		}
			
		writeLine("criteria.where(predicates.toArray(new Predicate[predicates.size()]));");
		skipLine();
		
		writeLine("criteria.select(builder.count(root));");	 
		writeLine("return session.createQuery(criteria).getSingleResult();");
		writeLine("}");
		skipLine();
		


		
		for (Property property : this.bean.properties) {
			if (property.referenceBean != null && property.relation.equals(RelationType.MANY_TO_ONE)) {

				writeLine("/**");
				writeLine(" * count object list from " + property.referenceBean.objectName); 
				writeLine(" */");
				writeLine("public Long countFrom" + property.capName + "(" + property.referenceBean.idType + " " + property.name + "Id) {");
				writeLine("Session session = this.sessionFactory.getCurrentSession();");
				writeLine("CriteriaBuilder builder = session.getCriteriaBuilder();");
				writeLine("CriteriaQuery<Long> criteria = builder.createQuery(Long.class);");
				skipLine();
				
				writeLine("Root<" + this.bean.className + "> root = criteria.from(" + this.bean.className + ".class);");
				writeLine("Join<" + property.beanDataType + ", " + bean.className + "> " + property.name + " = root.join(\"" + property.name + "\");");
				skipLine();
				
				writeLine("if (" + property.name + "Id == null){");
				writeLine("criteria.where(builder.isNull(" + property.name + ".get(\"id\")));");
				writeLine("} else {");
				writeLine("criteria.where(builder.equal(" + property.name + ".get(\"id\"), " + property.name + "Id));");
				writeLine("}");				
				skipLine();
				
				writeLine("criteria.select(builder.count(root));");	 
				writeLine("return session.createQuery(criteria).getSingleResult();");
				writeLine("}");
				skipLine();
				
				
				writeLine("/**");
				writeLine(" * count filtered object list from " + property.referenceBean.objectName); 
				writeLine(" */");
				writeLine("public Long countFrom" + property.capName + "(" + property.referenceBean.idType + " " + property.name + "Id, " + bean.basicViewBean.filterClassName + " filter) {");
				writeLine("Session session = this.sessionFactory.getCurrentSession();");
				writeLine("CriteriaBuilder builder = session.getCriteriaBuilder();");
				writeLine("CriteriaQuery<Long> criteria = builder.createQuery(Long.class);");
				skipLine();
				
				writeLine("Root<" + this.bean.className + "> root = criteria.from(" + this.bean.className + ".class);");
				for (Alias alias : getAllAliases(bean, null)) {
					writeLine("Join<" + alias.beanDataType + ", " + alias.parentBeanDataType + "> " + alias.name + " = " + alias.parentName + ".join(\"" + alias.propertyName + "\");");
				}
				skipLine();
				
				writeLine("List<Predicate> predicates = new ArrayList<>();");		
				for (ViewProperty viewProperty : this.bean.basicViewBean.properties) {
					writeRestriction(viewProperty);
				}
				writeLine("if (" + property.name + "Id == null){");
				writeLine("predicates.add(builder.isNull(" + property.name + ".get(\"id\")));");
				writeLine("} else {");
				writeLine("predicates.add(builder.equal(" + property.name + ".get(\"id\"), " + property.name + "Id));");
				writeLine("}");				
				skipLine();
					
				writeLine("criteria.where(predicates.toArray(new Predicate[predicates.size()]));");
				skipLine();
				
				writeLine("criteria.select(builder.count(root));");	 
				writeLine("return session.createQuery(criteria).getSingleResult();");
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
		writeLine("public List<" + this.bean.className + "> scroll(" + bean.basicViewBean.filterClassName + " filter, " + bean.basicViewBean.sortingClassName + " sorting, Long firstResult, Long maxResults) {");
		
		writeLine("Session session = this.sessionFactory.getCurrentSession();");
		writeLine("CriteriaBuilder builder = session.getCriteriaBuilder();");
		writeLine("CriteriaQuery<" + this.bean.className + "> criteria = builder.createQuery(" + this.bean.className + ".class);");
		skipLine();
		
		writeLine("Root<" + this.bean.className + "> root = criteria.from(" + this.bean.className + ".class);");	
		for (Alias alias : getAllAliases(bean, null)) {
			writeLine("Fetch<" + alias.beanDataType + ", " + alias.parentBeanDataType + "> " + alias.name + "Fetch = " + alias.parentName + ".fetch(\"" + alias.propertyName + "\");");
			writeLine("Join<" + alias.beanDataType + ", " + alias.parentBeanDataType + "> " + alias.name + " = (Join<" + alias.beanDataType + ", " + alias.parentBeanDataType + ">)" + alias.name +"Fetch;");
		}
		skipLine();
		
		writeLine("List<Predicate> predicates = new ArrayList<>();");		
		for (ViewProperty property : this.bean.basicViewBean.properties) {
			writeRestriction(property);
		}
			
		writeLine("criteria.where(predicates.toArray(new Predicate[predicates.size()]));");
		skipLine();
		
		writeLine("criteria.select(root);");
		writeLine("List<Order> orders = new ArrayList<>();");
		
		for (ViewProperty property : this.bean.basicViewBean.properties) {
			String joinedAliasName = StringUtils.isEmpty(property.joinedAliasName)?"root":property.joinedAliasName;
			writeLine("addOrder(builder, orders, " + joinedAliasName + ".get(\"" + property.lastPropertyName + "\"), sorting.get" + property.capName + "OrderType());");
		}
		
		writeLine("addOrder(builder, orders, root.get(\"id\"), OrderType.DESC);");
		writeLine("criteria.orderBy(orders);");
		skipLine();		
		
		writeLine("Query<" + this.bean.className + "> query = session.createQuery(criteria);");
		writeLine("if (firstResult != null){");
		writeLine("query.setFirstResult(firstResult.intValue());");
		writeLine("}");
		writeLine("if (maxResults != null){");
		writeLine("query.setMaxResults(maxResults.intValue());");
		writeLine("}");
		writeLine("return query.getResultList();");
		writeLine("}");
		skipLine();
		
		for (Property property : this.bean.properties) {
			if (property.referenceBean != null && property.relation.equals(RelationType.MANY_TO_ONE)) {

				writeLine("/**");
				writeLine(" * scroll filtered object list from " + property.referenceBean.objectName); 
				writeLine(" */");
				writeLine("@Override");
				writeLine("@SuppressWarnings(\"unchecked\")");
				writeLine("public List<" + this.bean.className + "> scrollFrom" + property.capName + "(" + property.referenceBean.idType + " " + property.name + "Id, " + bean.basicViewBean.filterClassName + " filter, " + bean.basicViewBean.sortingClassName + " sorting, Long firstResult, Long maxResults) {");
				writeLine("Session session = this.sessionFactory.getCurrentSession();");
				writeLine("CriteriaBuilder builder = session.getCriteriaBuilder();");
				writeLine("CriteriaQuery<" + this.bean.className + "> criteria = builder.createQuery(" + this.bean.className + ".class);");
				skipLine();
				
				writeLine("Root<" + this.bean.className + "> root = criteria.from(" + this.bean.className + ".class);");	
				for (Alias alias : getAllAliases(bean, null)) {
					writeLine("Fetch<" + alias.beanDataType + ", " + alias.parentBeanDataType + "> " + alias.name + "Fetch = " + alias.parentName + ".fetch(\"" + alias.propertyName + "\");");
					writeLine("Join<" + alias.beanDataType + ", " + alias.parentBeanDataType + "> " + alias.name + " = (Join<" + alias.beanDataType + ", " + alias.parentBeanDataType + ">)" + alias.name +"Fetch;");
				}
				skipLine();
				
				writeLine("List<Predicate> predicates = new ArrayList<>();");		
				for (ViewProperty viewProperty : this.bean.basicViewBean.properties) {
					writeRestriction(viewProperty);
				}
				writeLine("if (" + property.name + "Id == null){");
				writeLine("predicates.add(builder.isNull(" + property.name + ".get(\"id\")));");
				writeLine("} else {");
				writeLine("predicates.add(builder.equal(" + property.name + ".get(\"id\"), " + property.name + "Id));");
				writeLine("}");
				writeLine("criteria.where(predicates.toArray(new Predicate[predicates.size()]));");
				skipLine();
				
				writeLine("criteria.select(root);");
				writeLine("List<Order> orders = new ArrayList<>();");
				
				for (ViewProperty viewProperty : this.bean.basicViewBean.properties) {
					String joinedAliasName = StringUtils.isEmpty(viewProperty.joinedAliasName)?"root":viewProperty.joinedAliasName;
					writeLine("addOrder(builder, orders, " + joinedAliasName + ".get(\"" + viewProperty.lastPropertyName + "\"), sorting.get" + viewProperty.capName + "OrderType());");
				}
				
				writeLine("addOrder(builder, orders, root.get(\"id\"), OrderType.DESC);");
				writeLine("criteria.orderBy(orders);");
				skipLine();		
				
				writeLine("Query<" + this.bean.className + "> query = session.createQuery(criteria);");
				writeLine("if (firstResult != null){");
				writeLine("query.setFirstResult(firstResult.intValue());");
				writeLine("}");
				writeLine("if (maxResults != null){");
				writeLine("query.setMaxResults(maxResults.intValue());");
				writeLine("}");
				writeLine("return query.getResultList();");
				writeLine("}");
				skipLine();
			}
		}
	}
	
	private void createLoadOneToManyComponentList() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
            Bean currentBean = oneToManyComponent.referenceBean;
            writeLine("/**");
            writeLine(" * load one to many component " + currentBean.className + " list");
            writeLine(" */");
            writeLine("@Override");
			writeLine("public List<" + currentBean.className + "> load" + currentBean.className + "List(" + bean.idType + " " + bean.objectName + "Id) {");
			
			writeLine("Session session = this.sessionFactory.getCurrentSession();");
			writeLine("CriteriaBuilder builder = session.getCriteriaBuilder();");
			writeLine("CriteriaQuery<" + currentBean.className + "> criteria = builder.createQuery(" + currentBean.className + ".class);");
			skipLine();
			
			writeLine("Root<" + currentBean.className + "> root = criteria.from(" + currentBean.className + ".class);");
			writeLine("Join<" + oneToManyComponent.referenceProperty.beanDataType + ", " + currentBean.className + "> " + oneToManyComponent.referenceProperty.name + " = root.join(\"" + oneToManyComponent.referenceProperty.name + "\");");
			skipLine();
			
			writeLine("if (" + bean.objectName + "Id == null){");
			writeLine("criteria.where(builder.isNull(" + oneToManyComponent.referenceProperty.name + ".get(\"id\")));");
			writeLine("} else {");
			writeLine("criteria.where(builder.equal(" + oneToManyComponent.referenceProperty.name + ".get(\"id\"), " + bean.objectName + "Id));");
			writeLine("}");
			skipLine();
			
			writeLine("criteria.select(root);");
			writeLine("List<Order> orders = new ArrayList<>();");		
			writeLine("addOrder(builder, orders, root.get(\"id\"), OrderType.DESC);");
			writeLine("criteria.orderBy(orders);");
			skipLine();
			writeLine("return session.createQuery(criteria).getResultList();");
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
			writeLine("public Long count" + currentBean.className + "(" + bean.idType + " " + bean.objectName + "Id) {");
						
			writeLine("Session session = this.sessionFactory.getCurrentSession();");
			writeLine("CriteriaBuilder builder = session.getCriteriaBuilder();");
			writeLine("CriteriaQuery<Long> criteria = builder.createQuery(Long.class);");
			skipLine();
			
			writeLine("Root<" + currentBean.className + "> root = criteria.from(" + currentBean.className + ".class);");
			writeLine("Join<" + oneToManyComponent.referenceProperty.beanDataType + ", " + currentBean.className + "> " + oneToManyComponent.referenceProperty.name + " = root.join(\"" + oneToManyComponent.referenceProperty.name + "\");");
			skipLine();
			
			writeLine("if (" + bean.objectName + "Id == null){");
			writeLine("criteria.where(builder.isNull(" + oneToManyComponent.referenceProperty.name + ".get(\"id\")));");
			writeLine("} else {");
			writeLine("criteria.where(builder.equal(" + oneToManyComponent.referenceProperty.name + ".get(\"id\"), " + bean.objectName + "Id));");
			writeLine("}");
			skipLine();
			
			writeLine("criteria.select(builder.count(root));");	 
			writeLine("return session.createQuery(criteria).getSingleResult();");
			writeLine("}");
			skipLine();
			
			writeLine("/**");
			writeLine("@Override");
			writeLine(" * count filtered one to many component " + currentBean.className);
			writeLine(" */");
			writeLine("public Long count" + currentBean.className + "(" + bean.idType + " " + bean.objectName + "Id, " + currentBean.basicViewBean.filterClassName + " filter) {");
			
			writeLine("Session session = this.sessionFactory.getCurrentSession();");
			writeLine("CriteriaBuilder builder = session.getCriteriaBuilder();");
			writeLine("CriteriaQuery<Long> criteria = builder.createQuery(Long.class);");
			skipLine();
			
			writeLine("Root<" + currentBean.className + "> root = criteria.from(" + currentBean.className + ".class);");
			writeLine("Join<" + oneToManyComponent.referenceProperty.beanDataType + ", " + currentBean.className + "> " + oneToManyComponent.referenceProperty.name + " = root.join(\"" + oneToManyComponent.referenceProperty.name + "\");");
			for (Alias alias : getAllAliases(currentBean, null)) {
				writeLine("Join<" + alias.beanDataType + ", " + alias.parentBeanDataType + "> " + alias.name + " = " + alias.parentName + ".join(\"" + alias.propertyName + "\");");
			}
			skipLine();
			
			writeLine("List<Predicate> predicates = new ArrayList<>();");		
			for (ViewProperty viewProperty : currentBean.basicViewBean.properties) {
				writeRestriction(viewProperty);
			}			
			writeLine("if (" + bean.objectName + "Id == null){");
			writeLine("predicates.add(builder.isNull(" + oneToManyComponent.referenceProperty.name + ".get(\"id\")));");
			writeLine("} else {");
			writeLine("predicates.add(builder.equal(" + oneToManyComponent.referenceProperty.name + ".get(\"id\"), " + bean.objectName + "Id));");
			writeLine("}");
			writeLine("criteria.where(predicates.toArray(new Predicate[predicates.size()]));");
			skipLine();
			
			writeLine("criteria.select(builder.count(root));");	 
			writeLine("return session.createQuery(criteria).getSingleResult();");
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
			writeLine("@SuppressWarnings(\"unused\")");
			writeLine("public List<" + currentBean.className + "> scroll" + currentBean.className + "(" + bean.idType + " " + bean.objectName + "Id, " + currentBean.basicViewBean.filterClassName + " filter, " + currentBean.basicViewBean.sortingClassName + " sorting, Long firstResult, Long maxResults) {");
			
			writeLine("Session session = this.sessionFactory.getCurrentSession();");
			writeLine("CriteriaBuilder builder = session.getCriteriaBuilder();");
			writeLine("CriteriaQuery<" + currentBean.className + "> criteria = builder.createQuery(" + currentBean.className + ".class);");
			skipLine();
			
			writeLine("Root<" + currentBean.className + "> root = criteria.from(" + currentBean.className + ".class);");
			writeLine("Join<" + oneToManyComponent.referenceProperty.beanDataType + ", " + currentBean.className + "> " + oneToManyComponent.referenceProperty.name + " = root.join(\"" + oneToManyComponent.referenceProperty.name + "\");");
			for (Alias alias : getAllAliases(currentBean, null)) {
				writeLine("Join<" + alias.beanDataType + ", " + alias.parentBeanDataType + "> " + alias.name + " = " + alias.parentName + ".join(\"" + alias.propertyName + "\");");
			}
			skipLine();
			
			writeLine("List<Predicate> predicates = new ArrayList<>();");		
			for (ViewProperty viewProperty : currentBean.basicViewBean.properties) {
				writeRestriction(viewProperty);
			}			
			writeLine("if (" + bean.objectName + "Id == null){");
			writeLine("predicates.add(builder.isNull(" + oneToManyComponent.referenceProperty.name + ".get(\"id\")));");
			writeLine("} else {");
			writeLine("predicates.add(builder.equal(" + oneToManyComponent.referenceProperty.name + ".get(\"id\"), " + bean.objectName + "Id));");
			writeLine("}");
			writeLine("criteria.where(predicates.toArray(new Predicate[predicates.size()]));");
			skipLine();
			
			writeLine("criteria.select(root);");
			writeLine("List<Order> orders = new ArrayList<>();");
			
			for (ViewProperty property : currentBean.basicViewBean.properties) {
				String joinedAliasName = StringUtils.isEmpty(property.joinedAliasName)?"root":property.joinedAliasName;
				writeLine("addOrder(builder, orders, " + joinedAliasName + ".get(\"" + property.lastPropertyName + "\"), sorting.get" + property.capName + "OrderType());");
			}
			
			writeLine("addOrder(builder, orders, root.get(\"id\"), OrderType.DESC);");
			writeLine("criteria.orderBy(orders);");
			skipLine();		
			
			writeLine("Query<" + currentBean.className + "> query = session.createQuery(criteria);");
			writeLine("if (firstResult != null){");
			writeLine("query.setFirstResult(firstResult.intValue());");
			writeLine("}");
			writeLine("if (maxResults != null){");
			writeLine("query.setMaxResults(maxResults.intValue());");
			writeLine("}");
			writeLine("return query.getResultList();");
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
			writeLine("public " + currentBean.className + " load" + currentBean.className + "(" + currentBean.idType + " id) {");
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

		writeLine("Session session = this.sessionFactory.getCurrentSession();");
		writeLine("CriteriaBuilder builder = session.getCriteriaBuilder();");
		writeLine("CriteriaQuery<" + this.bean.className + "> criteria = builder.createQuery(" + this.bean.className + ".class);");
		skipLine();
			
		writeLine("Root<" + this.bean.className + "> root = criteria.from(" + this.bean.className + ".class);");	
		for (Alias alias : getReferenceAliases(bean, "")) {
			writeLine("Join<" + alias.beanDataType + ", " + alias.parentBeanDataType + "> " + alias.name + " = " + alias.parentName + ".join(\"" + alias.propertyName + "\");");
		}
		skipLine();
		
		writeLine("List<Predicate> predicates = new ArrayList<>();");		
		for (ViewProperty property : this.bean.referenceViewProperties) {
			writeEqualsRestriction(property);
		}
			
		writeLine("criteria.where(predicates.toArray(new Predicate[predicates.size()]));");
		skipLine();
		
		writeLine("criteria.select(root);");
		skipLine();
		writeLine("return session.createQuery(criteria).getSingleResult();");
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
		
		
		start = true;
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

		start = true;
		write(this.bean.className + " " + this.bean.objectName + " = findOrNull(");
		for (ViewProperty property:findPropertyList) {
			if (start) start = false; else write(", ");
			write(property.name);
		}
		writeLine(");");
		writeLine("return " + this.bean.objectName + " != null;");
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
		writeLine("Session session = this.sessionFactory.getCurrentSession();");
		writeLine("CriteriaBuilder builder = session.getCriteriaBuilder();");
		writeLine("CriteriaQuery<" + this.bean.className + "> criteria = builder.createQuery(" + this.bean.className + ".class);");
		skipLine();
		
		writeLine("Root<" + this.bean.className + "> root = criteria.from(" + this.bean.className + ".class);");	
		skipLine();
		
		writeLine("Predicate predicate = getStringContainsRestriction(builder, root.get(\"" + targetProperty.name + "\"), arg);");
		writeLine("if (predicate!=null){");
		writeLine("criteria.where(predicate);");
		writeLine("}");
		skipLine();
		
		writeLine("criteria.select(root);");
		skipLine();		
		
		writeLine("Query<" + this.bean.className + "> query = session.createQuery(criteria);");
		writeLine("query.setMaxResults(20);");
		writeLine("return query.getResultList();");
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

		public String propertyName;
	    public String name;
	    public String parentName;
	    public String beanDataType;
	    public String parentBeanDataType;
	}	
	
	private List<Alias> getAliases(Bean bean, int size, String parentName) {
		List<Alias> aliasList = new ArrayList<Alias>();

		for (int i = 0; i < size; i++) {
			Property currentProperty = bean.properties.get(i);
			if (currentProperty.visibility.isListVisible() && currentProperty.referenceBean != null && !currentProperty.relation.isComponentLink()) {
				Alias alias = new Alias();
				alias.propertyName = currentProperty.name;
				alias.parentName = StringUtils.isEmpty(parentName)?"root":parentName;
				alias.name = StringUtils.isEmpty(parentName)?currentProperty.name:parentName+JavaClassNaming.getClassNameFromObjectName(currentProperty.name);
				alias.parentBeanDataType = bean.className;
				alias.beanDataType = currentProperty.beanDataType;
				aliasList.add(alias);
				aliasList.addAll(getAliases(currentProperty, alias.name));
			}
		}

		return aliasList;
	}
	
	private List<Alias> getReferenceAliases(Bean bean, String parentName) {
		return getAliases(bean, bean.cardinality, parentName);
	}
	
	private List<Alias> getAllAliases(Bean bean, String parentName) {
		return getAliases(bean, bean.properties.size(), parentName);
	}
	
	private List<Alias> getAliases(Property property, String parentName) {
		if (property.embedded) {
			return getAllAliases(property.referenceBean, parentName);
		} else {
			return getReferenceAliases(property.referenceBean, parentName);
		}
	}
	
	
	private void writeRestriction(ViewProperty property) {
		
		switch (property.dataType) {
			case BOOLEAN :
				writeBooleanRestriction(property);
				return;
			case SHORT :
			case INTEGER :
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
		String joinedAliasName = StringUtils.isEmpty(property.joinedAliasName)?"root":property.joinedAliasName;
		writeLine("addStringContainsRestriction(builder, predicates, " + joinedAliasName + ".get(\"" + property.lastPropertyName + "\"), filter.get" + property.capName + "());");
	}
	
	private void writeBooleanRestriction(ViewProperty property) {
		String joinedAliasName = StringUtils.isEmpty(property.joinedAliasName)?"root":property.joinedAliasName;
		writeLine("addBooleanRestriction(builder, predicates, " + joinedAliasName + ".get(\"" + property.lastPropertyName + "\"), filter.get" + property.capName + "());");
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
		String joinedAliasName = StringUtils.isEmpty(property.joinedAliasName)?"root":property.joinedAliasName;
		writeLine("addBetweenRestriction(builder, predicates, " + joinedAliasName + ".get(\"" + property.lastPropertyName + "\"), filter.get" + property.capName + "MinValue(), filter.get" + property.capName + "MaxValue());");
	}
	
	private void writeEqualsRestriction(ViewProperty property) {
		String joinedAliasName = StringUtils.isEmpty(property.joinedAliasName)?"root":property.joinedAliasName;
		writeLine("addEqualsRestriction(builder, predicates, " + joinedAliasName + ".get(\"" + property.lastPropertyName + "\"), " + property.name + ");");
	}
}
