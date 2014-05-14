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


	Package scanPackage(PackageMetaData packageMetaData, Model model);

	Package fillPackage(PackageMetaData packageMetaData, Model model);

}
