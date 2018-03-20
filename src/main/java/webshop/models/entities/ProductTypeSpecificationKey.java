package webshop.models.entities;

import javax.persistence.*;

@Table(name = "prod_type_spec_keys")
@Entity
public class ProductTypeSpecificationKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "key_name", length = 32)
    private String fieldName;
    @Column(nullable = false, length = 8)
    private String type;

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getType() {
        return type;
    }
}
