package com.juggle.juggle.framework.data.entity.general;

import com.juggle.juggle.framework.data.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class LongEntity extends BaseEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID",updatable=false)
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int compareTo(BaseEntity o) {
		LongEntity other = (LongEntity)o;
		return this.getId().compareTo(other.getId());
	}
}
