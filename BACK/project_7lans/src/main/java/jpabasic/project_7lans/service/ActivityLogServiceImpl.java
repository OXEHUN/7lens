package jpabasic.project_7lans.service;

import jpabasic.project_7lans.dto.activityLog.ActivityLogRequestDto;
import jpabasic.project_7lans.dto.activityLog.ActivityLogResponseDto;

import jpabasic.project_7lans.repository.ActivityLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ActivityLogServiceImpl implements ActivityLogService {

    private final ActivityLogRepository activityLogRepository;

    // ==================================================================================================
    // 봉사자

    // 봉사자 활동 일지 조회 리스트
    // Req: Relation id, 날짜 정보(년, 월, 일)
    // Res: activityLog id, 날짜 정보(년, 월, 일), 활동 일지 승인 여부
    @Override
    public List<ActivityLogResponseDto.detailListByVolunteer> detailListByVolunteer(ActivityLogRequestDto.detailListByVolunteer listDto){
        return null;
    }

    // 봉사자 활동 일지 상세 조회
    // Req: Relation id, activityLog id
    // Res: activityLog id, 활동 일지 날짜(년, 월, 일), 활동 시간, 활동 기관, 봉사자 명, 활동 내용, 작성 완료 여부, 승인 여부
    @Override
    public ActivityLogResponseDto.detailByVolunteer detailByVolunteer(ActivityLogRequestDto.detailByVolunteer detailDto){
        return null;
    }

    // ==================================================================================================
    // 관리자

    // 관리자 활동 일지 리스트 조회(승인 안된)
    // Req: Center Id
    // Res: activityLog id, 제목, 봉사자 명, 아동 명, 날짜(년, 월, 일)
    @Override
    public List<ActivityLogResponseDto.listDisapprovedByManager> listDisapprovedByManager (ActivityLogRequestDto.listDisapprovedByManager disApprovedDto){
        return null;
    }

    // 관리자 활동 일지 리스트 조회(승인된)
    // Req: Center Id
    // Res: activityLog id, 제목, 봉사자 명, 아동 명, 날짜(년, 월, 일)
    @Override
    public List<ActivityLogResponseDto.listApprovedByManager> listApprovedByManager (ActivityLogRequestDto.listApprovedByManager approveDto){
        return null;
    }

    // 관리자 활동 일지 상세 조회
    // Req: Relation Id, activityLog Id
    // Res: activityLog id, 활동 일지 날짜(년, 월, 일), 활동 시간, 활동 기관, 봉사자 명, 활동 내용, 작성 완료 여부, 승인 여부
    @Override
    public List<ActivityLogResponseDto.detailByManager> detailByManager (ActivityLogRequestDto.detailByManager detailDto){
        return null;
    }

}
