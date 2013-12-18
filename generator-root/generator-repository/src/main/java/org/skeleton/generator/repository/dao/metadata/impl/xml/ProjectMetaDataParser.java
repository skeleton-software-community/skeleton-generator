package org.skeleton.generator.repository.dao.metadata.impl.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.skeleton.generator.model.metadata.PackageMetaData;
import org.skeleton.generator.model.metadata.ProjectMetaData;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ProjectMetaDataParser {
	
	private XPathExpression xPathDomain, xPathName, xPathSkeletons, xPathServerUrl, xPathServerPort, xPathDbEngine, xPathDbName, xPathDbUsername,
		xPathDbPassword, xPathSrcPath, xPathDstPath, xPathAudited, xPathPackages;
	private PackageMetaDataParser packageParser;
	
	public ProjectMetaDataParser() throws XPathExpressionException {
		XPath xPath = XPathFactory.newInstance().newXPath();
		xPathDomain = xPath.compile("domain");
		xPathName = xPath.compile("name");
		xPathSkeletons = xPath.compile("skeletons/skeleton");
		xPathServerUrl = xPath.compile("server/url");
		xPathServerPort = xPath.compile("server/port");
		xPathDbEngine = xPath.compile("database/engine");
		xPathDbName = xPath.compile("database/name");
		xPathDbUsername = xPath.compile("database/username");
		xPathDbPassword = xPath.compile("database/password");
		xPathSrcPath = xPath.compile("configuration/sourcePath");
		xPathDstPath = xPath.compile("configuration/targetPath");
		xPathAudited = xPath.compile("configuration/audited");
		xPathPackages = xPath.compile("packages/package");
		
		this.packageParser = new PackageMetaDataParser(new TableMetaDataParser(new ColumnMetaDataParser()));
	}

	public ProjectMetaData parse(Element root) throws XPathExpressionException {
		
		String domain = extractDomain(root);
		String name = extractName(root);
		
		List<String> skeletons = extractSkeletons(root);
		
		String serverUrl = extractServerUrl(root);
		String serverPort = extractServerPort(root);
		
		String dbEngine = extractDbEngine(root);
		String dbName = extractDbName(root);
		String dbUsername = extractDbUsername(root);
		String dbPassword = extractDbPassword(root);
		
		String srcPath = extractSrcPath(root);
		String dstPath = extractDstPath(root);
		
		boolean audited = extractAudited(root);
		
		NodeList packageElems = extractPackages(root);
		List<PackageMetaData> packageMetaDataList = packageParser.parse(packageElems);
		
		
		
		ProjectMetaData result = new ProjectMetaData();
		result.setAudited(String.valueOf(audited));
		result.setDatabaseEngine(dbEngine);
		result.setDatabaseName(dbName);
		result.setDomainName(domain);
		result.setPackageMetaDataList(packageMetaDataList);
		result.setPassword(dbPassword);
		result.setProjectName(name);
		result.setServerDNS(serverUrl);
		result.setServerPort(serverPort);
		result.setSkeleton(skeletonToString(skeletons));
		result.setSourceFolder(srcPath);
		result.setUserName(dbUsername);
		result.setWorkspaceFolder(dstPath);
		result.setWsUrl("notconfigured");

		return result;
	}
	
	private NodeList extractPackages(Element root) throws XPathExpressionException{
		return (NodeList) xPathPackages.evaluate(root, XPathConstants.NODESET);
	}

	private boolean extractAudited(Element root) throws XPathExpressionException{
		return new Boolean(xPathAudited.evaluate(root));
	}

	private String extractDstPath(Element root) throws XPathExpressionException{
		return xPathDstPath.evaluate(root);
	}

	private String extractSrcPath(Element root) throws XPathExpressionException{
		return xPathSrcPath.evaluate(root);
	}

	private String extractDbPassword(Element root) throws XPathExpressionException{
		return xPathDbPassword.evaluate(root);
	}

	private String extractDbUsername(Element root) throws XPathExpressionException{
		return xPathDbUsername.evaluate(root);
	}

	private String extractDbName(Element root) throws XPathExpressionException{
		return xPathDbName.evaluate(root);
	}

	private String extractDbEngine(Element root) throws XPathExpressionException{
		return xPathDbEngine.evaluate(root);
	}

	private String extractServerPort(Element root) throws XPathExpressionException{
		return xPathServerPort.evaluate(root);
	}

	private String extractServerUrl(Element root) throws XPathExpressionException{
		return xPathServerUrl.evaluate(root);
	}

	private List<String> extractSkeletons(Element root) throws XPathExpressionException {
		NodeList nl = (NodeList) xPathSkeletons.evaluate(root, XPathConstants.NODESET);
		List<String> result = new ArrayList<>(nl.getLength());
		for(int i=0;i<nl.getLength();i++){
			result.add(nl.item(i).getTextContent());
		}
		
		return result;
	}

	private String extractName(Element root) throws XPathExpressionException {
		return xPathName.evaluate(root);
	}

	private String extractDomain(Element root) throws XPathExpressionException {
		return xPathDomain.evaluate(root);
	}

	private String skeletonToString(List<String> skeletons) {
		StringBuilder sb = new StringBuilder();
		for(String s : skeletons){
			sb.append(s).append(' ');
		}
		return sb.toString();
	}

}
