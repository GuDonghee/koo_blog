package main.blog.acceptance;

import main.DatabaseCleaner;
import main.blog.controller.dto.UserCreateRequest;
import main.blog.controller.dto.error.ErrorResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserAcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        databaseCleaner.execute();
    }

    @DisplayName("회원가입을 하면 상태코드 201과 회원 PK값을 응답한다.")
    @Test
    void singUp() {
        // given
        UserCreateRequest request = new UserCreateRequest("데이빗", "koo@koo.com", "test1234!@");

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/users")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(201);
    }

    @DisplayName("회원가입을 할 때, 닉네임이 1~10자 사이의 한글또는 영어가 아니면 상태코드 400과 에러메세지를 응답한다.")
    @Test
    void signUp_invalidName() {
        // given
        String invalidName = " ";
        UserCreateRequest request = new UserCreateRequest(invalidName, "koo@koo.com", "test1234!@");

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/users")
                .then().log().all()
                .extract();
        ErrorResponse actual = response.as(ErrorResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(actual.getMessage()).isEqualTo("사용자 닉네임은 1 ~ 10 글자 사이의 한글 또는 영어만 입력해주세요.");
    }

    @DisplayName("회원가입을 할 때, 이메일이 이메일 형식이 아니면 상태코드 400과 에러메세지를 응답한다.")
    @Test
    void signUp_invalidEmail() {
        // given
        String invalidEmail = "invalidEmail";
        UserCreateRequest request = new UserCreateRequest("데이빗", invalidEmail, "test1234!@");

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/users")
                .then().log().all()
                .extract();
        ErrorResponse actual = response.as(ErrorResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(actual.getMessage()).isEqualTo("이메일 형식이 아닙니다.");
    }

    @DisplayName("회원가입을 할 때, 중복되는 이메일로 회원가입을 하려고 하면 상태코드 400과 에러메세지를 응답한다.")
    @Test
    void signUp_duplicateEmail() {
        // given
        UserCreateRequest request = new UserCreateRequest("데이빗", "koo@koo.com", "test1234!@");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/users")
                .then().log().all();

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/users")
                .then().log().all()
                .extract();
        ErrorResponse actual = response.as(ErrorResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(actual.getMessage()).isEqualTo("이미 저장된 이메일입니다.");
    }

    @DisplayName("회원가입을 할 때, 비밀번호가 6~15글자 사이의 영어, 숫자, 기호 조합이 아니면 상태코드 400과 에러메세지를 응답한다.")
    @Test
    void signUp_invalidPassword() {
        // given
        String invalidPassword = "";
        UserCreateRequest request = new UserCreateRequest("데이빗", "koo@koo.com", invalidPassword);

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/users")
                .then().log().all()
                .extract();
        ErrorResponse actual = response.as(ErrorResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(actual.getMessage()).isEqualTo("올바른 비밀번호 형식이 아닙니다.");
    }
}
