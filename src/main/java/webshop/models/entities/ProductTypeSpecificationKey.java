package webshop.models.entities;

import javax.persistence.*;

@Table(name = "prod_type_spec_keys")
@Entity
public class ProductTypeSpecificationKey {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "field", length = 32)
    private String fieldName;
    @Column(nullable = false, length = 8)
    private String type;
}
