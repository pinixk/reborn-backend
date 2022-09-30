package com.reborn.reborn.myworkout.presentation.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
public class MyWorkoutDto {

    private Long myWorkoutId;
    private Long workoutId;

    private String workoutName;

    private String uploadFileName;

    @QueryProjection
    public MyWorkoutDto(Long myWorkoutId, String workoutName, String uploadFileName) {
        this.myWorkoutId = myWorkoutId;
        this.workoutName = workoutName;
        this.uploadFileName = uploadFileName;
    }

    @QueryProjection
    public MyWorkoutDto(Long myWorkoutId, Long workoutId, String workoutName, String uploadFileName) {
        this.myWorkoutId = myWorkoutId;
        this.workoutId = workoutId;
        this.workoutName = workoutName;
        this.uploadFileName = uploadFileName;
    }

    @Getter
    public static class MyProgramList {

        private List<MyWorkoutDto> list;

        public MyProgramList(List<MyWorkoutDto> list) {
            this.list = list;
        }
    }

}
