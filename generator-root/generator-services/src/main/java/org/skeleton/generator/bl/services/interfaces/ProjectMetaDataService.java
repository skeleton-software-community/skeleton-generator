package org.skeleton.generator.bl.services.interfaces;

import org.skeleton.generator.exception.InvalidProjectMetaDataException;
import org.skeleton.generator.exception.ProjectNotFoundException;
import org.skeleton.generator.model.metadata.ColumnMetaData;
import org.skeleton.generator.model.metadata.PackageMetaData;
import org.skeleton.generator.model.metadata.ProjectMetaData;
import org.skeleton.generator.model.metadata.TableMetaData;

public interface ProjectMetaDataService {

	public ProjectMetaData loadProjectMetaData(String folderPath) throws ProjectNotFoundException, InvalidProjectMetaDataException;
	
	public void insertPackageMetaData(PackageMetaData packageMetaData, int index, ProjectMetaData projectMetaData);
	
	public void insertTableMetaData(TableMetaData tableMetaData, int index, PackageMetaData packageMetaData);
	
	public void insertColumnMetaData(ColumnMetaData columnMetaData, int index, TableMetaData tableMetaData);
}
