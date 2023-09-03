package az.project.bankdemoboot.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReqLogin {

    private String username;
    private String password;

}
