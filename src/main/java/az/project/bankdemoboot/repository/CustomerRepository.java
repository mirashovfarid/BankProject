package az.project.bankdemoboot.repository;

import az.project.bankdemoboot.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findAllByActive(Integer active);

    Customer getCustomerByIdAndActive(Long id, Integer active);
}
