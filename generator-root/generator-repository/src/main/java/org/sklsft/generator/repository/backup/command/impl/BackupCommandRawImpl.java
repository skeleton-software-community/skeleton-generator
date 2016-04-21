package org.sklsft.generator.repository.backup.command.impl;

import java.io.IOException;

import org.sklsft.generator.exception.InvalidFileException;
import org.sklsft.generator.repository.backup.command.interfaces.BackupCommand;
import org.sklsft.generator.repository.backup.file.interfaces.SimpleScriptFileReader;
import org.sklsft.generator.repository.build.Command;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Implementation of a {@link BackupCommand} that 
 * <li>reads a className in the file
 * <li>finds the corresponding component in a spring context
 * <li>execute the component as a Command
 * 
 * @author Nicolas Thibault
 *
 */
@Component
public class BackupCommandRawImpl implements BackupCommand, ApplicationContextAware {
	
	@Autowired
	private SimpleScriptFileReader reader;
	
	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	
	@Override
	public void execute(String path) {
		
		try {
			String className = reader.readScript(path);
			Class<?> clazz = Class.forName(className);
		
			((Command)applicationContext.getBean(clazz)).execute();
			
		} catch (IOException | ClassNotFoundException e) {
			throw new InvalidFileException("failed to read raw file at path : " + path, e);
		}		
	}
}
