package com.gusta.dscatalog.services;

import com.gusta.dscatalog.dtos.RoleDTO;
import com.gusta.dscatalog.dtos.UserDTO;

import com.gusta.dscatalog.entities.User;

import com.gusta.dscatalog.repositories.RoleRepository;
import com.gusta.dscatalog.repositories.UserRepository;

import com.gusta.dscatalog.services.exceptions.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional(readOnly = true)
  public Page<UserDTO> list(Pageable pageable) {
    var UserList = userRepository.findAll(pageable);
    return UserList.map(UserDTO::new);
  }

  @Transactional(readOnly = true)
  public UserDTO getById(Long id) {
    return userRepository.findById(id)
        .map(user -> new UserDTO(user))
        .orElseThrow(() -> new EntityNotFoundException("User not found"));
  }

  @Transactional
  public UserDTO create(UserDTO dto) {
    var user = UserDTO.toEntity(dto);
    validateDependencies(dto, user);

    user.setPassword(passwordEncoder.encode(dto.getPassword()));
    user = userRepository.save(user);
    return new UserDTO(user);
  }

  @Transactional
  public UserDTO update(Long id, UserDTO dto) {
    var user = userRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("User not found"));

    UserDTO.toEntity(dto, user);
    validateDependencies(dto, user);

    user = userRepository.save(user);
    return new UserDTO(user);
  }

  @Transactional
  public void delete(Long id) {
    if (!userRepository.existsById(id))
      throw new EntityNotFoundException("User not found");

    userRepository.deleteById(id);
  }

  private void validateDependencies(UserDTO dto, User entity) {
    var categoryIds = dto.getRoles().stream()
        .map(RoleDTO::getId)
        .toList();

    var categories = roleRepository.findAllById(categoryIds);
    entity.setRoles(new HashSet<>(categories));
  }
}
