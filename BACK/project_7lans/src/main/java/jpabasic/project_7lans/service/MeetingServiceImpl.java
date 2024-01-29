package jpabasic.project_7lans.service;

import jpabasic.project_7lans.dto.meetingSchedule.MeetingScheduleResponseDto;
import jpabasic.project_7lans.entity.Relation;
import jpabasic.project_7lans.entity.MeetingSchedule;
import jpabasic.project_7lans.entity.ScheduleType;
import jpabasic.project_7lans.repository.MeetingScheduleRepository;
import jpabasic.project_7lans.repository.RelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService{

    private final MeetingScheduleRepository meetingRepository;
    private final RelationRepository relationRepository;

    //미팅 생성
    @Transactional
    public void createMeeting(Relation relation, LocalDateTime startTime, LocalDateTime endTime){
        MeetingSchedule newMeeting = MeetingSchedule.create(relation, startTime, endTime);
        meetingRepository.save(newMeeting);
    }

    //예정 미팅 수정
/*    @Transactional
    public void updateMeeting(MeetingSchedule updateMeeting, ChildVolunteerRelation relation, LocalDateTime startTime, LocalDateTime endTime){

    }*/

    //해당 관계의 미팅 조회
    public List<MeetingScheduleResponseDto.monthList> findMeetingsByRelation(Long relationId, int month){
        //관계 찾기
        Relation relation = relationRepository.findById(relationId)
                .orElseThrow(()-> new IllegalArgumentException("[MeetingServiceImpl.findMeetingsByRelation] 해당 Id와 일치하는 relation이 존재하지 않습니다."));

        //관계된 모든 일정 받기 -> 월을 먼저 걸러서 받는 방법?
        List<MeetingSchedule> totalMeeting = relation.getMeetingScheduleList();

        //response
        List<MeetingScheduleResponseDto.monthList> monthMeeting = new ArrayList<>();

        for(MeetingSchedule meeting : totalMeeting){
            if(meeting.getScheduledStartTime().getMonthValue() == month){
                monthMeeting.add(MeetingScheduleResponseDto.monthList.builder()
                        .id(meeting.getId())
                        .thumbnailImgPath(meeting.getThumbnailImgPath())
                        .meetingUrl(meeting.getMeetingUrl())
                        .status(meeting.getStatus())
                        .day(meeting.getScheduledStartTime().getDayOfMonth())
                        .build());
            }
        }
        return monthMeeting;
    }

    //미팅 상태 확인(예정)
    public boolean isScheduled(MeetingSchedule meetingSchedule){
        return meetingSchedule.getStatus().equals(ScheduleType.SCHEDULED);
    }
    //미팅 상태 확인(열림)
    public boolean isOpened(MeetingSchedule meetingSchedule){
        return meetingSchedule.getStatus().equals(ScheduleType.OPENED);
    }
    //미팅 상태 확인(종료됨)
    public boolean isClosed(MeetingSchedule meetingSchedule){
        return meetingSchedule.getStatus().equals(ScheduleType.CLOSED);
    }

    //썸네일 수정
    public void changeThumnail(MeetingSchedule meetingSchedule, String thumnail){
        meetingSchedule.changeThumnail(thumnail);
    }


}
