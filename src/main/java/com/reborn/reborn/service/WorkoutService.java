package com.reborn.reborn.service;

import com.reborn.reborn.dto.*;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.entity.WorkoutCategory;
import com.reborn.reborn.entity.WorkoutImage;
import com.reborn.reborn.repository.MemberRepository;
import com.reborn.reborn.repository.WorkoutImageRepository;
import com.reborn.reborn.repository.WorkoutRepository;
import com.reborn.reborn.repository.custom.WorkoutSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;


@Service
@Transactional
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final MemberRepository memberRepository;
    private final WorkoutImageRepository workoutImageRepository;


    public Workout create(Long memberId, WorkoutRequestDto dto) {
        //TODO Exception
        Member member = memberRepository.findById(memberId).orElseThrow();
        Workout workout = Workout.builder()
                .workoutName(dto.getWorkoutName())
                .content(dto.getContent())
                .member(member)
                .workoutCategory(WorkoutCategory.valueOf(dto.getWorkoutCategory()))
                .build();

        Workout saveWorkout = workoutRepository.save(workout);
        List<FileDto> files = dto.getFiles();
        if (files.size() > 0) {
            files.forEach(file -> createImage(file, workout));
        }
        return saveWorkout;
    }

    @Transactional(readOnly = true)
    public Workout findWorkoutById(Long workoutId) {
        return workoutRepository.findById(workoutId).orElseThrow(() -> new NoSuchElementException("찾으시는 운동이 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<WorkoutListDto> pagingWorkoutWithSearchCondition(WorkoutSearchCondition cond) {
        return workoutRepository.pagingWorkWithSearchCondition(cond);
    }


    @Transactional
    public void deleteWorkout(Long authorId, Long workoutId) {
        //TODO Exception
        Workout workout = workoutRepository.findById(workoutId).orElseThrow();
        validIsAuthor(authorId, workout);
        workoutRepository.delete(workout);
    }

    public Workout updateWorkout(Long authorId, Long workoutId, WorkoutEditForm form) {
        //TODO EXCEPTION
        Workout workout = workoutRepository.findById(workoutId).orElseThrow();
        validIsAuthor(authorId, workout);
        workout.modifyWorkout(form.getWorkoutName(), form.getContent());

        deleteAndUpdateImage(form.getFiles(), workout);
        return workout;
    }


    public Long createImage(FileDto fileDto, Workout workout) {
        WorkoutImage workoutImage = new WorkoutImage(fileDto.getOriginFileName(), fileDto.getUploadFileName());
        workoutImage.uploadToWorkout(workout);
        workoutImageRepository.save(workoutImage);
        return workoutImage.getId();
    }


    public void deleteAndUpdateImage(List<FileDto> files, Workout workout) {
        if (files.size() == 0) {
            return;
        }
        workoutImageRepository.deleteAllByWorkoutId(workout.getId());
        files.forEach(file -> createImage(file, workout));
    }

    @Transactional(readOnly = true)
    public WorkoutResponseDto getWorkoutDetailDto(Long memberId, Long workoutId) {
        //TODO Exception
        Workout workout = workoutRepository.findByIdWithImagesAndMember(workoutId).orElseThrow();
        WorkoutResponseDto dto = WorkoutResponseDto.of(workout);
        dto.isAuthor(memberId);
        return dto;

    }

    private void validIsAuthor(Long authorId, Workout workout) {
        if (workout.getMember().getId() != authorId) {
            throw new RuntimeException("권한이 없음");
        }
    }

}
