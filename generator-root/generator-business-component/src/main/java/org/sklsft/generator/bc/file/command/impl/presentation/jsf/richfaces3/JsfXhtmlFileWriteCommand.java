package org.sklsft.generator.bc.file.command.impl.presentation.jsf.richfaces3;

import org.sklsft.generator.bc.file.command.impl.presentation.XhtmlFileWriteCommand;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.Property;

public abstract class JsfXhtmlFileWriteCommand extends XhtmlFileWriteCommand {

	public JsfXhtmlFileWriteCommand(String folderName, String fileName) {
		super(folderName, fileName);
	}

	protected void writeListComponent(Property property, Bean bean) {
		switch (property.dataType) {
		case BOOLEAN:
			if (property.nullable) {
				writeLine("<h:outputText value=" + CHAR_34 + "#{"
						+ bean.objectName + "." + property.name + "}"
						+ CHAR_34 + "/>");
			} else {
				writeLine("<h:selectBooleanCheckbox value=" + CHAR_34 + "#{"
						+ bean.objectName + "." + property.name + "}"
						+ CHAR_34 + " disabled=" + CHAR_34 + "true"
						+ CHAR_34 + "/>");
			}
			break;

		case DATETIME:
			writeLine("<h:outputText value=" + CHAR_34 + "#{"
					+ bean.objectName + "." + property.name + "}" + CHAR_34
					+ ">");

			switch (property.format) {
			case DATE:
				writeLine("<f:convertDateTime type=" + CHAR_34 + "date"
						+ CHAR_34 + " dateStyle=" + CHAR_34 + "medium"
						+ CHAR_34 + "/>");
				break;

			default:
				writeLine("<f:convertDateTime type=" + CHAR_34 + "both"
						+ CHAR_34 + " dateStyle=" + CHAR_34 + "medium"
						+ CHAR_34 + "/>");
				break;
			}
			writeLine("</h:outputText>");

			break;

		case DOUBLE:
			writeLine("<h:outputText value=" + CHAR_34 + "#{"
					+ bean.objectName + "." + property.name + "}" + CHAR_34
					+ ">");

			switch (property.format) {
			case TWO_DECIMALS:
				writeLine("<f:convertNumber pattern=" + CHAR_34 + "#,##0.00"
						+ CHAR_34 + "/>");
				break;

			case FOUR_DECIMALS:
				writeLine("<f:convertNumber pattern=" + CHAR_34
						+ "#,##0.0000" + CHAR_34 + "/>");
				break;

			default:
				writeLine("<f:convertNumber pattern=" + CHAR_34
						+ "#,##0.########" + CHAR_34 + "/>");
				break;
			}
			writeLine("</h:outputText>");
			break;

		case LONG:
			writeLine("<h:outputText value=" + CHAR_34 + "#{"
					+ bean.objectName + "." + property.name + "}" + CHAR_34
					+ ">");
			writeLine("<f:convertNumber pattern=" + CHAR_34 + "#,##0"
					+ CHAR_34 + "/>");
			writeLine("</h:outputText>");
			break;

		case STRING:
			writeLine("<h:outputText value=" + CHAR_34 + "#{"
					+ bean.objectName + "." + property.name + "}" + CHAR_34
					+ "/>");
			break;

		case TEXT:
			writeLine("<h:outputText value=" + CHAR_34 + ">" + CHAR_34
					+ "/>");
			writeLine("<rich:toolTip style=" + CHAR_34
					+ "background:white;border:white" + CHAR_34 + ">");
			writeLine("<h:inputTextarea value=" + CHAR_34 + "#{"
					+ bean.objectName + "." + property.name + "}" + CHAR_34
					+ " rows=" + CHAR_34 + "10" + CHAR_34 + " readonly="
					+ CHAR_34 + "true" + CHAR_34 + "/>");
			writeLine("</rich:toolTip>");
			break;
		}
	}

