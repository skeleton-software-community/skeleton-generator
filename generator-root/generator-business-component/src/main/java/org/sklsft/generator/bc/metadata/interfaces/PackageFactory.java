package org.sklsft.generator.bc.metadata.interfaces;

import org.sklsft.generator.model.domain.Model;
import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.metadata.PackageMetaData;


/**
 * 
 * @author Nicolas Thibault
 *
 */
public interface PackageFactory {


	Package scanPackage(PackageMetaData packageMetaData, Model model);

	Package fillPackage(PackageMetaData packageMetaData, Model model);

}
