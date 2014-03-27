package org.skeleton.generator.repository.dao.metadata.impl.csv.mapper;

import java.util.ArrayList;
import java.util.List;

import org.skeleton.generator.model.metadata.TableMetaData;
import org.springframework.stereotype.Component;


@Deprecated
@Component
public class TableMetaDataMapper {

	public List<TableMetaData> mapTableMetaDataList(List<String[]> tokensList, List<TableMetaData> tableMetaDataList) {
		
		for (String[] tokens:tokensList) {
			TableMetaData tableMetaData = new TableMetaData();
			
			tableMetaData.setName(tokens[0]);
			tableMetaData.setCardinality(Integer.parseInt(tokens[1]));
			tableMetaData.setListRendering(tokens[2]);
			tableMetaData.setDetailRendering(tokens[3]);
			tableMetaData.setComboxable((tokens[4].equals("ComboBox")?true:false));
			
			String[] interfaces = tokens[5].split(",");
			List<String> interfaceList = new ArrayList<String>();
			for (String token:interfaces) {
				token = token.trim();
				if (!token.isEmpty()) {
					interfaceList.add(token);
				}
			}
			tableMetaData.setInterfaces(interfaceList);
			
			String[] annotations = tokens[6].split(";");
			List<String> annotationList = new ArrayList<String>();
			for (String token:annotations) {
				token = token.trim();
				if (!token.isEmpty()) {
					annotationList.add(token);
				}
			}
			tableMetaData.setAnnotations(annotationList);
			
			tableMetaData.setCreateEnabled((tokens[7].equals("Not enabled")?false:true));
			tableMetaData.setUpdateEnabled((tokens[8].equals("Not enabled")?false:true));
			tableMetaData.setDeleteEnabled((tokens[9].equals("Not enabled")?false:true));
			
			tableMetaDataList.add(tableMetaData);
		}
		
		return tableMetaDataList;
	}

}
