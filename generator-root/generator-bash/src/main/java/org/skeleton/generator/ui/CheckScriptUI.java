package org.skeleton.generator.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.skeleton.generator.model.check.ScriptCheckWarning;
import org.skeleton.generator.model.check.WarningCheckType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckScriptUI {
	
	private static final Logger logger = LoggerFactory.getLogger(CheckScriptUI.class);
	
	public void printWarnings(List<ScriptCheckWarning> warnings){
		logger.warn(warnings.size() + " warnings have been generated");
		
		for(ScriptCheckWarning w : warnings){
			logger.warn(printSingleWarning(w));
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
	
	private String printSingleWarning(ScriptCheckWarning w){
		return toString(w.getType()) + w.getTable().originalName + " on step " + w.getStep(); 
	}
	
	private String toString(WarningCheckType type){
		switch (type) {
		case EMPTY_TABLE: return "Empty Table : ";
		case NOT_PROD_TARGET : return "Input source is not production : ";
		case HARDCODED_VALUES : return "Hardcoded values will be injected : ";
		default : throw new IllegalStateException();
		}
	}
	

}
