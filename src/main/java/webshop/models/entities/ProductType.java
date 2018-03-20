package webshop.models.entities;

import javax.persistence.*;

@Table(name = "prod_types")
@Entity
public class ProductType {
    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true, nullable = false, length = 40)
    private String name;
    @Column(name = "descr", length = 64)
    private String description;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
