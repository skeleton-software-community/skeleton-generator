package org.sklsft.generator.bc.metadata.impl;

import org.sklsft.generator.bc.metadata.interfaces.FormBeanFactory;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.ui.FormBean;
import org.springframework.stereotype.Component;

@Component("javaFormBeanFactory")
public class JavaFormBeanFactory implements FormBeanFactory {

	@Override
	public FormBean getFormBean(Bean bean) {
				
		FormBean formBean = new FormBean();
		
		formBean.className = bean.className + "Form";
		formBean.objectName = bean.objectName + "Form";
		
		formBean.baseMapperClassName = formBean.className + "BaseMapper";
		formBean.mapperClassName = formBean.className + "Mapper";
		formBean.mapperObjectName = formBean.objectName + "Mapper";
		
		formBean.properties = bean.getFormProperties();
		
		return formBean;
	}
	
	
	@Override
	public FormBean getFormBean(OneToMany oneToMany) {
		
		FormBean fullViewBean = new FormBean();
		
		Bean bean = oneToMany.referenceBean;
		
		fullViewBean.className = bean.className + "Form";
		fullViewBean.objectName = bean.objectName + "Form";
		
		fullViewBean.baseMapperClassName = fullViewBean.className + "BaseMapper";
		fullViewBean.mapperClassName = fullViewBean.className + "Mapper";
		fullViewBean.mapperObjectName = fullViewBean.objectName + "Mapper";
		
		fullViewBean.properties = oneToMany.getFormProperties();
		
		return fullViewBean;
	}
}
