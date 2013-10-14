package org.skeleton.generator.bc.command.file.impl.presentation.jsf;

import org.skeleton.generator.bc.command.file.impl.presentation.XhtmlFileWriteCommand;
import org.skeleton.generator.model.om.Bean;
import org.skeleton.generator.model.om.Property;

public abstract class JsfXhtmlFileWriteCommand extends XhtmlFileWriteCommand {

	public JsfXhtmlFileWriteCommand(String folderName, String fileName) {
		super(folderName, fileName);
	}

	protected void writeListComponent(Property property, Bean bean) {
		switch (property.dataType) {
		case BOOLEAN:
			if (property.nullable) {
				writeLine("<h:outputText value=" + (char) 34 + "#{"
						+ bean.objectName + "." + property.name + "}"
						+ (char) 34 + "/>");
			} else {
				writeLine("<h:selectBooleanCheckbox value=" + (char) 34 + "#{"
						+ bean.objectName + "." + property.name + "}"
						+ (char) 34 + " disabled=" + (char) 34 + "true"
						+ (char) 34 + "/>");
			}
			break;

		case DATETIME:
			writeLine("<h:outputText value=" + (char) 34 + "#{"
					+ bean.objectName + "." + property.name + "}" + (char) 34
					+ ">");

			switch (property.format) {
			case DATE:
				writeLine("<f:convertDateTime type=" + (char) 34 + "date"
						+ (char) 34 + " dateStyle=" + (char) 34 + "medium"
						+ (char) 34 + "/>");
				break;

			default:
				writeLine("<f:convertDateTime type=" + (char) 34 + "both"
						+ (char) 34 + " dateStyle=" + (char) 34 + "medium"
						+ (char) 34 + "/>");
				break;
			}
			writeLine("</h:outputText>");

			break;

		case DOUBLE:
			writeLine("<h:outputText value=" + (char) 34 + "#{"
					+ bean.objectName + "." + property.name + "}" + (char) 34
					+ ">");

			switch (property.format) {
			case TWO_DECIMALS:
				writeLine("<f:convertNumber pattern=" + (char) 34 + "#,##0.00"
						+ (char) 34 + "/>");
				break;

			case FOUR_DECIMALS:
				writeLine("<f:convertNumber pattern=" + (char) 34
						+ "#,##0.0000" + (char) 34 + "/>");
				break;

			default:
				writeLine("<f:convertNumber pattern=" + (char) 34
						+ "#,##0.########" + (char) 34 + "/>");
				break;
			}
			writeLine("</h:outputText>");
			break;

		case LONG:
			writeLine("<h:outputText value=" + (char) 34 + "#{"
					+ bean.objectName + "." + property.name + "}" + (char) 34
					+ ">");
			writeLine("<f:convertNumber pattern=" + (char) 34 + "#,##0"
					+ (char) 34 + "/>");
			writeLine("</h:outputText>");
			break;

		case STRING:
			writeLine("<h:outputText value=" + (char) 34 + "#{"
					+ bean.objectName + "." + property.name + "}" + (char) 34
					+ "/>");
			break;

		case TEXT:
			writeLine("<h:outputText value=" + (char) 34 + ">" + (char) 34
					+ "/>");
			writeLine("<rich:toolTip style=" + (char) 34
					+ "background:white;border:white" + (char) 34 + ">");
			writeLine("<h:inputTextarea value=" + (char) 34 + "#{"
					+ bean.objectName + "." + property.name + "}" + (char) 34
					+ " rows=" + (char) 34 + "10" + (char) 34 + " readonly="
					+ (char) 34 + "true" + (char) 34 + "/>");
			writeLine("</rich:toolTip>");
			break;
		}
	}

