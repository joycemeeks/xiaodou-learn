import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.annotation.JSONField;
import com.alibaba.fastjson2.annotation.JSONType;
import com.alibaba.fastjson2.reader.ObjectReader;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: PersonObject
 * @Description: fastjson2 为某个类指定一个自定义的反序列化器，控制反序列化时的具体逻辑。
 * @Author: luoxiaodou V=> dddou117
 * @Date: 2025-04-28
 * @Version: V1.0
 * @JDK: JDK21
 */
@JSONType(deserializer = PersonDeserializer.class)
public class PersonDeserializerTest {
    private String name;
    private int age;

    // 存储动态属性
    @JSONField(unwrapped = true)
    private Map<String, Object> additionalProperties = new HashMap<>();

    // 标准getter/setter（Fastjson2会默认序列化）
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // 通过@JSONField(unwrapped=true)实现动态属性扁平化序列化
    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void addAdditionalProperty(String key, Object value) {
        this.additionalProperties.put(key, value);
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public static void main(String[] args) {
        String json = "{\"name\":\"xiaodou\",\"age\":18,\"address\":\"123 Street\",\"nickname\":\"xiaodou\"}";

        // 使用Fastjson反序列化
        PersonDeserializerTest person = JSON.parseObject(json, PersonDeserializerTest.class);
        System.out.println("Name: " + person.name);  // 输出：Name: John
        System.out.println("Age: " + person.age);    // 输出：Age: 30
        System.out.println("Additional Properties: " + person.getAdditionalProperties());
    }
}

// 自定义反序列化器
class PersonDeserializer implements ObjectReader<PersonDeserializerTest> {
    @Override
    public PersonDeserializerTest readObject(JSONReader jsonReader, Type fieldType, Object fieldName, long features) {
        PersonDeserializerTest person = new PersonDeserializerTest();
        if (jsonReader.nextIfObjectStart()) {
            while (!jsonReader.nextIfObjectEnd()) {
                String key = jsonReader.readFieldName();
                switch (key) {
                    case "name":
                        person.setName(jsonReader.readString());
                        break;
                    case "age":
                        person.setAge(jsonReader.readInt32());
                        break;
                    default:
                        person.addAdditionalProperty(key, jsonReader.readAny());
                }
            }
        }
        return person;
    }
}