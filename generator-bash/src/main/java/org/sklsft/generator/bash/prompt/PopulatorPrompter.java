package org.sklsft.generator.bash.prompt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.sklsft.generator.model.backup.check.BackupPlanPostExecutionWarning;
import org.sklsft.generator.model.backup.check.BackupPlanPreExecutionWarning;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PopulatorPrompter {
	
	private static final Logger logger = LoggerFactory.getLogger(PopulatorPrompter.class);
	
	public void printPreExecutionWarnings(List<BackupPlanPreExecutionWarning> warnings){
		logger.warn(warnings.size() + " warnings have been generated");
		
		for(BackupPlanPreExecutionWarning w : warnings){
			logger.warn(printPreExecutionWarning(w));
		}
		
	}
	
	public void printPostExecutionWarnings(List<BackupPlanPostExecutionWarning> warnings){
		logger.warn(warnings.size() + " warnings have been generated");
		
		for(BackupPlanPostExecutionWarning w : warnings){
			logger.warn(printPostExecutionWarning(w));
		}
		
	}
	
	public void promptForConfirmation() throws IOException{
		System.out.println("Do you wish to continue? [Y/n]");
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		String input = bufferRead.readLine();
		
		if(!("Y".equals(input)||input.isEmpty())){
			logger.warn("Aborting ...");
			System.exit(0);
		}
	}
	
	private String printPreExecutionWarning(BackupPlanPreExecutionWarning w){
		String firstPart = w.getType().getDescription() + w.getTable().originalName;
		if(w.getStep()==BackupPlanPreExecutionWarning.NO_STEP){
			return firstPart;
		}else{
			return firstPart + " on step " + w.getStep();
		}
	}
	
	private String printPostExecutionWarning(BackupPlanPostExecutionWarning w){
		return w.getType().getDescription() + w.getTable().originalName;
	}
}
