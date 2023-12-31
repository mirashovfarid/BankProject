package az.project.bankdemoboot.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ReqCustomer {

    private Long customerId;
    private String name;
    private String surname;
    private String address;
    private String pin;
    private String seria;
    private Date dob;
    private String phone;
    private String cif;
    @JsonProperty(value = "token")
    private ReqToken reqToken; // after add for check token
}
