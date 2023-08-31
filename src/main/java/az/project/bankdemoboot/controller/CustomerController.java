package az.project.bankdemoboot.controller;

import az.project.bankdemoboot.dto.request.ReqCustomer;
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

    @GetMapping("/getCustomerList")
    public Response<List<RespCustomer>> getCustomerList(){
        return customerService.getCustomerList();
    }

    @GetMapping("/getCustomerById")
    public Response<RespCustomer> getCustomerById(@RequestParam Long customerId){
        return customerService.getCustomerById(customerId);
    }

    @PostMapping("/addCustomer")
    public Response addCustomer(@RequestBody ReqCustomer reqCustomer){
        return customerService.addCustomer(reqCustomer);
    }

    @PutMapping("/updateCustomer")
    public Response updateCustomer(@RequestBody ReqCustomer reqCustomer){
        return customerService.updateCustomer(reqCustomer);
    }

    @PutMapping("/DeleteCustomer/{customerId}")
    public Response deleteCustomer(@PathVariable Long customerId){
        return customerService.deleteCustomer(customerId);
    }
}
