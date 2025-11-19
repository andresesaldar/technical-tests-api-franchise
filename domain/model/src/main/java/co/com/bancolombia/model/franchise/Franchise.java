package co.com.bancolombia.model.franchise;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Franchise {
    private String id;
    private String slug;
    private String name;
}
