package org.sklsft.generator.repository.dao.metadata.interfaces;

import java.util.List;

import org.sklsft.generator.exception.InvalidProjectMetaDataException;
import org.sklsft.generator.model.metadata.TableMetaData;

@Deprecated
public interface TableMetaDataDao {

	List<TableMetaData> loadTableMetaDataList(String folderPath) throws InvalidProjectMetaDataException;
	
	void persistTableMetaDataList(List<TableMetaData> tableMetaDataList);
}
