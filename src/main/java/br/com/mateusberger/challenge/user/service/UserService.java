package br.com.mateusberger.challenge.user.service;

import br.com.mateusberger.challenge.commons.exceptions.InvalidInformationException;
import br.com.mateusberger.challenge.user.domain.RoleEnum;
import br.com.mateusberger.challenge.user.domain.User;
import br.com.mateusberger.challenge.user.domain.UserPublicInformation;
import br.com.mateusberger.challenge.user.dto.CreateUserDTO;
import br.com.mateusberger.challenge.user.dto.EditUserDTO;
import br.com.mateusberger.challenge.user.dto.UserDTO;
import br.com.mateusberger.challenge.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    public UserDTO createNewUser(CreateUserDTO request){

        var user = modelMapper.map(request, User.class);

        user.setRole(RoleEnum.DEFAULT);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User saved = userRepository.save(user);

        return modelMapper.map(saved, UserDTO.class);
    }

    public UserDTO editUserInformation(Long userId, EditUserDTO request){

        if (isNull(request.getUsername()) && isNull(request.getRole())) throw new InvalidInformationException();

        var user = userRepository.findById(userId).orElseThrow(InvalidInformationException::new);

        if (nonNull(request.getRole())) user.setRole(request.getRole());

        if (nonNull(request.getUsername())) user.setUsername(request.getUsername());

        var saved = userRepository.save(user);

        return modelMapper.map(saved, UserDTO.class);
    }

    public UserDTO getUserInformation(UserPublicInformation user){
        return modelMapper.map(user, UserDTO.class);
    }

}
