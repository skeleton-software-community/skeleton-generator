package org.skeleton.generator.bc.strategy.impl.configuration;

import org.skeleton.generator.bc.command.file.impl.conf.java.SpringHibernateRichfacesBaseControllerFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.java.SpringHibernateRichfacesBuildFailureExceptionFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.java.SpringHibernateRichfacesCommandBuilderFactoryFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.java.SpringHibernateRichfacesCommandBuilderFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.java.SpringHibernateRichfacesCommandExecutorFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.java.SpringHibernateRichfacesCommandFailureExceptionFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.java.SpringHibernateRichfacesCommandFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.java.SpringHibernateRichfacesCustomFilterFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.java.SpringHibernateRichfacesInvalidStateExceptionFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.java.SpringHibernateRichfacesObjectNotFoundExceptionFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.pom.MavenEclipseBatchFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.pom.MavenInstallBatchFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.pom.SpringHibernateRichfacesBusinessComponentPomFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.pom.SpringHibernateRichfacesBusinessModelPomFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.pom.SpringHibernateRichfacesRepositoryPomFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.pom.SpringHibernateRichfacesRootPomFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.pom.SpringHibernateRichfacesServicesPomFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.pom.SpringHibernateRichfacesUtilPomFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.pom.SpringHibernateRichfacesWebappPomFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.spring.SpringHibernateRichfacesSpringBusinessComponentFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.spring.SpringHibernateRichfacesSpringRepositoryFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.spring.SpringHibernateRichfacesSpringServicesFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.spring.SpringHibernateRichfacesSpringWebappFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.test.SpringHibernateRichfacesLogbackTestFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.test.SpringHibernateRichfacesSpringTestBusinessComponentFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.test.SpringHibernateRichfacesSpringTestRepositoryFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.test.SpringHibernateRichfacesSpringTestServicesFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.webapp.SpringHibernateRichfacesFacesConfigFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.webapp.SpringHibernateRichfacesLogbackFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.webapp.SpringHibernateRichfacesProjectPropertiesFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.webapp.SpringHibernateRichfacesWebXmlFileWriteCommand;
import org.skeleton.generator.bc.executor.FileWriteCommandTreeNode;
import org.skeleton.generator.bc.strategy.interfaces.LayerStrategy;
import org.skeleton.generator.model.om.Project;

public class SpringHibernateRichfacesConfigurationStrategy  implements LayerStrategy {

	@Override
	public FileWriteCommandTreeNode getLayerNode(Project project) {
		
		FileWriteCommandTreeNode configurationTreeNode = new FileWriteCommandTreeNode("Configuration");
		
		/*
		 * poms
		 */
		FileWriteCommandTreeNode pomTreeNode = new FileWriteCommandTreeNode("pom files");
		configurationTreeNode.add(pomTreeNode);
		
		FileWriteCommandTreeNode rootPomTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesRootPomFileWriteCommand(project));
		pomTreeNode.add(rootPomTreeNode);
		
