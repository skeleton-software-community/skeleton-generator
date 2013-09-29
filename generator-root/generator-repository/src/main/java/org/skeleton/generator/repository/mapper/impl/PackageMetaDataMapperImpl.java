package org.skeleton.generator.repository.mapper.impl;

import java.util.List;

import org.skeleton.generator.model.metadata.PackageMetaData;
import org.skeleton.generator.repository.mapper.interfaces.PackageMetaDataMapper;
import org.springframework.stereotype.Component;



@Component
public class PackageMetaDataMapperImpl implements PackageMetaDataMapper {

	@Override
	public List<PackageMetaData> mapPackageMetaDataList(List<String[]> tokensList, List<PackageMetaData> packageMetaDataList) {
		
		for (String[] tokens:tokensList) {
			PackageMetaData packageMetaData = new PackageMetaData();
			packageMetaData.setName(tokens[0]);
			packageMetaDataList.add(packageMetaData);
		}
		
		return packageMetaDataList;
	}

}
