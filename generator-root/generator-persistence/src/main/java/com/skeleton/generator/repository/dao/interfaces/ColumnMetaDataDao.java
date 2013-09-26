package com.skeleton.generator.repository.dao.interfaces;

import java.util.List;

import com.skeleton.generator.exception.InvalidProjectMetaDataException;
import com.skeleton.generator.model.metadata.ColumnMetaData;
import com.skeleton.generator.model.metadata.TableMetaData;

public interface ColumnMetaDataDao {

	List<ColumnMetaData> loadColumnMetaDataList(String folderPath) throws InvalidProjectMetaDataException;
	
	void persistTableMetaDataList(List<TableMetaData> tableMetaDataList);
}
