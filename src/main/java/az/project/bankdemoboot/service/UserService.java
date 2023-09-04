package az.project.bankdemoboot.service;

import az.project.bankdemoboot.dto.request.ReqLogin;
import az.project.bankdemoboot.dto.request.ReqToken;
import az.project.bankdemoboot.dto.response.RespUser;
import az.project.bankdemoboot.dto.response.Response;

public interface UserService {
    Response<RespUser> login(ReqLogin reqLogin);

    Response logout(ReqToken reqToken);
}
