package com.example.tripleclubmileageservice.controller

import com.example.tripleclubmileageservice.domain.UserPointType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.util.*

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@ExtendWith(value = [RestDocumentationExtension::class, SpringExtension::class])
internal class UserPointApiControllerTest {

    val userPointTypes = UserPointType.values().joinToString(", "){"${it.name} : ${it.typeName}"}

    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp(
        webApplicationContext: WebApplicationContext,
        restDocumentation: RestDocumentationContextProvider
    ) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply<DefaultMockMvcBuilder>(
                documentationConfiguration(restDocumentation).uris()
                    .withScheme("http")
                    .withHost("localhost")
                    .withPort(8080)
            )
            .build()
    }

    @Test
    fun `유저 포인트 조회`() {
        val userId = UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f745")

        this.mockMvc.perform(
            get("/points/{userId}", userId)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andDo(
                document(
                    "get-user-points",
                    preprocessResponse(prettyPrint()),
                    pathParameters(
                        parameterWithName("userId").description("유저 ID"),
                    ),
                    responseFields(
                        fieldWithPath("userId").description("유저 아이디"),
                        fieldWithPath("nowPoint").description("현재 포인트"),
                        fieldWithPath("usePoint").description("사용 포인트"),
                        fieldWithPath("accumulativePoint").description("누적 포인트"),
                    )
                )
            )
    }

    @Test
    fun `유저 포인트 이력 조회`() {
        val userId = UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f745")

        this.mockMvc.perform(
            get("/points/histories/{userId}", userId)
                .param("page", "0")
                .param("size", "2")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andDo(
                document(
                    "get-user-histories-points",
                    preprocessResponse(prettyPrint()),
                    requestParameters(
                        parameterWithName("page").description("페이지 번호"),
                        parameterWithName("size").description("페이지 사이즈"),
                    ),
                    pathParameters(
                        parameterWithName("userId").description("유저 ID"),
                    ),
                    responseFields(
                        fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수"),
                        fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("총 매장 수"),
                        fieldWithPath("number").type(JsonFieldType.NUMBER).description("현재 페이지"),
                        fieldWithPath("size").type(JsonFieldType.NUMBER).description("페이지 당 매장 수"),
                        fieldWithPath("data[].id").description("아이디(PK)"),
                        fieldWithPath("data[].userId").description("유저 아이디"),
                        fieldWithPath("data[].reviewId").description("리뷰 아이디"),
                        fieldWithPath("data[].userPointType").description("이벤트 타입(${userPointTypes})"),
                        fieldWithPath("data[].changePoint").description("포인트"),
                        fieldWithPath("data[].createdAt").description("생성시간"),
                    )
                )
            )
    }

    @Test
    fun `유저 포인트 전체 이력 조회`() {
        this.mockMvc.perform(
            get("/points/histories")
                .param("page", "0")
                .param("size", "2")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andDo(
                document(
                    "get-all-histories-points",
                    preprocessResponse(prettyPrint()),
                    requestParameters(
                        parameterWithName("page").description("페이지 번호"),
                        parameterWithName("size").description("페이지 사이즈"),
                    ),
                    responseFields(
                        fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수"),
                        fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("총 매장 수"),
                        fieldWithPath("number").type(JsonFieldType.NUMBER).description("현재 페이지"),
                        fieldWithPath("size").type(JsonFieldType.NUMBER).description("페이지 당 매장 수"),
                        fieldWithPath("data[].id").description("아이디(PK)"),
                        fieldWithPath("data[].userId").description("유저 아이디"),
                        fieldWithPath("data[].reviewId").description("리뷰 아이디"),
                        fieldWithPath("data[].userPointType").description("이벤트 타입(${userPointTypes})"),
                        fieldWithPath("data[].changePoint").description("포인트"),
                        fieldWithPath("data[].createdAt").description("생성시간"),
                    )
                )
            )
    }
}