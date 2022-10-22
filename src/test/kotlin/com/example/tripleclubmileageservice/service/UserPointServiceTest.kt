package com.example.tripleclubmileageservice.service

import com.example.tripleclubmileageservice.common.advice.exception.NotFoundException
import com.example.tripleclubmileageservice.repository.UserPointHistoryRepository
import com.example.tripleclubmileageservice.repository.UserPointRepository
import io.mockk.every
import io.mockk.mockk
import org.amshove.kluent.invoking
import org.amshove.kluent.shouldThrow
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

internal class UserPointServiceTest {
    internal lateinit var userPointRepository: UserPointRepository
    internal lateinit var userPointHistoryRepository: UserPointHistoryRepository
    internal lateinit var userPointServiceImpl: UserPointServiceImpl

    @BeforeEach
    fun setup() {
        userPointRepository = mockk()
        userPointHistoryRepository = mockk()

        userPointServiceImpl = UserPointServiceImpl(userPointRepository, userPointHistoryRepository)
    }

    @Test
    fun `유저가 존재하지 않는다면 NotFoundException 예외 발생`() {
        //given
        every { userPointRepository.findById(any()) } returns Optional.empty()

        //when
        //then
        invoking { userPointServiceImpl.get(UUID.randomUUID()) } shouldThrow NotFoundException::class
    }

}