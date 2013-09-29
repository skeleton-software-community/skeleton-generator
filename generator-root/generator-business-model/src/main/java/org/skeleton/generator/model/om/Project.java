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

	public String domainName;
    public String projectName;
    public String sourceFolder;
    public String workspaceFolder;
    public String serverDNS;
    public String serverPort;
    public String wsUrl;
    public SkeletonType skeletonType;
    public DatabaseEngine databaseEngine;
    public String databaseName;
    public String userName;
    public String password;
    
    public Model model;
}
