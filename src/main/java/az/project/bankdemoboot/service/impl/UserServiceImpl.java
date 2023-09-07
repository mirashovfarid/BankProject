package az.project.bankdemoboot.service.impl;

import az.project.bankdemoboot.dto.request.ReqLogin;
import az.project.bankdemoboot.dto.request.ReqToken;
import az.project.bankdemoboot.dto.response.RespStatus;
import az.project.bankdemoboot.dto.response.RespToken;
import az.project.bankdemoboot.dto.response.RespUser;
import az.project.bankdemoboot.dto.response.Response;
import az.project.bankdemoboot.entity.User;
import az.project.bankdemoboot.entity.UserToken;
import az.project.bankdemoboot.enums.EnumAvailableStatus;
import az.project.bankdemoboot.exception.BankException;
import az.project.bankdemoboot.exception.ExceptionConstants;
import az.project.bankdemoboot.repository.UserRepository;
import az.project.bankdemoboot.repository.UserTokenRepository;
import az.project.bankdemoboot.service.UserService;
import az.project.bankdemoboot.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    private final Utility utility;
    @Override
    public Response<RespUser> login(ReqLogin reqLogin) {
        Response<RespUser> response = new Response<>();
        RespUser respUser = new RespUser();
        try {
             String username = reqLogin.getUsername();
             String password = reqLogin.getPassword();
             if (username == null || password == null){
                 throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid  data request !");
             }

             User user = userRepository.findUserByUsernameAndPasswordAndActive(username, password, EnumAvailableStatus.ACTIVE.value);
             if (user == null){
                 throw new BankException(ExceptionConstants.USER_NOT_FOUND, "User not found !");
             }
             String token = UUID.randomUUID().toString();
            UserToken userToken = UserToken.builder().
                    user(user).
                    token(token).
                    build();
            userTokenRepository.save(userToken);
            respUser.setUsername(username);
            respUser.setFullName(user.getFullName());
            respUser.setRespToken(new RespToken(user.getId(), token));
            response.setT(respUser);
            response.setStatus(RespStatus.getSuccessMessage());
        }catch (BankException ex){
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        }catch (Exception ex){
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception !"));
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public Response logout(ReqToken reqToken) {
        Response response = new Response();
        try {
            UserToken userToken = utility.checkToken(reqToken);
            userToken.setActive(EnumAvailableStatus.DEACTIVE.value);
            userTokenRepository.save(userToken);
            response.setStatus(RespStatus.getSuccessMessage());

        }catch (BankException ex){
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        }catch (Exception ex){
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception !"));
            ex.printStackTrace();
        }

        return response;
    }

}
