package com.devilpanda.user_service.fw;

import com.devilpanda.user_service.adapter.rest.UserDto;
import com.devilpanda.user_service.adapter.rest.UserFormDto;
import com.devilpanda.user_service.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.transaction.support.TransactionOperations;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerIntegrationTest extends AbstractApiIntegrationTest {
    private static final String LOGIN_PETROV = "petrov";
    private static final String LOGIN_IVANOV = "ivanov";
    private static final String EMAIL_PETROV = "petrov@gmail.com";
    private static final String EMAIL_IVANOV = "ivanov@gmail.com";
    private static final String PASSWORD = "strong_password";

    @Autowired
    private TransactionOperations transactionOperations;
    @Autowired
    private EntityManager entityManager;

    @AfterEach
    public void tearDown() {
        transactionOperations.executeWithoutResult(transactionStatus -> deleteAllEntitiesOf(User.class));
    }

    @Test
    public void createUser() throws Exception {
        UserFormDto userForm = new UserFormDto(LOGIN_PETROV, EMAIL_PETROV, PASSWORD);

        UserDto response = performCreateUserAndGetResponse(userForm);

        assertEquals(LOGIN_PETROV, response.getLogin());
        assertEquals(EMAIL_PETROV, response.getEmail());
    }

    @Test
    public void createUser_error_loginTaken() throws Exception {
        UserFormDto userForm1 = new UserFormDto(LOGIN_PETROV, EMAIL_PETROV, PASSWORD);
        UserFormDto userForm2 = new UserFormDto(LOGIN_PETROV, EMAIL_IVANOV, PASSWORD);
        performCreateUser(userForm1);

        MockHttpServletResponse response = performCreateUser(userForm2)
                .andExpect(status().isConflict())
                .andReturn().getResponse();

        assertEquals(String.format("Login %s is already taken", LOGIN_PETROV), response.getContentAsString());
    }

    @Test
    public void createUser_error_emailTaken() throws Exception {
        UserFormDto userForm1 = new UserFormDto(LOGIN_PETROV, EMAIL_PETROV, PASSWORD);
        UserFormDto userForm2 = new UserFormDto(LOGIN_IVANOV, EMAIL_PETROV, PASSWORD);
        performCreateUser(userForm1);

        MockHttpServletResponse response = performCreateUser(userForm2)
                .andExpect(status().isConflict())
                .andReturn().getResponse();

        assertEquals(String.format("Email %s is already taken", EMAIL_PETROV), response.getContentAsString());

    }

    @Test
    public void getUser_byLogin() throws Exception {
        performCreateUser(new UserFormDto(LOGIN_IVANOV, EMAIL_IVANOV, PASSWORD));

        UserDto user = performGetUserByLoginAndGetResponse(LOGIN_IVANOV);

        assertEquals(LOGIN_IVANOV, user.getLogin());
        assertEquals(EMAIL_IVANOV, user.getEmail());
    }

    // =-----------------------------------------------------
    // Implementation
    // =-----------------------------------------------------

    private <T> void deleteAllEntitiesOf(Class<T> entityClass) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<T> criteriaDelete = criteriaBuilder.createCriteriaDelete(entityClass);
        criteriaDelete.from(entityClass);
        entityManager.createQuery(criteriaDelete).executeUpdate();
    }
}
