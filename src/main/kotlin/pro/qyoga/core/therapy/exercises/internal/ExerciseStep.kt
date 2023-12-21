package pro.qyoga.core.therapy.exercises.internal

import org.springframework.data.relational.core.mapping.Table


@Table("exercise_steps")
data class ExerciseStep(
    val description: String,
    val imageId: Long?
)