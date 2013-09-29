package org.skeleton.generator.repository.mapper.interfaces;

import java.util.List;

import org.skeleton.generator.model.metadata.ColumnMetaData;


public interface ColumnMetaDataMapper {

	List<ColumnMetaData> mapColumnMetaDataList (List<String[]> tokensList, List<ColumnMetaData> columnMetaDataList);
}
