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
    fun `?????? ????????? ??????`() {
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
                        parameterWithName("userId").description("?????? ID"),
                    ),
                    responseFields(
                        fieldWithPath("userId").description("?????? ?????????"),
                        fieldWithPath("nowPoint").description("?????? ?????????"),
                        fieldWithPath("usePoint").description("?????? ?????????"),
                        fieldWithPath("accumulativePoint").description("?????? ?????????"),
                    )
                )
            )
    }

    @Test
    fun `?????? ????????? ?????? ??????`() {
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
                        parameterWithName("page").description("????????? ??????"),
                        parameterWithName("size").description("????????? ?????????"),
                    ),
                    pathParameters(
                        parameterWithName("userId").description("?????? ID"),
                    ),
                    responseFields(
                        fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("??? ????????? ???"),
                        fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("??? ?????? ???"),
                        fieldWithPath("number").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                        fieldWithPath("size").type(JsonFieldType.NUMBER).description("????????? ??? ?????? ???"),
                        fieldWithPath("data[].id").description("?????????(PK)"),
                        fieldWithPath("data[].userId").description("?????? ?????????"),
                        fieldWithPath("data[].reviewId").description("?????? ?????????"),
                        fieldWithPath("data[].userPointType").description("????????? ??????(${userPointTypes})"),
                        fieldWithPath("data[].changePoint").description("?????????"),
                        fieldWithPath("data[].createdAt").description("????????????"),
                    )
                )
            )
    }

    @Test
    fun `?????? ????????? ?????? ?????? ??????`() {
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
                        parameterWithName("page").description("????????? ??????"),
                        parameterWithName("size").description("????????? ?????????"),
                    ),
                    responseFields(
                        fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("??? ????????? ???"),
                        fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("??? ?????? ???"),
                        fieldWithPath("number").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                        fieldWithPath("size").type(JsonFieldType.NUMBER).description("????????? ??? ?????? ???"),
                        fieldWithPath("data[].id").description("?????????(PK)"),
                        fieldWithPath("data[].userId").description("?????? ?????????"),
                        fieldWithPath("data[].reviewId").description("?????? ?????????"),
                        fieldWithPath("data[].userPointType").description("????????? ??????(${userPointTypes})"),
                        fieldWithPath("data[].changePoint").description("?????????"),
                        fieldWithPath("data[].createdAt").description("????????????"),
                    )
                )
            )
    }
}