package com.UDO.GameAnalytics.repository;

import com.UDO.GameAnalytics.dto.event.response.DailyTotalDto;
import com.UDO.GameAnalytics.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {

    Page<Event> findByGameId(Long revenuesId, Pageable pageable);
    List<Event> findByGameId(Long revenuesId);
    @Query("""
        SELECT new com.UDO.GameAnalytics.dto.event.response.DailyTotalDto(
            CAST(e.timestamp AS java.time.LocalDate), SUM(e.amount)
        )
        FROM Event e
        WHERE e.game.id = :gameId
        GROUP BY CAST(e.timestamp AS java.time.LocalDate)
        ORDER BY CAST(e.timestamp AS java.time.LocalDate) ASC
    """)
    Page<DailyTotalDto> findDailyTotalsByGameId(@Param("gameId") Long gameId, Pageable pageable);



}