	protected void writeDetailComponent(Property property, Bean bean) {
		if (!property.editable) {
			switch (property.dataType) {
			case BOOLEAN:
				if (property.nullable) {
					writeLine("<h:inputText id=" + CHAR_34 + bean.objectName
							+ property.capName + CHAR_34 + " style="
							+ CHAR_34 + "width:300px" + CHAR_34 + " value="
							+ CHAR_34 + "#{" + bean.objectName + "."
							+ property.name + "}" + CHAR_34);
					writeLine("readonly=" + CHAR_34 + "true" + CHAR_34
							+ "/>");
				} else {
					writeLine("<h:selectBooleanCheckbox id=" + CHAR_34
							+ bean.objectName + property.capName + CHAR_34
							+ " value=" + CHAR_34 + "#{" + bean.objectName
							+ "." + property.name + "}" + CHAR_34);
					writeLine("readonly=" + CHAR_34 + "true" + CHAR_34
							+ " disabled=" + CHAR_34 + "true" + CHAR_34
							+ "/>");
				}
				break;

			case DATETIME:
				writeLine("<h:inputText id=" + CHAR_34 + bean.objectName
						+ property.capName + CHAR_34 + " style=" + CHAR_34
						+ "width:300px;background:lightgrey" + CHAR_34
						+ " value=" + CHAR_34 + "#{" + bean.objectName + "."
						+ property.name + "}" + CHAR_34);
				writeLine("readonly=" + CHAR_34 + "true" + CHAR_34 + ">");

				switch (property.format) {
				case DATE:
					writeLine("<f:convertDateTime type=" + CHAR_34 + "date"
							+ CHAR_34 + " dateStyle=" + CHAR_34 + "medium"
							+ CHAR_34 + "/>");
					break;

				default:
					writeLine("<f:convertDateTime type=" + CHAR_34 + "both"
							+ CHAR_34 + " dateStyle=" + CHAR_34 + "medium"
							+ CHAR_34 + "/>");
					break;
				}
				writeLine("</h:inputText>");
				break;

			case DOUBLE:
				writeLine("<h:inputText id=" + CHAR_34 + bean.objectName
						+ property.capName + CHAR_34 + " style=" + CHAR_34
						+ "width:300px;background:lightgrey" + CHAR_34
						+ " value=" + CHAR_34 + "#{" + bean.objectName + "."
						+ property.name + "}" + CHAR_34);
				writeLine("readonly=" + CHAR_34 + "true" + CHAR_34 + ">");

				switch (property.format) {
				case TWO_DECIMALS:
					writeLine("<f:convertNumber pattern=" + CHAR_34
							+ "#,##0.00" + CHAR_34 + "/>");
					break;

				case FOUR_DECIMALS:
					writeLine("<f:convertNumber pattern=" + CHAR_34
							+ "#,##0.0000" + CHAR_34 + "/>");
					break;

				default:
					writeLine("<f:convertNumber pattern=" + CHAR_34
							+ "#,##0.########" + CHAR_34 + "/>");
					break;

				}
				writeLine("</h:inputText>");
				break;

			case LONG:
				writeLine("<h:inputText id=" + CHAR_34 + bean.objectName
						+ property.capName + CHAR_34 + " style=" + CHAR_34
						+ "width:300px;background:lightgrey" + CHAR_34
						+ " value=" + CHAR_34 + "#{" + bean.objectName + "."
						+ property.name + "}" + CHAR_34);
				writeLine("readonly=" + CHAR_34 + "true" + CHAR_34 + ">");
				writeLine("<f:convertNumber integerOnly=" + CHAR_34 + "true" + CHAR_34 + " pattern=" + CHAR_34 + "#,##0"
						+ CHAR_34 + "/>");
				writeLine("</h:inputText>");
				break;

			case STRING:
				writeLine("<h:inputText id=" + CHAR_34 + bean.objectName
						+ property.capName + CHAR_34 + " style=" + CHAR_34
						+ "width:300px;background:lightgrey" + CHAR_34
						+ " value=" + CHAR_34 + "#{" + bean.objectName + "."
						+ property.name + "}" + CHAR_34);
				writeLine("readonly=" + CHAR_34 + "true" + CHAR_34 + ">");
				writeLine("</h:inputText>");
				break;

			case TEXT:
				writeLine("<h:inputTextarea id=" + CHAR_34 + bean.objectName
						+ property.capName + CHAR_34 + " style=" + CHAR_34
						+ "width:600px;background:lightgrey" + CHAR_34
						+ " rows=" + CHAR_34 + "10" + CHAR_34 + " value="
						+ CHAR_34 + "#{" + bean.objectName + "."
						+ property.name + "}" + CHAR_34);
				writeLine("readonly=" + CHAR_34 + "true" + CHAR_34 + ">");
				writeLine("</h:inputTextarea>");
				break;
			}
		} else {
			if (property.comboBoxBean != null) {
				write("<h:selectOneMenu id=" + CHAR_34 + bean.objectName
						+ property.capName + CHAR_34 + " style=" + CHAR_34
						+ "width:300px" + CHAR_34 + " value=" + CHAR_34
						+ "#{" + bean.objectName + "." + property.name + "}"
						+ CHAR_34);
				if (!property.nullable) {
					skipLine();
					write(" required=" + CHAR_34 + "true" + CHAR_34);
				}
				writeLine(">");

				switch (property.dataType) {
				case DATETIME:
					switch (property.format) {
					case DATE:
						writeLine("<f:convertDateTime type=" + CHAR_34
								+ "date" + CHAR_34 + " dateStyle="
								+ CHAR_34 + "long" + CHAR_34 + "/>");
						break;

					default:
						writeLine("<f:convertDateTime type=" + CHAR_34
								+ "both" + CHAR_34 + " dateStyle="
								+ CHAR_34 + "long" + CHAR_34 + "/>");
						break;

					}
					break;

				case DOUBLE:
					switch (property.format) {
					case TWO_DECIMALS:
						writeLine("<f:convertNumber pattern=" + CHAR_34
								+ "#,##0.00" + CHAR_34 + "/>");
						break;

					case FOUR_DECIMALS:
						writeLine("<f:convertNumber pattern=" + CHAR_34
								+ "#,##0.0000" + CHAR_34 + "/>");
						break;

					default:
						writeLine("<f:convertNumber pattern=" + CHAR_34
								+ "#,##0.########" + CHAR_34 + "/>");
						break;
					}
					break;

				case LONG:
					writeLine("<f:convertNumber integerOnly=" + CHAR_34 + "true" + CHAR_34 + " pattern=" + CHAR_34 + "#,##0"
							+ CHAR_34 + "/>");
					break;

				default:
					break;
				}

				writeLine("<f:selectItems value=" + CHAR_34
						+ "#{commonController."
						+ property.comboBoxBean.objectName
						+ property.comboBoxBean.properties.get(1).capName
						+ "List}" + CHAR_34 + "/>");
				writeLine("</h:selectOneMenu>");

			} else {
				switch (property.dataType) {
				case BOOLEAN:
					if (property.nullable) {
						writeLine("<h:inputText id=" + CHAR_34
								+ bean.objectName + property.capName
								+ CHAR_34 + " style=" + CHAR_34
								+ "width:300px" + CHAR_34 + " value="
								+ CHAR_34 + "#{" + bean.objectName + "."
								+ property.name + "}" + CHAR_34 + "/>");
					} else {
						writeLine("<h:selectBooleanCheckbox id=" + CHAR_34
								+ bean.objectName + property.capName
								+ CHAR_34 + " value=" + CHAR_34 + "#{"
								+ bean.objectName + "." + property.name + "}"
								+ CHAR_34);
						writeLine("readonly=" + CHAR_34 + "false" + CHAR_34
								+ " disabled=" + CHAR_34 + "false"
								+ CHAR_34 + "/>");
					}
					break;

				case DATETIME:
					writeLine("<rich:calendar id=" + CHAR_34
							+ bean.objectName + property.capName + CHAR_34
							+ " inputStyle=" + CHAR_34 + "width:280px"
							+ CHAR_34 + " value=" + CHAR_34 + "#{"
							+ bean.objectName + "." + property.name + "}"
							+ CHAR_34);

					switch (property.format) {
					case DATE:
						write(" datePattern=" + CHAR_34 + "dd MMMM yyyy"
								+ CHAR_34);
						break;

					default:
						write(" datePattern=" + CHAR_34
								+ "dd MMMM yyyy HH:mm" + CHAR_34);
						break;
					}

					if (!property.nullable) {
						skipLine();
						write("required=" + CHAR_34 + "true" + CHAR_34);
					}
					writeLine("/>");
					break;

				case DOUBLE:
					write("<h:inputText id=" + CHAR_34 + bean.objectName
							+ property.capName + CHAR_34 + " style="
							+ CHAR_34 + "width:300px" + CHAR_34 + " value="
							+ CHAR_34 + "#{" + bean.objectName + "."
							+ property.name + "}" + CHAR_34);
					if (!property.nullable) {
						skipLine();
						write("required=" + CHAR_34 + "true" + CHAR_34);
					}
					writeLine(">");

					switch (property.format) {
					case TWO_DECIMALS:
						writeLine("<f:convertNumber pattern=" + CHAR_34
								+ "#,##0.00" + CHAR_34 + "/>");
						break;

					case FOUR_DECIMALS:
						writeLine("<f:convertNumber pattern=" + CHAR_34
								+ "#,##0.0000" + CHAR_34 + "/>");
						break;

					default:
						writeLine("<f:convertNumber pattern=" + CHAR_34
								+ "#,##0.########" + CHAR_34 + "/>");
						break;
					}
					writeLine("</h:inputText>");
					break;

				case LONG:
					write("<h:inputText id=" + CHAR_34 + bean.objectName
							+ property.capName + CHAR_34 + " style="
							+ CHAR_34 + "width:300px" + CHAR_34 + " value="
							+ CHAR_34 + "#{" + bean.objectName + "."
							+ property.name + "}" + CHAR_34);
					if (!property.nullable) {
						skipLine();
						write("required=" + CHAR_34 + "true" + CHAR_34);
					}
					writeLine(">");
					writeLine("<f:convertNumber integerOnly=" + CHAR_34 + "true" + CHAR_34 + " pattern=" + CHAR_34 + "#,##0"
							+ CHAR_34 + "/>");
					writeLine("</h:inputText>");
					break;

				case STRING:
					write("<h:inputText id=" + CHAR_34 + bean.objectName
							+ property.capName + CHAR_34 + " style="
							+ CHAR_34 + "width:300px" + CHAR_34 + " value="
							+ CHAR_34 + "#{" + bean.objectName + "."
							+ property.name + "}" + CHAR_34);
					if (!property.nullable) {
						skipLine();
						write("required=" + CHAR_34 + "true" + CHAR_34);
					}
					writeLine("/>");
					break;

				case TEXT:
					write("<h:inputTextarea id=" + CHAR_34 + bean.objectName
							+ property.capName + CHAR_34 + " style="
							+ CHAR_34 + "width:600px" + CHAR_34 + " rows="
							+ CHAR_34 + "10" + CHAR_34 + " value="
							+ CHAR_34 + "#{" + bean.objectName + "."
							+ property.name + "}" + CHAR_34);
					if (!property.nullable) {
						skipLine();
						write("required=" + CHAR_34 + "true" + CHAR_34);
					}
					writeLine("/>");
					break;
				}
			}
		}
	}
	
