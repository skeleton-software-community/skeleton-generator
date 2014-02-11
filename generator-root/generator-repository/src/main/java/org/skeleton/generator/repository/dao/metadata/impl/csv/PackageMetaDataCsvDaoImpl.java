package org.skeleton.generator.repository.dao.metadata.impl.csv;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.skeleton.generator.exception.InvalidFileException;
import org.skeleton.generator.exception.InvalidProjectMetaDataException;
import org.skeleton.generator.model.metadata.PackageMetaData;
import org.skeleton.generator.repository.dao.metadata.impl.csv.mapper.PackageMetaDataMapper;
import org.skeleton.generator.repository.dao.metadata.interfaces.PackageMetaDataDao;
import org.skeleton.generator.repository.dao.metadata.interfaces.TableMetaDataDao;
import org.skeleton.generator.repository.file.interfaces.CsvFileParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PackageMetaDataCsvDaoImpl implements PackageMetaDataDao {

	private static final String PACKAGES_FILE_NAME = "PACKAGES.txt";
	
	/*
	 * properties
	 */
	@Resource(name="packageMetaDataCsvFileParser")
	private CsvFileParser parser;
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
			tokensList = parser.readData(packagesPath.toString());
		} catch (IOException | InvalidFileException e) {
			throw new InvalidProjectMetaDataException("Could not read packages in " + packagesPath.toString(), e);
		}
		
		List<PackageMetaData> packageMetaDataList;
		try {
			packageMetaDataList = packageMetaDataMapper.mapPackageMetaDataList(tokensList, new ArrayList<PackageMetaData>());
		} catch (Exception e) {
			throw new InvalidProjectMetaDataException("Could not map packages in : " + packagesPath.toString(), e);
		}
		
		for (PackageMetaData packageMetaData:packageMetaDataList) {
			packageMetaData.setTables(tableMetaDataDao.loadTableMetaDataList(folderPath + File.separator + packageMetaData.getName()));
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
