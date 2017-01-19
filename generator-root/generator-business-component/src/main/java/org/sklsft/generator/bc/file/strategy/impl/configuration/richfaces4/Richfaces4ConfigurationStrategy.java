package org.sklsft.generator.bc.file.strategy.impl.configuration.richfaces4;

import org.sklsft.generator.bc.file.command.impl.conf.context.DataSourceContextFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.envers.AuditEntityFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.envers.AuditListenerFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.ApiPomFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.BusinessComponentPomFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.BusinessModelPomFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.MavenInstallBatchFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.PopulatorPomFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.RepositoryPomFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.Richfaces4RootPomFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.Richfaces4WebappPomFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.ServicesPomFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.TestPomFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.pom.UtilPomFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.population.LogbackPopulatorFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.population.PopulatorLauncherFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.population.PopulatorProjectPropertiesFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.spring.Richfaces4SpringWebappFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.spring.SpringBusinessComponentFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.spring.SpringPopulatorFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.spring.SpringPopulatorRepositoryFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.spring.SpringRepositoryFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.spring.SpringServicesFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.spring.SpringTestRepositoryFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.test.LogbackTestFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.webapp.LogbackFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.webapp.ProjectPropertiesFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.webapp.Richfaces4WebXmlFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.webapp.mvc.controller.RichfacesBaseControllerFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.conf.webapp.mvc.filter.RichfacesCustomFilterFileWriteCommand;
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
		
		FileWriteCommandTreeNode testPomTreeNode = new FileWriteCommandTreeNode(new TestPomFileWriteCommand(project));
		pomTreeNode.add(testPomTreeNode);
		
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
		
		FileWriteCommandTreeNode logbackTreeNode = new FileWriteCommandTreeNode(new LogbackFileWriteCommand(project));
		webappTreeNode.add(logbackTreeNode);
		
		FileWriteCommandTreeNode baseControllerTreeNode = new FileWriteCommandTreeNode(new RichfacesBaseControllerFileWriteCommand(project));
		webappTreeNode.add(baseControllerTreeNode);
		
		FileWriteCommandTreeNode customFilterTreeNode = new FileWriteCommandTreeNode(new RichfacesCustomFilterFileWriteCommand(project));
		webappTreeNode.add(customFilterTreeNode);
		
		
		/*
		 * spring
		 */
		FileWriteCommandTreeNode springTreeNode = new FileWriteCommandTreeNode("spring configuration files");
		configurationTreeNode.add(springTreeNode);
		
		FileWriteCommandTreeNode springRepositoryTreeNode = new FileWriteCommandTreeNode(new SpringRepositoryFileWriteCommand(project));
		springTreeNode.add(springRepositoryTreeNode);
		
		FileWriteCommandTreeNode springPopulatorRepositoryTreeNode = new FileWriteCommandTreeNode(new SpringPopulatorRepositoryFileWriteCommand(project));
		springTreeNode.add(springPopulatorRepositoryTreeNode);
		
		FileWriteCommandTreeNode springBusinessComponentTreeNode = new FileWriteCommandTreeNode(new SpringBusinessComponentFileWriteCommand(project));
		springTreeNode.add(springBusinessComponentTreeNode);
		
		FileWriteCommandTreeNode springServicesTreeNode = new FileWriteCommandTreeNode(new SpringServicesFileWriteCommand(project));
		springTreeNode.add(springServicesTreeNode);
		
		FileWriteCommandTreeNode springWebappTreeNode = new FileWriteCommandTreeNode(new Richfaces4SpringWebappFileWriteCommand(project));
		springTreeNode.add(springWebappTreeNode);
		
		FileWriteCommandTreeNode springPopulatorTreeNode = new FileWriteCommandTreeNode(new SpringPopulatorFileWriteCommand(project));
		springTreeNode.add(springPopulatorTreeNode);
		
		FileWriteCommandTreeNode springTestRepositoryTreeNode = new FileWriteCommandTreeNode(new SpringTestRepositoryFileWriteCommand(project));
		springTreeNode.add(springTestRepositoryTreeNode);
		
		
		/*
		 * population
		 */
		FileWriteCommandTreeNode populationTreeNode = new FileWriteCommandTreeNode("Population");
		configurationTreeNode.add(populationTreeNode);
		
		FileWriteCommandTreeNode populatorPropertiesTreeNode = new FileWriteCommandTreeNode(new PopulatorProjectPropertiesFileWriteCommand(project));
		populationTreeNode.add(populatorPropertiesTreeNode);
		
		FileWriteCommandTreeNode populatorLauncherTreeNode = new FileWriteCommandTreeNode(new PopulatorLauncherFileWriteCommand(project));
		populationTreeNode.add(populatorLauncherTreeNode);
		
		FileWriteCommandTreeNode logbackPopulatorTreeNode = new FileWriteCommandTreeNode(new LogbackPopulatorFileWriteCommand(project));
		populationTreeNode.add(logbackPopulatorTreeNode);
		
		
		/*
		 * envers
		 */
		if (project.audited) {
			FileWriteCommandTreeNode enversTreeNode = new FileWriteCommandTreeNode("envers files");
			configurationTreeNode.add(enversTreeNode);
			
			FileWriteCommandTreeNode auditEntityTreeNode = new FileWriteCommandTreeNode(new AuditEntityFileWriteCommand(project));
			enversTreeNode.add(auditEntityTreeNode);
		
			FileWriteCommandTreeNode auditListenerTreeNode = new FileWriteCommandTreeNode(new AuditListenerFileWriteCommand(project));
			enversTreeNode.add(auditListenerTreeNode);
		}
		
		
		/*
		 * test files
		 */
		FileWriteCommandTreeNode springTestTreeNode = new FileWriteCommandTreeNode("test configuration files");
		configurationTreeNode.add(springTestTreeNode);		
		
		FileWriteCommandTreeNode logbackTestTreeNode = new FileWriteCommandTreeNode(new LogbackTestFileWriteCommand(project));
		springTestTreeNode.add(logbackTestTreeNode);
		
		return configurationTreeNode;
	}

}