	protected void writeFilter(Property property, Bean bean) {
		switch (property.dataType) {
		case STRING:
	
			writeLine("<h:inputText id=" + CHAR_34 + bean.objectName
					+ property.capName + "DataTableFilter" + CHAR_34);
			writeLine("value=" + CHAR_34 + "#{" + bean.listViewObjectName + "." + bean.filterObjectName + "." + property.name + "}" + CHAR_34);
			writeLine("styleClass=" + CHAR_34 + "dataTableFilter" + CHAR_34 + ">");
			writeLine("<a4j:support event=" + CHAR_34 + "onkeyup" + CHAR_34 + " reRender=" + CHAR_34 + bean.objectName + "List, " + bean.objectName + "Scroller" + CHAR_34);
			writeLine("ignoreDupResponses=" + CHAR_34 + "true" + CHAR_34 + " requestDelay=" + CHAR_34 + "500" + CHAR_34);
			writeLine("oncomplete=" + CHAR_34 + "setCaretToEnd(event);" + CHAR_34 + " />");
			writeLine("</h:inputText>");
			break;
			
		
		default:
			
			writeLine("<h:inputText id=" + CHAR_34 + bean.objectName
					+ property.capName + "DataTableFilter" + CHAR_34);
			writeLine("value=" + CHAR_34 + "#{" + bean.listViewObjectName + "." + bean.filterObjectName + "." + property.name + "}" + CHAR_34);
			writeLine("styleClass=" + CHAR_34 + "dataTableFilter" + CHAR_34 + ">");
			writeLine("<a4j:support event=" + CHAR_34 + "onkeyup" + CHAR_34 + " reRender=" + CHAR_34 + bean.objectName + "List, " + bean.objectName + "Scroller" + CHAR_34);
			writeLine("ignoreDupResponses=" + CHAR_34 + "true" + CHAR_34 + " requestDelay=" + CHAR_34 + "500" + CHAR_34);
			writeLine("oncomplete=" + CHAR_34 + "setCaretToEnd(event);" + CHAR_34 + " />");
			writeLine("</h:inputText>");
			break;
			
	
		}
	}
	
