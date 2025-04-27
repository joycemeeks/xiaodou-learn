import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.annotation.JSONField;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: FlexiblePerson
 * @Description: 多态反序列化。让 JSON 文本中带上一个 type 字段，反序列化时根据 type 自动转成对应的子类。
 * @Author: luoxiaodou V=>dddou117
 * @Date: 2025/4/27
 * @Version: V1.0
 * @JDK: JDK21
 */
// @JSONType
public class FlexiblePerson  {
    private String name;
    @JSONField(name = "*")
    private Map<String, Object> additionalProperties = new HashMap<>();

    public void addAdditionalProperty(String key, Object value) {
        this.additionalProperties.put(key, value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public static void main(String[] args) {
        String json = "{\"name\":\"xiaodou\",\"age\":18,\"address\":\"123 Street\",\"nickname\":\"xiaodou\"}";

        FlexiblePerson person = JSON.parseObject(json, FlexiblePerson.class);
        // 可以在这里处理额外的属性
        // JSON.parseObject(json)
        //     .forEach((key, value) -> {
        //         if (!"name".equals(key)) {
        //             person.addAdditionalProperty(key, value);
        //         }
        //     });

        System.out.println("Name: " + person.getName());
        System.out.println("Additional Properties: " + person.getAdditionalProperties());
    }
}
