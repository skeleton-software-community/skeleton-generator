package com.skeleton.generator.bl.factory.interfaces;

import com.skeleton.generator.model.metadata.PackageMetaData;
import com.skeleton.generator.model.om.Model;
import com.skeleton.generator.model.om.Package;


/**
 * 
 * @author Nicolas Thibault
 *
 */
public interface PackageFactory {

	Package buildPackage(PackageMetaData packageMetaData, Model model);

}
