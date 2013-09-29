package org.skeleton.generator.repository.mapper.impl;

import java.util.List;

import org.skeleton.generator.model.metadata.ColumnMetaData;
import org.skeleton.generator.repository.mapper.interfaces.ColumnMetaDataMapper;
import org.springframework.stereotype.Component;



@Component
public class ColumnMetaDataMapperImpl implements ColumnMetaDataMapper {

	@Override
	public List<ColumnMetaData> mapColumnMetaDataList(List<String[]> tokensList, List<ColumnMetaData> columnMetaDataList) {
		for (String[] tokens:tokensList) {
			ColumnMetaData columnMetaData = new ColumnMetaData();
			
			columnMetaData.setName(tokens[0]);
			columnMetaData.setDataType(tokens[1]);
			
			boolean nullable = true;
			if (tokens[2].equals("NOT NULL")){
				nullable = false;
			}
			columnMetaData.setNullable(nullable);
			columnMetaData.setReferenceTableName(tokens[3]);
			columnMetaData.setReferenceTableRelation(tokens[4]);
			columnMetaData.setFormat(tokens[5]);
			
			boolean editable = true;
			if (tokens[6].equals("Not Editable")){
				editable = false;
			}
			columnMetaData.setEditable(editable);
			
			columnMetaData.setVisibility(tokens[7]);
			columnMetaData.setRendering(tokens[8]);
			
			columnMetaDataList.add(columnMetaData);
		}
		
		return columnMetaDataList;
	}

}
