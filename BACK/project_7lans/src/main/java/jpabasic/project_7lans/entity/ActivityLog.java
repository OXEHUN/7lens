package jpabasic.project_7lans.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActivityLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @OneToOne(mappedBy = "activityLog", fetch = FetchType.LAZY)
    private MeetingSchedule meetingSchedule;

    private LocalDateTime realStartTime;

    private LocalDateTime realEndTime;

    private String content = null;

    private Boolean writeStatus = false; // 작성 완료 여부
    private Boolean approveStatus = false; // 승인 완료 여부


    public void changeContent(String newContent){
        this.content = newContent;
    }

    public void changeTime(LocalDateTime realStartTime, LocalDateTime realEndTime){
        this.realStartTime = realStartTime;
        this.realEndTime = realEndTime;
    }

    public void changeMeetingSchedule(MeetingSchedule meetingSchedule){
        this.meetingSchedule = meetingSchedule;
    }

    // 작성 완료 여부 관련
    public void writeDone(){ this.writeStatus = true; }

    public void writeNotDone(){ this.writeStatus = false; }

    // 승인 완료 여부 관련
    public void approve(){
        this.approveStatus = true;
    }

    public void disApprove(){
        this.approveStatus = false;
    }

    @Builder
    public static ActivityLog createActivityLog(
            LocalDateTime startTime, LocalDateTime endTime
    ){
        return ActivityLog.builder()
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}
