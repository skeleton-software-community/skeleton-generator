package org.sklsft.generator.bc.file.command.impl.java.bc.mapper;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sklsft.generator.bc.file.command.impl.java.JavaFileWriteCommand;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.OneToMany;
import org.sklsft.generator.model.om.OneToManyComponent;
import org.sklsft.generator.model.om.OneToOne;
import org.sklsft.generator.model.om.Property;
import org.sklsft.generator.model.om.UniqueComponent;

public class BaseMapperImplFileWriteCommand extends JavaFileWriteCommand {

	private Bean bean;
	private Set<String> daoSet;

	/*
	 * constructor
	 */
	public BaseMapperImplFileWriteCommand(Bean bean) {
		super(bean.myPackage.model.project.workspaceFolder + File.separator + bean.myPackage.model.project.projectName + "-business-component\\src\\main\\java\\"
				+ bean.myPackage.baseMapperImplPackageName.replace(".", "\\"), bean.baseMapperClassName);

		this.bean = bean;
	}

	@Override
	protected void fetchSpecificImports() {

		this.daoSet = new HashSet<>();

		javaImports.add("import " + this.bean.myPackage.model.daoExceptionPackageName + ".ObjectNotFoundException;");
		javaImports.add("import org.springframework.beans.factory.annotation.Autowired;");
		javaImports.add("import " + this.bean.myPackage.omPackageName + "." + this.bean.className + ";");
		javaImports.add("import " + this.bean.myPackage.ovPackageName + "." + this.bean.viewClassName + ";");
		javaImports.add("import " + this.bean.myPackage.DAOInterfacePackageName + "." + this.bean.daoInterfaceName + ";");
		this.daoSet.add(this.bean.daoClassName);

		for (Property property : this.bean.properties) {
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

			for (Property property : currentBean.properties) {
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

			for (Property property : currentBean.properties) {
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
			for (Property property : currentBean.properties) {
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
			for (Property property : currentBean.properties) {
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

		writeLine("package " + this.bean.myPackage.baseMapperImplPackageName + ";");
		skipLine();
		
		writeImports();
		skipLine();
		
		writeLine("/**");
		writeLine(" * auto generated base mapper class file");
		writeLine(" * <br/>no modification should be done to this file");
		writeLine(" * <br/>processed by skeleton-generator");
		writeLine(" */");
		writeLine("public class " + this.bean.baseMapperClassName + " {");
		skipLine();

		writeLine("/*");
		writeLine(" * properties");
		writeLine(" */");
		writeLine("@Autowired");
		writeLine("protected " + this.bean.daoInterfaceName + " " + this.bean.daoObjectName + ";");

		this.daoSet = new HashSet<>();
		this.daoSet.add(this.bean.daoClassName);

		for (Property property : this.bean.properties) {
			if (property.referenceBean != null) {
				boolean test = this.daoSet.add(property.referenceBean.daoClassName);
				if (test) {
					writeLine("@Autowired");
					writeLine("protected " + property.referenceBean.daoInterfaceName + " " + property.referenceBean.daoObjectName + ";");

				}
			}
		}

		for (UniqueComponent uniqueComponent : this.bean.uniqueComponentList) {
			Bean currentBean = uniqueComponent.referenceBean;
			for (Property property : currentBean.properties) {
				if (property.referenceBean != null) {
					boolean test = this.daoSet.add(property.referenceBean.daoClassName);
					if (test) {
						writeLine("@Autowired");
						writeLine("protected " + property.referenceBean.daoInterfaceName + " " + property.referenceBean.daoObjectName + ";");

					}
				}
			}
		}

		for (OneToManyComponent oneToManyComponent : this.bean.oneToManyComponentList) {
			Bean currentBean = oneToManyComponent.referenceBean;
			for (Property property : currentBean.properties) {
				if (property.referenceBean != null) {
					boolean test = this.daoSet.add(property.referenceBean.daoClassName);
					if (test) {
						writeLine("@Autowired");
						writeLine("protected " + property.referenceBean.daoInterfaceName + " " + property.referenceBean.daoObjectName + ";");

					}
				}
			}
		}

		for (OneToMany oneToMany : this.bean.oneToManyList) {
			Bean currentBean = oneToMany.referenceBean;
			boolean test = this.daoSet.add(currentBean.daoClassName);
			if (test) {
				writeLine("@Autowired");
				writeLine("protected " + currentBean.daoInterfaceName + " " + currentBean.daoObjectName + ";");

			}
			for (Property property : currentBean.properties) {
				if (property.referenceBean != null) {
					test = this.daoSet.add(property.referenceBean.daoClassName);
					if (test) {
						writeLine("@Autowired");
						writeLine("protected " + property.referenceBean.daoInterfaceName + " " + property.referenceBean.daoObjectName + ";");

					}
				}
			}
		}

		for (OneToOne oneToOne : this.bean.oneToOneList) {
			Bean currentBean = oneToOne.referenceBean;
			boolean test = this.daoSet.add(currentBean.daoClassName);
			if (test) {
				writeLine("@Autowired");
				writeLine("protected " + currentBean.daoInterfaceName + " " + currentBean.daoObjectName + ";");

			}
			for (Property property : currentBean.properties) {
				if (property.referenceBean != null) {
					test = this.daoSet.add(property.referenceBean.daoClassName);
					if (test) {
						writeLine("@Autowired");
						writeLine("protected " + property.referenceBean.daoInterfaceName + " " + property.referenceBean.daoObjectName + ";");

					}
				}
			}
		}
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
		writeLine("public " + this.bean.viewClassName + " map" + this.bean.viewClassName + "(" + this.bean.viewClassName + " " + this.bean.viewObjectName + ", " + this.bean.className + " "
				+ this.bean.objectName + ") {");
		writeLine(this.bean.viewObjectName + ".setSelected(false);");

		for (Property property : this.bean.properties) {
			if (property.referenceBean == null) {
				writeLine(this.bean.viewObjectName + "." + property.setterName + "(" + this.bean.objectName + "." + property.fetchName + ");");
			} else {
				List<Property> findPropertyList = property.referenceBean.getFindProperties();
				if (property.nullable) {
					writeLine("if (" + this.bean.objectName + "." + property.getterName + "() != null) {");
					for (Property findProperty : findPropertyList) {
						writeLine(this.bean.viewObjectName + "." + property.setterName + findProperty.capName + "(" + this.bean.objectName + "." + property.fetchName + "." + findProperty.fetchName
								+ ");");
					}
					writeLine("}");

				} else {
					for (Property findProperty : findPropertyList) {
						writeLine(this.bean.viewObjectName + "." + property.setterName + findProperty.capName + "(" + this.bean.objectName + "." + property.getterName + "()." + findProperty.fetchName
								+ ");");
					}
				}
			}
		}

		writeLine("return " + this.bean.viewObjectName + ";");

		writeLine("}");
		skipLine();

		writeLine("/**");
		writeLine(" * mapping visible object to business object");
		writeLine(" */");
		writeLine("public " + this.bean.className + " map" + this.bean.className + "(" + this.bean.className + " " + this.bean.objectName + ", " + this.bean.viewClassName + " "
				+ this.bean.viewObjectName + ") throws ObjectNotFoundException {");

		for (Property property : this.bean.properties) {
			if (property.referenceBean == null) {
				writeLine(this.bean.objectName + "." + property.setterName + "(" + this.bean.viewObjectName + "." + property.fetchName + ");");
			} else {
				List<Property> findPropertyList = property.referenceBean.getFindProperties();
				writeLine(this.bean.objectName + "." + property.setterName + "(" + property.referenceBean.daoObjectName + ".find" + property.referenceBean.className + "(");
				writeLine(this.bean.viewObjectName + "." + property.getterName + findPropertyList.get(0).capName + "()");
				for (int j = 1; j < findPropertyList.size(); j++) {
					writeLine("," + this.bean.viewObjectName + "." + property.getterName + findPropertyList.get(j).capName + "()");
				}

				writeLine("));");
			}
		}

		writeLine("return " + this.bean.objectName + ";");
		writeLine("}");
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
					+ " " + currentBean.objectName + ") {");

			writeLine(currentBean.viewObjectName + ".setSelected(false);");

			for (Property property : currentBean.properties) {
				if (property.referenceBean == null) {
					writeLine(currentBean.viewObjectName + "." + property.setterName + "(" + currentBean.objectName + "." + property.fetchName + ");");

				} else {
					List<Property> findPropertyList = property.referenceBean.getFindProperties();
					if (property.nullable) {
						writeLine("if (" + currentBean.objectName + "." + property.getterName + "() != null) {");

						for (Property findProperty : findPropertyList) {
							writeLine(currentBean.viewObjectName + "." + property.setterName + findProperty.capName + "(" + currentBean.objectName + "." + property.fetchName + "."
									+ findProperty.fetchName + ");");
						}
						writeLine("}");

					} else {
						for (Property findProperty : findPropertyList) {
							writeLine(currentBean.viewObjectName + "." + property.setterName + findProperty.capName + "(" + currentBean.objectName + "." + property.getterName + "()."
									+ findProperty.fetchName + ");");
						}
					}
				}
			}

			writeLine("return " + currentBean.viewObjectName + ";");
			writeLine("}");
			skipLine();

			writeLine("/**");
			writeLine(" * mapping visible unique component to business unique component " + currentBean.objectName);
			writeLine(" */");
			skipLine();

			writeLine("public " + currentBean.className + " map" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ", " + currentBean.viewClassName + " "
					+ currentBean.viewObjectName + ") throws ObjectNotFoundException {");

			for (Property property : currentBean.properties) {
				if (property.referenceBean == null) {
					writeLine(currentBean.objectName + "." + property.setterName + "(" + currentBean.viewObjectName + "." + property.fetchName + ");");
				} else {
					List<Property> findPropertyList = property.referenceBean.getFindProperties();
					writeLine(currentBean.objectName + "." + property.setterName + "(" + property.referenceBean.daoObjectName + ".find" + property.referenceBean.className + "(");
					writeLine(currentBean.viewObjectName + "." + property.getterName + findPropertyList.get(0).capName + "()");
					for (int j = 1; j < findPropertyList.size(); j++) {
						writeLine("," + currentBean.viewObjectName + "." + property.getterName + findPropertyList.get(j).capName + "()");
					}
					writeLine("));");
				}
			}

			writeLine("return " + currentBean.objectName + ";");
			writeLine("}");
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
					+ " " + currentBean.objectName + ") {");
			writeLine(currentBean.viewObjectName + ".setSelected(false);");

			for (Property property : currentBean.properties) {
				if (property.referenceBean == null) {
					writeLine(currentBean.viewObjectName + "." + property.setterName + "(" + currentBean.objectName + "." + property.fetchName + ");");
				} else {
					List<Property> findPropertyList = property.referenceBean.getFindProperties();
					if (property.nullable) {
						writeLine("if (" + currentBean.objectName + "." + property.getterName + "() != null) {");

						for (Property findProperty : findPropertyList) {
							writeLine(currentBean.viewObjectName + "." + property.setterName + findProperty.capName + "(" + currentBean.objectName + "." + property.fetchName + "."
									+ findProperty.fetchName + ");");
						}
						writeLine("}");

					} else {
						for (Property findProperty : findPropertyList) {
							writeLine(currentBean.viewObjectName + "." + property.setterName + findProperty.capName + "(" + currentBean.objectName + "." + property.getterName + "()."
									+ findProperty.fetchName + ");");
						}
					}
				}
			}

			writeLine("return " + currentBean.viewObjectName + ";");
			writeLine("}");
			skipLine();

			writeLine("/**");
			writeLine(" * mapping visible one to many component to business one to many component " + currentBean.objectName);
			writeLine(" */");
			writeLine("public " + currentBean.className + " map" + currentBean.className + "(" + currentBean.className + " " + currentBean.objectName + ", " + currentBean.viewClassName + " "
					+ currentBean.viewObjectName + ") throws ObjectNotFoundException {");

			for (Property property : currentBean.properties) {
				if (property.referenceBean == null) {
					writeLine(currentBean.objectName + "." + property.setterName + "(" + currentBean.viewObjectName + "." + property.fetchName + ");");
				} else {
					List<Property> findPropertyList = property.referenceBean.getFindProperties();
					writeLine(currentBean.objectName + "." + property.setterName + "(" + property.referenceBean.daoObjectName + ".find" + property.referenceBean.className + "(");
					writeLine(currentBean.viewObjectName + "." + property.getterName + findPropertyList.get(0).capName + "()");
					for (int j = 1; j < findPropertyList.size(); j++) {
						writeLine("," + currentBean.viewObjectName + "." + property.getterName + findPropertyList.get(j).capName + "()");
					}

					writeLine("));");

				}
			}

			writeLine("return " + currentBean.objectName + ";");
			writeLine("}");
			skipLine();
		}
	}
}
