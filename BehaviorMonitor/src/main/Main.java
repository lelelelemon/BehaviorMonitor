package main;

import java.util.Date;
import java.util.List;

import Operation.SiteExtraction;
import Operation.relationBuild;
import base.PMHibernateImpl;
import bean.Duration;
import date.DateUtil;

public class Main {

	public static void main(String[] args) {
		// List<SiteReading> siteReadings = PMHibernateImpl.getInstance()
		// .retrieveSiteReading();
		// for (SiteReading siteReading : siteReadings) {
		// if (siteReading.getHost().equals("www.cnblogs.com"))
		// HTMLParserExtraction.extractCNBLOG(siteReading.getAddress());
		// else if (siteReading.getHost().equals("blog.csdn.net"))
		// HTMLParserExtraction.extractCSDN(siteReading.getAddress());
		// else if (siteReading.getHost().equals("stackoverflow.com")) {
		// HTMLParserExtraction.extractSOF(siteReading.getAddress());
		// }
		//
		// }
		List<Duration> durations = PMHibernateImpl.getInstance()
				.retrieveDuration();
		Duration duration = durations.get(0);
		Date start = duration.getStart();
		Date end = duration.getEnd();
		System.out.print("start is " + start);
		System.out.print("end is " + end);
		SiteExtraction.retrieveBrowserHisWithLimit("history3.xml", start, end);
		relationBuild.buildBetween(start, end);
		
	}
}
 