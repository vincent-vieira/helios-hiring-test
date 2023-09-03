package io.vieira.fizzbuzz.observability

import com.ninjasquad.springmockk.MockkBean
import io.vieira.fizzbuzz.observability.FizzBuzzGenerationCounter
import io.vieira.fizzbuzz.observability.FizzBuzzStatisticsController
import io.vieira.fizzbuzz.observability.MostRequestedFizzBuzzGeneration
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(controllers = [FizzBuzzStatisticsController::class])
class FizzBuzzStatisticsControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var fizzBuzzGenerationCounter: FizzBuzzGenerationCounter

    @Test
    fun shouldExposeStatisticsAboutMostRequestedGeneration() {
        every { fizzBuzzGenerationCounter.findMostRequested() } returns MostRequestedFizzBuzzGeneration(
                limit = 150, replacements = mapOf(3 to "fizz", 5 to "buzz"), hits = 3
        )

        mockMvc
                .get("/fizz-buzz/statistics/most-requested") {
                    accept = MediaType.APPLICATION_JSON
                }
                .andExpect {
                    status { isOk() }
                    content { contentTypeCompatibleWith(MediaType.APPLICATION_JSON) }
                    content { json("""{"int1": 3,"int2": 5,"limit": 150,"str1": "fizz","str2": "buzz","count": 3}""", true) }
                }

        verify {
            fizzBuzzGenerationCounter.findMostRequested()
        }
        confirmVerified(fizzBuzzGenerationCounter)
    }

    @Test
    fun shouldReturnNotFoundWhenNoGenerationHasBeenRequested() {
        every { fizzBuzzGenerationCounter.findMostRequested() } returns null

        mockMvc
                .get("/fizz-buzz/statistics/most-requested") {
                    accept = MediaType.APPLICATION_JSON
                }
                .andExpect {
                    status { isNotFound() }
                }

        verify {
            fizzBuzzGenerationCounter.findMostRequested()
        }
        confirmVerified(fizzBuzzGenerationCounter)
    }
}