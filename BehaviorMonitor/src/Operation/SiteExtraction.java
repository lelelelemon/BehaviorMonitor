package Operation;

import io.ReadFromFile;
import io.XMLReaderTest;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

	public static void main(String[] args) {
		//new SiteExtraction().extractSite("BrowserHistory.txt");
		retrieveBrowserHis("history.xml");
//		System.out.println(PMHibernateImpl.getInstance().retrieveSiteReading()
//				.size());
	}

	public static void retrieveBrowserHis(String filename) {
		Map<String, String> historys = XMLReaderTest
				.readXMLString(filename);
		for (String key : historys.keySet()) {
			String value = historys.get(key);
			Date startTime;
			try {
				startTime = DateUtil.parse(value);
				SiteReading browserHis = new SiteReading();
				browserHis.setAddress(key);
				browserHis.setStartTime(startTime);
				PMHibernateImpl.getInstance().save(browserHis);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
