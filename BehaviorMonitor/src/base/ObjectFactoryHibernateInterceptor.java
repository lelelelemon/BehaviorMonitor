package base;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ObjectFactoryHibernateInterceptor extends EmptyInterceptor
		implements ApplicationContextAware {

	private static final long serialVersionUID = 5054659049155012993L;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {

	}

	@Override
	public boolean onLoad(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		// TODO Auto-generated method stub
		return super.onLoad(entity, id, state, propertyNames, types);
	}

}
