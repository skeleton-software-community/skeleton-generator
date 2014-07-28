package org.sklsft.generator.bc.metadata.interfaces;

import org.sklsft.generator.model.metadata.PackageMetaData;
import org.sklsft.generator.model.om.Model;
import org.sklsft.generator.model.om.Package;


/**
 * 
 * @author Nicolas Thibault
 *
 */
public interface PackageFactory {


	Package scanPackage(PackageMetaData packageMetaData, Model model);

	Package fillPackage(PackageMetaData packageMetaData, Model model);

}
