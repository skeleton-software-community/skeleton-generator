package org.sklsft.generator.bl.services.interfaces;

import org.sklsft.generator.model.metadata.ColumnMetaData;
import org.sklsft.generator.model.metadata.PackageMetaData;
import org.sklsft.generator.model.metadata.ProjectMetaData;
import org.sklsft.generator.model.metadata.TableMetaData;

public interface ProjectMetaDataService {

	ProjectMetaData loadProjectMetaData(String folderPath);
	
	void insertPackageMetaData(PackageMetaData packageMetaData, int index, ProjectMetaData projectMetaData);
	
	void insertTableMetaData(TableMetaData tableMetaData, int index, PackageMetaData packageMetaData);
	
	void insertColumnMetaData(ColumnMetaData columnMetaData, int index, TableMetaData tableMetaData);

	void persistProjectMetaData(ProjectMetaData projectMetaData);

	void initProjectMetaData(ProjectMetaData projectMetaData);
}
