package pro.qyoga.tests.cases.app.publc

import io.kotest.assertions.withClue
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Then
import org.hamcrest.Matchers.matchesRegex
import org.jsoup.Jsoup
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import pro.qyoga.tests.assertions.shouldHaveElement
import pro.qyoga.tests.clients.PublicClient
import pro.qyoga.tests.fixture.object_mothers.therapists.THE_THERAPIST_LOGIN
import pro.qyoga.tests.fixture.object_mothers.therapists.THE_THERAPIST_PASSWORD
import pro.qyoga.tests.infra.web.QYogaAppIntegrationBaseTest
import pro.qyoga.tests.pages.publc.LoginPage


class LoginPageTest : QYogaAppIntegrationBaseTest() {

    @Test
    fun `After login with valid credentials user should be redirected to index page`() {
        // Given

        // When
        val response = PublicClient.authApi.loginForVerification(THE_THERAPIST_LOGIN, THE_THERAPIST_PASSWORD)

        response.Then {
            statusCode(HttpStatus.FOUND.value())
            header("location", matchesRegex(".*:\\d{4,5}/therapist"))
        }
    }

    @Test
    fun `After login with invalid credentials user should be redirected to login page with error message`() {
        // Given

        // When
        val response = PublicClient.authApi.loginForVerification(THE_THERAPIST_LOGIN, "invalid login")

        val page = response.Then {
            statusCode(HttpStatus.OK.value())
        } Extract {
            Jsoup.parse(body().asString())
        }

        withClue("Cannot find error message by ${LoginPage.LoginForm.invalidUserName}") {
            page shouldHaveElement LoginPage.LoginForm.invalidUserName
        }
    }

    @Test
    fun `After login with valid credentials of disabled account, user should be redirected to login page with error message`() {
        // Given
        backgrounds.users.disable(THE_THERAPIST_LOGIN)

        // When
        val response = PublicClient.authApi.loginForVerification(THE_THERAPIST_LOGIN, THE_THERAPIST_PASSWORD)

        val page = response.Then {
            statusCode(HttpStatus.OK.value())
        } Extract {
            Jsoup.parse(body().asString())
        }

        page shouldHaveElement LoginPage.LoginForm.invalidUserName
    }

}