package handler;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HandlerMapperTest {

    @Test
    @DisplayName("모든 경로에 대해 적절한 핸들러를 반환하는지 검증한다.")
    void getHandlerMappingTest() {
        assertAll(
                () -> assertSame(HomeHandler.getInstance(), HandlerMapper.getHandler("/")),
                () -> assertSame(CreateUserHandler.getInstance(), HandlerMapper.getHandler("/user/create")),
                () -> assertSame(RegisterHandler.getInstance(), HandlerMapper.getHandler("/registration"))
        );
    }

    @Test
    @DisplayName("매핑되지 않은 경로 요청 시 HomeHandler를 반환한다")
    void shouldReturnHomeHandlerWhenPathIsUnmapped() {
        assertSame(HomeHandler.getInstance(), HandlerMapper.getHandler("/not/mapping/test/path"));
    }

    @Test
    @DisplayName("파일 확장자가 포함된 경로(/create.html)는 CreateUserHandler와 매핑되지 않고 HomeHandler를 반환한다")
    void shouldNotMapToCreateUserHandlerWhenPathHasExtension() {
        assertSame(HomeHandler.getInstance(), HandlerMapper.getHandler("/create.html"));
    }
}
