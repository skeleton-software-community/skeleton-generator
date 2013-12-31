package org.skeleton.generator.repository.dao.metadata.impl.csv.mapper;

import java.util.List;

import org.skeleton.generator.model.metadata.PackageMetaData;
import org.springframework.stereotype.Component;



@Component
public class PackageMetaDataMapper {

	public List<PackageMetaData> mapPackageMetaDataList(List<String[]> tokensList, List<PackageMetaData> packageMetaDataList) {
		
		for (String[] tokens:tokensList) {
			PackageMetaData packageMetaData = new PackageMetaData();
			packageMetaData.setName(tokens[0]);
			packageMetaDataList.add(packageMetaData);
		}
		
		return packageMetaDataList;
	}

}
