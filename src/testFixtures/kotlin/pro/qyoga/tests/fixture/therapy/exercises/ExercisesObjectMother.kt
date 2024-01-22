package pro.qyoga.tests.fixture.therapy.exercises

import org.springframework.data.jdbc.core.mapping.AggregateReference
import org.springframework.web.multipart.MultipartFile
import pro.qyoga.app.therapist.therapy.exercises.toStepIdx
import pro.qyoga.core.therapy.exercises.dtos.CreateExerciseRequest
import pro.qyoga.core.therapy.exercises.dtos.ExerciseSearchDto
import pro.qyoga.core.therapy.exercises.dtos.ExerciseSummaryDto
import pro.qyoga.core.therapy.exercises.model.ExerciseStep
import pro.qyoga.core.therapy.exercises.model.ExerciseType
import pro.qyoga.tests.fixture.data.randomCyrillicWord
import pro.qyoga.tests.fixture.data.randomListIndexed
import pro.qyoga.tests.fixture.data.randomMinutesDuration
import pro.qyoga.tests.fixture.data.randomSentence
import java.time.Duration
import kotlin.random.Random


object ExercisesObjectMother {

    fun createExerciseRequest(
        title: String = randomCyrillicWord(),
        description: String = randomSentence(),
        duration: Duration = randomExerciseDuration(),
        exerciseType: ExerciseType = randomExerciseTypeId(),
        steps: () -> List<ExerciseStep> = { exerciseSteps(Random.nextInt(1, 5)) }
    ) = CreateExerciseRequest(
        exerciseSummary(title, description, duration, exerciseType),
        steps()
    )


    fun exerciseSteps(description: String = randomSentence()) =
        ExerciseStep(description, AggregateReference.to(0L))

    fun exerciseSearchDto(title: String) = ExerciseSearchDto(title = title)

    fun createExerciseRequests(count: Int): List<CreateExerciseRequest> =
        (1..count).map { createExerciseRequest() }

    fun exerciseSteps(count: Int) = randomListIndexed(min = count, max = count) { exerciseSteps() }

    fun exerciseImages(vararg images: Pair<Int, MultipartFile>): Map<String, MultipartFile> =
        images.associate { it.first.toStepIdx() to it.second }

    fun exerciseSummary(
        title: String = randomCyrillicWord(),
        description: String = randomSentence(),
        duration: Duration = randomExerciseDuration(),
        exerciseType: ExerciseType = randomExerciseTypeId(),
    ) =
        ExerciseSummaryDto(
            title,
            description,
            duration,
            exerciseType
        )
}

fun randomExerciseDuration(): Duration = randomMinutesDuration(4, 30)

fun randomExerciseTypeId() = ExerciseType.entries.random()
