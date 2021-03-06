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
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import bean.ClusterRelation;
import bean.Duration;
import bean.FileReading;
import bean.OnlineText;
import bean.RecomRelation;
import bean.SiteReading;

/**
 * A hibernate/Spring framework implementation for IObjectFactory
 * 
 * @author zhang tiange
 * 
 */
@Transactional
public class PMHibernateImpl extends HibernateDaoSupport implements
		IPersistenceManager, BeanFactoryAware {

	public static IPersistenceManager getInstance() {
		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
				new String[] { "HibernateApplicationContext-aop.xml" });
		return (IPersistenceManager) ((BeanFactory) appContext)
				.getBean("persistenceManager");
	}

	BeanFactory beanFactory;

	private Class getImplClass(Class clazz) {
		if (clazz.isInterface())
			return beanFactory.getBean(getBeanID(clazz)).getClass();
		else
			return clazz;
	}

	private String getBeanID(Class clazz) {
		int pos = clazz.getName().lastIndexOf('.') + 1;
		return clazz.getName().substring(pos);
	}

	public IModelObject create(Class clazz) {
		IModelObject bean = (IModelObject) beanFactory
				.getBean(getBeanID(clazz));
		return bean;
	}

	public IModelObject get(Class clazz, Serializable id) {
		IModelObject bean = (IModelObject) getHibernateTemplate().get(
				getImplClass(clazz), id);
		return bean;
	}

	public IModelObject load(IModelObject e) {
		getHibernateTemplate().load(e, e.getId());
		return e;
	}

	public void delete(IModelObject obj) {
		getHibernateTemplate().delete(obj);
	}

	public IModelObject save(IModelObject obj) {
		getSession().saveOrUpdate(obj);
		return obj;
	}

	public IModelObject create(IModelObject obj) {
		getSession().saveOrUpdate(obj);
		return obj;
	}

	public <T extends IModelObject> Collection<T> all(Class<T> clazz) {
		return getHibernateTemplate().find("from " + clazz.getName());
	}

	public boolean exist(IModelObject value) {
		throw new RuntimeException("not supported");
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.fudan.framework.persistence.IObjectFactory#deleteAll(java.lang.Class)
	 */
	public void deleteAll(Class clazz) {
		getHibernateTemplate().deleteAll(all(clazz));
	}

	public Criteria createCriteria(Class clazz) {
		return getHibernateSession().createCriteria(clazz);
	}

	public void save(IModelObject model, Serializable id) {
		// getSession().persist(model, id);
	}

	public Session getHibernateSession() {
		return getSession();
	}

	public void logicDelete(IModelObject modelObject) {
		save(modelObject);
	}

	@Override
	public Query createQuery(String hql) {
		return getHibernateSession().createQuery(hql);
	}

	public List<File> retrieveFiles() {
		List<File> files = (List<File>) getHibernateSession().createQuery(
				"from file").list();
		return files;
	}

	public List<SiteReading> retrieveSiteReading() {
		List<SiteReading> siteReadings = (List<SiteReading>) getHibernateSession()
				.createQuery("from SiteReading").list();
		return siteReadings;
	}

	// get siteReading between start and end
	public List<SiteReading> retrieveSiteReadingByTime(String start, String end) {
		List<SiteReading> siteReadings = (List<SiteReading>) getHibernateSession()
				.createQuery(
						"from SiteReading where startTime > '" + start
								+ "' and startTime < '" + end
								+ "' order by startTime ASC").list();
		return siteReadings;
	}

	public List<OnlineText> retrieveOnlineText() {
		List<OnlineText> onlineText = (List<OnlineText>) getHibernateSession()
				.createQuery("from OnlineText").list();
		return onlineText;
	}

	public List<RecomRelation> retrieveRecomRelat(String filename) {
		List<RecomRelation> recomRelation = (List<RecomRelation>) getHibernateSession()
				.createQuery("from RecomRelation").list();
		return recomRelation;
	}

	public List<String> retrieveRecomWeb(String filename) {
		List<String> recomRelation = (List<String>) getHibernateSession()
				.createQuery(
						"select webSite from RecomRelation where fileabspath = '"
								+ filename + "' order by value DESC").list();
		return recomRelation;
	}

	public List<FileReading> retrieveFileReading(String start, String end) {
		List<FileReading> fileReadings = (List<FileReading>) getHibernateSession()
				.createQuery(
						"from FileReading where startTime > '" + start
								+ "' and startTime < '" + end + "'").list();
		return fileReadings;
	}

	public List<Duration> retrieveDuration() {
		List<Duration> durations = (List<Duration>) getHibernateSession()
				.createQuery("from Duration order by start DESC limit 1")
				.list();
		return durations;
	}

	public List<FileReading> retrieveFileReading() {
		List<FileReading> filereadings = (List<FileReading>) getHibernateSession()
				.createQuery("from FileReading order by startTime DESC limit 1")
				.list();
		return filereadings;
	}

	public List<ClusterRelation> retrieveClusterRela() {
		List<ClusterRelation> clusterRelas = (List<ClusterRelation>) getHibernateSession()
				.createQuery("from ClusterRelation").list();
		return clusterRelas;
	}

	public List<ClusterRelation> retrieveClusterRelaByLabel(String label) {
		List<ClusterRelation> clusterRelas = (List<ClusterRelation>) getHibernateSession()
				.createQuery(
						"from ClusterRelation where name = '" + label
								+ "'  order by score DESC").list();
		return clusterRelas;
	}

	public List<ClusterRelation> retrieveClusterRelaByAddress(String address) {
		List<ClusterRelation> clusterRelas = (List<ClusterRelation>) getHibernateSession()
				.createQuery(
						"from ClusterRelation where metafile = '" + address
								+ "'order by score DESC").list();
		return clusterRelas;
	}

}
