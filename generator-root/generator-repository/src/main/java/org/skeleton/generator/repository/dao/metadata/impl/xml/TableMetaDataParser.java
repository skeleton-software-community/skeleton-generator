package org.skeleton.generator.repository.dao.metadata.impl.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.skeleton.generator.model.metadata.ColumnMetaData;
import org.skeleton.generator.model.metadata.TableMetaData;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class TableMetaDataParser {

	private static final String CARDINALITY_ATTR = "cardinality";
	private static final String CREATE_ENABLED_ATTR = "createEnabled";
	private static final String DELETE_ENABLED_ATTR = "deleteEnabled";
	private static final String LIST_RENDERING_ATTR = "listRendering";
	private static final String NAME_ATTR = "name";
	private static final String UPDATE_ENABLED_ATTR = "updateEnabled";
	private XPathExpression xPathInterface, xPathAnnotation, xPathColumn;
	private ColumnMetaDataParser columnParser;
	
	public TableMetaDataParser(ColumnMetaDataParser columnParser) throws XPathExpressionException {
		XPath xPath = XPathFactory.newInstance().newXPath();
		xPathInterface = xPath.compile("interfaces/interface");
		xPathAnnotation = xPath.compile("annotations/annotation");
		xPathColumn = xPath.compile("columns/column");
		this.columnParser = columnParser;
	}
	
	public List<TableMetaData> parse(NodeList tableElems) throws XPathExpressionException {
		List<TableMetaData> result = new ArrayList<>(tableElems.getLength());
		
		for(int i=0;i<tableElems.getLength();i++){
			TableMetaData t = parse((Element) tableElems.item(i));
			result.add(t);
		}
		
		return result;
	}
	
	public TableMetaData parse(Element item) throws XPathExpressionException{
		TableMetaData result = new TableMetaData();
		
		NodeList annotationElems = extractAnnotationList(item);
		NodeList interfaceElems = extractInterfaceList(item);
		
		int cardinality = extractCardinality(item);
		boolean comboxable = extractComboxable(item);
		boolean createEnabled = extractCreateEnabled(item);
		boolean deleteEnabled = extractDeleteEnabled(item);
		String detailRendering = extractDetailRendering(item);
		String listRendering = extractListRendering(item);
		String name = extractName(item);
		boolean updateEnabled = extractUpdateEnabled(item);
		
		List<ColumnMetaData> columnMetaDataList = columnParser.parse(extractColumnList(item));
		
		result.setAnnotationList(elementListToString(annotationElems));
		result.setCardinality(cardinality);
		result.setColumnMetaDataList(columnMetaDataList);
		result.setComboxable(comboxable);
		result.setCreateEnabled(createEnabled);
		result.setDeleteEnabled(deleteEnabled);
		result.setDetailRendering(detailRendering);
		result.setInterfaceList(elementListToString(interfaceElems));
		result.setListRendering(listRendering);
		result.setName(name);
		result.setUpdateEnabled(updateEnabled);
		
		return result;
	}
	
	private NodeList extractColumnList(Element item) throws XPathExpressionException {
		return (NodeList) xPathColumn.evaluate(item, XPathConstants.NODESET);
	}

	private String elementListToString(NodeList elemsList){
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<elemsList.getLength(); i++){
			sb.append(elemsList.item(i).getTextContent()).append(',');
		}
		if(sb.length()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}

	private boolean extractUpdateEnabled(Element item) {
		return new Boolean(item.getAttribute(UPDATE_ENABLED_ATTR));
	}

	private String extractName(Element item) {
		return item.getAttribute(NAME_ATTR);
	}

	private String extractListRendering(Element item) {
		return item.getAttribute(LIST_RENDERING_ATTR);
	}

	private NodeList extractInterfaceList(Element item) throws XPathExpressionException {
		return (NodeList) xPathInterface.evaluate(item, XPathConstants.NODESET);
	}

	private String extractDetailRendering(Element item) {
		return "notconfigured";
	}

	private boolean extractDeleteEnabled(Element item) {
		return new Boolean(item.getAttribute(DELETE_ENABLED_ATTR));
	}

	private boolean extractCreateEnabled(Element item) {
		return new Boolean(item.getAttribute(CREATE_ENABLED_ATTR));
	}

	private boolean extractComboxable(Element item) {
		return false;
	}

	private int extractCardinality(Element item) {
		return new Integer(item.getAttribute(CARDINALITY_ATTR));
	}

	private NodeList extractAnnotationList(Element item) throws XPathExpressionException {
		return (NodeList) xPathAnnotation.evaluate(item, XPathConstants.NODESET);
	}

}
