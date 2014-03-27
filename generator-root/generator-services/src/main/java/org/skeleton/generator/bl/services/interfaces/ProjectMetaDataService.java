package org.skeleton.generator.bl.services.interfaces;

import org.skeleton.generator.model.metadata.ColumnMetaData;
import org.skeleton.generator.model.metadata.PackageMetaData;
import org.skeleton.generator.model.metadata.ProjectMetaData;
import org.skeleton.generator.model.metadata.TableMetaData;

public interface ProjectMetaDataService {

	ProjectMetaData loadProjectMetaData(String folderPath);
	
	void insertPackageMetaData(PackageMetaData packageMetaData, int index, ProjectMetaData projectMetaData);
	
	void insertTableMetaData(TableMetaData tableMetaData, int index, PackageMetaData packageMetaData);
	
	void insertColumnMetaData(ColumnMetaData columnMetaData, int index, TableMetaData tableMetaData);

	void persistProjectMetaData(ProjectMetaData projectMetaData);

	void initProjectMetaData(ProjectMetaData projectMetaData);
}
