package de.simonscholz.telegrambot.persistence.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Data;

@Entity
@Data
public class WeatherRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Date createdAt;

	private Date updatedAt;

	private Long userId;

	private String city;

	private String command;

	@PrePersist
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@PreUpdate
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
