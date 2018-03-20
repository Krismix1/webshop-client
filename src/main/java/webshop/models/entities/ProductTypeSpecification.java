package webshop.models.entities;

import javax.persistence.*;

@Table(name = "prod_type_specs")
@Entity
public class ProductTypeSpecification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "value", length = 64)
    private String value;
    @OneToOne
    private ProductTypeSpecificationKey productTypeSpecificationKey;
    @ManyToOne
    private ProductType productType;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ProductTypeSpecificationKey getProductTypeSpecificationKey() {
        return productTypeSpecificationKey;
    }

    public void setProductTypeSpecificationKey(ProductTypeSpecificationKey productTypeSpecificationKey) {
        this.productTypeSpecificationKey = productTypeSpecificationKey;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
}
