package jpabasic.project_7lans.service;

import jpabasic.project_7lans.dto.meetingSchedule.MeetingScheduleRequestDto;
import jpabasic.project_7lans.dto.meetingSchedule.MeetingScheduleResponseDto;
import jpabasic.project_7lans.entity.MeetingImage;
import jpabasic.project_7lans.entity.Relation;
import jpabasic.project_7lans.entity.MeetingSchedule;
import jpabasic.project_7lans.entity.ScheduleType;
import jpabasic.project_7lans.repository.MeetingImageRepository;
import jpabasic.project_7lans.repository.MeetingScheduleRepository;
import jpabasic.project_7lans.repository.RelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService{

    private final MeetingScheduleRepository meetingRepository;
    private final RelationRepository relationRepository;
    private final MeetingImageRepository meetingImageRepository;

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
                        .meetingId(meeting.getId())
                        .thumbnailImgPath(meeting.getThumbnailImgPath())
                        .meetingUrl(meeting.getMeetingUrl())
                        .status(meeting.getStatus())
                        .day(meeting.getScheduledStartTime().getDayOfMonth())
                        .build());
            }
        }
        return monthMeeting;
    }

    @Override
    public List<MeetingScheduleResponseDto.imgList> getImgList(Long meetingId) {
        MeetingSchedule meetingSchedule = meetingRepository.findById(meetingId)
                .orElseThrow(()-> new IllegalArgumentException("[MeetingServiceImpl.getImgList] 해당 Id와 일치하는 meeting이 존재하지 않습니다."));

        List<MeetingImage> images = meetingSchedule.getMeetingImageList();

        List<MeetingScheduleResponseDto.imgList> responseImg = new ArrayList<>();
        for(MeetingImage img : images){
            responseImg.add(MeetingScheduleResponseDto.imgList.builder()
                    .imgId(img.getId())
                    .imgPath(img.getImgPath())
                    .build());
        }

        return responseImg;
    }

    //미팅 생성
    @Override
    @Transactional
    public void create(Long relationId) {
        //미팅을 만들고 relation에 넣어주기

    }

    //썸네일 수정
    @Override
    public void changeThumbnail(Long imgId) {
        MeetingImage img = meetingImageRepository.findById(imgId)
                .orElseThrow(()-> new IllegalArgumentException("[MeetingServiceImpl.changeThumbnail] 해당 Id와 일치하는 meetingImg가 존재하지 않습니다."));

        MeetingSchedule meetingSchedule = img.getMeetingSchedule();

        meetingSchedule.changeThumnail(img.getImgPath());
    }

    @Override
    @Transactional
    public Long saveImg(MeetingScheduleRequestDto.saveImg img) {
        String uploadPath = "https://i10e103.p.ssafy.io/images/";

        File folder = new File(uploadPath);

        if(!folder.exists()){
            try{
                folder.mkdir();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String curDate = sdf.format(new Date());
        String ftype = img.getFile().getContentType();

        String newFileName = curDate + Long.toString(System.nanoTime());
        if(ftype.equals("image/jpeg") || ftype.equals("image/jpg")){
            newFileName += ".jpg";
        }
        else {
            newFileName += ".png";
        }

        Path copyOfLocation = Paths.get(uploadPath + File.separator + newFileName);

        MeetingSchedule meeting = meetingRepository.findById(img.getMeetingId())
                .orElseThrow(()-> new IllegalArgumentException("[MeetingServiceImpl.saveImg] 해당 Id와 일치하는 meetingId가 존재하지 않습니다."));

        try{
            Files.copy(img.getFile().getInputStream(), copyOfLocation, StandardCopyOption.REPLACE_EXISTING);

            return meetingImageRepository.save(MeetingImage.builder()
                    .meetingSchedule(meeting)
                    .imgPath(copyOfLocation.toString())
                    .serverFileName(newFileName)
                    .originFileName(img.getFile().getOriginalFilename())
                    .contentType(ftype)
                    .fileSize(img.getFile().getSize())
                    .build()).getId();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    @Transactional
    public void choiceImg(List<MeetingScheduleRequestDto.choiceImg> imgs) {
           for(MeetingScheduleRequestDto.choiceImg imgDto : imgs){
                meetingImageRepository.deleteById(imgDto.getImgId());
           }
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


}
