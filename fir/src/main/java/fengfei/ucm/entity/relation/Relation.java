package fengfei.ucm.entity.relation;

public class Relation implements java.io.Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    public long sourceId; // required
    public long targetId; // required
    public byte type; // required
    public int state; // required
    public long updatedAt; // required
    public int createdAt; // required
    public long attachmentId;//attachment id

    public Relation() {
    }

    public Relation(
            long sourceId,
            long targetId,
            byte type,
            int state,
            long updatedAt,
            int createdAt) {
        super();
        this.sourceId = sourceId;
        this.targetId = targetId;
        this.type = type;
        this.state = state;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public Relation(Relation other) {

        this.sourceId = other.sourceId;
        this.targetId = other.targetId;
        this.type = other.type;
        this.state = other.state;
        this.updatedAt = other.updatedAt;
        this.createdAt = other.createdAt;
        this.attachmentId = other.attachmentId;
    }

    public Relation deepCopy() {
        return new Relation(this);
    }

    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    public long getTargetId() {
        return targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
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
        result = prime * result + createdAt;
        result = prime * result + (int) (sourceId ^ (sourceId >>> 32));
        result = prime * result + state;
        result = prime * result + (int) (targetId ^ (targetId >>> 32));
        result = prime * result + type;
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
        Relation other = (Relation) obj;
        if (createdAt != other.createdAt)
            return false;
        if (sourceId != other.sourceId)
            return false;
        if (state != other.state)
            return false;
        if (targetId != other.targetId)
            return false;
        if (type != other.type)
            return false;
        if (updatedAt != other.updatedAt)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Relation [sourceId=" + sourceId + ", targetId=" + targetId + ", type=" + type
                + ", state=" + state + ", updatedAt=" + updatedAt + ", createdAt=" + createdAt
                + "]";
    }

}
