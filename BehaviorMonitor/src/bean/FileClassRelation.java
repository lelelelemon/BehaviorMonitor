package bean;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;

import base.BaseModelObject;
@Entity
@Embeddable
public class FileClassRelation extends BaseModelObject {
	
	private String className;
	private String fileAbsName  ;
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getFileAbsName() {
		return fileAbsName;
	}

	public void setFileAbsName(String fileAbsName) {
		this.fileAbsName = fileAbsName;
	}

}
