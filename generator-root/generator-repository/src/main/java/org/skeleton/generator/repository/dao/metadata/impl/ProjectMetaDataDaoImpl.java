package org.skeleton.generator.repository.dao.metadata.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.annotation.Resource;

import org.skeleton.generator.exception.ConfigurationReadException;
import org.skeleton.generator.exception.InvalidFileException;
import org.skeleton.generator.exception.InvalidProjectMetaDataException;
import org.skeleton.generator.exception.ProjectNotFoundException;
import org.skeleton.generator.model.metadata.ProjectMetaData;
import org.skeleton.generator.repository.dao.metadata.interfaces.PackageMetaDataDao;
import org.skeleton.generator.repository.dao.metadata.interfaces.ProjectMetaDataDao;
import org.skeleton.generator.repository.file.interfaces.FileManager;
import org.skeleton.generator.repository.mapper.interfaces.ProjectMetaDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ProjectMetaDataDaoImpl implements ProjectMetaDataDao {
	
	private static final String PARAMETERS_FILE_NAME = "PARAMETERS.txt";
	private static final String MODEL_FOLDER_NAME = "MODEL";
	
	/*
	 * properties
	 */
	@Resource(name="projetctMetaDataFileManager")
	private FileManager metaDataFileManager;
	@Autowired
	private ProjectMetaDataMapper projectMetaDataMapper;
	@Autowired
	private PackageMetaDataDao packageMetaDataDao;

	
	/*
	 * (non-Javadoc)
	 * @see com.skeleton.generator.repository.dao.interfaces.ProjectMetaDataDao#loadProjectMetaData(java.lang.String)
	 */
	@Override
	public ProjectMetaData loadProjectMetaData(String folderPath)  throws ConfigurationReadException{

		Path parametersPath = Paths.get(folderPath + File.separator + PARAMETERS_FILE_NAME);
		
		if (!Files.exists(parametersPath)) {
			throw new ConfigurationReadException(new ProjectNotFoundException("Unable to find project in folder : " + folderPath));
		}
		
		List<String[]> tokensList = null;
		
		try {
			tokensList = metaDataFileManager.readData(parametersPath.toString());
		} catch (IOException | InvalidFileException e) {
			throw new ConfigurationReadException(new InvalidProjectMetaDataException("Could not read parameters", e));
		}
		
		ProjectMetaData projectMetaData = projectMetaDataMapper.mapProjectMetaData(tokensList, new ProjectMetaData());
		
		try {
			projectMetaData.setPackageMetaDataList(packageMetaDataDao.loadPackageMetaDataList(folderPath + File.separator + MODEL_FOLDER_NAME));
		} catch (InvalidProjectMetaDataException e) {
			throw new ConfigurationReadException(e);
		}
		
		
		return projectMetaData;
	}

	@Override
	public void persistProjectMetaData(ProjectMetaData projectMetaData) {
		// TODO Auto-generated method stub
		
	}

}
