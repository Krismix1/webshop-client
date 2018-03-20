package webshop.models.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

// TODO: 20-Mar-18 Remove the deserializer, as Spring will do the same
@JsonDeserialize(using = ProductTypeSpecificationVO.ProductTypeSpecificationVODeserializer.class)
public class ProductTypeSpecificationVO {
    // TODO: 20-Mar-18 Add validation annotations
    private String keyName;
    private String value;
    private String keyType;

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

    public static class ProductTypeSpecificationVODeserializer extends StdDeserializer<ProductTypeSpecificationVO> {

        public ProductTypeSpecificationVODeserializer() {
            this(null);
        }

        public ProductTypeSpecificationVODeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public ProductTypeSpecificationVO deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);

            ProductTypeSpecificationVO data = new ProductTypeSpecificationVO();

            data.keyName = node.get("keyName").asText();
            data.value = node.get("value").asText();
            data.keyType = node.get("keyType").asText();

            return data;
        }
    }
}
