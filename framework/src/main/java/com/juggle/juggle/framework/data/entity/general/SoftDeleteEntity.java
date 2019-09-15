package com.juggle.juggle.framework.data.entity.general;

import javax.persistence.MappedSuperclass;

import com.juggle.juggle.framework.data.entity.SoftDelete;
import com.juggle.juggle.framework.data.filter.meta.Filtered;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;

//below SQL injection must be declared in real entity class.
@SQLDelete(
		sql = "UPDATE #{entityName} SET deleted = CURRENT_TIMESTAMP WHERE id = ?"
)
@SQLDeleteAll(
		sql = "UPDATE #{entityName} SET deleted = CURRENT_TIMESTAMP WHERE id = ?"
)
@Where(clause = "deleted = 0")
@MappedSuperclass
public class SoftDeleteEntity extends AuditEntity implements SoftDelete {
	@Filtered
	@JsonIgnore
	private long deleted;

	public long getDeleted() {
		return deleted;
	}

	public void setDeleted(long deleted) {
		this.deleted = deleted;
	}
}
