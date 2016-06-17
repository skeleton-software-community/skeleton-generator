package org.sklsft.generator.bc.file.strategy.impl.configuration.richfaces4;

import org.sklsft.generator.bc.file.command.impl.conf.context.DataSourceContextFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.java.mvc.controller.RichfacesBaseControllerFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.java.mvc.filter.RichfacesCustomFilterFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.java.population.PopulatorLauncherFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.ApiPomFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.BusinessComponentPomFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.BusinessModelPomFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.MavenInstallBatchFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.PopulatorPomFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.RepositoryPomFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.Richfaces4RootPomFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.Richfaces4WebappPomFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.ServicesPomFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.UtilPomFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.spring.LogbackPopulatorFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.spring.Richfaces4SpringWebappFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.spring.SpringBusinessComponentFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.spring.SpringPopulatorFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.spring.SpringPopulatorRepositoryFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.spring.SpringRepositoryFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.spring.SpringServicesFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.spring.test.LogbackTestFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.spring.test.SpringTestRepositoryFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.webapp.LogbackFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.webapp.PopulatorProjectPropertiesFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.webapp.ProjectPropertiesFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.webapp.Richfaces4WebXmlFileWriteCommand;
import org.sklsft.generator.bc.file.executor.FileWriteCommandTreeNode;
import org.sklsft.generator.bc.file.strategy.interfaces.LayerStrategy;
import org.sklsft.generator.model.domain.Project;

public class Richfaces4ConfigurationStrategy  implements LayerStrategy {

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
		
		FileWriteCommandTreeNode rootPomTreeNode = new FileWriteCommandTreeNode(new Richfaces4RootPomFileWriteCommand(project));
		pomTreeNode.add(rootPomTreeNode);
		
		FileWriteCommandTreeNode apiPomTreeNode = new FileWriteCommandTreeNode(new ApiPomFileWriteCommand(project));
		pomTreeNode.add(apiPomTreeNode);
		
		FileWriteCommandTreeNode utilPomTreeNode = new FileWriteCommandTreeNode(new UtilPomFileWriteCommand(project));
		pomTreeNode.add(utilPomTreeNode);
		
		FileWriteCommandTreeNode businessModelPomTreeNode = new FileWriteCommandTreeNode(new BusinessModelPomFileWriteCommand(project));
		pomTreeNode.add(businessModelPomTreeNode);
		
		FileWriteCommandTreeNode repositoryPomTreeNode = new FileWriteCommandTreeNode(new RepositoryPomFileWriteCommand(project));
		pomTreeNode.add(repositoryPomTreeNode);
		
		FileWriteCommandTreeNode businessComponentPomTreeNode = new FileWriteCommandTreeNode(new BusinessComponentPomFileWriteCommand(project));
		pomTreeNode.add(businessComponentPomTreeNode);
		
		FileWriteCommandTreeNode servicesPomTreeNode = new FileWriteCommandTreeNode(new ServicesPomFileWriteCommand(project));
		pomTreeNode.add(servicesPomTreeNode);
		
		FileWriteCommandTreeNode webappPomTreeNode = new FileWriteCommandTreeNode(new Richfaces4WebappPomFileWriteCommand(project));
		pomTreeNode.add(webappPomTreeNode);
		
		FileWriteCommandTreeNode populatorPomTreeNode = new FileWriteCommandTreeNode(new PopulatorPomFileWriteCommand(project));
		pomTreeNode.add(populatorPomTreeNode);
		
		FileWriteCommandTreeNode mavenInstallPomTreeNode = new FileWriteCommandTreeNode(new MavenInstallBatchFileWriteCommand(project));
		pomTreeNode.add(mavenInstallPomTreeNode);
		
		
		/*
		 * webapp
		 */
		FileWriteCommandTreeNode webappTreeNode = new FileWriteCommandTreeNode("webapp files");
		configurationTreeNode.add(webappTreeNode);
		
		FileWriteCommandTreeNode webXmlPomTreeNode = new FileWriteCommandTreeNode(new Richfaces4WebXmlFileWriteCommand(project));
		webappTreeNode.add(webXmlPomTreeNode);
		
