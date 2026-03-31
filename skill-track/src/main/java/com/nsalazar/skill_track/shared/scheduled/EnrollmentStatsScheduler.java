package com.nsalazar.skill_track.shared.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduled component that periodically logs platform-level enrollment statistics.
 * Runs once every day at midnight. Can be extended to query repositories,
 * persist metrics, or dispatch digest emails.
 */
@Slf4j
@Component
public class EnrollmentStatsScheduler {

    /**
     * Fires at midnight every day (second minute hour day month weekday).
     * Inject repository ports here to compute real statistics as the platform grows.
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void logDailyEnrollmentStats() {
        log.info("[SCHEDULER] Daily enrollment stats job started.");
        log.info("[SCHEDULER] Daily enrollment stats job completed.");
    }
}
