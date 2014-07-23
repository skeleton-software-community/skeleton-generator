package org.sklsft.generator.bc.factory.model.interfaces;

import org.sklsft.generator.model.metadata.TableMetaData;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.Model;
import org.sklsft.generator.model.om.Table;

public interface BeanFactory {

	Bean scanBean(TableMetaData tableMetaData, Table table);
	Bean fillBean(Table table, Model model);
	

}
