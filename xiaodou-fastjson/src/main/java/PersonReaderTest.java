import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONFactory;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.reader.ObjectReader;
import com.alibaba.fastjson2.reader.ObjectReaderProvider;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: PersonReaderTest
 * @Description: 自定义的JSON解析器来解析Person对象，并将未知属性存入additionalProperties中
 * @Author: luoxiaodou V=>dddou117
 * @Date: 2025/4/27
 * @Version: V1.0
 * @JDK: JDK21
 */
public class PersonReaderTest {
    private String name;
    private int age;
    private Map<String, Object> additionalProperties = new HashMap<>();

    // getter/setter
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

    public void addAdditionalProperty(String key, Object value) {
        this.additionalProperties.put(key, value);
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public static void main(String[] args) {
        // 注册自定义Reader
        ObjectReaderProvider provider = JSONFactory.getDefaultObjectReaderProvider();
        provider.register(PersonReaderTest.class, new PersonReader());

        String json = "{\"name\":\"xiaodou\",\"age\":18,\"address\":\"123 Street\",\"nickname\":\"xiaodou\"}";

        PersonReaderTest person = JSON.parseObject(json, PersonReaderTest.class);

        System.out.println("Name: " + person.getName());
        System.out.println("Age: " + person.getAge());
        System.out.println("Additional Properties: " + person.getAdditionalProperties());
    }
}

class PersonReader implements ObjectReader<PersonReaderTest> {
    @Override
    public PersonReaderTest readObject(JSONReader reader, Type fieldType, Object fieldName, long features) {
        PersonReaderTest person = new PersonReaderTest();
        while (!reader.nextIfObjectEnd()) { // 检查是否到达对象结尾
            String name = reader.readFieldName(); // 读取当前字段名
            switch (name) {
                case "name":
                    person.setName(reader.readString());
                    break;
                case "age":
                    person.setAge(reader.readInt32());
                    break;
                default:
                    person.addAdditionalProperty(name, reader.readAny());
            }
        }
        return person;
    }
}