package ru.otus.otuskotlin.markeplace.springapp

import com.ninjasquad.springmockk.MockkBean
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import ru.otus.otuskotlin.marketplace.backend.repo.sql.RepoAdSQL

@SpringBootTest
class ApplicationTests {
    @MockkBean
    private lateinit var repo: RepoAdSQL

    @Test
    fun contextLoads() {
    }
}
