package lk.ijse.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Supplier {
    private String id;
    private String name;
    private String contact;
    private String address;
}
