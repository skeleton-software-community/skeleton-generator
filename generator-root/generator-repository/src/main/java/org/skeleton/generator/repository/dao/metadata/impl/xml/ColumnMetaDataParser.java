package org.skeleton.generator.repository.dao.metadata.impl.xml;

import java.util.ArrayList;
import java.util.List;

import org.skeleton.generator.model.metadata.ColumnMetaData;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ColumnMetaDataParser {
	
	private static final String RENDERING_ATTR = "rendering";
	private static final String VISIBILITY_ATTR = "visibility";
	private static final String EDITABLE_ATTR = "editable";
	private static final String FORMAT_ATTR = "format";
	private static final String REFERENCE_TABLE_RELATION_ATTR = "referenceTableRelation";
	private static final String REFERENCE_TABLE_NAME_ATTR = "referenceTableName";
	private static final String NULLABLE_ATTR = "nullable";
	private static final String DATATYPE_ATTR = "dataType";
	private static final String NAME_ATTR = "name";

	public List<ColumnMetaData> parse(NodeList columnElems){
		List<ColumnMetaData> result = new ArrayList<>(columnElems.getLength());
		for(int i=0; i<columnElems.getLength(); i++){
			ColumnMetaData c = parse((Element) columnElems.item(i));
			result.add(c);
		}
		return result;
	}

	private ColumnMetaData parse(Element item) {
		String name = extractName(item);
		String dataType = extractType(item);
		boolean nullable = extractNullable(item);
		String refTabName = extractRefTabName(item);
		String refTabRelation = extractRefTabRelation(item);
		String format = extractFormat(item);
		boolean editable = extractEditable(item);
		String visibility = extractVisibility(item);
		String rendering = extractRendering(item);
		
		ColumnMetaData result = new ColumnMetaData();
		result.setDataType(dataType);
		result.setEditable(editable);
		result.setFormat(format);
		result.setName(name);
		result.setNullable(nullable);
		result.setReferenceTableName(refTabName);
		result.setReferenceTableRelation(refTabRelation);
		result.setRendering(rendering);
		result.setVisibility(visibility);
		
		return result;
	}

	private String extractName(Element item) {
		return item.getAttribute(NAME_ATTR);
	}

	private String extractType(Element item) {
		return item.getAttribute(DATATYPE_ATTR);
	}

	private boolean extractNullable(Element item) {
		return new Boolean(item.getAttribute(NULLABLE_ATTR));
	}

	private String extractRefTabName(Element item) {
		return item.getAttribute(REFERENCE_TABLE_NAME_ATTR);
	}

	private String extractRefTabRelation(Element item) {
		return item.getAttribute(REFERENCE_TABLE_RELATION_ATTR);
	}

	private String extractFormat(Element item) {
		return item.getAttribute(FORMAT_ATTR);
	}

	private boolean extractEditable(Element item) {
		return new Boolean(item.getAttribute(EDITABLE_ATTR));
	}

	private String extractVisibility(Element item) {
		return item.getAttribute(VISIBILITY_ATTR);
	}

	private String extractRendering(Element item) {
		return item.getAttribute(RENDERING_ATTR);
	}

}
