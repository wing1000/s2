package fengfei.ucm.entity.profile;

import java.io.Serializable;

/**
 * UserAttrType
 */
public class AttrType implements Serializable {

    private static final long serialVersionUID = 1L;
    /** uAttrId **/
    private Integer uAttrId;

    /** name **/
    private String name;
    /** dataType **/
    private String dataType;
    /** desc **/
    private String desc;

    public Integer getUAttrId() {
        return uAttrId;
    }

    public void setUAttrId(Integer uAttrId) {
        this.uAttrId = uAttrId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}