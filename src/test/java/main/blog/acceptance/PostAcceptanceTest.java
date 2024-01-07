package main.blog.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import main.DatabaseCleaner;
import main.auth.controller.dto.LoginRequest;
import main.blog.controller.dto.PostCreateRequest;
import main.blog.controller.dto.UserCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

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
                .when().post("/post")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(201);
    }
}
