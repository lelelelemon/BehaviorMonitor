package bean;

import javax.persistence.Entity;

import org.hibernate.annotations.Type;

import base.BaseModelObject;

@Entity
public class RecomRelation extends BaseModelObject {
	@Type(type = "text")
	String webSite;
	String fileAbsPath;
	double value;

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getFileAbsPath() {
		return fileAbsPath;
	}

	public void setFileAbsPath(String fileAbsPath) {
		this.fileAbsPath = fileAbsPath;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

}
