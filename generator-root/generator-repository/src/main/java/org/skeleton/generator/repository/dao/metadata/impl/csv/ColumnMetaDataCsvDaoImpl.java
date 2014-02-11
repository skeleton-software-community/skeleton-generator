package org.skeleton.generator.repository.dao.metadata.impl.csv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.skeleton.generator.exception.InvalidFileException;
import org.skeleton.generator.exception.InvalidProjectMetaDataException;
import org.skeleton.generator.model.metadata.ColumnMetaData;
import org.skeleton.generator.model.metadata.TableMetaData;
import org.skeleton.generator.repository.dao.metadata.impl.csv.mapper.ColumnMetaDataMapper;
import org.skeleton.generator.repository.dao.metadata.interfaces.ColumnMetaDataDao;
import org.skeleton.generator.repository.file.interfaces.CsvFileParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ColumnMetaDataCsvDaoImpl implements ColumnMetaDataDao {
	
	/*
	 * properties
	 */
	@Resource(name="columnMetaDataCsvFileParser")
	private CsvFileParser parser;
	@Autowired
	private ColumnMetaDataMapper columnMetaDataMapper;

	
	/*
	 * (non-Javadoc)
	 * @see com.skeleton.generator.repository.dao.interfaces.ColumnMetaDataDao#loadColumnMetaDataList(java.lang.String)
	 */
	@Override
	public List<ColumnMetaData> loadColumnMetaDataList(String filePath) throws InvalidProjectMetaDataException {
		
		Path columnsPath = Paths.get(filePath);
		
		if (!Files.exists(columnsPath)) {
			throw new InvalidProjectMetaDataException("Unable to find columns in file : " + filePath);
		}
		
		List<String[]> tokensList = null;
		
		try {
			tokensList = parser.readData(columnsPath.toString());
		} catch (IOException | InvalidFileException e) {
			throw new InvalidProjectMetaDataException("Could not read columns in : " + columnsPath.toString(), e);
		}
		
		try {
			List<ColumnMetaData> columnMetaDataList = columnMetaDataMapper.mapColumnMetaDataList(tokensList, new ArrayList<ColumnMetaData>());
			return columnMetaDataList;
		} catch (Exception e) {
			throw new InvalidProjectMetaDataException("Could not map columns in : " + columnsPath.toString(), e);
		}
	}
	

	/*
	 * (non-Javadoc)
	 * @see com.skeleton.generator.repository.dao.interfaces.ColumnMetaDataDao#persistTableMetaDataList(java.util.List)
	 */
	@Override
	public void persistTableMetaDataList(List<TableMetaData> tableMetaDataList) {
		// TODO Auto-generated method stub

	}

}
