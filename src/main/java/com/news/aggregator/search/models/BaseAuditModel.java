package com.news.aggregator.search.models;

import lombok.*;
import lombok.experimental.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;
import java.util.Date;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseAuditModel {
    private Date createdAt;
    private Date updatedAt;
}