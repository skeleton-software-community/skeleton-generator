package org.skeleton.generator.bc.factory.command.impl;

import org.skeleton.generator.bc.factory.command.interfaces.FileWriteCommandTreeFactory;
import org.skeleton.generator.model.om.Project;

public class FileWriteCommandTreeFactoryBuilder {
	
	public static FileWriteCommandTreeFactory getFileWriteCommandTreeFactory(Project project) {
		switch (project.skeletonType) {
			case SPRING_HIBERNATE_RICHFACES:
				return new SpringHibernateRichFacesCommandTreeFactory();
				
			case BASIC_SPRING_HIBERNATE_RICHFACES:
				return new BasicSpringHibernateRichFacesCommandTreeFactory();
				
			default:
				throw new IllegalArgumentException("Unhandled Skeleton type : " + project.skeletonType.name());
		}
	}
}
