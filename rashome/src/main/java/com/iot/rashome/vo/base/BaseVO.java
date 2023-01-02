package com.iot.rashome.vo.base;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@MappedSuperclass
public abstract class BaseVO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    public String createdBy = "SYSTEM";

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    @CreationTimestamp
    public Date creationDate;

    public String updatedBy = "SYSTEM";

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    @UpdateTimestamp
    public Date updatedDate;
}
