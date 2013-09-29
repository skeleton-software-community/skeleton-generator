package org.skeleton.generator.repository.dao.metadata.interfaces;

import java.util.List;

import org.skeleton.generator.exception.InvalidProjectMetaDataException;
import org.skeleton.generator.model.metadata.PackageMetaData;


public interface PackageMetaDataDao {

	List<PackageMetaData> loadPackageMetaDataList(String folderPath) throws InvalidProjectMetaDataException;
	
	void persistPackageMetaDataList(List<PackageMetaData> packageMetaDataList);
}
