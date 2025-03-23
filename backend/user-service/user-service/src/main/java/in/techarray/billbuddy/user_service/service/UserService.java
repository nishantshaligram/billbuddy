package in.techarray.billbuddy.user_service.service;

import in.techarray.billbuddy.user_service.dto.UserRequestDTO;
import in.techarray.billbuddy.user_service.exception.ValidationException;
import in.techarray.billbuddy.user_service.model.User;

public interface UserService {
    User registerUser( UserRequestDTO userRequestDTO) throws ValidationException;
}
