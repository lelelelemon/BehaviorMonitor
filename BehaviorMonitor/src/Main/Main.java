package Main;

import java.util.List;

import Operation.HTMLParserExtraction;
import base.PMHibernateImpl;
import bean.SiteReading;

public class Main {
	public static void main(String[] args) {
		List<SiteReading> siteReadings = PMHibernateImpl.getInstance()
				.retrieveSiteReading();
		for (SiteReading siteReading : siteReadings) {
			if (siteReading.getHost().equals("www.cnblogs.com"))
				HTMLParserExtraction.extractCNBLOG(siteReading.getAddress());
			else if (siteReading.getHost().equals("blog.csdn.net"))
				HTMLParserExtraction.extractCSDN(siteReading.getAddress());
			else if (siteReading.getHost().equals("stackoverflow.com")) {
				HTMLParserExtraction.extractSOF(siteReading.getAddress());
			}

		}
	}
}
