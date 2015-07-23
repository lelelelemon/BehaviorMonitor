package Operation;

import io.XMLReaderTest;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.management.relation.Relation;

import base.PMHibernateImpl;
import bean.FileReading;
import bean.RecomRelation;
import bean.SiteReading;
import date.DateUtil;

public class relationBuild {
	final static double A = 0.5;
	final static double B = 0.5;

	public static void buildRelation(List<FileReading> codeHisArray) {
		for (int i = 0; i < codeHisArray.size(); i++) {
			FileReading codeHis = codeHisArray.get(i);
			Date startTime = codeHis.getStartTime();
			Date endTime = codeHis.getEndTime();
			if (i != codeHisArray.size() - 1) {
				endTime = codeHisArray.get(i + 1).getStartTime();
				codeHis.setEndTime(endTime);
				PMHibernateImpl.getInstance().save(codeHis);
			}
			String stringS = DateUtil.format(startTime, "yyyy-MM-dd HH:mm:ss");
			String stringE = DateUtil.format(endTime, "yyyy-MM-dd HH:mm:ss");

			// browserHisArray should be retrieved from the database
			List<SiteReading> browserHisArray = null;
			browserHisArray = PMHibernateImpl.getInstance()
					.retrieveSiteReadingByTime(stringS, stringE);
			if (browserHisArray.equals(null) || browserHisArray.size() == 0)
				continue;
			for (SiteReading browserHis : browserHisArray) {
				Date broStartTime = browserHis.getStartTime();
				Date broEndTime = browserHis.getEndTime();
				// if the start time of browsertime is between the code history
				double value = 0;
				value = (broEndTime.getTime() - broStartTime.getTime()) * A
						+ (broStartTime.getTime() - startTime.getTime()) * B;
				RecomRelation relation = new RecomRelation();
				relation.setWebSite(browserHis.getAddress());
				relation.setFileAbsPath(codeHis.getName());
				relation.setValue(value);
				PMHibernateImpl.getInstance().save(relation);
			}
		}

	}

	public static void buildYesterday() {
		Date date = new Date();
		Date date1 = date;
		date1.setDate(date.getDate() - 1);
		buildBetween(date, date1);
	}

	public static void buildBetween(Date start, Date end) {

		String startS = DateUtil.format(start, "yyyy-MM-dd HH:mm:ss");
		String startE = DateUtil.format(end, "yyyy-MM-dd HH:mm:ss");
		List<FileReading> filereadings = PMHibernateImpl.getInstance()
				.retrieveFileReading(startS, startE);
		FileReading fr = filereadings.get(filereadings.size() - 1);
		fr.setEndTime(end);
		PMHibernateImpl.getInstance().save(fr);
		buildRelation(filereadings);

	}

	public static void main(String[] args) throws ParseException {

	}

}
