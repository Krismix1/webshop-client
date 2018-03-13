package webshop.models.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

@JsonDeserialize(using = ProductTypeSpecificationVO.ProductTypeSpecificationVODeserializer.class)
public class ProductTypeSpecificationVO {
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
