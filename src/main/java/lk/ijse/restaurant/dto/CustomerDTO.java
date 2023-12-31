package lk.ijse.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class CustomerDTO {
    private String id;
    private String name;
    private String nic;
    private String email;
    private String contact;
    private String address;
}
