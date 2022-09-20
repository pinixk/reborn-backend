package com.reborn.reborn.controller;

import com.reborn.reborn.dto.WorkoutListDto;
<<<<<<< HEAD
import com.reborn.reborn.dto.WorkoutPageDto;
=======
import com.reborn.reborn.dto.WorkoutSliceDto;
>>>>>>> 113f9386f9f64211c2345b78b02f41d117e6e735
import com.reborn.reborn.dto.WorkoutResponseDto;
import com.reborn.reborn.dto.WorkoutRequestDto;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.repository.custom.WorkoutSearchCondition;
import com.reborn.reborn.security.LoginMember;
import com.reborn.reborn.service.WorkoutImageService;
import com.reborn.reborn.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/workout")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;
    private final WorkoutImageService workoutImageService;

    @GetMapping
<<<<<<< HEAD
    public ResponseEntity<WorkoutPageDto> getWorkoutList(@ModelAttribute WorkoutSearchCondition cond) {
        List<WorkoutListDto> responseDto = workoutService.pagingWorkout(cond);
        return ResponseEntity.ok().body(new WorkoutPageDto(responseDto));
    }

    @PostMapping
    public ResponseEntity<Long> createWorkout(@RequestBody WorkoutRequestDto workoutRequestDto){
        Long saveWorkoutId = workoutService.create(workoutRequestDto);
=======
    public ResponseEntity<WorkoutSliceDto> getWorkoutList(@ModelAttribute WorkoutSearchCondition cond) {
        List<WorkoutListDto> responseDto = workoutService.pagingWorkout(cond);
        return ResponseEntity.ok().body(new WorkoutSliceDto(responseDto));
    }

    @PostMapping
    public ResponseEntity<Long> createWorkout(@LoginMember Member member, @RequestBody WorkoutRequestDto workoutRequestDto){
        Long saveWorkoutId = workoutService.create(member, workoutRequestDto);
>>>>>>> 113f9386f9f64211c2345b78b02f41d117e6e735
        Workout workout = workoutService.findWorkoutById(saveWorkoutId);
        workoutRequestDto.getFiles().forEach(dto -> workoutImageService.create(dto,workout));
        log.info("save Workout");
        return ResponseEntity.created(URI.create("/api/v1/workout"+saveWorkoutId)).body(saveWorkoutId);
    }

    @GetMapping("/{workoutId}")
<<<<<<< HEAD
    public ResponseEntity<WorkoutResponseDto> getWorkout(@PathVariable Long workoutId){
=======
    public ResponseEntity<WorkoutResponseDto> getWorkoutDetail(@PathVariable Long workoutId){
>>>>>>> 113f9386f9f64211c2345b78b02f41d117e6e735
        WorkoutResponseDto dto =  workoutService.getWorkoutDto(workoutId);
        log.info("get myWorkout");
        return ResponseEntity.ok().body(dto);
    }


}
