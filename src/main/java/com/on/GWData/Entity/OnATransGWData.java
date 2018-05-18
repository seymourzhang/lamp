package com.on.GWData.Entity;
import java.util.Date;
import javax.persistence.*;

/**
 *on_a_deal_log日志表
 *   @AUTO
 */
@Entity(name = "on_a_deal_log")
@NamedStoredProcedureQuery(name = "on_a_TransGWData", procedureName = "on_a_TransGWData", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "_res", type = String.class)
})
public class OnATransGWData {
    // 作业批次id
    @Id
    @Column(name = "id_batch", updatable = false)
    private Integer idBatch;

    // 日志频道id
    @Column(name = "channel_id")
    private String channelId;

    // 父jobid
    @Column(name = "log_date")
    private Date logDate;

    // job名称
    @Column(name = "logging_object_type")
    private String loggingObjectType;

    // 读取行数
    @Column(name = "object_name")
    private String objectName;

    // 写入行数
    @Column(name = "object_copy")
    private String objectCopy;

    // 更新行数
    @Column(name = "repository_directory")
    private String repositoryDirectory;

    // 输入行数
    @Column(name = "filename")
    private String filename;

    // 输出行数
    @Column(name = "object_id")
    private String objectId;

    // 拒绝行数
    @Column(name = "object_revision")
    private String objectRevision;

    // 错误
    @Column(name = "parent_channel_id")
    private String parentChannelId;

    // 结果集
    @Column(name = "root_channel_id")
    private String rootChannelId;

    @Column(name = "_res")
    private String _res;


    public Integer getIdBatch() {
        return idBatch;
    }

    public void setIdBatch(Integer idBatch) {
        this.idBatch = idBatch;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getLoggingObjectType() {
        return loggingObjectType;
    }

    public void setLoggingObjectType(String loggingObjectType) {
        this.loggingObjectType = loggingObjectType;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectCopy() {
        return objectCopy;
    }

    public void setObjectCopy(String objectCopy) {
        this.objectCopy = objectCopy;
    }

    public String getRepositoryDirectory() {
        return repositoryDirectory;
    }

    public void setRepositoryDirectory(String repositoryDirectory) {
        this.repositoryDirectory = repositoryDirectory;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectRevision() {
        return objectRevision;
    }

    public void setObjectRevision(String objectRevision) {
        this.objectRevision = objectRevision;
    }

    public String getParentChannelId() {
        return parentChannelId;
    }

    public void setParentChannelId(String parentChannelId) {
        this.parentChannelId = parentChannelId;
    }

    public String getRootChannelId() {
        return rootChannelId;
    }

    public void setRootChannelId(String rootChannelId) {
        this.rootChannelId = rootChannelId;
    }

    public String get_res() {
        return this._res;
    }

    public void set_res(String _res) {
        this._res = _res;
    }

}
