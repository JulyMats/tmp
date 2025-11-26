package com.app.umkaSchool.config;

import com.app.umkaSchool.service.HomeworkAssignmentService;
import com.app.umkaSchool.service.ProgressSnapshotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private final ProgressSnapshotService progressSnapshotService;
    private final HomeworkAssignmentService homeworkAssignmentService;

    public ScheduledTasks(ProgressSnapshotService progressSnapshotService,
                         HomeworkAssignmentService homeworkAssignmentService) {
        this.progressSnapshotService = progressSnapshotService;
        this.homeworkAssignmentService = homeworkAssignmentService;
    }


    @Scheduled(cron = "0 0 1 * * ?")
    public void cleanupYesterdaySnapshots() {
        logger.info("Starting scheduled cleanup of yesterday's duplicate snapshots");
        try {
            progressSnapshotService.cleanupYesterdaySnapshots();
            logger.info("Cleanup of yesterday's snapshots completed successfully");
        } catch (Exception e) {
            logger.error("Error during scheduled snapshot cleanup: {}", e.getMessage(), e);
        }
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void updateOverdueAssignments() {
        logger.info("Starting scheduled update of overdue homework assignments");
        try {
            homeworkAssignmentService.updateOverdueAssignments();
            logger.info("Update of overdue assignments completed successfully");
        } catch (Exception e) {
            logger.error("Error during scheduled overdue assignments update: {}", e.getMessage(), e);
        }
    }
}

