package com.skeleton.generator.repository.mapper.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.skeleton.generator.model.metadata.TableMetaData;
import com.skeleton.generator.repository.mapper.interfaces.TableMetaDataMapper;


@Component
public class TableMetaDataMapperImpl implements TableMetaDataMapper {

	@Override
	public List<TableMetaData> mapTableMetaDataList(List<String[]> tokensList, List<TableMetaData> tableMetaDataList) {
		
		for (String[] tokens:tokensList) {
			TableMetaData tableMetaData = new TableMetaData();
			
			tableMetaData.setName(tokens[0]);
			tableMetaData.setCardinality(Integer.parseInt(tokens[1]));
			tableMetaData.setListRendering(tokens[2]);
			tableMetaData.setDetailRendering(tokens[3]);
			tableMetaData.setComboxable((tokens[4].equals("ComboBox")?true:false));
			tableMetaData.setInterfaceList(tokens[5]);
			tableMetaData.setAnnotationList(tokens[6]);
			tableMetaData.setCreateEnabled((tokens[7].equals("Not enabled")?false:true));
			tableMetaData.setUpdateEnabled((tokens[8].equals("Not enabled")?false:true));
			tableMetaData.setDeleteEnabled((tokens[9].equals("Not enabled")?false:true));
			
			tableMetaDataList.add(tableMetaData);
		}
		
		return tableMetaDataList;
	}

}
