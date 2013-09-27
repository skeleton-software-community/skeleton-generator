package com.skeleton.generator.bl.factory.model.interfaces;

import com.skeleton.generator.model.metadata.TableMetaData;
import com.skeleton.generator.model.om.Bean;
import com.skeleton.generator.model.om.Table;

public interface BeanFactory {

	Bean buildBean(TableMetaData tableMetaData, Table table);

}
