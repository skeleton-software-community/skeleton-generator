package org.skeleton.generator.repository.dao.metadata.impl.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.skeleton.generator.model.metadata.PackageMetaData;
import org.skeleton.generator.model.metadata.TableMetaData;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class PackageMetaDataParser {
	
	private static final String NAME_ATTR = "name";
	private XPathExpression xPathTables;
	private TableMetaDataParser tableParser;
	
	
	
	public PackageMetaDataParser(TableMetaDataParser tableParser) throws XPathExpressionException {
		XPath xPath = XPathFactory.newInstance().newXPath();
		xPathTables = xPath.compile("tables/table");
		this.tableParser = tableParser;
	}

	public List<PackageMetaData> parse(NodeList packageElems) throws XPathExpressionException{
		List<PackageMetaData> result = new ArrayList<>(packageElems.getLength());
		
		for(int i=0;i<packageElems.getLength();i++){
			PackageMetaData p = parse((Element) packageElems.item(i));
			result.add(p);
		}
		
		return result;
	}

	private PackageMetaData parse(Element item) throws XPathExpressionException {
		String name = extractName(item);
		
		NodeList tableElems = extractTables(item);
		List<TableMetaData> tableMetaDataList = tableParser.parse(tableElems);
		
		
		PackageMetaData result = new PackageMetaData();
		result.setName(name);
		result.setTableMetaDataList(tableMetaDataList);
		return result;
	}

	private NodeList extractTables(Element item) throws XPathExpressionException {
		return (NodeList) xPathTables.evaluate(item, XPathConstants.NODESET);
	}

	private String extractName(Element item) throws XPathExpressionException {
		return item.getAttribute(NAME_ATTR);
	}

}
