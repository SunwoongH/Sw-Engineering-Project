package maker.server.Dto.Clothes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClothesDto {
    private String clothesImage;
    private Date date;
    private ArrayList<String> season;
    private ArrayList<String> kind;
    private ArrayList<Integer> washingMethod;
    private char size;
}
