package az.project.bankdemoboot.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespUser {

    private String username;
    private String fullName;
    @JsonProperty(value = "token")
    private RespToken respToken;

}
