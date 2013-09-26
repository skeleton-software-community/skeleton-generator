package com.skeleton.generator.repository.mapper.interfaces;

import java.util.List;

import com.skeleton.generator.model.metadata.PackageMetaData;

public interface PackageMetaDataMapper {

	List<PackageMetaData> mapPackageMetaDataList (List<String[]> tokensList, List<PackageMetaData> packageMetaDataList);
}
