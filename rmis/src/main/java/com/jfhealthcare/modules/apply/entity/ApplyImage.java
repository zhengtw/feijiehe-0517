package com.jfhealthcare.modules.apply.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Table(name = "apply_image")
@Data
public class ApplyImage {
    /**
     * 影像唯一号码
     */
    @Id
    @Column(name = "instance_uid")
    private String instanceUid;

    /**
     * id
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;

    /**
     * 序列唯一
     */
    @Column(name = "series_uid")
    private String seriesUid;

    /**
     * 影像序号
     */
    @Column(name = "image_number")
    private String imageNumber;

    /**
     * 传输语法识别号
     */
    @Column(name = "transfer_uid")
    private String transferUid;

    /**
     * 影像格式ID
     */
    @Column(name = "class_uid")
    private String classUid;

    /**
     * 影像备份标签
     */
    @Column(name = "backup_label")
    private String backupLabel;

    /**
     * 上传仪器的AE-Title
     */
    @Column(name = "source_ae")
    private String sourceAe;

    /**
     * 影像上传日期
     */
    @Column(name = "rcvd_date")
    private String rcvdDate;

    /**
     * 影像上传时间
     */
    @Column(name = "rcvd_time")
    private String rcvdTime;

    /**
     * 影像大小
     */
    @Column(name = "file_size")
    private Integer fileSize;

    /**
     * 存储协议交易唯一号
     */
    @Column(name = "transaction_uid")
    private String transactionUid;

    /**
     * 是否删除
     */
    @Column(name = "imgae_deleted")
    private Boolean imgaeDeleted;

    /**
     * 是否关键影像
     */
    @Column(name = "key_image")
    private Boolean keyImage;

    /**
     * 影像的水平像素
     */
    @Column(name = "n_rows")
    private Integer nRows;

    /**
     * 影像的垂直像素
     */
    @Column(name = "n_columns")
    private Integer nColumns;

    /**
     * 影像的灰阶
     */
    @Column(name = "bits_allocated")
    private Integer bitsAllocated;

    /**
     * 动态影像的影像格数
     */
    @Column(name = "n_frames")
    private Integer nFrames;

    /**
     * 影像呈现状态卷标
     */
    @Column(name = "pr_label")
    private String prLabel;

    /**
     * 影像呈现状态说明
     */
    @Column(name = "pr_desc")
    private String prDesc;

    /**
     * 影像呈现状态制作时间或影像生成的时间
     */
    @Column(name = "pr_time")
    private String prTime;

    /**
     * 影像呈现状态制作日期或影像生成的日期
     */
    @Column(name = "pr_date")
    private String prDate;

    /**
     * 影像呈现状态制作人
     */
    @Column(name = "pr_creator")
    private String prCreator;

    /**
     * 更新者
     */
    @Column(name = "UPD_USER")
    private String updUser;

    /**
     * 更新时间
     */
    @Column(name = "UPD_TIME")
    private Date updTime;

    /**
     * 创建者
     */
    @Column(name = "CRT_USER")
    private String crtUser;

    /**
     * 创建时间
     */
    @Column(name = "CRT_TIME")
    private Date crtTime;

    /**
     * 影像存储路径
     */
    @Column(name = "image_file")
    private String imageFile;

}