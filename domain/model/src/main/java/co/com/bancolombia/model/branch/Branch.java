package co.com.bancolombia.model.branch;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Branch {
    private String id;
    private String slug;
    private String name;
    private String franchiseId;
}
