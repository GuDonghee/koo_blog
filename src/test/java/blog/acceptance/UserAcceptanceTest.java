package blog.acceptance;

import blog.DatabaseCleaner;
import blog.controller.dto.UserCreateRequest;
import blog.controller.dto.error.ErrorResponse;
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
        assertThat(response.header("location")).isEqualTo("/users/1");
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
}
