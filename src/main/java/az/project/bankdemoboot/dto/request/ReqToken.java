package az.project.bankdemoboot.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReqToken {
    private Long userId;
    private String token;
}
