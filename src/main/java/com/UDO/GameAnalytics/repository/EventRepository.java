package com.UDO.GameAnalytics.repository;

import com.UDO.GameAnalytics.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Long> {
}
