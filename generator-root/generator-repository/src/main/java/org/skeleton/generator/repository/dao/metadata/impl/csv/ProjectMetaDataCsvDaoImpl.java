package org.skeleton.generator.repository.dao.metadata.impl.csv;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.NotImplementedException;
import org.skeleton.generator.exception.InvalidFileException;
import org.skeleton.generator.exception.InvalidProjectMetaDataException;
import org.skeleton.generator.exception.ProjectAlreadyConfiguredException;
import org.skeleton.generator.exception.ProjectNotFoundException;
import org.skeleton.generator.model.metadata.PersistenceMode;
import org.skeleton.generator.model.metadata.ProjectMetaData;
import org.skeleton.generator.repository.dao.metadata.impl.csv.mapper.ProjectMetaDataMapper;
import org.skeleton.generator.repository.dao.metadata.interfaces.PackageMetaDataDao;
import org.skeleton.generator.repository.dao.metadata.interfaces.ProjectMetaDataDao;
import org.skeleton.generator.repository.file.interfaces.CsvFileParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Deprecated
@Component(value="projectMetaDataCsvDao")
public class ProjectMetaDataCsvDaoImpl implements ProjectMetaDataDao {
	
	/*
	 * properties
	 */
	@Resource(name="projetctMetaDataCsvFileParser")
	private CsvFileParser parser;
	@Autowired
	private ProjectMetaDataMapper projectMetaDataMapper;
	@Autowired
	private PackageMetaDataDao packageMetaDataDao;

	
	/*
	 * (non-Javadoc)
	 * @see com.skeleton.generator.repository.dao.interfaces.ProjectMetaDataDao#loadProjectMetaData(java.lang.String)
	 */
	@Override
	public ProjectMetaData loadProjectMetaData(String workspacePath) {

		String sourcePath = workspacePath + File.separator + ProjectMetaDataDao.DATA_MODEL_FOLDER_NAME;
		
		Path parametersPath = Paths.get(sourcePath + File.separator + CSV_PARAMETERS_FILE_NAME);
		
		if (!Files.exists(parametersPath)) {
			throw new ProjectNotFoundException("Unable to find project in folder : " + workspacePath);
		}
		
		List<String[]> tokensList = null;
		
		try {
			tokensList = parser.readData(parametersPath.toString());
		} catch (IOException | InvalidFileException e) {
			throw new InvalidProjectMetaDataException("Could not read parameters");
		}
		
		ProjectMetaData projectMetaData = new ProjectMetaData();
		projectMetaData.setWorkspaceFolder(workspacePath);
		projectMetaData.setSourceFolder(sourcePath);
		projectMetaData.setPersistenceMode(PersistenceMode.CSV);
		
		projectMetaData = projectMetaDataMapper.mapProjectMetaData(tokensList, projectMetaData);
		
		projectMetaData.setPackages(packageMetaDataDao.loadPackageMetaDataList(sourcePath + File.separator + CSV_MODEL_FOLDER_NAME));
		
		return projectMetaData;
	}

	@Override
	public void persistProjectMetaData(ProjectMetaData projectMetaData) {
		
		throw new NotImplementedException();
	}
	
	@Override
	public void initProject(ProjectMetaData projectMetaData) {
		
		throw new NotImplementedException();
	}

}
