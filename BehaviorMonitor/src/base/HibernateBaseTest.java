package base;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:HibernateApplicationContext-aop.xml" })
@TransactionConfiguration(transactionManager = "transactionManager")
@Transactional
public class HibernateBaseTest extends TestCase implements
		ApplicationContextAware {
	protected ApplicationContext appContext;

	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		appContext = context;

	}

	public IPersistenceManager getPersistenceManager() {
		return (IPersistenceManager) appContext.getBean("persistenceManager");
	}
	
	protected void assertObjectPersisted(IModelObject object) {
		assertNotNull(object.getId());
	}
	

}
