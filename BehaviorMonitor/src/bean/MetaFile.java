package bean;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import base.BaseModelObject;
import base.PMHibernateImpl;

@Entity
public class MetaFile extends BaseModelObject {
	private String file_id;
	private String path;
	private String name;
	@Type(type = "text")
	private String content;

	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}

	@Id
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void save() {
		PMHibernateImpl.getInstance().save(this);
	}

}
