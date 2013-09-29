package org.skeleton.generator.bc.factory.model.interfaces;

import org.skeleton.generator.model.metadata.PackageMetaData;
import org.skeleton.generator.model.om.Model;
import org.skeleton.generator.model.om.Package;


/**
 * 
 * @author Nicolas Thibault
 *
 */
public interface PackageFactory {

	Package buildPackage(PackageMetaData packageMetaData, Model model);

}
