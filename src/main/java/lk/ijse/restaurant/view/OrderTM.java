package lk.ijse.restaurant.view;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderTM {
    private String code;
    private String description;
    private Integer qty;
    private Double unitPrice;
    private Double total;
    private Button btn;
}
