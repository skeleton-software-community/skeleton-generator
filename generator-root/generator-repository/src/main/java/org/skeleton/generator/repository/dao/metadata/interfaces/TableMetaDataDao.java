package org.skeleton.generator.repository.dao.metadata.interfaces;

import java.util.List;

import org.skeleton.generator.exception.InvalidProjectMetaDataException;
import org.skeleton.generator.model.metadata.TableMetaData;

@Deprecated
public interface TableMetaDataDao {

	List<TableMetaData> loadTableMetaDataList(String folderPath) throws InvalidProjectMetaDataException;
	
	void persistTableMetaDataList(List<TableMetaData> tableMetaDataList);
}
