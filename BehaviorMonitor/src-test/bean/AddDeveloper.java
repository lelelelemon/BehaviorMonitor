package bean;

import base.PMHibernateImpl;

public class AddDeveloper {
	public static void main(String[] args) {
		Developer dev = new Developer();
		dev.setName("xiaoming");
		PMHibernateImpl.getInstance().save(dev);
		//PMHibernateImpl.getInstance().delete(dev);
		
	}
}
