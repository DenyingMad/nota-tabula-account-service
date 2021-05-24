package com.devilpanda.user_service.fw;

import com.devilpanda.user_service.adapter.rest.dto.UserAuthDto;
import com.devilpanda.user_service.adapter.rest.dto.UserCreationRequestDto;
import com.devilpanda.user_service.adapter.rest.dto.UserDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles(profiles = {"test"})
public class AbstractApiIntegrationTest {
    private static final String REST_API_USER = "/rest/api/user/";
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    protected ResultActions performCreateUser(UserCreationRequestDto userForm) throws Exception {
        String contentJson = objectMapper.writeValueAsString(userForm);
        return this.mvc.perform(post(REST_API_USER)
                .content(contentJson)
                .contentType(APPLICATION_JSON));
    }

    private ResultActions performGetUserInfo(String httpHeaderLogin) throws Exception {
        return this.mvc.perform(get(REST_API_USER)
                .header("userLogin", httpHeaderLogin));
    }

    protected UserDto performGetUserInfoAndGetResponse(String httpHeaderLogin) throws Exception {
        MockHttpServletResponse response = performGetUserInfo(httpHeaderLogin)
                .andExpect(status().isOk())
                .andReturn().getResponse();
        return getObjectFromResponse(response, new TypeReference<>() {
        });
    }

    protected ResultActions performChangeUserName(String httpHeaderLogin, String userName) throws Exception {
        return this.mvc.perform(put(REST_API_USER + "/username")
                .header("userLogin", httpHeaderLogin)
                .content(userName)
                .contentType(APPLICATION_JSON));
    }

    protected ResultActions performGetUserByLogin(String login) throws Exception {
        return this.mvc.perform(get(REST_API_USER + "login")
                .param("login", login)
                .contentType(APPLICATION_JSON));
    }

    protected UserAuthDto performGetUserByLoginAndGetResponse(String login) throws Exception {
        MockHttpServletResponse response = performGetUserByLogin(login)
                .andExpect(status().isOk())
                .andReturn().getResponse();
        return getObjectFromResponse(response, new TypeReference<>() {
        });
    }

    // =-----------------------------------------------------
    // Implementation
    // =-----------------------------------------------------

    private <T> T getObjectFromResponse(MockHttpServletResponse response, TypeReference<T> reference) throws Exception {
        String json = response.getContentAsString();
        return objectMapper.readValue(json, reference);
    }
}
