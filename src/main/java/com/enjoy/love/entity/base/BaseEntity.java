package com.enjoy.love.entity.base;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.apache.commons.lang.builder.ToStringBuilder;

@MappedSuperclass
public class BaseEntity {
	private Date createDate;
	private Date modifyDate;
	
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	@PrePersist
	public void prePersist() {
		if(this.getCreateDate() == null) {
			this.setCreateDate(this.getCurrentDate());
		}
	}
	
	@PreUpdate
	public void preUpdate() {
		if(this.getCreateDate() == null) {
			this.setCreateDate(this.getCurrentDate());
		}
	}
	
	private Date getCurrentDate() {
    	return new Date();
    }
	
	public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }
}
