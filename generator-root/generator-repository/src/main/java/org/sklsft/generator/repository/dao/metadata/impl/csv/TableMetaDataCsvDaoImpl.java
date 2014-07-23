package org.sklsft.generator.repository.dao.metadata.impl.csv;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.sklsft.generator.exception.InvalidFileException;
import org.sklsft.generator.exception.InvalidProjectMetaDataException;
import org.sklsft.generator.model.metadata.TableMetaData;
import org.sklsft.generator.repository.dao.metadata.impl.csv.mapper.TableMetaDataMapper;
import org.sklsft.generator.repository.dao.metadata.interfaces.ColumnMetaDataDao;
import org.sklsft.generator.repository.dao.metadata.interfaces.TableMetaDataDao;
import org.sklsft.generator.repository.file.interfaces.CsvFileParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Deprecated
@Component
public class TableMetaDataCsvDaoImpl implements TableMetaDataDao {

	private static final String TABLES_FILE_NAME = "TABLES.txt";
	private static final String TABLES_FOLDER_NAME = "TABLES";
	
	/*
	 * properties
	 */
	@Resource(name="tableMetaDataCsvFileParser")
	private CsvFileParser parser;
	@Autowired
	private TableMetaDataMapper tableMetaDataMapper;
	@Autowired
	private ColumnMetaDataDao columnMetaDataDao;
	
	
	/*
	 * (non-Javadoc)
	 * @see com.skeleton.generator.repository.dao.interfaces.PackageMetaDataDao#loadPackageMetaDataList(java.lang.String)
	 */
	@Override
	public List<TableMetaData> loadTableMetaDataList(String folderPath) throws InvalidProjectMetaDataException {

		Path tablesPath = Paths.get(folderPath + File.separator + TABLES_FILE_NAME);
		
		if (!Files.exists(tablesPath)) {
			throw new InvalidProjectMetaDataException("Unable to find tables in folder : " + folderPath);
		}
		
		List<String[]> tokensList = null;
		
		try {
			tokensList = parser.readData(tablesPath.toString());
		} catch (IOException | InvalidFileException e) {
			throw new InvalidProjectMetaDataException("Could not read tables", e);
		}
		
		List<TableMetaData> tableMetaDataList;
		try {
			tableMetaDataList = tableMetaDataMapper.mapTableMetaDataList(tokensList, new ArrayList<TableMetaData>());
		} catch (Exception e) {
			throw new InvalidProjectMetaDataException("Could not map tables in : " + tablesPath.toString(), e);
		}
		
		for (TableMetaData tableMetaData:tableMetaDataList) {
			tableMetaData.setColumns(columnMetaDataDao.loadColumnMetaDataList(folderPath + File.separator + TABLES_FOLDER_NAME + File.separator + tableMetaData.getName() + ".txt"));
		}
		return tableMetaDataList;
	}

	
	@Override
	public void persistTableMetaDataList(List<TableMetaData> tableMetaDataList) {
		// TODO Auto-generated method stub
		
	}

}
