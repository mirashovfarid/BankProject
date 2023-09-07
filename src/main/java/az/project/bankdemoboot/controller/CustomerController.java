package az.project.bankdemoboot.controller;

import az.project.bankdemoboot.dto.request.ReqCustomer;
import az.project.bankdemoboot.dto.request.ReqToken;
import az.project.bankdemoboot.dto.request.ReqTransaction;
import az.project.bankdemoboot.dto.response.RespCustomer;
import az.project.bankdemoboot.dto.response.Response;
import az.project.bankdemoboot.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/getCustomerList")
    public Response<List<RespCustomer>> getCustomerList(@RequestBody ReqToken reqToken){
        return customerService.getCustomerList(reqToken);
    }

    @PostMapping("/getCustomerById")
    public Response<RespCustomer> getCustomerById(@RequestBody ReqCustomer reqCustomer){
        return customerService.getCustomerById(reqCustomer);
    }

    @PostMapping("/addCustomer")
    public Response addCustomer(@RequestBody ReqCustomer reqCustomer){
        return customerService.addCustomer(reqCustomer);
    }

    @PutMapping("/updateCustomer")
    public Response updateCustomer(@RequestBody ReqCustomer reqCustomer){
        return customerService.updateCustomer(reqCustomer);
    }

    @PutMapping("/DeleteCustomer")
    public Response deleteCustomer(@RequestBody ReqCustomer reqCustomer){
        return customerService.deleteCustomer(reqCustomer);
    }
}
