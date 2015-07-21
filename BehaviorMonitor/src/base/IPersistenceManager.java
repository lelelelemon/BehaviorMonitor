/*
 * Copyright 2006 Centenum Software Solutions, Inc. All rights reserved.
 * CENTENUM PROPRIETARY/CONFIDENTIAL. 
 */
package base;

import java.io.File;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

import bean.Duration;
import bean.FileReading;
import bean.OnlineText;
import bean.RecomRelation;
import bean.SiteReading;

/**
 * Object Factory is used to process O-R mapping for model objects
 * 
 * @author zhangtiange
 */
public interface IPersistenceManager {

	/**
	 * create an instance of specified type
	 * 
	 * @param <E>
	 * @param clazz
	 * @return instance of class
	 */
	IModelObject create(Class<? extends IModelObject> clazz);

	/**
	 * load a persistened object
	 * 
	 * @param <E>
	 * @param clazz
	 *            type of persistened object
	 * @param id
	 *            object ID
	 * @return result object
	 */
	<T extends IModelObject> T get(Class<T> clazz, Serializable id);

	/**
	 * load the model object and synchronize the object with DB
	 * 
	 * @param e
	 * @return the loaded model object
	 */
	IModelObject load(IModelObject e);

	/**
	 * delete the model object
	 * 
	 * @param obj
	 */
	void delete(IModelObject obj);

	/**
	 * logic delete the model object
	 * 
	 * @param obj
	 */
	void logicDelete(IModelObject obj);

	/**
	 * delete all the model object of specified type
	 * 
	 * @param clazz
	 */
	void deleteAll(Class<? extends IModelObject> clazz);

	/**
	 * save the model object
	 * 
	 * @param obj
	 */
	IModelObject save(IModelObject obj);

	IModelObject create(IModelObject obj);

	/**
	 * save the model object with specified id
	 * 
	 * @param obj
	 * @param id
	 */
	void save(IModelObject obj, Serializable id);

	/**
	 * get all objects of specified type
	 * 
	 * @param clazz
	 * @return
	 */
	<T extends IModelObject> Collection<T> all(Class<T> clazz);

	/**
	 * check if the specified object is exist
	 * 
	 * @param value
	 * @return
	 */
	boolean exist(IModelObject value);

	/**
	 * get hibernate session
	 * 
	 * @return Hibernate Session
	 */
	Session getHibernateSession();

	Query createQuery(String hql);

	Criteria createCriteria(Class<? extends IModelObject> clazz);

	public List<File> retrieveFiles();

	public List<SiteReading> retrieveSiteReading();

	public List<OnlineText> retrieveOnlineText();

	public List<SiteReading> retrieveSiteReadingByTime(String start, String end);

	public List<RecomRelation> retrieveRecomRelat(String filename);

	public List<String> retrieveRecomWeb(String filename);

	public List<FileReading> retrieveFileReading(String start, String end);
	
	public List<Duration> retrieveDuration();
}
