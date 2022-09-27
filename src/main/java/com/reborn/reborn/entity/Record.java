package com.reborn.reborn.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Record extends BaseTimeEntity{

    @Id
    @Column(name = "record_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "my_workout_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private MyWorkout myWorkout;

    private Integer weight;

}
