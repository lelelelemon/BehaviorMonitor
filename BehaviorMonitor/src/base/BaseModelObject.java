package base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@MappedSuperclass
public abstract class BaseModelObject implements IModelObject,
		Comparable<IModelObject> {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer _id) {
		id = _id;
	}

	@Override
	public int compareTo(IModelObject o) {
		return this.getId().compareTo(o.getId());
	}

	public boolean equals(Object other) {
		if (other == null || other.getClass() != this.getClass())
			return false;
		EqualsBuilder eb = new EqualsBuilder();
		eb.append(this.getId(), ((IModelObject) other).getId());
		return eb.isEquals();
	}

	/**
	 * use HashCodeBuilder to calculate a hashcode
	 */
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

}
