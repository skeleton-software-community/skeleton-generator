package com.skeleton.generator.bl.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skeleton.generator.bl.command.file.interfaces.FileWriteCommand;

public class FileWriteCommandExecutor {
	
	private static final Logger logger = LoggerFactory.getLogger(FileWriteCommandExecutor.class);
	/*
	 * properties
	 */
	private FileWriteCommand command;
	private boolean selected;
	private String label;
	
	
	/*
	 * getters and setters
	 */
	public FileWriteCommand getCommand() {
		return command;
	}
	public void setCommand(FileWriteCommand command) {
		this.command = command;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	
	/*
	 * constructor
	 */
	public FileWriteCommandExecutor(FileWriteCommand command, String label) {
		this.command = command;
		this.label = label;
		this.selected = true;
	}
	
	
	public void execute() {
		if (selected) {

			logger.info("start executing command : " + label);
			if (command != null) {
				try {
					command.execute();
				} catch (Exception e) {
					logger.error("command " + label + "has failed : " + e.getClass().getSimpleName() + " - " + e.getMessage());
				}
			}
		}
	}
}
