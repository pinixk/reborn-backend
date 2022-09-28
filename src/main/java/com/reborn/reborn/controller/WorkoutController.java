package com.reborn.reborn.controller;

import com.reborn.reborn.dto.*;
import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.repository.custom.WorkoutSearchCondition;
import com.reborn.reborn.security.LoginMember;
import com.reborn.reborn.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/v1/workout")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @GetMapping
    public ResponseEntity<WorkoutSliceDto> getWorkoutList(@ModelAttribute WorkoutSearchCondition cond) {
        return ResponseEntity.ok().body(workoutService.getPagingWorkout(cond));
    }

    @PostMapping
    public ResponseEntity<Long> createWorkout(@LoginMember Long memberId, @RequestBody WorkoutRequestDto dto) {
        Workout workout = workoutService.create(memberId, dto);
        log.info("save Workout");
        return ResponseEntity.created(URI.create("/api/v1/workout/" + workout.getId())).body(workout.getId());
    }

    @GetMapping("/{workoutId}")
    public ResponseEntity<WorkoutResponseDto> getWorkoutDetail(@LoginMember Long memberId, @PathVariable Long workoutId) {

        WorkoutResponseDto dto = workoutService.getWorkoutDetailDto(memberId, workoutId);
        log.info("memberId={}", memberId);
        log.info("get myWorkout");
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{workoutId}")
    public ResponseEntity<Void> deleteWorkout(@LoginMember Long memberId, @PathVariable Long workoutId) {
        workoutService.deleteWorkout(memberId, workoutId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{workoutId}")
    public ResponseEntity<Void> editWorkout(@LoginMember Long memberId, @PathVariable Long workoutId, @RequestBody WorkoutEditForm form) {
        workoutService.updateWorkout(memberId, workoutId, form);
        return ResponseEntity.noContent().build();
    }
}
