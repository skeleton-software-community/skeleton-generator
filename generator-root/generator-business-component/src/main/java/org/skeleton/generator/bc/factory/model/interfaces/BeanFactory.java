package org.skeleton.generator.bc.factory.model.interfaces;

import org.skeleton.generator.model.metadata.TableMetaData;
import org.skeleton.generator.model.om.Bean;
import org.skeleton.generator.model.om.Model;
import org.skeleton.generator.model.om.Table;

public interface BeanFactory {

	Bean scanBean(TableMetaData tableMetaData, Table table);
	Bean fillBean(Table table, Model model);
	

}
