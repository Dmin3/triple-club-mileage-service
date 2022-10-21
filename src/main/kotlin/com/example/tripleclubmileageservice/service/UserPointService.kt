package com.example.tripleclubmileageservice.service

import com.example.tripleclubmileageservice.common.data.Paginated
import com.example.tripleclubmileageservice.common.exception.NotFoundException
import com.example.tripleclubmileageservice.data.UserPointHistoryResponse
import com.example.tripleclubmileageservice.data.UserPointResponse
import com.example.tripleclubmileageservice.repository.UserPointHistoryRepository
import com.example.tripleclubmileageservice.repository.UserPointRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*


interface UserPointService{
    fun get(userId: UUID) : UserPointResponse
    fun getUserByHistories(userId: UUID, pageable: Pageable) : Paginated<UserPointHistoryResponse>
    fun getAllHistories(pageable: Pageable) : Paginated<UserPointHistoryResponse>
}

@Service
class UserPointServiceImpl(
    private val userPointRepository: UserPointRepository,
    private val userPointHistoryRepository: UserPointHistoryRepository
) : UserPointService {
    override fun get(userId: UUID): UserPointResponse {
        val findUserPoint = userPointRepository.findById(userId)
        if (findUserPoint.isEmpty) throw NotFoundException("not found : $userId")

        return findUserPoint.get().result()
    }

    override fun getUserByHistories(userId: UUID, pageable: Pageable): Paginated<UserPointHistoryResponse> {
        val userHistories = userPointHistoryRepository.findAllByUserId(userId, pageable)
        val list = userHistories.map { it.result() }.toList()
        return Paginated(list, userHistories)
    }

    override fun getAllHistories(pageable: Pageable): Paginated<UserPointHistoryResponse> {
        val histories = userPointHistoryRepository.findAll(pageable)
        val list = histories.map { it.result() }.toList()
        return Paginated(list, histories)
    }
}