	protected void writeFilter(Property property, Bean currentBean, Bean parentBean) {
		switch (property.dataType) {
		case STRING:
	
			writeLine("<h:inputText id=" + CHAR_34 + currentBean.objectName
					+ property.capName + "DataTableFilter" + CHAR_34);
			writeLine("value=" + CHAR_34 + "#{" + parentBean.detailViewObjectName + "." + currentBean.filterObjectName + "." + property.name + "}" + CHAR_34);
			writeLine("styleClass=" + CHAR_34 + "dataTableFilter" + CHAR_34 + ">");
			writeLine("<a4j:support event=" + CHAR_34 + "onkeyup" + CHAR_34 + " reRender=" + CHAR_34 + currentBean.objectName + "List, " + currentBean.objectName + "Scroller" + CHAR_34);
			writeLine("ignoreDupResponses=" + CHAR_34 + "true" + CHAR_34 + " requestDelay=" + CHAR_34 + "500" + CHAR_34);
			writeLine("oncomplete=" + CHAR_34 + "setCaretToEnd(event);" + CHAR_34 + " />");
			writeLine("</h:inputText>");
			break;
			
		
		default:
			
			writeLine("<h:inputText id=" + CHAR_34 + currentBean.objectName
					+ property.capName + "DataTableFilter" + CHAR_34);
			writeLine("value=" + CHAR_34 + "#{" + parentBean.detailViewObjectName + "." + currentBean.filterObjectName + "." + property.name + "}" + CHAR_34);
			writeLine("styleClass=" + CHAR_34 + "dataTableFilter" + CHAR_34 + ">");
			writeLine("<a4j:support event=" + CHAR_34 + "onkeyup" + CHAR_34 + " reRender=" + CHAR_34 + currentBean.objectName + "List, " + currentBean.objectName + "Scroller" + CHAR_34);
			writeLine("ignoreDupResponses=" + CHAR_34 + "true" + CHAR_34 + " requestDelay=" + CHAR_34 + "500" + CHAR_34);
			writeLine("oncomplete=" + CHAR_34 + "setCaretToEnd(event);" + CHAR_34 + " />");
			writeLine("</h:inputText>");
			break;
			
	
		}
	}
	
