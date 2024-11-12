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
      .map(UserDTO::new)
      .orElseThrow(() -> new EntityNotFoundException("User not found"));
  }

  @Transactional
  public UserDTO create(UserDTO.Create dto) {
    var user = UserDTO.createToEntity(dto);
    validateDependencies(dto, user);

    var hashedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(hashedPassword);

    user = userRepository.save(user);
    return new UserDTO(user);
  }

  @Transactional
  public UserDTO update(Long id, UserDTO.Update dto) {
    var user = userRepository.findById(id).orElseThrow(() -> {
      throw new EntityNotFoundException("User not found");
    });

    UserDTO.updateToEntity(dto, user);
//    validateDependencies(dto, user);

    user = userRepository.save(user);
    return new UserDTO(user);
  }

  @Transactional
  public void delete(Long id) {
    if (!userRepository.existsById(id))
      throw new EntityNotFoundException("User not found");

    userRepository.deleteById(id);
  }

  private void validateDependencies(UserDTO.Create dto, User entity) {
    var categoryIds = dto.roles().stream()
      .map(RoleDTO::getId)
      .toList();

    var categories = roleRepository.findAllById(categoryIds);
    entity.setRoles(new HashSet<>(categories));
  }
}
