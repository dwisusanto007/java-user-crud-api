package com.antigravity.user_api.controller;

import com.antigravity.user_api.dto.UserDTO;
import com.antigravity.user_api.exception.UserNotFoundException;
import com.antigravity.user_api.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllUsers_ShouldReturnListOfUsers() throws Exception {
        UserDTO user1 = UserDTO.builder().id(1L).name("John").email("john@example.com").build();
        UserDTO user2 = UserDTO.builder().id(2L).name("Jane").email("jane@example.com").build();
        List<UserDTO> users = Arrays.asList(user1, user2);

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("John"));
    }

    @Test
    public void getUserById_ShouldReturnUser_WhenUserExists() throws Exception {
        UserDTO user = UserDTO.builder().id(1L).name("John").email("john@example.com").build();

        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    public void getUserById_ShouldReturn404_WhenUserNotFound() throws Exception {
        when(userService.getUserById(99L)).thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(get("/api/users/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    public void createUser_ShouldReturnCreatedUser() throws Exception {
        UserDTO userDTO = UserDTO.builder().name("John").email("john@example.com").build();
        UserDTO createdUser = UserDTO.builder().id(1L).name("John").email("john@example.com").build();

        when(userService.createUser(any(UserDTO.class))).thenReturn(createdUser);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    public void updateUser_ShouldReturnUpdatedUser_WhenUserExists() throws Exception {
        UserDTO userDTO = UserDTO.builder().name("John Updated").email("john@example.com").build();
        UserDTO updatedUser = UserDTO.builder().id(1L).name("John Updated").email("john@example.com").build();

        when(userService.updateUser(eq(1L), any(UserDTO.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Updated"));
    }

    @Test
    public void deleteUser_ShouldReturnNoContent_WhenUserExists() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteUser_ShouldReturn404_WhenUserNotFound() throws Exception {
        doThrow(new UserNotFoundException("User not found")).when(userService).deleteUser(99L);

        mockMvc.perform(delete("/api/users/99"))
                .andExpect(status().isNotFound());
    }
}
