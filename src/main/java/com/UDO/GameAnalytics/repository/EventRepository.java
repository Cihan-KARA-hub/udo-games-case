package com.UDO.GameAnalytics.repository;

import com.UDO.GameAnalytics.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {

    Page<Event> findByGameId(Long revenuesId, Pageable pageable);
    List<Event> findByGameId(Long revenuesId);
}
