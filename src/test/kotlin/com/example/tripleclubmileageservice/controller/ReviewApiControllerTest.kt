package com.example.tripleclubmileageservice.controller


import com.example.tripleclubmileageservice.data.EventType
import com.example.tripleclubmileageservice.data.ReviewAction
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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.operation.preprocess.Preprocessors.*
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@ExtendWith(value = [RestDocumentationExtension::class, SpringExtension::class])
internal class ReviewApiControllerTest {
    val eventTypes = EventType.values().joinToString(", ") { "${it.name}" }
    val reviewActions = ReviewAction.values().joinToString(", "){"${it.name} : ${it.action}"}

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
    fun `리뷰 이벤트 발생`() {
        val body = """
            {
                "type": "REVIEW",
                "action": "ADD",
                "reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",
                "content": "GOOD!",
                "attachedPhotoIds": ["e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-851d-4a50-bb07-9cc15cbdc332"],
                "userId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
                "placeId": "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
            }
        """.trimIndent()
        this.mockMvc.perform(
            post("/events")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andDo(
                document(
                    "post-review-event",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("type").description("이벤트 타입(${eventTypes})"),
                        fieldWithPath("action").description("리뷰 이벤트 타입(${reviewActions})"),
                        fieldWithPath("reviewId").description("리뷰 아이디"),
                        fieldWithPath("content").description("리뷰 내용"),
                        fieldWithPath("attachedPhotoIds").description("리뷰에 첨부된 이미지 ID"),
                        fieldWithPath("userId").description("유저 아이디"),
                        fieldWithPath("placeId").description("장소 아이디")
                    ),
                    responseFields(
                        fieldWithPath("reviewId").description("리뷰 아이디"),
                        fieldWithPath("content").description("리뷰 내용"),
                        fieldWithPath("attachedPhotoIds").description("리뷰에 저장된 사진 Id"),
                        fieldWithPath("userId").description("유저아이디"),
                        fieldWithPath("placeId").description("장소아이디")
                    )
                )
            )


    }

}