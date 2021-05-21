package org.sklsft.generator.skeletons.core.commands.model;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.OneToOne;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.model.domain.business.Property;
import org.sklsft.generator.model.util.naming.JavaClassNaming;
import org.sklsft.generator.skeletons.commands.impl.typed.JavaFileWriteCommand;


/**
 * 
 * template for Entity jpa meta model file<br>
 * 
 * @author Nicolas Thibault
 *
 */
public class EntityMetaModelFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;

	/*
	 * constructor
	 */
	public EntityMetaModelFileWriteCommand(Bean bean) {

		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.modelArtefactName + File.separator + bean.myPackage.model.javaSourcesFolder + File.separator
				+ bean.myPackage.omPackageName.replace(".", File.separator), bean.className + "_");

		this.bean = bean;

	}

	@Override
	protected void fetchSpecificImports() {
		
		javaImports.add("import java.util.Date;");
		javaImports.add("import java.time.LocalDate;");
		javaImports.add("import java.math.BigDecimal;");
		
		javaImports.add("import javax.persistence.metamodel.SetAttribute;");
		javaImports.add("import javax.persistence.metamodel.SingularAttribute;");
		javaImports.add("import javax.persistence.metamodel.StaticMetamodel;");
		

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
	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + this.bean.myPackage.omPackageName + ";");
		skipLine();

		writeImports();
		skipLine();

		writeLine("/**");
		writeLine(" * auto generated entity metamodel class file");
		writeLine(" * <br/>write modifications between specific code marks");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		writeLine("@StaticMetamodel(" + bean.className + ".class)");
		writeLine("public abstract class " + bean.className + "_ {");
		
		skipLine();
		
		createAttributes();
		createStaticNames();
		
		this.writeNotOverridableContent();

		write("}");
	}

	
	private void createAttributes() {

		writeLine("public static volatile SingularAttribute<" + bean.className + ", " + bean.idType + "> id;");
		
		for (Property property:bean.properties) {
			writeLine("public static volatile SingularAttribute<" + bean.className + ", " + property.beanDataType + "> " + property.name + ";");
		}

		for (OneToMany oneToMany : this.bean.oneToManyList) {
			writeLine("public static volatile SetAttribute<" + bean.className + ", " + oneToMany.referenceBean.className + "> " + oneToMany.collectionName + ";");
		}

		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			writeLine("public static volatile SetAttribute<" + bean.className + ", " + oneToManyComponent.referenceBean.className + "> " + oneToManyComponent.collectionName + ";");
		}

		for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
			writeLine("public static volatile SingularAttribute<" + bean.className + ", " + oneToOneComponent.referenceBean.className + "> " + oneToOneComponent.referenceBean.objectName + ";");
		}

		for (OneToOne oneToOne : this.bean.oneToOneList) {
			writeLine("public static volatile SingularAttribute<" + bean.className + ", " + oneToOne.referenceBean.className + "> " + oneToOne.referenceBean.objectName + ";");
		}

		skipLine();

	}
	
	private void createStaticNames() {

		writeLine("public static final String ID = \"id\";");
		
		for (Property property:bean.properties) {
			writeLine("public static final String " + JavaClassNaming.toDatabaseName(property.name) + " = \"" + property.name + "\";");
		}

		for (OneToMany oneToMany : this.bean.oneToManyList) {
			writeLine("public static final String " + JavaClassNaming.toDatabaseName(oneToMany.collectionName) + " = \"" + oneToMany.collectionName + "\";");
		}

		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			writeLine("public static final String " + JavaClassNaming.toDatabaseName(oneToManyComponent.collectionName) + " = \"" + oneToManyComponent.collectionName + "\";");
		}

		for (OneToOneComponent oneToOneComponent : this.bean.oneToOneComponentList) {
			writeLine("public static final String " + JavaClassNaming.toDatabaseName(oneToOneComponent.referenceBean.className) + " = \"" + oneToOneComponent.referenceBean.className + "\";");
		}

		for (OneToOne oneToOne : this.bean.oneToOneList) {
			writeLine("public static volatile SingularAttribute<" + bean.className + ", " + oneToOne.referenceBean.className + "> " + oneToOne.referenceBean.objectName + ";");
		}
		
		skipLine();
	}
}
