package com.on.util.Entity;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="on_a_code", catalog=  "pro_lamp")
public class OnACode {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "code_id")
    private Long codeId;

    @Column(name = "code_type")
    private String codeType;

    @Column(name = "code_name")
    private String codeName;

    @Column(name = "value")
    private String value;

    @Column(name = "create_time")
    private Date createTime;

    public Long getCodeId() {
        return codeId;
    }

    public void setCodeId(Long codeId) {
        this.codeId = codeId;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
