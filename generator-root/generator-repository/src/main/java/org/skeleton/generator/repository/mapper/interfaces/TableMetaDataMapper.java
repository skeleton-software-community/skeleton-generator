package org.skeleton.generator.repository.mapper.interfaces;

import java.util.List;

import org.skeleton.generator.model.metadata.TableMetaData;


public interface TableMetaDataMapper {

	List<TableMetaData> mapTableMetaDataList (List<String[]> tokensList, List<TableMetaData> tableMetaDataList);
}
