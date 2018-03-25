package webshop.models.domain;

public class ProductTypeVO {
    // TODO: 20-Mar-18 Add validation annotations
    private String name;
    private String description;
    private ProductTypeSpecificationVO[] specifications;

    // @formatter:off
    // Needed for Jackson Deserializer
    protected ProductTypeVO() {}
    // @formatter:on

    public ProductTypeVO(String name, String description, ProductTypeSpecificationVO[] specifications) {
        this.name = name;
        this.description = description;
        this.specifications = specifications;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ProductTypeSpecificationVO[] getSpecifications() {
        return specifications;
    }
}