		FileWriteCommandTreeNode utilPomTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesUtilPomFileWriteCommand(project));
		pomTreeNode.add(utilPomTreeNode);
		
		FileWriteCommandTreeNode businessModelPomTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesBusinessModelPomFileWriteCommand(project));
		pomTreeNode.add(businessModelPomTreeNode);
		
		FileWriteCommandTreeNode repositoryPomTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesRepositoryPomFileWriteCommand(project));
		pomTreeNode.add(repositoryPomTreeNode);
		
		FileWriteCommandTreeNode businessComponentPomTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesBusinessComponentPomFileWriteCommand(project));
		pomTreeNode.add(businessComponentPomTreeNode);
		
		FileWriteCommandTreeNode servicesPomTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesServicesPomFileWriteCommand(project));
		pomTreeNode.add(servicesPomTreeNode);
		
		FileWriteCommandTreeNode webappPomTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesWebappPomFileWriteCommand(project));
		pomTreeNode.add(webappPomTreeNode);
		
		FileWriteCommandTreeNode mavenEclipsePomTreeNode = new FileWriteCommandTreeNode(new MavenEclipseBatchFileWriteCommand(project));
		pomTreeNode.add(mavenEclipsePomTreeNode);
		
		FileWriteCommandTreeNode mavenInstallPomTreeNode = new FileWriteCommandTreeNode(new MavenInstallBatchFileWriteCommand(project));
		pomTreeNode.add(mavenInstallPomTreeNode);
		
		
		/*
		 * webapp
		 */
		FileWriteCommandTreeNode webappTreeNode = new FileWriteCommandTreeNode("webapp files");
		configurationTreeNode.add(webappTreeNode);
		
		FileWriteCommandTreeNode facesConfigPomTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesFacesConfigFileWriteCommand(project));
		webappTreeNode.add(facesConfigPomTreeNode);
		
		FileWriteCommandTreeNode webXmlPomTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesWebXmlFileWriteCommand(project));
		webappTreeNode.add(webXmlPomTreeNode);
		
		FileWriteCommandTreeNode propertiesTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesProjectPropertiesFileWriteCommand(project));
		webappTreeNode.add(propertiesTreeNode);
		
		FileWriteCommandTreeNode logbackTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesLogbackFileWriteCommand(project));
		webappTreeNode.add(logbackTreeNode);
		
		
		/*
		 * spring
		 */
		FileWriteCommandTreeNode springTreeNode = new FileWriteCommandTreeNode("spring configuration files");
		configurationTreeNode.add(springTreeNode);
		
		FileWriteCommandTreeNode springRepositoryTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesSpringRepositoryFileWriteCommand(project));
		springTreeNode.add(springRepositoryTreeNode);
		
		FileWriteCommandTreeNode springBusinessComponentTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesSpringBusinessComponentFileWriteCommand(project));
		springTreeNode.add(springBusinessComponentTreeNode);
		
		FileWriteCommandTreeNode springServicesTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesSpringServicesFileWriteCommand(project));
		springTreeNode.add(springServicesTreeNode);
		
		FileWriteCommandTreeNode springWebappTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesSpringWebappFileWriteCommand(project));
		springTreeNode.add(springWebappTreeNode);
		
		
		/*
		 * java files
		 */
		FileWriteCommandTreeNode javaTreeNode = new FileWriteCommandTreeNode("java files");
		configurationTreeNode.add(javaTreeNode);
		
		FileWriteCommandTreeNode objectNotFoundExceptionTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesObjectNotFoundExceptionFileWriteCommand(project));
		javaTreeNode.add(objectNotFoundExceptionTreeNode);
		
		FileWriteCommandTreeNode invalidStateExceptionTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesInvalidStateExceptionFileWriteCommand(project));
		javaTreeNode.add(invalidStateExceptionTreeNode);
		
		FileWriteCommandTreeNode buildFailureExceptionTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesBuildFailureExceptionFileWriteCommand(project));
		javaTreeNode.add(buildFailureExceptionTreeNode);
		
		FileWriteCommandTreeNode commandFailureExceptionTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesCommandFailureExceptionFileWriteCommand(project));
		javaTreeNode.add(commandFailureExceptionTreeNode);
		
		FileWriteCommandTreeNode baseControllerTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesBaseControllerFileWriteCommand(project));
		javaTreeNode.add(baseControllerTreeNode);
		
		FileWriteCommandTreeNode customFilterTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesCustomFilterFileWriteCommand(project));
		javaTreeNode.add(customFilterTreeNode);
		
		FileWriteCommandTreeNode commandTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesCommandFileWriteCommand(project));
		javaTreeNode.add(commandTreeNode);
		
		FileWriteCommandTreeNode commandBuilderTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesCommandBuilderFileWriteCommand(project));
		javaTreeNode.add(commandBuilderTreeNode);
		
		FileWriteCommandTreeNode commandBuilderFactoryTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesCommandBuilderFactoryFileWriteCommand(project));
		javaTreeNode.add(commandBuilderFactoryTreeNode);
		
		FileWriteCommandTreeNode commandExecutorTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesCommandExecutorFileWriteCommand(project));
		javaTreeNode.add(commandExecutorTreeNode);
		
		
		/*
		 * spring test files
		 */
		FileWriteCommandTreeNode springTestTreeNode = new FileWriteCommandTreeNode("spring test configuration files");
		configurationTreeNode.add(springTestTreeNode);
		
		FileWriteCommandTreeNode springTestRepositoryTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesSpringTestRepositoryFileWriteCommand(project));
		springTestTreeNode.add(springTestRepositoryTreeNode);
		
		FileWriteCommandTreeNode springTestBusinessComponentTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesSpringTestBusinessComponentFileWriteCommand(project));
		springTestTreeNode.add(springTestBusinessComponentTreeNode);
		
		FileWriteCommandTreeNode springTestServicesTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesSpringTestServicesFileWriteCommand(project));
		springTestTreeNode.add(springTestServicesTreeNode);
		
		FileWriteCommandTreeNode logbackTestTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesLogbackTestFileWriteCommand(project));
		springTestTreeNode.add(logbackTestTreeNode);
		
		return configurationTreeNode;
	}

}
