package org.skeleton.generator.model.om;

import org.skeleton.generator.util.metadata.DatabaseEngine;
import org.skeleton.generator.util.metadata.SkeletonType;


/**
 * Full representation of a project<br/>
 * Properties are willingly public because of their intensive use in file write commands<br/>
 * @author Nicolas Thibault
 *
 */
public class Project {

    public String sourceFolder;
    public String workspaceFolder;
	public String domainName;
    public String projectName;
    public SkeletonType skeletonType;
    
    public DatabaseEngine databaseEngine;
    public String databaseName;
    public String databaseDNS;
    public String databasePort;
 public String databaseUserName;
    public String databasePassword;
    
    public boolean audited;
    
    public Model model;
}
