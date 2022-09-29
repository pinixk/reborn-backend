package com.reborn.reborn.exception.workout;

import com.reborn.reborn.exception.CustomException;
import org.springframework.http.HttpStatus;

public class WorkoutNotFoundException extends CustomException {
    public WorkoutNotFoundException( String message) {
        super(HttpStatus.NOT_FOUND, "운동이 존재하지 않습니다 : " + message);
    }
}