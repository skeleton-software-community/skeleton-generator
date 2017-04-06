package org.sklsft.generator.skeletons.tree;

import org.apache.commons.lang.StringUtils;
import org.sklsft.generator.skeletons.commands.interfaces.FileWriteCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The code generator uses a tree of FileWriteCommandExecutor
 * each FileWriteCommandExecutor contains a {@link FileWriteCommand} to be executed
 * the only role of the executor is to launch the {@link FileWriteCommand} when selected through the ui<br/>
 * When the embeded {@link FileWriteCommand} is null, a simple logging will occur (can be used for neutral nodes)
 * @author Nicolas Thibault
 *
 */
public class FileWriteCommandExecutor {
	
	private static final Logger logger = LoggerFactory.getLogger(FileWriteCommandExecutor.class);
	/*
	 * properties
	 */
	private FileWriteCommand command;
	private boolean selected;
	private String label;
	
	
	/*
	 * constructors
	 */
	public FileWriteCommandExecutor(FileWriteCommand command) {
		this.command = command;
		this.label = command.getLabel();
		this.selected = true;
	}
	
	public FileWriteCommandExecutor(String label) {
		this.label = label;
		this.selected = true;
	}
	
	public FileWriteCommandExecutor() {
		this.selected = true;
	}
	
	
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
	
	
	public void execute() {
		if (selected) {
			if (!StringUtils.isEmpty(label)) {
				logger.info("executing command : " + label);
			}
			if (command != null) {
				try {
					command.execute();
				} catch (Exception e) {
					logger.error("command " + label + " has failed : " + e.getClass().getSimpleName() + " - " + e.getMessage(), e);
				}
			}
		}
	}
}
