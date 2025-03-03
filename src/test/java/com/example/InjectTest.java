package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InjectTest {

    @Test
    void testInject() {
        Inject injector = new Inject();
        SomeBean someBean = injector.inject(new SomeBean());

        // Проверяем, что зависимости внедрены корректно
        someBean.foo(); // Ожидаемый вывод: A C
    }
}