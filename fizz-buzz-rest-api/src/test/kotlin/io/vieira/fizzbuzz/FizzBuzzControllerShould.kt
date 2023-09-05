package io.vieira.fizzbuzz

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.*
import io.vieira.fizzbuzz.observability.FizzBuzzGenerationCounter
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@WebMvcTest(controllers = [FizzBuzzController::class])
class FizzBuzzControllerShould {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockkBean
    private lateinit var fizzBuzzAlgorithm: FizzBuzzAlgorithm

    @MockkBean
    private lateinit var fizzBuzzGenerationCounter: FizzBuzzGenerationCounter

    @Test
    fun generateFizzBuzzWithLimitAndDefaultReplacements() {
        val replacements = mapOf(3 to "fizz", 5 to "buzz")

        val result = listOf("1", "2", "fizz")

        every { fizzBuzzAlgorithm.generate(any(), any()) } returns result
        every { fizzBuzzGenerationCounter.registerNew(any(), any()) } just Runs

        mockMvc
                .post("/fizz-buzz") {
                    accept = MediaType.APPLICATION_JSON
                    contentType = MediaType.APPLICATION_JSON
                    content = """{ "limit": 100 }"""
                }
                .andExpect {
                    status { isOk() }
                    content { contentTypeCompatibleWith(MediaType.APPLICATION_JSON) }
                    content { json(objectMapper.writeValueAsString(result), true) }
                }

        verifySequence {
            fizzBuzzAlgorithm.generate(limit = 100, replacements = replacements)
            fizzBuzzGenerationCounter.registerNew(limit = 100, replacements = replacements)
        }
        confirmVerified(fizzBuzzAlgorithm, fizzBuzzGenerationCounter)
    }


    @Test
    fun generateCustomFizzBuzzWithCustomReplacements() {
        val replacements = mapOf(3 to "titi", 5 to "toto")
        val request = FizzBuzzGenerationRequest(int1 = 3, int2 = 5, str1 = "titi", str2 = "toto")

        val result = listOf("1", "2", "3")

        every { fizzBuzzAlgorithm.generate(any(), any()) } returns result
        every { fizzBuzzGenerationCounter.registerNew(any(), any()) } just Runs

        mockMvc
                .post("/fizz-buzz") {
                    accept = MediaType.APPLICATION_JSON
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(request)
                }
                .andExpect {
                    status { isOk() }
                    content { contentTypeCompatibleWith(MediaType.APPLICATION_JSON) }
                    content { json(objectMapper.writeValueAsString(result), true) }
                }

        verifySequence {
            fizzBuzzAlgorithm.generate(limit = 100, replacements = replacements)
            fizzBuzzGenerationCounter.registerNew(limit = 100, replacements = replacements)
        }
        confirmVerified(fizzBuzzAlgorithm, fizzBuzzGenerationCounter)
    }

    @Test
    fun generateCustomFizzBuzzWithCustomLimits() {
        val replacements = mapOf(3 to "titi", 5 to "toto")
        val request = FizzBuzzGenerationRequest(int1 = 3, int2 = 5, str1 = "titi", str2 = "toto", limit = 5)

        val result = listOf("1", "2", "3")

        every { fizzBuzzAlgorithm.generate(any(), any()) } returns result
        every { fizzBuzzGenerationCounter.registerNew(any(), any()) } just Runs

        mockMvc
                .post("/fizz-buzz") {
                    accept = MediaType.APPLICATION_JSON
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(request)
                }
                .andExpect {
                    status { isOk() }
                    content { contentTypeCompatibleWith(MediaType.APPLICATION_JSON) }
                    content { json(objectMapper.writeValueAsString(result), true) }
                }

        verifySequence {
            fizzBuzzAlgorithm.generate(limit = 5, replacements = replacements)
            fizzBuzzGenerationCounter.registerNew(limit = 5, replacements = replacements)
        }
        confirmVerified(fizzBuzzAlgorithm, fizzBuzzGenerationCounter)
    }
}