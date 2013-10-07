package org.skeleton.generator.bc.command.file.impl.java.bc;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.skeleton.generator.bc.command.file.impl.java.JavaFileWriteCommand;
import org.skeleton.generator.model.om.Bean;
import org.skeleton.generator.model.om.OneToMany;
import org.skeleton.generator.model.om.OneToManyComponent;
import org.skeleton.generator.model.om.OneToOne;
import org.skeleton.generator.model.om.Property;
import org.skeleton.generator.model.om.UniqueComponent;

public class BaseMapperInterfaceFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;
	private Set<String> daoSet;

	/*
	 * constructor
	 */
	public BaseMapperInterfaceFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-business-component\\src\\main\\java\\"
				+ bean.myPackage.baseMapperInterfacePackageName.replace(".", "\\"), bean.baseMapperInterfaceName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {

		this.daoSet = new HashSet<>();

		javaImports.add("import " + this.bean.myPackage.model.daoExceptionPackageName + ".ObjectNotFoundException;");
		javaImports.add("import " + this.bean.myPackage.baseMapperInterfacePackageName + "." + this.bean.baseMapperInterfaceName + ";");
		javaImports.add("import " + this.bean.myPackage.omPackageName + "." + this.bean.className + ";");
		javaImports.add("import " + this.bean.myPackage.ovPackageName + "." + this.bean.viewClassName + ";");
		javaImports.add("import " + this.bean.myPackage.DAOInterfacePackageName + "." + this.bean.daoInterfaceName + ";");
		this.daoSet.add(this.bean.daoClassName);

		for (Property property : this.bean.propertyList) {
			if (property.referenceBean != null) {
				boolean test = this.daoSet.add(property.referenceBean.daoObjectName);
				if (test) {
					javaImports.add("import " + property.referenceBean.myPackage.DAOInterfacePackageName + "." + property.referenceBean.daoInterfaceName + ";");
				}
			}
		}

		for (UniqueComponent uniqueComponent : this.bean.uniqueComponentList) {

			Bean currentBean = uniqueComponent.referenceBean;
			javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.viewClassName + ";");

			for (Property property : currentBean.propertyList) {
				if (property.referenceBean != null) {
					boolean test = this.daoSet.add(property.referenceBean.daoObjectName);
					if (test) {
						javaImports.add("import " + currentBean.myPackage.DAOInterfacePackageName + "." + property.referenceBean.daoInterfaceName + ";");
					}
				}
			}
		}

		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.viewClassName + ";");

			for (Property property : currentBean.propertyList) {
				if (property.referenceBean != null) {
					boolean test = this.daoSet.add(property.referenceBean.daoObjectName);
					if (test) {
						javaImports.add("import " + property.referenceBean.myPackage.DAOInterfacePackageName + "." + property.referenceBean.daoInterfaceName + ";");
					}
				}
			}
		}

		for (OneToMany oneToMany : this.bean.oneToManyList) {
			Bean currentBean = oneToMany.referenceBean;
			javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.viewClassName + ";");

			boolean test = daoSet.add(currentBean.daoObjectName);
			if (test) {
				javaImports.add("import " + currentBean.myPackage.DAOInterfacePackageName + "." + currentBean.daoInterfaceName + ";");

			}
			for (Property property : currentBean.propertyList) {
				if (property.referenceBean != null) {
					test = this.daoSet.add(property.referenceBean.daoObjectName);
					if (test) {
						javaImports.add("import " + property.referenceBean.myPackage.DAOInterfacePackageName + "." + property.referenceBean.daoInterfaceName + ";");
					}
				}
			}
		}

		for (OneToOne oneToOne : this.bean.oneToOneList) {
			Bean currentBean = oneToOne.referenceBean;
			javaImports.add("import " + currentBean.myPackage.omPackageName + "." + currentBean.className + ";");
			javaImports.add("import " + currentBean.myPackage.ovPackageName + "." + currentBean.viewClassName + ";");

			boolean test = daoSet.add(currentBean.daoObjectName);
			if (test) {
				javaImports.add("import " + currentBean.myPackage.DAOInterfacePackageName + "." + currentBean.daoInterfaceName + ";");
			}
			for (Property property : currentBean.propertyList) {
				if (property.referenceBean != null) {
					test = this.daoSet.add(property.referenceBean.daoObjectName);
					if (test) {
						javaImports.add("import " + property.referenceBean.myPackage.DAOInterfacePackageName + "." + property.referenceBean.daoInterfaceName + ";");
					}
				}
			}
		}

	}

	@Override
	protected void writeContent() throws IOException {

		writeLine("package " + this.bean.myPackage.baseMapperInterfacePackageName + ";");
		skipLine();
		
		writeImports();
		skipLine();
		
		writeLine("/**");
		writeLine(" * auto generated base mapper interface file");
		writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		skipLine();

		writeLine("public interface " + this.bean.baseMapperInterfaceName + " {");
		skipLine();

		createMapping();
		createUniqueComponentMapping();
		createOneToManyComponentMapping();

		writeLine("}");

	}

	private void createMapping() {
		writeLine("/**");
		writeLine(" * mapping business object to visible object");
		writeLine(" */");
		skipLine();
		writeLine("public " + this.bean.viewClassName + " map" + this.bean.viewClassName + "(" + this.bean.viewClassName + " " + this.bean.viewObjectName + ", " + this.bean.className + " "
				+ this.bean.objectName + ");");
		skipLine();

		writeLine("/**");
		writeLine(" * mapping visible object to business object");
		writeLine(" */");
		writeLine("public " + this.bean.className + " map" + this.bean.className + "(" + this.bean.className + " " + this.bean.objectName + ", " + this.bean.viewClassName + " "
				+ this.bean.viewObjectName + ") throws ObjectNotFoundException;");
		skipLine();
	}

	private void createUniqueComponentMapping() {
		for (UniqueComponent uniqueComponent : this.bean.uniqueComponentList) {
			Bean currentBean = uniqueComponent.referenceBean;

			writeLine("/**");
			writeLine(" * mapping business unique component to visible unique component " + currentBean.objectName);
			writeLine(" */");
			skipLine();

			writeLine("public " + currentBean.viewClassName + " map" + currentBean.viewClassName + "(" + currentBean.viewClassName + " " + currentBean.viewObjectName + ", " + currentBean.className
					+ " " + currentBean.objectName + ");");
			skipLine();

			writeLine("/**");
			writeLine(" * mapping visible unique component to business unique component " + currentBean.objectName);
			writeLine(" */");
			skipLine();

			writeLine("public " + currentBean.className + " map" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ", " + currentBean.viewClassName + " "
					+ currentBean.viewObjectName + ") throws ObjectNotFoundException;");
			skipLine();
		}
	}

	private void createOneToManyComponentMapping() {
		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;

			writeLine("/**");
			writeLine(" * mapping business one to many component to visible one to many component " + currentBean.objectName);
			writeLine(" */");
			writeLine("public " + currentBean.viewClassName + " map" + currentBean.viewClassName + "(" + currentBean.viewClassName + " " + currentBean.viewObjectName + ", " + currentBean.className
					+ " " + currentBean.objectName + ");");
			skipLine();

			writeLine("/**");
			writeLine(" * mapping visible one to many component to business one to many component " + currentBean.objectName);
			writeLine(" */");
			writeLine("public " + currentBean.className + " map" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ", " + currentBean.viewClassName + " "
					+ currentBean.viewObjectName + ") throws ObjectNotFoundException;");
			skipLine();
		}
	}
}
