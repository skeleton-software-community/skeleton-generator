package com.skeleton.generator.repository.dao.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skeleton.generator.exception.InvalidFileException;
import com.skeleton.generator.exception.InvalidProjectMetaDataException;
import com.skeleton.generator.exception.ProjectNotFoundException;
import com.skeleton.generator.model.metadata.ProjectMetaData;
import com.skeleton.generator.repository.dao.interfaces.PackageMetaDataDao;
import com.skeleton.generator.repository.dao.interfaces.ProjectMetaDataDao;
import com.skeleton.generator.repository.file.interfaces.FileManager;
import com.skeleton.generator.repository.mapper.interfaces.ProjectMetaDataMapper;

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
	public ProjectMetaData loadProjectMetaData(String folderPath) throws ProjectNotFoundException, InvalidProjectMetaDataException {

		Path parametersPath = Paths.get(folderPath + File.separator + PARAMETERS_FILE_NAME);
		
		if (!Files.exists(parametersPath)) {
			throw new ProjectNotFoundException("Unable to find project in folder : " + folderPath);
		}
		
		List<String[]> tokensList = null;
		
		try {
			tokensList = metaDataFileManager.readData(parametersPath.toString());
		} catch (IOException | InvalidFileException e) {
			throw new InvalidProjectMetaDataException("Could not read parameters", e);
		}
		
		ProjectMetaData projectMetaData = projectMetaDataMapper.mapProjectMetaData(tokensList, new ProjectMetaData());
		
		projectMetaData.setPackageMetaDataList(packageMetaDataDao.loadPackageMetaDataList(folderPath + File.separator + MODEL_FOLDER_NAME));
		
		
		return projectMetaData;
	}

	@Override
	public void persistProjectMetaData(ProjectMetaData projectMetaData) {
		// TODO Auto-generated method stub
		
	}

}
