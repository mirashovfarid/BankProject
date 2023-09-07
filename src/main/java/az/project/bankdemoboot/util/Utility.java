package az.project.bankdemoboot.util;

import az.project.bankdemoboot.dto.request.ReqToken;
import az.project.bankdemoboot.entity.User;
import az.project.bankdemoboot.entity.UserToken;
import az.project.bankdemoboot.enums.EnumAvailableStatus;
import az.project.bankdemoboot.exception.BankException;
import az.project.bankdemoboot.exception.ExceptionConstants;
import az.project.bankdemoboot.repository.UserRepository;
import az.project.bankdemoboot.repository.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Utility {

    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    public UserToken checkToken(ReqToken reqToken){
        Long userId = reqToken.getUserId();
        String token = reqToken.getToken();
        if (userId == null || token == null){
            throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data !");
        }
        User user = userRepository.findUserByIdAndActive(userId, EnumAvailableStatus.ACTIVE.value);
        if (user == null){
            throw new BankException(ExceptionConstants.USER_NOT_FOUND, "User not Found !");
        }
        UserToken userToken = userTokenRepository.findUserTokenByUserAndTokenAndActive(user, token, EnumAvailableStatus.ACTIVE.value);
        if (userToken == null){
            throw new BankException(ExceptionConstants.INVALID_TOKEN, "Invalid token !");
        }
        return userToken;
    }
}
