package bean;

import javax.persistence.Entity;

import org.hibernate.annotations.Type;
import org.simpleframework.xml.Text;

import base.BaseModelObject;

@Entity
public class ClusterRelation extends BaseModelObject {
	private String name;
	@Type(type = "text")
	private String metaFile;
	private double score;
	private String title;

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

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
