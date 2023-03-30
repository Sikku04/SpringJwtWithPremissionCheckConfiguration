package com.mixi.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_id")
	private Long id;

	private String roleName;

	@ManyToMany
	@JoinTable(name = "role_responsibility", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "resp_id"))
	private Set<CMSResponsibility> responsibility = new HashSet<>();

	private String createdAt;
	private String updatedAt;

	private boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<CMSResponsibility> getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(Set<CMSResponsibility> responsibility) {
		this.responsibility = responsibility;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + ", responsibility=" + responsibility + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", active=" + active + ", getId()=" + getId()
				+ ", getRoleName()=" + getRoleName() + ", getCreatedAt()=" + getCreatedAt() + ", getUpdatedAt()="
				+ getUpdatedAt() + ", isActive()=" + isActive() + ", getResponsibility()=" + getResponsibility()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

}
