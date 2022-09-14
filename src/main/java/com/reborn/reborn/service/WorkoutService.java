package com.reborn.reborn.service;

import com.reborn.reborn.dto.WorkoutResponseDto;
import com.reborn.reborn.dto.WorkoutRequestDto;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.Workout;


public interface WorkoutService {

    Workout create(Member member, WorkoutRequestDto dto);

    WorkoutResponseDto getMyWorkout(Member member, Long id);
}
