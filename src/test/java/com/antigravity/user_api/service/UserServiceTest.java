package com.antigravity.user_api.service;

import com.antigravity.user_api.dto.UserDTO;
import com.antigravity.user_api.entity.User;
import com.antigravity.user_api.exception.UserNotFoundException;
import com.antigravity.user_api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void getAllUsers_ShouldReturnListOfUserDTOs() {
        User user1 = User.builder().id(1L).name("John").email("john@example.com").build();
        User user2 = User.builder().id(2L).name("Jane").email("jane@example.com").build();

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<UserDTO> result = userService.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getName());
    }

    @Test
    public void getUserById_ShouldReturnUserDTO_WhenUserExists() {
        User user = User.builder().id(1L).name("John").email("john@example.com").build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO result = userService.getUserById(1L);

        assertEquals("John", result.getName());
    }

    @Test
    public void getUserById_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(99L));
    }

    @Test
    public void createUser_ShouldReturnCreatedUserDTO() {
        UserDTO inputDTO = UserDTO.builder().name("John").email("john@example.com").build();
        User savedUser = User.builder().id(1L).name("John").email("john@example.com").build();

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserDTO result = userService.createUser(inputDTO);

        assertEquals(1L, result.getId());
        assertEquals("John", result.getName());
    }

    @Test
    public void updateUser_ShouldReturnUpdatedUserDTO_WhenUserExists() {
        User existingUser = User.builder().id(1L).name("Old Name").email("old@example.com").build();
        UserDTO updateDTO = UserDTO.builder().name("New Name").email("new@example.com").build();
        User updatedUser = User.builder().id(1L).name("New Name").email("new@example.com").build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        UserDTO result = userService.updateUser(1L, updateDTO);

        assertEquals("New Name", result.getName());
    }

    @Test
    public void updateUser_ShouldThrowException_WhenUserNotFound() {
        UserDTO updateDTO = UserDTO.builder().name("New Name").build();
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(99L, updateDTO));
    }

    @Test
    public void deleteUser_ShouldDeleteUser_WhenUserExists() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteUser_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.existsById(99L)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(99L));
    }
}
