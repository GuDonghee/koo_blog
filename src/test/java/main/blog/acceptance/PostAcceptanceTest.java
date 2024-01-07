package main.blog.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import main.DatabaseCleaner;
import main.auth.controller.dto.LoginRequest;
import main.blog.controller.dto.CommentCreateRequest;
import main.blog.controller.dto.PostCreateRequest;
import main.blog.controller.dto.UserCreateRequest;
import main.blog.service.dto.PostDetailResponse;
import main.blog.service.dto.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostAcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    private String accessToken;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        databaseCleaner.execute();

        UserCreateRequest userCreateRequest = new UserCreateRequest("데이빗", "koo@koo.com", "test1234!@");
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(userCreateRequest)
                .when().post("/users")
                .then().log().all();

        LoginRequest loginRequest = new LoginRequest("koo@koo.com", "test1234!@");
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when().get("/auth")
                .then().log().all()
                .extract();

        accessToken = (String) response.as(Map.class).get("token");
    }

    @DisplayName("게시물을 등록하면 상태코드 200을 응답한다.")
    @Test
    void createPost() {
        // given
        PostCreateRequest request = new PostCreateRequest("포스트 제목", "포스트 내용");

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(request)
                .when().post("/posts")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(201);
    }

    @DisplayName("전체 포스트 목록을 조회하면 상태코드 200과 포스트 목록을 응답한다.")
    @Test
    void findPosts() {
        // given
        PostCreateRequest firstRequest = new PostCreateRequest("첫번째임다", "포스트 내용");
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(firstRequest)
                .when().post("/posts")
                .then().log().all();

        PostCreateRequest secondRequest = new PostCreateRequest("두번째임다", "포스트 내용");
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(secondRequest)
                .when().post("/posts")
                .then().log().all();

        PostCreateRequest thirdRequest = new PostCreateRequest("세번째임다", "포스트 내용");
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(thirdRequest)
                .when().post("/posts")
                .then().log().all();

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/posts")
                .then().log().all()
                .extract();
        List<PostResponse> actual = response.as(List.class);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(actual).hasSize(3);
    }

    @DisplayName("포스트를 상세 조회하면 상태코드 200과 포스트 정보를 응답한다.")
    @Test
    void findPost() {
        // given
        PostCreateRequest firstRequest = new PostCreateRequest("첫번째임다", "포스트 내용");
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(firstRequest)
                .when().post("/posts")
                .then().log().all();

        CommentCreateRequest first = new CommentCreateRequest(1L, "코멘트 내용");
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(first)
                .when().post("/comments")
                .then().log().all();

        CommentCreateRequest second = new CommentCreateRequest(1L, "두번째 코멘트 내용");
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(second)
                .when().post("/comments")
                .then().log().all()
                .extract();

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/posts/1")
                .then().log().all()
                .extract();
        PostDetailResponse actual = response.as(PostDetailResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(actual.getId()).isEqualTo(1);
    }
}
