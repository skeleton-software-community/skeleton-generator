package org.sklsft.generator.bc.metadata.factory.interfaces;

import java.util.List;

import org.sklsft.generator.model.domain.Model;
import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.metadata.PackageMetaData;


/**
 * 
 * @author Nicolas Thibault
 *
 */
public interface PackageFactory {


	List<Package> scanPackages(PackageMetaData packageMetaData, Model model, Package parent);

	void fillPackage(PackageMetaData packageMetaData, Model model);

}
