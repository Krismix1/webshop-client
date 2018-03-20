package webshop.models.domain;

public class ProductTypeVO {
    private String name;
    private String description;

    public ProductTypeVO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
