package org.sklsft.generator.skeletons.core.commands.model;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.OneToOne;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.model.domain.business.Property;
import org.sklsft.generator.model.metadata.DataType;
import org.sklsft.generator.skeletons.commands.impl.typed.JavaFileWriteCommand;


/**
 * 
 * template for Entity file with Hibernate mapping
 * <br>Takes account of unique constraints defined by cardinality
 * @author Nicolas Thibault
 *
 */
public class EntityBeanFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	/*
	 * constructor
	 */
	public EntityBeanFileWriteCommand(Bean bean) {

		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-business-model" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator
				+ bean.myPackage.omPackageName.replace(".", File.separator), bean.className);

		this.bean = bean;

	}

	@Override
	protected void fetchSpecificImports() {
		if (bean.myPackage.model.project.audited) {
			javaImports.add("import org.hibernate.envers.Audited;");
		}
		javaImports.add("import java.util.Set;");
		javaImports.add("import java.util.Date;");
		javaImports.add("import java.io.Serializable;");
		javaImports.add("import javax.persistence.CascadeType;");
		javaImports.add("import javax.persistence.Column;");
		javaImports.add("import javax.persistence.Entity;");
		javaImports.add("import javax.persistence.FetchType;");
		javaImports.add("import javax.persistence.GeneratedValue;");
		javaImports.add("import javax.persistence.GenerationType;");
		javaImports.add("import javax.persistence.Id;");
		javaImports.add("import javax.persistence.JoinColumn;");
		javaImports.add("import javax.persistence.ManyToOne;");
		javaImports.add("import javax.persistence.OneToMany;");
		javaImports.add("import javax.persistence.OneToOne;");
		javaImports.add("import javax.persistence.SequenceGenerator;");
		javaImports.add("import javax.persistence.Table;");
		javaImports.add("import javax.persistence.Temporal;");
		javaImports.add("import javax.persistence.TemporalType;");
		javaImports.add("import javax.persistence.Lob;");
		javaImports.add("import org.hibernate.annotations.Fetch;");
		javaImports.add("import org.hibernate.annotations.FetchMode;");
		javaImports.add("import javax.persistence.UniqueConstraint;");
		javaImports.add("import org.hibernate.annotations.Type;");
		
		

		for (Property property : this.bean.properties) {
			if (property.referenceBean != null) {
				javaImports.add("import " + property.referenceBean.myPackage.omPackageName + "." + property.referenceBean.className + ";");
			}
		}

		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
		}

		for (OneToMany oneToMany : this.bean.oneToManyList) {
			Bean currentBean = oneToMany.referenceBean;
			javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
		}

		for (OneToOne oneToOne : this.bean.oneToOneList) {
			Bean currentBean = oneToOne.referenceBean;
			javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
		}
		
		for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
			Bean currentBean = oneToOneComponent.referenceBean;
			javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
		}
		
		if (bean.isComponent && !bean.isEmbedded) {
			
			Bean parentBean = bean.myPackage.model.findBean(bean.table.columns.get(1).referenceTable.originalName);
			javaImports.add("import " + parentBean.myPackage.omPackageName + "." + parentBean.className + ";");
		}
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + this.bean.myPackage.omPackageName + ";");
		skipLine();

		writeImports();
		skipLine();

		writeLine("/**");
		writeLine(" * auto generated entity class file");
		writeLine(" * <br/>write modifications between specific code marks");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		skipLine();

		writeLine("@Entity");
		if (bean.myPackage.model.project.audited) {
			writeLine("@Audited");
		}
		write("@Table(name=" + CHAR_34 + this.bean.table.name + CHAR_34 );
		
		if (this.bean.table.cardinality > 0) {
			skipLine();
			write(", uniqueConstraints = {@UniqueConstraint(columnNames = {");
			write(CHAR_34 + this.bean.table.columns.get(1).name + CHAR_34);
			for (int i = 2; i <= this.bean.table.cardinality; i++) {
				write(", " + CHAR_34 + this.bean.table.columns.get(i).name + CHAR_34);
			}
			
			writeLine("})})");
		}
		else{
			writeLine(")");
		}
		if (bean.annotations != null) {
			for (String annotation:bean.annotations) {
				writeLine(annotation);
			}
		}

		write("public class " + bean.className + " implements org.sklsft.commons.model.interfaces.Entity<Long>");
		if (bean.interfaces != null) {
			for (String interfaceElem:bean.interfaces) {
				write(", " + interfaceElem);
			}
		}
		writeLine(" {");
		skipLine();

		writeLine("private static final long serialVersionUID = 1L;");
		skipLine();

		createNoArgConstructor();
		createProperties();
		createGettersAndSetters();

		this.writeNotOverridableContent();

		write("}");
	}

	/* create no argument constructor */

	private void createNoArgConstructor() {
		writeLine("/*");
		writeLine(" * no argument constructor");
		writeLine(" */");

		writeLine("public " + this.bean.className + "(){");
		writeLine("}");
		skipLine();
	}

	/**
	 * 
	 */
	private void createProperties() {
		writeLine("/*");
		writeLine(" * properties");
		writeLine(" */");

		writeLine("@Id");
		writeLine("@Column(name = " + CHAR_34 + "id" + CHAR_34 + ", nullable = false)");
		writeLine("@SequenceGenerator(name = " + CHAR_34 + "generator" + CHAR_34 + ", sequenceName = " + CHAR_34 + this.bean.table.name + "_id_seq" + CHAR_34 + ", allocationSize=1)");
		writeLine("@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = " + CHAR_34 + "generator" + CHAR_34 + ")");
		writeLine("private Long id;");
		skipLine();
		
		if (bean.isComponent && !bean.isEmbedded) {
			
			Bean parentBean = bean.myPackage.model.findBean(bean.table.columns.get(1).referenceTable.originalName);
			
			if (bean.table.columns.get(1).annotations != null) {
				for (String annotation:bean.table.columns.get(1).annotations) {
					writeLine(annotation);
				}
			}
			
			writeLine("@ManyToOne(fetch = FetchType.LAZY)");
			writeLine("@JoinColumn(name = " + CHAR_34 + bean.table.columns.get(1).name + CHAR_34 + ", nullable = false)");				
			writeLine("private " + parentBean.className + " " + parentBean.objectName + ";");
			skipLine();
		}


		for (int i = 1; i < this.bean.properties.size(); i++) {
			Property property = this.bean.properties.get(i);
			
			if (property.annotations != null) {
				for (String annotation:property.annotations) {
					writeLine(annotation);
				}
			}

			if (property.referenceBean != null) {
				if (!property.embedded) {
					writeLine("@ManyToOne(fetch = FetchType.LAZY)");
					if (bean.isComponent) {
						writeLine("@Fetch(FetchMode.JOIN)");
					}
	
					write("@JoinColumn(name = " + CHAR_34 + property.column.name + CHAR_34);
					if (!property.nullable) {
						write(", nullable = false");
					}
					if (property.unique) {
						write(", unique = true");
					}
					writeLine(")");
					
					writeLine("private " + property.beanDataType + " " + property.name + ";");
					skipLine();
				} else {
					writeLine("@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)");
					write("@JoinColumn(name = " + CHAR_34 + property.column.name + CHAR_34 + ", unique = true");
					write(", nullable = false");
					writeLine(")");
					writeLine("private " + property.referenceBean.className + " " + property.name + ";");
					skipLine();
				}
			} else {
				if (property.dataType.equals(DataType.DATETIME)) {
					writeLine("@Temporal(TemporalType.TIMESTAMP)");
				}
				if (property.dataType.equals(DataType.TEXT)) {
					writeLine("@Lob");
					writeLine("@Type(type=" + CHAR_34 + "org.hibernate.type.TextType" + CHAR_34 + ")");
				}
				write("@Column(name = " + CHAR_34 + property.column.name + CHAR_34);
				if (!property.nullable) {
					write(", nullable = false");
				}
				if (property.unique) {
					write(", unique = true");
				}
				writeLine(")");
				
				writeLine("private " + property.beanDataType + " " + property.name + ";");
				skipLine();
			}
			
		}

		for (OneToMany oneToMany : this.bean.oneToManyList) {
			writeLine("@OneToMany(fetch = FetchType.LAZY, mappedBy = " + CHAR_34 + oneToMany.referenceProperty.name + CHAR_34 + ")");
			writeLine("private Set <" + oneToMany.referenceBean.className + "> " + oneToMany.collectionName + ";");
			skipLine();
		}

		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			write("@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true");
			writeLine(", mappedBy = " + CHAR_34 + oneToManyComponent.parentBean.objectName + CHAR_34 + ")");
			

			writeLine("private Set <" + oneToManyComponent.referenceBean.className + "> " + oneToManyComponent.collectionName + ";");
			skipLine();
		}

				
		for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
			writeLine("@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = " + CHAR_34 + oneToOneComponent.parentBean.objectName + CHAR_34 + ")");
			writeLine("@Fetch(FetchMode.JOIN)");
			writeLine("private " + oneToOneComponent.referenceBean.className + " " + oneToOneComponent.referenceBean.objectName + ";");
			skipLine();
		}
		
		
		for (OneToOne oneToOne : this.bean.oneToOneList) {
			writeLine("@OneToOne(fetch = FetchType.LAZY, mappedBy = " + CHAR_34 + bean.objectName + CHAR_34 + ")");
			writeLine("@Fetch(FetchMode.JOIN)");
			writeLine("private " + oneToOne.referenceBean.className + " " + oneToOne.referenceBean.objectName + ";");
			skipLine();
		}

		skipLine();

	}

	/**
     * 
     */
	private void createGettersAndSetters() {
		writeLine("/*");
		writeLine(" * getters and setters");
		writeLine(" */");
		
		if (bean.isComponent && !bean.isEmbedded) {
			
			Bean parentBean = bean.myPackage.model.findBean(bean.table.columns.get(1).referenceTable.originalName);
			
			writeLine("public " + parentBean.className + " get" + parentBean.className + "() {");
			writeLine("return this." + parentBean.objectName + ";");
			writeLine("}");
			skipLine();
			writeLine("public void set" + parentBean.className + "(" + parentBean.className + " " + parentBean.objectName + ") {");
			writeLine("this." + parentBean.objectName + " = " + parentBean.objectName + ";");
			writeLine("}");
			skipLine();
		}

		for (Property property : this.bean.properties) {
			writeLine("public " + property.beanDataType + " " + property.getterName + "() {");
			writeLine("return this." + property.name + ";");
			writeLine("}");
			skipLine();
			writeLine("public void " + property.setterName + "(" + property.beanDataType + " " + property.name + ") {");
			writeLine("this." + property.name + " = " + property.name + ";");
			writeLine("}");
			skipLine();
		}

		for (OneToMany oneToMany : this.bean.oneToManyList) {
			writeLine("public Set <" + oneToMany.referenceBean.className + "> " + oneToMany.collectionGetterName + " () {");
			writeLine("return this." + oneToMany.collectionName + ";");
			writeLine("}");
			skipLine();
			writeLine("public void " + oneToMany.collectionSetterName + "(Set <" + oneToMany.referenceBean.className + "> " + oneToMany.collectionName + ") {");
			writeLine("this." + oneToMany.collectionName + " = " + oneToMany.collectionName + ";");
			writeLine("}");
			skipLine();
		}

		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			writeLine("public Set <" + oneToManyComponent.referenceBean.className + "> " + oneToManyComponent.collectionGetterName + " () {");
			writeLine("return this." + oneToManyComponent.collectionName + ";");
			writeLine("}");
			skipLine();
			writeLine("public void " + oneToManyComponent.collectionSetterName + "(Set <" + oneToManyComponent.referenceBean.className + "> " + oneToManyComponent.collectionName + ") {");
			writeLine("this." + oneToManyComponent.collectionName + " = " + oneToManyComponent.collectionName + ";");
			writeLine("}");
			skipLine();
		}

		
		for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
			writeLine("public " + oneToOneComponent.referenceBean.className + " " + oneToOneComponent.getterName + " () {");
			writeLine("return this." + oneToOneComponent.referenceBean.objectName + ";");
			writeLine("}");
			skipLine();
			writeLine("public void " + oneToOneComponent.setterName + " (" + oneToOneComponent.referenceBean.className + " " + oneToOneComponent.referenceBean.objectName + ") {");
			writeLine("this." + oneToOneComponent.referenceBean.objectName + " = " + oneToOneComponent.referenceBean.objectName + ";");
			writeLine("}");
			skipLine();
		}
		
		
		for (OneToOne oneToOne : this.bean.oneToOneList) {
			writeLine("public " + oneToOne.referenceBean.className + " " + oneToOne.getterName + " () {");
			writeLine("return this." + oneToOne.referenceBean.objectName + ";");
			writeLine("}");
			skipLine();
			writeLine("public void " + oneToOne.setterName + " (" + oneToOne.referenceBean.className + " " + oneToOne.referenceBean.objectName + ") {");
			writeLine("this." + oneToOne.referenceBean.objectName + " = " + oneToOne.referenceBean.objectName + ";");
			writeLine("}");
			skipLine();
		}

		skipLine();
	}
}
