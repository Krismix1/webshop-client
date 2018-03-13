package webshop.models.entities;

import javax.persistence.*;

@Table(name = "prod_type_specs")
@Entity
public class ProductTypeSpecification {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "value", length = 64)
    private String value;
    @OneToOne
    private ProductTypeSpecificationKey productTypeSpecificationKey;
    @ManyToOne
    private ProductType productType;
}
