package com.skeleton.generator.repository.mapper.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.skeleton.generator.model.metadata.PackageMetaData;
import com.skeleton.generator.repository.mapper.interfaces.PackageMetaDataMapper;


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
