package com.skeleton.generator.repository.dao.interfaces;

import java.util.List;

import com.skeleton.generator.exception.InvalidProjectMetaDataException;
import com.skeleton.generator.model.metadata.PackageMetaData;

public interface PackageMetaDataDao {

	List<PackageMetaData> loadPackageMetaDataList(String folderPath) throws InvalidProjectMetaDataException;
	
	void persistPackageMetaDataList(List<PackageMetaData> packageMetaDataList);
}
