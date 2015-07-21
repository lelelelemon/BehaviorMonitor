package bean;

import javax.persistence.Entity;

import base.BaseModelObject;
@Entity

public class Developer extends BaseModelObject {
	private String developer_id;
	

	public String getDeveloper_id() {
		return developer_id;
	}

	public void setDeveloper_id(String developer_id) {
		this.developer_id = developer_id;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
