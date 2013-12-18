package fengfei.ucm.relation.entity;

public class Metadata implements java.io.Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public long sourceId; // required
	public String type; // required
	public int count; // required
	public int state; // required
	public long updatedAt; // required
	public int createdAt; // required

	public Metadata() {
	}

	public Metadata(long sourceId, String type, int count, int state,
			long updatedAt, int createdAt) {
		super();
		this.sourceId = sourceId;
		this.type = type;
		this.count = count;
		this.state = state;
		this.updatedAt = updatedAt;
		this.createdAt = createdAt;
	}

	public long getSourceId() {
		return sourceId;
	}

	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(long updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(int createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + count;
		result = prime * result + createdAt;
		result = prime * result + (int) (sourceId ^ (sourceId >>> 32));
		result = prime * result + state;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + (int) (updatedAt ^ (updatedAt >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Metadata other = (Metadata) obj;
		if (count != other.count)
			return false;
		if (createdAt != other.createdAt)
			return false;
		if (sourceId != other.sourceId)
			return false;
		if (state != other.state)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (updatedAt != other.updatedAt)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Metadata [sourceId=" + sourceId + ", type=" + type + ", count="
				+ count + ", state=" + state + ", updatedAt=" + updatedAt
				+ ", createdAt=" + createdAt + "]";
	}

}
