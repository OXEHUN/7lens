package jpabasic.project_7lans.controller;

import jakarta.validation.Valid;
import jpabasic.project_7lans.dto.child.ChildResponseDto;
import jpabasic.project_7lans.dto.meetingSchedule.MeetingScheduleRequestDto;
import jpabasic.project_7lans.dto.meetingSchedule.MeetingScheduleResponseDto;
import jpabasic.project_7lans.entity.MeetingSchedule;
import jpabasic.project_7lans.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/meetingSchedue")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    //해당 달 미팅 조회하기
    @GetMapping("/{relationId}/{month}")
    public ResponseEntity<?> monthList(@PathVariable("relationId") Long relationId, @PathVariable("month") int month){
        try{
            List<MeetingScheduleResponseDto.monthList> meetingSchedule = meetingService.findMeetingsByRelation(relationId, month);

            return new ResponseEntity<List<MeetingScheduleResponseDto.monthList>>(meetingSchedule, HttpStatus.OK);

        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //해당 날짜 사진들 보기
    @GetMapping("/image/{meetingId}")
    public ResponseEntity<?> imageList(@PathVariable("meetingId") Long meetingId){
        try{
            List<MeetingScheduleResponseDto.imgList> imgList = meetingService.getImgList(meetingId);

            return new ResponseEntity<List<MeetingScheduleResponseDto.imgList>>(imgList, HttpStatus.OK);

        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //미팅 추가 하기
    @GetMapping("/create/{relationId}")
    public ResponseEntity create(@PathVariable("relationId") Long relationId){
        try{
            meetingService.create(relationId);

            return new ResponseEntity(HttpStatus.OK);

        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //썸네일 수정하기
    @GetMapping("/changethumbnail/{imgId}")
    public ResponseEntity changeThumbnail(@PathVariable("imgId") Long imgId){
        try{
            meetingService.changeThumbnail(imgId);

            return new ResponseEntity(HttpStatus.OK);

        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //사진 추가하기
    @PostMapping("/image/saveImg")
    public ResponseEntity<Long> saveImg(@RequestBody @Valid MeetingScheduleRequestDto.saveImg img){
        try{
            return new ResponseEntity<>(meetingService.saveImg(img), HttpStatus.OK);

        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
