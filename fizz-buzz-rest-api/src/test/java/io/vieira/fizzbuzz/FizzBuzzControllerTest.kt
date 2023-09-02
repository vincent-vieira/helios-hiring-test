package io.vieira.fizzbuzz

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [FizzBuzzController::class])
class FizzBuzzControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockkBean
    private lateinit var fizzBuzzAlgorithm: FizzBuzzAlgorithm

    @Test
    fun shouldGenerateFizzBuzzWithLimitAndDefaultReplacements() {
        val result = listOf("1", "2", "fizz")

        every { fizzBuzzAlgorithm.generate(any(), any()) } returns result

        mockMvc.perform(post("/fizz-buzz")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("""{ "limit":  100 }"""))
                .andExpect {
                    status().isOk
                    content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                    content().json(objectMapper.writeValueAsString(result), true)
                }

        verify {
            fizzBuzzAlgorithm.generate(limit = 100, replacements = mapOf(
                    3 to "fizz", 5 to "buzz"
            ))
        }
        confirmVerified(fizzBuzzAlgorithm)
    }


    @Test
    fun shouldGenerateCustomFizzBuzzWithCustomReplacements() {
        val replacements = mapOf(5 to "toto")
        val request = FizzBuzzGenerationRequest(replacements = replacements)

        val result = listOf("1", "2", "3")

        every { fizzBuzzAlgorithm.generate(any(), any()) } returns result

        mockMvc.perform(post("/fizz-buzz")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect {
                    status().isOk
                    content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                    content().json(objectMapper.writeValueAsString(result), true)
                }

        verify { fizzBuzzAlgorithm.generate(limit = 100, replacements = replacements) }
        confirmVerified(fizzBuzzAlgorithm)
    }

    @Test
    fun shouldGenerateCustomFizzBuzzWithCustomLimits() {
        val request = FizzBuzzGenerationRequest(limit = 5)

        val result = listOf("1", "2", "3")

        every { fizzBuzzAlgorithm.generate(any(), any()) } returns result

        mockMvc.perform(post("/fizz-buzz")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect {
                    status().isOk
                    content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                    content().json(objectMapper.writeValueAsString(result), true)
                }

        verify { fizzBuzzAlgorithm.generate(limit = 5, replacements = mapOf(3 to "fizz", 5 to "buzz")) }
        confirmVerified(fizzBuzzAlgorithm)
    }
}