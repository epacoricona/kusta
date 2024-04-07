package micro.desafio.kusta.domain.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "person")
public class Person {
	@Id
    private String id;   
    private String name;    
    private String lasName;
}