	public void writeFilterExpression(Property property, Bean bean) {
		
		switch (property.dataType) {
		case STRING:
	
			writeLine("filterExpression=" + CHAR_34 + "#{fn:containsIgnoreCase(" + bean.objectName + "." + property.name + ", " + bean.listViewObjectName + "." + bean.filterObjectName + "." + property.name + ")}" + CHAR_34 + ">");
			break;
		
		
		case TEXT:
			
			writeLine("filterExpression=" + CHAR_34 + "#{fn:containsIgnoreCase(" + bean.objectName + "." + property.name + ", " + bean.listViewObjectName + "." + bean.filterObjectName + "." + property.name + ")}" + CHAR_34 + ">");
			break;
			
		
		case DATETIME:
			
			writeLine("filterExpression=" + CHAR_34 + "#{customFilter.filterDate(" + bean.objectName + "." + property.name + ", " + bean.listViewObjectName + "." + bean.filterObjectName + "." + property.name + ")}" + CHAR_34 + ">");
			break;
			
			
		case BOOLEAN:
			
			writeLine("filterExpression=" + CHAR_34 + "#{customFilter.filterBoolean(" + bean.objectName + "." + property.name + ", " + bean.listViewObjectName + "." + bean.filterObjectName + "." + property.name + ")}" + CHAR_34 + ">");
			break;
			
		
		default:
			
			writeLine("filterExpression=" + CHAR_34 + "#{fn:containsIgnoreCase(" + bean.objectName + "." + property.name + ", " + bean.listViewObjectName + "." + bean.filterObjectName + "." + property.name + ")}" + CHAR_34 + ">");
			break;
		}
	}
	
