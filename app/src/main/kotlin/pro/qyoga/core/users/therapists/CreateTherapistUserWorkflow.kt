package pro.qyoga.core.users.therapists

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import pro.qyoga.core.users.auth.UsersFactory
import pro.qyoga.core.users.auth.UsersRepo
import pro.qyoga.core.users.auth.model.Role


@Component
class CreateTherapistUserWorkflow(
    private val usersRepo: UsersRepo,
    private val therapistsRepo: TherapistsRepo,
    private val usersFactory: UsersFactory
) : (RegisterTherapistRequest, CharSequence) -> Therapist {

    private val log = LoggerFactory.getLogger(javaClass)

    @Transactional
    override fun invoke(
        registerTherapistRequest: RegisterTherapistRequest,
        password: CharSequence
    ): Therapist {
        log.info("Creating new therapist user for {}", registerTherapistRequest.email)

        var user = usersFactory.createUser(registerTherapistRequest.email, password, setOf(Role.ROLE_THERAPIST))
        user = usersRepo.save(user)

        var therapist = Therapist(registerTherapistRequest.firstName, registerTherapistRequest.lastName, user.id)
        therapist = therapistsRepo.save(therapist)

        log.info("Therapist user created, id = {}", user.id)

        return therapist
    }

}
