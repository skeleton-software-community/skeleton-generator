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
		
		FileWriteCommandTreeNode rootPomTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesRootPomFileWriteCommand(project),"root pom.xml");
		pomTreeNode.add(rootPomTreeNode);
		
		FileWriteCommandTreeNode utilPomTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesUtilPomFileWriteCommand(project),"util pom.xml");
		pomTreeNode.add(utilPomTreeNode);
		
		FileWriteCommandTreeNode businessModelPomTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesBusinessModelPomFileWriteCommand(project),"business model pom.xml");
		pomTreeNode.add(businessModelPomTreeNode);
		
		FileWriteCommandTreeNode repositoryPomTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesRepositoryPomFileWriteCommand(project),"repository pom.xml");
		pomTreeNode.add(repositoryPomTreeNode);
		
		FileWriteCommandTreeNode businessComponentPomTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesBusinessComponentPomFileWriteCommand(project),"business component pom.xml");
		pomTreeNode.add(businessComponentPomTreeNode);
		
		FileWriteCommandTreeNode servicesPomTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesServicesPomFileWriteCommand(project),"services pom.xml");
		pomTreeNode.add(servicesPomTreeNode);
		
		FileWriteCommandTreeNode webappPomTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesWebappPomFileWriteCommand(project),"webapp pom.xml");
		pomTreeNode.add(webappPomTreeNode);
		
		FileWriteCommandTreeNode mavenEclipsePomTreeNode = new FileWriteCommandTreeNode(new MavenEclipseBatchFileWriteCommand(project),"maven eclipse bat");
		pomTreeNode.add(mavenEclipsePomTreeNode);
		
		FileWriteCommandTreeNode mavenInstallPomTreeNode = new FileWriteCommandTreeNode(new MavenInstallBatchFileWriteCommand(project),"maven install bat");
		pomTreeNode.add(mavenInstallPomTreeNode);
		
		
		/*
		 * webapp
		 */
		FileWriteCommandTreeNode webappTreeNode = new FileWriteCommandTreeNode("webapp files");
		configurationTreeNode.add(webappTreeNode);
		
		FileWriteCommandTreeNode facesConfigPomTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesFacesConfigFileWriteCommand(project),"faces-config.xml");
		webappTreeNode.add(facesConfigPomTreeNode);
		
		FileWriteCommandTreeNode webXmlPomTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesWebXmlFileWriteCommand(project),"web.xml");
		webappTreeNode.add(webXmlPomTreeNode);
		
		FileWriteCommandTreeNode propertiesTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesProjectPropertiesFileWriteCommand(project), project.projectName + ".properties");
		webappTreeNode.add(propertiesTreeNode);
		
		FileWriteCommandTreeNode logbackTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesLogbackFileWriteCommand(project), "logback.xml");
		webappTreeNode.add(logbackTreeNode);
		
		
		/*
		 * spring
		 */
		FileWriteCommandTreeNode springTreeNode = new FileWriteCommandTreeNode("spring configuration files");
		configurationTreeNode.add(springTreeNode);
		
		FileWriteCommandTreeNode springRepositoryTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesSpringRepositoryFileWriteCommand(project),"applicationContext-" + project.projectName + "-repository.xml");
		springTreeNode.add(springRepositoryTreeNode);
		
		FileWriteCommandTreeNode springBusinessComponentTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesSpringBusinessComponentFileWriteCommand(project),"applicationContext-" + project.projectName + "-business-component.xml");
		springTreeNode.add(springBusinessComponentTreeNode);
		
		FileWriteCommandTreeNode springServicesTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesSpringServicesFileWriteCommand(project),"applicationContext-" + project.projectName + "-services.xml");
		springTreeNode.add(springServicesTreeNode);
		
		FileWriteCommandTreeNode springWebappTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesSpringWebappFileWriteCommand(project),"applicationContext-" + project.projectName + "-webapp.xml");
		springTreeNode.add(springWebappTreeNode);
		
		
		/*
		 * java files
		 */
		FileWriteCommandTreeNode javaTreeNode = new FileWriteCommandTreeNode("java files");
		configurationTreeNode.add(javaTreeNode);
		
		FileWriteCommandTreeNode objectNotFoundExceptionTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesObjectNotFoundExceptionFileWriteCommand(project),"ObjectNotFoundException.java");
		javaTreeNode.add(objectNotFoundExceptionTreeNode);
		
		FileWriteCommandTreeNode invalidStateExceptionTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesInvalidStateExceptionFileWriteCommand(project),"InvalidStateException.java");
		javaTreeNode.add(invalidStateExceptionTreeNode);
		
		FileWriteCommandTreeNode buildFailureExceptionTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesBuildFailureExceptionFileWriteCommand(project),"BuildFailureException.java");
		javaTreeNode.add(buildFailureExceptionTreeNode);
		
		FileWriteCommandTreeNode commandFailureExceptionTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesCommandFailureExceptionFileWriteCommand(project),"CommandFailureException.java");
		javaTreeNode.add(commandFailureExceptionTreeNode);
		
		FileWriteCommandTreeNode baseControllerTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesBaseControllerFileWriteCommand(project),"BaseController.java");
		javaTreeNode.add(baseControllerTreeNode);
		
		FileWriteCommandTreeNode customFilterTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesCustomFilterFileWriteCommand(project),"CustomFilter.java");
		javaTreeNode.add(customFilterTreeNode);
		
		FileWriteCommandTreeNode commandTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesCommandFileWriteCommand(project),"Command.java");
		javaTreeNode.add(commandTreeNode);
		
		FileWriteCommandTreeNode commandBuilderTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesCommandBuilderFileWriteCommand(project),"CommandBuilder.java");
		javaTreeNode.add(commandBuilderTreeNode);
		
		FileWriteCommandTreeNode commandBuilderFactoryTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesCommandBuilderFactoryFileWriteCommand(project),"CommandBuilderFactory.java");
		javaTreeNode.add(commandBuilderFactoryTreeNode);
		
		FileWriteCommandTreeNode commandExecutorTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesCommandExecutorFileWriteCommand(project),"CommandExecutor.java");
		javaTreeNode.add(commandExecutorTreeNode);
		
		
		/*
		 * spring test files
		 */
		FileWriteCommandTreeNode springTestTreeNode = new FileWriteCommandTreeNode("spring test configuration files");
		configurationTreeNode.add(springTestTreeNode);
		
		FileWriteCommandTreeNode springTestRepositoryTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesSpringTestRepositoryFileWriteCommand(project),"applicationContext-" + project.projectName + "-repository-test.xml");
		springTestTreeNode.add(springTestRepositoryTreeNode);
		
		FileWriteCommandTreeNode springTestBusinessComponentTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesSpringTestBusinessComponentFileWriteCommand(project),"applicationContext-" + project.projectName + "-business-component-test.xml");
		springTestTreeNode.add(springTestBusinessComponentTreeNode);
		
		FileWriteCommandTreeNode springTestServicesTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesSpringTestServicesFileWriteCommand(project),"applicationContext-" + project.projectName + "-services-test.xml");
		springTestTreeNode.add(springTestServicesTreeNode);
		
		FileWriteCommandTreeNode logbackTestTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesLogbackTestFileWriteCommand(project), "logback-test.xml");
		springTestTreeNode.add(logbackTestTreeNode);
		
		return configurationTreeNode;
	}

}
