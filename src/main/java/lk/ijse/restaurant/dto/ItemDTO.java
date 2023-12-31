package lk.ijse.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemDTO {
    private String code;
    private String description;
    private Double unitprice;
    private Integer qtyonhand;
}
