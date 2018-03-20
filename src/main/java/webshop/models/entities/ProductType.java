package webshop.models.entities;

import javax.persistence.*;
import java.util.List;

@Table(name = "prod_types")
@Entity
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false, length = 40)
    private String name;
    @Column(name = "descr", length = 64)
    private String description;
    @OneToMany(mappedBy = "productType")
    private List<ProductTypeSpecification> specifications;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProductTypeSpecification> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(List<ProductTypeSpecification> specifications) {
        this.specifications = specifications;
    }
}
