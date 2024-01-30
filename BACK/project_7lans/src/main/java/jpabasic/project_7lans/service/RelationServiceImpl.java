package jpabasic.project_7lans.service;

import jpabasic.project_7lans.dto.relation.RelationRequestDto;
import jpabasic.project_7lans.dto.relation.RelationResponseDto;
import jpabasic.project_7lans.entity.*;
import jpabasic.project_7lans.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class RelationServiceImpl implements RelationService {

    private final RelationRepository relationRepository;
    private final MemberRepository memberRepository;
    private final ChildCenterRepository childCenterRepository;
    private final VolunteerRepository volunteerRepository;
    private final DinosaurRepository dinosaurRepository;


    @Override
    public RelationResponseDto.info createRelation(RelationRequestDto.create requestDto) {
        log.info("Creating a relation with Child ID: {}, Volunteer ID: {}", requestDto.getChildId(), requestDto.getVolunteerId());

        Child child = (Child) memberRepository
                .findById(requestDto.getChildId())
                .orElseThrow();

        Volunteer volunteer = (Volunteer) memberRepository
                .findById(requestDto.getVolunteerId())
                .orElseThrow();

        ChildCenter childCenter = child.getChildCenter();

        // 랜덤 공룡 꺼내오기
        long count = dinosaurRepository.count();
        int randomIndex = new Random().nextInt((int)count);
        Dinosaur dinosaur = dinosaurRepository.findAll(PageRequest.of(randomIndex, 1)).getContent().get(0);

        Egg egg = Egg.builder()
                .dinosaur(dinosaur)
                .build();

        Relation relation = Relation.builder()
                .child(child)
                .volunteer(volunteer)
                .childCenter(childCenter)
                .egg(egg)
                .build();

        relationRepository.save(relation);
        log.info("Successfully created a relation with Child ID: {}, Volunteer ID: {}", requestDto.getChildId(), requestDto.getVolunteerId());
        return RelationResponseDto.info.toDto(relation);
    }

    @Override
    public void deleteRelation(RelationRequestDto.delete requestDto) {
        Relation relation = relationRepository.findById(requestDto.getRelationId())
                .orElseThrow(() -> new IllegalArgumentException("[RelationServiceImpl.deleteRelation] 해당 Id와 일치하는 relation이 존재하지 않습니다."));
        relationRepository.delete(relation);

        log.info("Relation with ID {} was deleted successfully.", requestDto.getRelationId());
    }

}
