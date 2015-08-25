package de.simonscholz.telegrambot.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.simonscholz.telegrambot.persistence.domain.WeatherRequest;

public interface RequestRepository extends JpaRepository<WeatherRequest, Long> {

}