		FileWriteCommandTreeNode propertiesTreeNode = new FileWriteCommandTreeNode(new ProjectPropertiesFileWriteCommand(project));
		webappTreeNode.add(propertiesTreeNode);
		
		FileWriteCommandTreeNode populatorPropertiesTreeNode = new FileWriteCommandTreeNode(new PopulatorProjectPropertiesFileWriteCommand(project));
		webappTreeNode.add(populatorPropertiesTreeNode);
		
		FileWriteCommandTreeNode logbackTreeNode = new FileWriteCommandTreeNode(new LogbackFileWriteCommand(project));
		webappTreeNode.add(logbackTreeNode);
		
		
		/*
		 * spring
		 */
		FileWriteCommandTreeNode springTreeNode = new FileWriteCommandTreeNode("spring configuration files");
		configurationTreeNode.add(springTreeNode);
		
		FileWriteCommandTreeNode springRepositoryTreeNode = new FileWriteCommandTreeNode(new SpringRepositoryFileWriteCommand(project));
		springTreeNode.add(springRepositoryTreeNode);
		
		FileWriteCommandTreeNode springPopulatorRepositoryTreeNode = new FileWriteCommandTreeNode(new SpringPopulatorRepositoryFileWriteCommand(project));
		springTreeNode.add(springPopulatorRepositoryTreeNode);
		
		FileWriteCommandTreeNode logbackPopulatorTreeNode = new FileWriteCommandTreeNode(new LogbackPopulatorFileWriteCommand(project));
		springTreeNode.add(logbackPopulatorTreeNode);
		
		FileWriteCommandTreeNode springBusinessComponentTreeNode = new FileWriteCommandTreeNode(new SpringBusinessComponentFileWriteCommand(project));
		springTreeNode.add(springBusinessComponentTreeNode);
		
		FileWriteCommandTreeNode springServicesTreeNode = new FileWriteCommandTreeNode(new SpringServicesFileWriteCommand(project));
		springTreeNode.add(springServicesTreeNode);
		
		FileWriteCommandTreeNode springWebappTreeNode = new FileWriteCommandTreeNode(new Richfaces4SpringWebappFileWriteCommand(project));
		springTreeNode.add(springWebappTreeNode);
		
		FileWriteCommandTreeNode springPopulatorTreeNode = new FileWriteCommandTreeNode(new SpringPopulatorFileWriteCommand(project));
		springTreeNode.add(springPopulatorTreeNode);
		
		
		/*
		 * java files
		 */
		FileWriteCommandTreeNode javaTreeNode = new FileWriteCommandTreeNode("java files");
		configurationTreeNode.add(javaTreeNode);
		
		FileWriteCommandTreeNode baseControllerTreeNode = new FileWriteCommandTreeNode(new RichfacesBaseControllerFileWriteCommand(project));
		javaTreeNode.add(baseControllerTreeNode);
		
		FileWriteCommandTreeNode customFilterTreeNode = new FileWriteCommandTreeNode(new RichfacesCustomFilterFileWriteCommand(project));
		javaTreeNode.add(customFilterTreeNode);
		
		FileWriteCommandTreeNode populatorLauncherTreeNode = new FileWriteCommandTreeNode(new PopulatorLauncherFileWriteCommand(project));
		javaTreeNode.add(populatorLauncherTreeNode);
		
				
		
		/*
		 * spring test files
		 */
		FileWriteCommandTreeNode springTestTreeNode = new FileWriteCommandTreeNode("spring test configuration files");
		configurationTreeNode.add(springTestTreeNode);
		
		FileWriteCommandTreeNode springTestRepositoryTreeNode = new FileWriteCommandTreeNode(new SpringTestRepositoryFileWriteCommand(project));
		springTestTreeNode.add(springTestRepositoryTreeNode);
		
		FileWriteCommandTreeNode logbackTestTreeNode = new FileWriteCommandTreeNode(new LogbackTestFileWriteCommand(project));
		springTestTreeNode.add(logbackTestTreeNode);
		
		return configurationTreeNode;
	}

}
