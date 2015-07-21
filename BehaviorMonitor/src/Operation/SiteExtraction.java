package Operation;

import io.ReadFromFile;

import java.util.List;

import base.PMHibernateImpl;
import bean.SiteReading;

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
		 new SiteExtraction().extractSite("BrowserHistory.txt");
		System.out.println(PMHibernateImpl.getInstance().retrieveSiteReading()
				.size());
	}
}
