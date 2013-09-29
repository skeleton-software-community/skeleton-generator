package org.skeleton.generator.repository.dao.metadata.interfaces;

import java.util.List;

import org.skeleton.generator.exception.InvalidProjectMetaDataException;
import org.skeleton.generator.model.metadata.ColumnMetaData;
import org.skeleton.generator.model.metadata.TableMetaData;


public interface ColumnMetaDataDao {

	List<ColumnMetaData> loadColumnMetaDataList(String folderPath) throws InvalidProjectMetaDataException;
	
	void persistTableMetaDataList(List<TableMetaData> tableMetaDataList);
}
