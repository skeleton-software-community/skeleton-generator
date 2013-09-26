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
import com.skeleton.generator.model.metadata.PackageMetaData;
import com.skeleton.generator.repository.dao.interfaces.PackageMetaDataDao;
import com.skeleton.generator.repository.dao.interfaces.TableMetaDataDao;
import com.skeleton.generator.repository.file.interfaces.FileManager;
import com.skeleton.generator.repository.mapper.interfaces.PackageMetaDataMapper;

@Component
public class PackageMetaDataDaoImpl implements PackageMetaDataDao {

	private static final String PACKAGES_FILE_NAME = "PACKAGES.txt";
	
	/*
	 * properties
	 */
	@Resource(name="packageMetaDataFileManager")
	private FileManager metaDataFileManager;
	@Autowired
	private PackageMetaDataMapper packageMetaDataMapper;
	@Autowired
	private TableMetaDataDao tableMetaDataDao;
	
	
	/*
	 * (non-Javadoc)
	 * @see com.skeleton.generator.repository.dao.interfaces.PackageMetaDataDao#loadPackageMetaDataList(java.lang.String)
	 */
	@Override
	public List<PackageMetaData> loadPackageMetaDataList(String folderPath) throws InvalidProjectMetaDataException {

		Path packagesPath = Paths.get(folderPath + File.separator + PACKAGES_FILE_NAME);
		
		if (!Files.exists(packagesPath)) {
			throw new InvalidProjectMetaDataException("Unable to find packages in folder : " + folderPath);
		}
		
		List<String[]> tokensList = null;
		
		try {
			tokensList = metaDataFileManager.readData(packagesPath.toString());
		} catch (IOException | InvalidFileException e) {
			throw new InvalidProjectMetaDataException("Could not read packages", e);
		}
		
		List<PackageMetaData> packageMetaDataList = packageMetaDataMapper.mapPackageMetaDataList(tokensList, new ArrayList<PackageMetaData>());
		
		for (PackageMetaData packageMetaData:packageMetaDataList) {
			packageMetaData.setTableMetaDataList(tableMetaDataDao.loadTableMetaDataList(folderPath + File.separator + packageMetaData.getName()));
		}
		return packageMetaDataList;
	}

	
	/*
	 * (non-Javadoc)
	 * @see com.skeleton.generator.repository.dao.interfaces.PackageMetaDataDao#persistPackageMetaDataList(java.util.List)
	 */
	@Override
	public void persistPackageMetaDataList(List<PackageMetaData> packageMetaDataList) {
		// TODO Auto-generated method stub
		
	}

}
