package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public class Inject {
    private final Properties properties;

    public Inject() {
        properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (inputStream == null) {
                throw new RuntimeException("Файл config.properties не найден");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки файла properties", e);
        }
    }

    public <T> T inject(T object) {
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(AutoInject.class)) {
                Class<?> fieldType = field.getType();
                String implementationClassName = properties.getProperty(fieldType.getName());
                if (implementationClassName != null) {
                    try {
                        Class<?> implementationClass = Class.forName(implementationClassName);
                        Object implementationInstance = implementationClass.getDeclaredConstructor().newInstance();
                        field.setAccessible(true);
                        field.set(object, implementationInstance);
                    } catch (Exception e) {
                        throw new RuntimeException("Ошибка внедрения зависимости", e);
                    }
                }
            }
        }
        return object;
    }
}