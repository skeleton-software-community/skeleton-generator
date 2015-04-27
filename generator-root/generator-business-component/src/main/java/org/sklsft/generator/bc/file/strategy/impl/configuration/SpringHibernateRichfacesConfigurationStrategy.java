package org.sklsft.generator.bc.file.strategy.impl.configuration;

import org.sklsft.generator.bc.file.command.impl.conf.context.DataSourceContextFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.java.exceptions.BusinessExceptionFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.java.exceptions.InvalidStateExceptionFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.java.exceptions.ObjectNotFoundExceptionFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.java.mvc.aspect.SpringHibernateRichfacesAjaxMethodAnnotationFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.java.mvc.aspect.SpringHibernateRichfacesAjaxMethodAspectFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.java.mvc.controller.SpringHibernateRichfacesBaseControllerFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.java.mvc.filter.SpringHibernateRichfacesCustomFilterFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.java.population.CommandFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.MavenEclipseBatchFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.MavenInstallBatchFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.SpringHibernateRichfacesApiPomFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.SpringHibernateRichfacesBusinessComponentPomFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.SpringHibernateRichfacesBusinessModelPomFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.SpringHibernateRichfacesRepositoryPomFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.SpringHibernateRichfacesRootPomFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.SpringHibernateRichfacesServicesPomFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.SpringHibernateRichfacesUtilPomFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.SpringHibernateRichfacesWebappPomFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.spring.SpringHibernateRichfacesSpringBusinessComponentFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.spring.SpringHibernateRichfacesSpringRepositoryFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.spring.SpringHibernateRichfacesSpringServicesFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.spring.SpringHibernateRichfacesSpringWebappFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.spring.test.SpringHibernateRichfacesLogbackTestFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.spring.test.SpringHibernateRichfacesSpringTestBusinessComponentFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.spring.test.SpringHibernateRichfacesSpringTestRepositoryFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.spring.test.SpringHibernateRichfacesSpringTestServicesFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.webapp.SpringHibernateRichfacesFacesConfigFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.webapp.SpringHibernateRichfacesLogbackFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.webapp.SpringHibernateRichfacesProjectPropertiesFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.webapp.SpringHibernateRichfacesWebXmlFileWriteCommand;
import org.sklsft.generator.bc.file.executor.FileWriteCommandTreeNode;
import org.sklsft.generator.bc.file.strategy.interfaces.LayerStrategy;
import org.sklsft.generator.model.om.Project;

public class SpringHibernateRichfacesConfigurationStrategy  implements LayerStrategy {

	@Override
	public FileWriteCommandTreeNode getLayerNode(Project project) {
		
		FileWriteCommandTreeNode configurationTreeNode = new FileWriteCommandTreeNode("Configuration");
		
		/*
		 * datasource context
		 */
		FileWriteCommandTreeNode contextTreeNode = new FileWriteCommandTreeNode("context");
		configurationTreeNode.add(contextTreeNode);
		
		FileWriteCommandTreeNode dataSourceContextTreeNode = new FileWriteCommandTreeNode(new DataSourceContextFileWriteCommand(project));
		contextTreeNode.add(dataSourceContextTreeNode);
		
		/*
		 * poms
		 */
		FileWriteCommandTreeNode pomTreeNode = new FileWriteCommandTreeNode("pom files");
		configurationTreeNode.add(pomTreeNode);
		
		FileWriteCommandTreeNode rootPomTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesRootPomFileWriteCommand(project));
		pomTreeNode.add(rootPomTreeNode);
		
		FileWriteCommandTreeNode apiPomTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesApiPomFileWriteCommand(project));
		pomTreeNode.add(apiPomTreeNode);
		
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
		
		FileWriteCommandTreeNode businessExceptionTreeNode = new FileWriteCommandTreeNode(new BusinessExceptionFileWriteCommand(project));
		javaTreeNode.add(businessExceptionTreeNode);
		
		FileWriteCommandTreeNode objectNotFoundExceptionTreeNode = new FileWriteCommandTreeNode(new ObjectNotFoundExceptionFileWriteCommand(project));
		javaTreeNode.add(objectNotFoundExceptionTreeNode);
		
		FileWriteCommandTreeNode invalidStateExceptionTreeNode = new FileWriteCommandTreeNode(new InvalidStateExceptionFileWriteCommand(project));
		javaTreeNode.add(invalidStateExceptionTreeNode);
		
		FileWriteCommandTreeNode baseControllerTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesBaseControllerFileWriteCommand(project));
		javaTreeNode.add(baseControllerTreeNode);
		
		FileWriteCommandTreeNode customFilterTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesCustomFilterFileWriteCommand(project));
		javaTreeNode.add(customFilterTreeNode);
		
		FileWriteCommandTreeNode ajaxMethodAnnotationTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesAjaxMethodAnnotationFileWriteCommand(project));
		javaTreeNode.add(ajaxMethodAnnotationTreeNode);
		
		FileWriteCommandTreeNode ajaxMethodAspectTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesAjaxMethodAspectFileWriteCommand(project));
		javaTreeNode.add(ajaxMethodAspectTreeNode);
		
		FileWriteCommandTreeNode commandTreeNode = new FileWriteCommandTreeNode(new CommandFileWriteCommand(project));
		javaTreeNode.add(commandTreeNode);
		
		
		
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
