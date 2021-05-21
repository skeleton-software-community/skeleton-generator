package org.sklsft.generator.components.metadata.factory.impl;

import javax.annotation.Resource;

import org.sklsft.generator.components.metadata.factory.interfaces.BasicViewBeanFactory;
import org.sklsft.generator.components.metadata.factory.interfaces.ViewPropertiesFactory;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.ui.BasicViewBean;
import org.springframework.stereotype.Component;

@Component("javaBasicViewBeanFactory")
public class JavaBasicViewBeanFactory implements BasicViewBeanFactory {
	
	@Resource(name="javaViewPropertiesFactory")
	private ViewPropertiesFactory viewPropertiesFactory;

	@Override
	public BasicViewBean getBasicViewBean(Bean bean) {
				
		BasicViewBean basicViewBean = new BasicViewBean();
		
		basicViewBean.className = bean.className + "BasicView";
		basicViewBean.objectName = bean.objectName + "BasicView";
		
		basicViewBean.baseMapperClassName = basicViewBean.className + "BaseMapper";
		basicViewBean.mapperClassName = basicViewBean.className + "Mapper";
		basicViewBean.mapperObjectName = basicViewBean.objectName + "Mapper";
		
		basicViewBean.filterClassName = bean.className + "Filter";
		basicViewBean.filterObjectName = bean.objectName + "Filter";
		basicViewBean.sortingClassName = bean.className + "Sorting";
		
		basicViewBean.properties = viewPropertiesFactory.getBasicViewProperties(bean);
		
		return basicViewBean;
	}

	@Override
	public BasicViewBean getBasicViewBean(OneToMany oneToMany) {
		
		BasicViewBean basicViewBean = new BasicViewBean();
		
		Bean bean = oneToMany.referenceBean;
		
		basicViewBean.className = bean.className + "BasicView";
		basicViewBean.objectName = bean.objectName + "BasicView";
		
		basicViewBean.baseMapperClassName = basicViewBean.className + "BaseMapper";
		basicViewBean.mapperClassName = basicViewBean.className + "Mapper";
		basicViewBean.mapperObjectName = basicViewBean.objectName + "Mapper";
		
		basicViewBean.filterClassName = bean.className + "Filter";
		basicViewBean.filterObjectName = bean.objectName + "Filter";
		
		basicViewBean.properties = viewPropertiesFactory.getBasicViewProperties(oneToMany);
		
		return basicViewBean;
	}

	
}