	public void writeFilterExpression(Property property, Bean currentBean, Bean parentBean) {
		
		switch (property.dataType) {
		case STRING:
	
			writeLine("filterExpression=" + CHAR_34 + "#{fn:containsIgnoreCase(" + currentBean.objectName + "." + property.name + ", " + parentBean.detailViewObjectName + "." + currentBean.filterObjectName + "." + property.name + ")}" + CHAR_34 + ">");
			break;
		
		
		case TEXT:
			
			writeLine("filterExpression=" + CHAR_34 + "#{fn:containsIgnoreCase(" + currentBean.objectName + "." + property.name + ", " + parentBean.detailViewObjectName + "." + currentBean.filterObjectName + "." + property.name + ")}" + CHAR_34 + ">");
			break;
			
		
		case DATETIME:
			
			writeLine("filterExpression=" + CHAR_34 + "#{customFilter.filterDate(" + currentBean.objectName + "." + property.name + ", " + parentBean.detailViewObjectName + "." + currentBean.filterObjectName + "." + property.name + ")}" + CHAR_34 + ">");
			break;
			
			
		case BOOLEAN:
			
			writeLine("filterExpression=" + CHAR_34 + "#{customFilter.filterBoolean(" + currentBean.objectName + "." + property.name + ", " + parentBean.detailViewObjectName + "." + currentBean.filterObjectName + "." + property.name + ")}" + CHAR_34 + ">");
			break;
			
		
		default:
			
			writeLine("filterExpression=" + CHAR_34 + "#{fn:containsIgnoreCase(" + currentBean.objectName + "." + property.name + ", " + parentBean.detailViewObjectName + "." + currentBean.filterObjectName + "." + property.name + ")}" + CHAR_34 + ">");
			break;
		}
	}
}
