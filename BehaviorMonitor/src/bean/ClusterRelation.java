package bean;

import javax.persistence.Entity;

import base.BaseModelObject;

@Entity
public class ClusterRelation extends BaseModelObject {
	private String name;
	private String metaFile;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMetaFile() {
		return metaFile;
	}

	public void setMetaFile(String metaFile) {
		this.metaFile = metaFile;
	}

}