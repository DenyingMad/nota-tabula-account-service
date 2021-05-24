package com.devilpanda.user_service.fw;

import com.devilpanda.user_service.adapter.jpa.UserRepository;
import com.devilpanda.user_service.adapter.rest.dto.UserAuthDto;
import com.devilpanda.user_service.adapter.rest.dto.UserCreationRequestDto;
import com.devilpanda.user_service.adapter.rest.dto.UserDto;
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
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void tearDown() {
        transactionOperations.executeWithoutResult(transactionStatus -> deleteAllEntitiesOf(User.class));
    }

    @Test
    public void createUser() throws Exception {
        UserCreationRequestDto userForm = new UserCreationRequestDto(LOGIN_PETROV, EMAIL_PETROV, PASSWORD, false);

        performCreateUser(userForm);

        assertUserCreated();
    }

    @Test
    public void getUser() throws Exception {
        performCreateUser(new UserCreationRequestDto(LOGIN_IVANOV, EMAIL_IVANOV, PASSWORD, false));

        UserDto user = performGetUserInfoAndGetResponse(LOGIN_IVANOV);

        assertEquals(EMAIL_IVANOV, user.getEmail());
        assertEquals(LOGIN_IVANOV, user.getUserName());
    }

    @Test
    public void createUser_error_loginTaken() throws Exception {
        UserCreationRequestDto userForm1 = new UserCreationRequestDto(LOGIN_PETROV, EMAIL_PETROV, PASSWORD, false);
        UserCreationRequestDto userForm2 = new UserCreationRequestDto(LOGIN_PETROV, EMAIL_IVANOV, PASSWORD, false);
        performCreateUser(userForm1);

        MockHttpServletResponse response = performCreateUser(userForm2)
                .andExpect(status().isConflict())
                .andReturn().getResponse();

        assertEquals(String.format("Login %s is already taken", LOGIN_PETROV), response.getContentAsString());
    }

    @Test
    public void createUser_error_emailTaken() throws Exception {
        UserCreationRequestDto userForm1 = new UserCreationRequestDto(LOGIN_PETROV, EMAIL_PETROV, PASSWORD, false);
        UserCreationRequestDto userForm2 = new UserCreationRequestDto(LOGIN_IVANOV, EMAIL_PETROV, PASSWORD, false);
        performCreateUser(userForm1);

        MockHttpServletResponse response = performCreateUser(userForm2)
                .andExpect(status().isConflict())
                .andReturn().getResponse();

        assertEquals(String.format("Email %s is already taken", EMAIL_PETROV), response.getContentAsString());

    }

    @Test
    public void getUser_byLogin() throws Exception {
        performCreateUser(new UserCreationRequestDto(LOGIN_IVANOV, EMAIL_IVANOV, PASSWORD, false));

        UserAuthDto user = performGetUserByLoginAndGetResponse(LOGIN_IVANOV);

        assertEquals(LOGIN_IVANOV, user.getLogin());
        assertEquals(EMAIL_IVANOV, user.getEmail());
    }

    @Test
    public void changeUserName() throws Exception {
        performCreateUser(new UserCreationRequestDto(LOGIN_IVANOV, EMAIL_IVANOV, PASSWORD, false));

        performChangeUserName(LOGIN_IVANOV, LOGIN_PETROV);

        assertUserNameIs(LOGIN_PETROV);
    }

    // ------------------------------------------------------
    // = Implementation
    // ------------------------------------------------------

    private void assertUserNameIs(String userName) {
        User user = userRepository.findUserByLogin(LOGIN_IVANOV).get();
        assertEquals(LOGIN_IVANOV, user.getLogin());
        assertEquals(LOGIN_PETROV, user.getUserName());
    }

    private void assertUserCreated() {
        User user = userRepository.findUserByLogin(LOGIN_PETROV).get();
        assertEquals(LOGIN_PETROV, user.getLogin());
        assertEquals(EMAIL_PETROV, user.getEmail());
    }

    private <T> void deleteAllEntitiesOf(Class<T> entityClass) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<T> criteriaDelete = criteriaBuilder.createCriteriaDelete(entityClass);
        criteriaDelete.from(entityClass);
        entityManager.createQuery(criteriaDelete).executeUpdate();
    }
}
