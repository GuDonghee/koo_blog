package main.auth.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import main.DatabaseCleaner;
import main.auth.controller.dto.LoginRequest;
import main.blog.controller.dto.UserCreateRequest;
import main.blog.controller.dto.error.ErrorResponse;
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
public class AuthAcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        databaseCleaner.execute();
    }

    @DisplayName("로그인을 하면 상태코드 200과 엑세스토큰을 발급한다.")
    @Test
    void login() {
        // given
        UserCreateRequest userCreateRequest = new UserCreateRequest("데이빗", "koo@koo.com", "test1234!@");
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(userCreateRequest)
                .when().post("/users")
                .then().log().all();

        LoginRequest request = new LoginRequest("koo@koo.com", "test1234!@");

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().get("/auth")
                .then().log().all()
                .extract();
        Map actual = response.as(Map.class);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(actual.get("token")).isNotNull();
    }

    @DisplayName("로그인을 할 때, 저장되지 않은 회원정보면 상태코드 404와 에러메시지를 응답한다.")
    @Test
    void login_not_found_user() {
        // given
        LoginRequest request = new LoginRequest("koo@koo.com", "test1234!@");

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().get("/auth")
                .then().log().all()
                .extract();
        ErrorResponse actual = response.as(ErrorResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(404);
        assertThat(actual.getMessage()).isEqualTo("이메일 또는 비밀번호와 일치하는 회원이 없습니다.");
    }
}
