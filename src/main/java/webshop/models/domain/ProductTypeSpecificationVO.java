package webshop.models.domain;

public class ProductTypeSpecificationVO {
    // TODO: 20-Mar-18 Add validation annotations
    private String keyName;
    private String value;
    private String keyType;

    // @formatter:off
    // Needed for Jackson Deserializer
    protected ProductTypeSpecificationVO(){}
    // @formatter:on

    public ProductTypeSpecificationVO(String keyName, String value, String keyType) {
        this.keyName = keyName;
        this.value = value;
        this.keyType = keyType;
    }

    public String getKeyName() {
        return keyName;
    }

    public String getValue() {
        return value;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }
}