	protected void writeDetailComponent(Property property, Bean bean) {
		if (!property.editable) {
			switch (property.dataType) {
			case BOOLEAN:
				if (property.nullable) {
					writeLine("<h:inputText id=" + (char) 34 + bean.objectName
							+ property.capName + (char) 34 + " style="
							+ (char) 34 + "width:300px" + (char) 34 + " value="
							+ (char) 34 + "#{" + bean.objectName + "."
							+ property.name + "}" + (char) 34);
					writeLine("readonly=" + (char) 34 + "true" + (char) 34
							+ "/>");
				} else {
					writeLine("<h:selectBooleanCheckbox id=" + (char) 34
							+ bean.objectName + property.capName + (char) 34
							+ " value=" + (char) 34 + "#{" + bean.objectName
							+ "." + property.name + "}" + (char) 34);
					writeLine("readonly=" + (char) 34 + "true" + (char) 34
							+ " disabled=" + (char) 34 + "true" + (char) 34
							+ "/>");
				}
				break;

			case DATETIME:
				writeLine("<h:inputText id=" + (char) 34 + bean.objectName
						+ property.capName + (char) 34 + " style=" + (char) 34
						+ "width:300px;background:lightgrey" + (char) 34
						+ " value=" + (char) 34 + "#{" + bean.objectName + "."
						+ property.name + "}" + (char) 34);
				writeLine("readonly=" + (char) 34 + "true" + (char) 34 + ">");

				switch (property.format) {
				case DATE:
					writeLine("<f:convertDateTime type=" + (char) 34 + "date"
							+ (char) 34 + " dateStyle=" + (char) 34 + "medium"
							+ (char) 34 + "/>");
					break;

				default:
					writeLine("<f:convertDateTime type=" + (char) 34 + "both"
							+ (char) 34 + " dateStyle=" + (char) 34 + "medium"
							+ (char) 34 + "/>");
					break;
				}
				writeLine("</h:inputText>");
				break;

			case DOUBLE:
				writeLine("<h:inputText id=" + (char) 34 + bean.objectName
						+ property.capName + (char) 34 + " style=" + (char) 34
						+ "width:300px;background:lightgrey" + (char) 34
						+ " value=" + (char) 34 + "#{" + bean.objectName + "."
						+ property.name + "}" + (char) 34);
				writeLine("readonly=" + (char) 34 + "true" + (char) 34 + ">");

				switch (property.format) {
				case TWO_DECIMALS:
					writeLine("<f:convertNumber pattern=" + (char) 34
							+ "#,##0.00" + (char) 34 + "/>");
					break;

				case FOUR_DECIMALS:
					writeLine("<f:convertNumber pattern=" + (char) 34
							+ "#,##0.0000" + (char) 34 + "/>");
					break;

				default:
					writeLine("<f:convertNumber pattern=" + (char) 34
							+ "#,##0.########" + (char) 34 + "/>");
					break;

				}
				writeLine("</h:inputText>");
				break;

			case LONG:
				writeLine("<h:inputText id=" + (char) 34 + bean.objectName
						+ property.capName + (char) 34 + " style=" + (char) 34
						+ "width:300px;background:lightgrey" + (char) 34
						+ " value=" + (char) 34 + "#{" + bean.objectName + "."
						+ property.name + "}" + (char) 34);
				writeLine("readonly=" + (char) 34 + "true" + (char) 34 + ">");
				writeLine("<f:convertNumber pattern=" + (char) 34 + "#,##0"
						+ (char) 34 + "/>");
				writeLine("</h:inputText>");
				break;

			case STRING:
				writeLine("<h:inputText id=" + (char) 34 + bean.objectName
						+ property.capName + (char) 34 + " style=" + (char) 34
						+ "width:300px;background:lightgrey" + (char) 34
						+ " value=" + (char) 34 + "#{" + bean.objectName + "."
						+ property.name + "}" + (char) 34);
				writeLine("readonly=" + (char) 34 + "true" + (char) 34 + ">");
				writeLine("</h:inputText>");
				break;

			case TEXT:
				writeLine("<h:inputTextarea id=" + (char) 34 + bean.objectName
						+ property.capName + (char) 34 + " style=" + (char) 34
						+ "width:600px;background:lightgrey" + (char) 34
						+ " rows=" + (char) 34 + "10" + (char) 34 + " value="
						+ (char) 34 + "#{" + bean.objectName + "."
						+ property.name + "}" + (char) 34);
				writeLine("readonly=" + (char) 34 + "true" + (char) 34 + ">");
				writeLine("</h:inputText>");
				break;
			}
		} else {
			if (property.comboBoxBean != null) {
				write("<h:selectOneMenu id=" + (char) 34 + bean.objectName
						+ property.capName + (char) 34 + " style=" + (char) 34
						+ "width:300px" + (char) 34 + " value=" + (char) 34
						+ "#{" + bean.objectName + "." + property.name + "}"
						+ (char) 34);
				if (!property.nullable) {
					skipLine();
					write(" required=" + (char) 34 + "true" + (char) 34
							+ " requiredMessage=" + (char) 34
							+ "#{i18n.mandatoryField}" + (char) 34);
				}
				writeLine(">");

				switch (property.dataType) {
				case DATETIME:
					switch (property.format) {
					case DATE:
						writeLine("<f:convertDateTime type=" + (char) 34
								+ "date" + (char) 34 + " dateStyle="
								+ (char) 34 + "long" + (char) 34 + "/>");
						break;

					default:
						writeLine("<f:convertDateTime type=" + (char) 34
								+ "both" + (char) 34 + " dateStyle="
								+ (char) 34 + "long" + (char) 34 + "/>");
						break;

					}
					break;

				case DOUBLE:
					switch (property.format) {
					case TWO_DECIMALS:
						writeLine("<f:convertNumber pattern=" + (char) 34
								+ "#,##0.00" + (char) 34 + "/>");
						break;

					case FOUR_DECIMALS:
						writeLine("<f:convertNumber pattern=" + (char) 34
								+ "#,##0.0000" + (char) 34 + "/>");
						break;

					default:
						writeLine("<f:convertNumber pattern=" + (char) 34
								+ "#,##0.########" + (char) 34 + "/>");
						break;
					}
					break;

				case LONG:
					writeLine("<f:convertNumber pattern=" + (char) 34 + "#,##0"
							+ (char) 34 + "/>");
					break;

				default:
					break;
				}

				writeLine("<f:selectItems value=" + (char) 34
						+ "#{commonController."
						+ property.comboBoxBean.objectName
						+ property.comboBoxBean.propertyList.get(1).capName
						+ "List}" + (char) 34 + "/>");
				writeLine("</h:selectOneMenu>");

			} else {
				switch (property.dataType) {
				case BOOLEAN:
					if (property.nullable) {
						writeLine("<h:inputText id=" + (char) 34
								+ bean.objectName + property.capName
								+ (char) 34 + " style=" + (char) 34
								+ "width:300px" + (char) 34 + " value="
								+ (char) 34 + "#{" + bean.objectName + "."
								+ property.name + "}" + (char) 34 + "/>");
					} else {
						writeLine("<h:selectBooleanCheckbox id=" + (char) 34
								+ bean.objectName + property.capName
								+ (char) 34 + " value=" + (char) 34 + "#{"
								+ bean.objectName + "." + property.name + "}"
								+ (char) 34);
						writeLine("readonly=" + (char) 34 + "false" + (char) 34
								+ " disabled=" + (char) 34 + "false"
								+ (char) 34 + "/>");
					}
					break;

				case DATETIME:
					writeLine("<rich:calendar id=" + (char) 34
							+ bean.objectName + property.capName + (char) 34
							+ " inputStyle=" + (char) 34 + "width:280px"
							+ (char) 34 + " value=" + (char) 34 + "#{"
							+ bean.objectName + "." + property.name + "}"
							+ (char) 34);

					switch (property.format) {
					case DATE:
						write(" datePattern=" + (char) 34 + "dd MMMM yyyy"
								+ (char) 34);
						break;

					default:
						write(" datePattern=" + (char) 34
								+ "dd MMMM yyyy HH:mm" + (char) 34);
						break;
					}

					if (!property.nullable) {
						skipLine();
						write("required=" + (char) 34 + "true" + (char) 34
								+ " requiredMessage=" + (char) 34
								+ "#{i18n.mandatoryField}" + (char) 34);
					}
					writeLine("/>");
					break;

				case DOUBLE:
					write("<h:inputText id=" + (char) 34 + bean.objectName
							+ property.capName + (char) 34 + " style="
							+ (char) 34 + "width:300px" + (char) 34 + " value="
							+ (char) 34 + "#{" + bean.objectName + "."
							+ property.name + "}" + (char) 34);
					if (!property.nullable) {
						skipLine();
						write("required=" + (char) 34 + "true" + (char) 34
								+ " requiredMessage=" + (char) 34
								+ "#{i18n.mandatoryField}" + (char) 34);
					}
					writeLine(">");

					switch (property.format) {
					case TWO_DECIMALS:
						writeLine("<f:convertNumber pattern=" + (char) 34
								+ "#,##0.00" + (char) 34 + "/>");
						break;

					case FOUR_DECIMALS:
						writeLine("<f:convertNumber pattern=" + (char) 34
								+ "#,##0.0000" + (char) 34 + "/>");
						break;

					default:
						writeLine("<f:convertNumber pattern=" + (char) 34
								+ "#,##0.########" + (char) 34 + "/>");
						break;
					}
					writeLine("</h:inputText>");
					break;

				case LONG:
					write("<h:inputText id=" + (char) 34 + bean.objectName
							+ property.capName + (char) 34 + " style="
							+ (char) 34 + "width:300px" + (char) 34 + " value="
							+ (char) 34 + "#{" + bean.objectName + "."
							+ property.name + "}" + (char) 34);
					if (!property.nullable) {
						skipLine();
						write("required=" + (char) 34 + "true" + (char) 34
								+ " requiredMessage=" + (char) 34
								+ "#{i18n.mandatoryField}" + (char) 34);
					}
					writeLine(">");
					writeLine("<f:convertNumber pattern=" + (char) 34 + "#,##0"
							+ (char) 34 + "/>");
					writeLine("</h:inputText>");
					break;

				case STRING:
					write("<h:inputText id=" + (char) 34 + bean.objectName
							+ property.capName + (char) 34 + " style="
							+ (char) 34 + "width:300px" + (char) 34 + " value="
							+ (char) 34 + "#{" + bean.objectName + "."
							+ property.name + "}" + (char) 34);
					if (!property.nullable) {
						skipLine();
						write("required=" + (char) 34 + "true" + (char) 34
								+ " requiredMessage=" + (char) 34
								+ "#{i18n.mandatoryField}" + (char) 34);
					}
					writeLine("/>");
					break;

				case TEXT:
					write("<h:inputTextarea id=" + (char) 34 + bean.objectName
							+ property.capName + (char) 34 + " style="
							+ (char) 34 + "width:600px" + (char) 34 + " rows="
							+ (char) 34 + "10" + (char) 34 + " value="
							+ (char) 34 + "#{" + bean.objectName + "."
							+ property.name + "}" + (char) 34);
					if (!property.nullable) {
						skipLine();
						write("required=" + (char) 34 + "true" + (char) 34
								+ " requiredMessage=" + (char) 34
								+ "#{i18n.mandatoryField}" + (char) 34);
					}
					writeLine("/>");
					break;
				}
			}
		}
	}
}
