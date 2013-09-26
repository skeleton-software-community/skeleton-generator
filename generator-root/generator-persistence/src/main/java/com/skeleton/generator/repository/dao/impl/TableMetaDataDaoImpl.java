package com.skeleton.generator.repository.dao.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skeleton.generator.exception.InvalidFileException;
import com.skeleton.generator.exception.InvalidProjectMetaDataException;
import com.skeleton.generator.model.metadata.TableMetaData;
import com.skeleton.generator.repository.dao.interfaces.ColumnMetaDataDao;
import com.skeleton.generator.repository.dao.interfaces.TableMetaDataDao;
import com.skeleton.generator.repository.file.interfaces.FileManager;
import com.skeleton.generator.repository.mapper.interfaces.TableMetaDataMapper;

@Component
public class TableMetaDataDaoImpl implements TableMetaDataDao {

	private static final String TABLES_FILE_NAME = "TABLES.txt";
	private static final String TABLES_FOLDER_NAME = "TABLES";
	
	/*
	 * properties
	 */
	@Resource(name="tableMetaDataFileManager")
	private FileManager metaDataFileManager;
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
			tokensList = metaDataFileManager.readData(tablesPath.toString());
		} catch (IOException | InvalidFileException e) {
			throw new InvalidProjectMetaDataException("Could not read tables", e);
		}
		
		List<TableMetaData> tableMetaDataList = tableMetaDataMapper.mapTableMetaDataList(tokensList, new ArrayList<TableMetaData>());
		
		for (TableMetaData tableMetaData:tableMetaDataList) {
			tableMetaData.setColumnMetaDataList(columnMetaDataDao.loadColumnMetaDataList(folderPath + File.separator + TABLES_FOLDER_NAME + File.separator + tableMetaData.getName() + ".txt"));
		}
		return tableMetaDataList;
	}

	
	@Override
	public void persistTableMetaDataList(List<TableMetaData> tableMetaDataList) {
		// TODO Auto-generated method stub
		
	}

}
