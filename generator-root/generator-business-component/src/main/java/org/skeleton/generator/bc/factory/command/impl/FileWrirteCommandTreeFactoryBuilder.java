package org.skeleton.generator.bc.factory.command.impl;

import org.skeleton.generator.bc.factory.command.interfaces.FileWriteCommandTreeFactory;
import org.skeleton.generator.model.om.Project;

public class FileWrirteCommandTreeFactoryBuilder {
	
	public static FileWriteCommandTreeFactory getFileWriteCommandTreeFactory(Project project) {
		switch (project.skeletonType) {
			case SPRING_HIBERNATE_RICHFACES:
				return new SpringHibernateRichFacesCommandTreeFactory();
			default:
				throw new IllegalArgumentException("Unhandled Skeleton type : " + project.skeletonType.name());
		}
	}
}
