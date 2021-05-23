package com.devilpanda.user_service.adapter.rest;

import com.devilpanda.user_service.adapter.rest.dto.UserAuthDto;
import com.devilpanda.user_service.adapter.rest.dto.UserDtoMapper;
import com.devilpanda.user_service.adapter.rest.dto.UserCreationRequestDto;
import com.devilpanda.user_service.domain.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserAuthDtoMapperTest {
    private ObjectMapper objectMapper;
    private UserDtoMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = new UserDtoMapper();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setDateFormat(new SimpleDateFormat("MM-dd-yyyy"));
        objectMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setDefaultPrettyPrinter(new MyDefaultPrettyPrinter());
    }

    @Test
    public void map_authDtoFromUser() {
        User user = readObjectFromFile("/UserDtoMapper/User.json", User.class);

        UserAuthDto dto = mapper.mapDtoFromUser(user);

        assertEqualsToFile("/UserDtoMapper/UserAuthDto.json", dto);
    }

    @Test
    public void map_userFromUserCreationRequestDto() {
        UserCreationRequestDto userForm = readObjectFromFile("/UserDtoMapper/UserCreationRequestDto.json", UserCreationRequestDto.class);

        User user = mapper.mapUserFromUserDto(userForm);

        assertEqualsToFile("/UserDtoMapper/User.json", user);
    }

    // =-----------------------------------------------------
    // Implementation
    // =-----------------------------------------------------

    private <T> T readObjectFromFile(String fileName, Class<T> tClass) {
        String content = readFromFile(fileName);
        try {
            return objectMapper.readValue(content, tClass);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private String readFromFile(String fileName) {
        URL path = getClass().getResource(fileName);
        try {
            return Files.readString(Path.of(path.toURI()));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void assertEqualsToFile(String fileName, Object actualObject) {
        try {
            String actual = objectMapper.writeValueAsString(actualObject);
            String expected = readFromFile(fileName);

            assertEquals(expected, actual);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static class MyDefaultPrettyPrinter extends DefaultPrettyPrinter {
        public MyDefaultPrettyPrinter() {
            this._objectIndenter = new DefaultIndenter("  ", "\n");
            this._arrayIndenter = _objectIndenter;
        }

        @Override
        public DefaultPrettyPrinter createInstance() {
            return new MyDefaultPrettyPrinter();
        }

        @Override
        public void writeObjectFieldValueSeparator(JsonGenerator g) throws IOException {
            g.writeRaw(": ");
        }
    }
}
