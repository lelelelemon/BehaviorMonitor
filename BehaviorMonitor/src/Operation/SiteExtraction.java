package Operation;

import io.ReadFromFile;
import io.XMLReaderTest;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

import base.PMHibernateImpl;
import bean.SiteReading;
import date.DateUtil;

// extract site from files
public class SiteExtraction {
	// extract all site from history files
	public boolean extractSite(String fileName) {
		List<String> sites = ReadFromFile.readFileByLines(fileName);
		for (String site : sites) {
			SiteReading siteReading = new SiteReading();
			siteReading.setAddress(site);
			siteReading.setHost(siteReading.getHost());
			PMHibernateImpl.getInstance().save(siteReading);
		}
		return false;
	}

	public static void main(String[] args) throws ParseException {
		String time = " 2015-7-20";
		String time2 = "2015-7-21 00:00:00";
		Date date1 = new Date();
		Date date2 = new Date();
		date2.setDate(date2.getDate() - 1);
		System.out.println("d1 is " + time);
		// new SiteExtraction().extractSite("BrowserHistory.txt");
		// retrieveBrowserHis("history.xml");
		retrieveBrowserHisWithLimit("history3.xml", date2, date1);
		int size = PMHibernateImpl.getInstance()
				.retrieveSiteReadingByTime(time, time2).size();
		// System.out.println("size is : " + size);
		// System.out.println(PMHibernateImpl.getInstance().retrieveSiteReading()
		// .size());
	}

	public static void retrieveBrowserHis(String filename) {
		Map<String, String> historys = XMLReaderTest.readXMLString(filename);
		for (String key : historys.keySet()) {
			String value = historys.get(key);
			Date startTime;
			try {
				startTime = DateUtil.parse(value);
				SiteReading browserHis = new SiteReading();
				browserHis.setAddress(key);
				browserHis.setStartTime(startTime);
				browserHis.extractHost();
				System.out.println("Start time is : " + startTime);
				PMHibernateImpl.getInstance().save(browserHis);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public static void retrieveBrowserHisWithLimit(String filename, Date start,
			Date end) {
		Map<String, String> historys = XMLReaderTest.readXMLString(filename);
		System.out.println(historys.size());
		for (String key : historys.keySet()) {
			String value = historys.get(key);
			System.out.println("value " + value);
			Date startTime;
			Date endTime = end;
			try {
				startTime = DateUtil.parse(value);
				System.out.println("value " + startTime);
				if (startTime.before(end) && startTime.after(start)) {
					SiteReading browserHis = new SiteReading();
					browserHis.setAddress(key);
					browserHis.setStartTime(startTime);
					browserHis.setEndTime(endTime);
					browserHis.extractHost();
					if (browserHis.getHost().equals("www.cnblogs.com")) {
						HTMLParserExtraction.extractCNBLOG(browserHis
								.getAddress());
						PMHibernateImpl.getInstance().save(browserHis);
					} else if (browserHis.getHost().equals("blog.csdn.net")) {
						HTMLParserExtraction.extractCSDN(browserHis
								.getAddress());
						PMHibernateImpl.getInstance().save(browserHis);
					} else if (browserHis.getHost().equals("stackoverflow.com")) {
						HTMLParserExtraction
								.extractSOF(browserHis.getAddress());
						PMHibernateImpl.getInstance().save(browserHis);
					}

					System.out.println("Start time is : " + startTime);

				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		String stringS = DateUtil.format(start, "yyyy-MM-dd HH:mm:ss");
		String stringE = DateUtil.format(end, "yyyy-MM-dd HH:mm:ss");

		List<SiteReading> siteReadings = PMHibernateImpl.getInstance()
				.retrieveSiteReadingByTime(stringS, stringE);
		for (SiteReading sr : siteReadings) {
			int index = siteReadings.indexOf(sr) + 1;
			if (index == siteReadings.size())
				break;
			sr.setEndTime(siteReadings.get(index).getStartTime());
			PMHibernateImpl.getInstance().save(sr);
		}
	}

}
