package org.skeleton.generator.repository.dao.metadata.impl.csv.mapper;

import java.util.ArrayList;
import java.util.List;

import org.skeleton.generator.model.metadata.ColumnMetaData;
import org.skeleton.generator.model.metadata.DataType;
import org.skeleton.generator.model.metadata.Format;
import org.skeleton.generator.model.metadata.RelationType;
import org.skeleton.generator.model.metadata.Visibility;
import org.springframework.stereotype.Component;


@Deprecated
@Component
public class ColumnMetaDataMapper {

	public List<ColumnMetaData> mapColumnMetaDataList(List<String[]> tokensList, List<ColumnMetaData> columnMetaDataList) {
		for (String[] tokens:tokensList) {
			ColumnMetaData columnMetaData = new ColumnMetaData();
			
			columnMetaData.setName(tokens[0]);
			columnMetaData.setDataType(DataType.valueOf(tokens[1]));
			
			boolean nullable = true;
			if (tokens[2].equals("NOT NULL")){
				nullable = false;
			}
			columnMetaData.setNullable(nullable);
			columnMetaData.setReferenceTableName(tokens[3].isEmpty()?null:tokens[3]);
			columnMetaData.setReferenceTableRelation(tokens[4].isEmpty()?RelationType.PROPERTY:RelationType.valueOf(tokens[4]));
			columnMetaData.setFormat(tokens[5].isEmpty()?Format.DEFAULT:Format.valueOf(tokens[5]));
			
			boolean editable = true;
			if (tokens[6].equals("Not Editable")){
				editable = false;
			}
			columnMetaData.setEditable(editable);
			
			columnMetaData.setVisibility(tokens[7].isEmpty()?Visibility.VISIBLE:Visibility.valueOf(tokens[7]));
			columnMetaData.setRendering(tokens[8]);
			
			String[] annotations = tokens[9].split(";");
			List<String> annotationList = new ArrayList<String>();
			for (String token:annotations) {
				token = token.trim();
				if (!token.isEmpty()) {
					annotationList.add(token);
				}
			}
			columnMetaData.setAnnotations(annotationList);
			
			columnMetaDataList.add(columnMetaData);
		}
		
		return columnMetaDataList;
	}

}
