package helloworld.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceivedOrder {
    private String destination;
    private String brandName;
    private String clothType;
    private String size;
}
