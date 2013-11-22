package org.skeleton.generator.bc.command.file.impl.java.model;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.skeleton.generator.bc.command.file.impl.java.JavaFileWriteCommand;
import org.skeleton.generator.model.om.Bean;
import org.skeleton.generator.model.om.OneToMany;
import org.skeleton.generator.model.om.OneToManyComponent;
import org.skeleton.generator.model.om.OneToOne;
import org.skeleton.generator.model.om.Property;
import org.skeleton.generator.model.om.UniqueComponent;
import org.skeleton.generator.util.metadata.DataType;
import org.skeleton.generator.util.metadata.DatabaseEngine;



public class EntityBeanFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	/*
	 * constructor
	 */
	public EntityBeanFileWriteCommand(Bean bean) {

		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-business-model\\src\\main\\java\\"
				+ bean.myPackage.omPackageName.replace(".", "\\"), bean.className);

		this.bean = bean;

	}

	@Override
	protected void fetchSpecificImports() {
		if (bean.myPackage.model.project.audited) {
			javaImports.add("import org.hibernate.envers.Audited;");
		}
		javaImports.add("import java.util.Collection;");
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

		for (Property property : this.bean.propertyList) {
			if (property.referenceBean != null) {
				javaImports.add("import " + property.referenceBean.myPackage.omPackageName + "." + property.referenceBean.className + ";");
			}
		}

		for (UniqueComponent uniqueComponent : this.bean.uniqueComponentList) {
			Bean currentBean = uniqueComponent.referenceBean;
			javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
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
		writeLine("@Table(name=" + (char) 34 + this.bean.table.name + (char) 34 + ")");
		if (!StringUtils.isEmpty(bean.annotations)) {
			writeLine(this.bean.annotations);
		}

		write("public class " + bean.className + " implements Serializable");
		if (!StringUtils.isEmpty(this.bean.interfaces)) {
			write(", " + this.bean.interfaces);
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
		writeLine("@Column(name = " + (char) 34 + "id" + (char) 34 + ", nullable = false)");
		writeLine("@SequenceGenerator(name = " + (char) 34 + "generator" + (char) 34 + ", sequenceName = " + (char) 34 + this.bean.table.name + "_id_seq" + (char) 34 + ", allocationSize=1)");
		writeLine("@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = " + (char) 34 + "generator" + (char) 34 + ")");
		writeLine("private Long id;");
		skipLine();

		for (int i = 1; i < this.bean.propertyList.size(); i++) {
			Property property = this.bean.propertyList.get(i);

			if (property.referenceBean != null) {
				writeLine("@ManyToOne(fetch = FetchType.LAZY)");
				if (bean.isComponent) {
					writeLine("@Fetch(FetchMode.JOIN)");
				}

				write("@JoinColumn(name = " + (char) 34 + property.column.name + (char) 34);
				if (!property.nullable) {
					write(", nullable = false");
				}
				if (property.unique) {
					write(", unique = true");
				}
				writeLine(")");
			} else {
				if (property.dataType.equals(DataType.DATETIME)) {
					writeLine("@Temporal(TemporalType.TIMESTAMP)");
				}
				if (property.dataType.equals(DataType.TEXT) && bean.myPackage.model.project.databaseEngine.equals(DatabaseEngine.ORACLE)) {
					writeLine("@Lob");
				}
				write("@Column(name = " + (char) 34 + property.column.name + (char) 34);
				if (!property.nullable) {
					write(", nullable = false");
				}
				if (property.unique) {
					write(", unique = true");
				}
				writeLine(")");
			}
			writeLine("private " + property.beanDataType + " " + property.name + ";");
			skipLine();
		}

		for (OneToMany oneToMany : this.bean.oneToManyList) {
			writeLine("@OneToMany(fetch = FetchType.LAZY, mappedBy = " + (char) 34 + oneToMany.referenceProperty.name + (char) 34 + ")");
			writeLine("private Collection <" + oneToMany.referenceBean.className + "> " + oneToMany.collectionName + ";");
			skipLine();
		}

		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			writeLine("@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)");
			writeLine("@JoinColumn(name = " + (char) 34 + oneToManyComponent.referenceColumn.name + (char) 34 + ", nullable=false)");
			writeLine("private Collection <" + oneToManyComponent.referenceBean.className + "> " + oneToManyComponent.collectionName + ";");
			skipLine();
		}

		for (OneToOne oneToOne : this.bean.oneToOneList) {
			writeLine("@OneToOne(fetch = FetchType.LAZY, mappedBy = " + (char) 34 + oneToOne.referenceProperty.name + (char) 34 + ")");
			writeLine("private " + oneToOne.referenceBean.className + " " + oneToOne.referenceBean.objectName + ";");
			skipLine();
		}

		for (UniqueComponent uniqueComponent : this.bean.uniqueComponentList) {
			writeLine("@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)");
			write("@JoinColumn(name = " + (char) 34 + uniqueComponent.referenceColumn.name + (char) 34 + ", unique = true");
			if (!uniqueComponent.referenceColumn.nullable) {
				write(", nullable = false");
			}
			writeLine(")");
			writeLine("private " + uniqueComponent.referenceBean.className + " " + uniqueComponent.referenceBean.objectName + ";");
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

		for (Property property : this.bean.propertyList) {
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
			writeLine("public Collection <" + oneToMany.referenceBean.className + "> " + oneToMany.collectionGetterName + " () {");
			writeLine("return this." + oneToMany.collectionName + ";");
			writeLine("}");
			skipLine();
			writeLine("public void " + oneToMany.collectionSetterName + "(Collection <" + oneToMany.referenceBean.className + "> " + oneToMany.collectionName + ") {");
			writeLine("this." + oneToMany.collectionName + " = " + oneToMany.collectionName + ";");
			writeLine("}");
			skipLine();
		}

		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			writeLine("public Collection <" + oneToManyComponent.referenceBean.className + "> " + oneToManyComponent.collectionGetterName + " () {");
			writeLine("return this." + oneToManyComponent.collectionName + ";");
			writeLine("}");
			skipLine();
			writeLine("public void " + oneToManyComponent.collectionSetterName + "(Collection <" + oneToManyComponent.referenceBean.className + "> " + oneToManyComponent.collectionName + ") {");
			writeLine("this." + oneToManyComponent.collectionName + " = " + oneToManyComponent.collectionName + ";");
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

		for (UniqueComponent uniqueComponent : this.bean.uniqueComponentList) {
			writeLine("public " + uniqueComponent.referenceBean.className + " " + uniqueComponent.getterName + "() {");
			writeLine("return this." + uniqueComponent.referenceBean.objectName + ";");
			writeLine("}");
			skipLine();
			writeLine("public void " + uniqueComponent.setterName + "(" + uniqueComponent.referenceBean.className + " " + uniqueComponent.referenceBean.objectName + ") {");
			writeLine("this." + uniqueComponent.referenceBean.objectName + " = " + uniqueComponent.referenceBean.objectName + ";");
			writeLine("}");
			skipLine();
		}
		skipLine();
	}
}
