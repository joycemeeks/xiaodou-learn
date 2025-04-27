import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.annotation.JSONField;

import java.util.HashMap;
import java.util.Map;
/**
 * @ClassName: PersonObject
 * @Description: fastjson2 序列化未知属性，Map集合
 * @Author: luoxiaodou V=> dddou117
 * @Date: 2025-04-28
 * @Version: V1.0
 * @JDK: JDK21
 */
public class PersonMap {
    private String name;
    private int age;

    // 存储动态属性
    // 通过@JSONField(unwrapped=true)实现动态属性扁平化序列化
    @JSONField(unwrapped = true)
    private Map<String, Object> additionalProperties = new HashMap<>();

    public void addAdditionalProperty(String key, Object value) {
        this.additionalProperties.put(key, value);
    }

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

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public static void main(String[] args) {
        PersonMap person = new PersonMap();
        person.setName("小豆1号");
        person.setAge(18);
        Map<String, Object> map = new HashMap<>();
        map.put("name", "小豆2号");
        person.addAdditionalProperty("address", "123 Street");
        person.addAdditionalProperty("nickname", "dadou");
        person.addAdditionalProperty("map", map);
        // 使用Fastjson序列化
        String json = JSON.toJSONString(person);
        // 输出：{"address":"123 Street","nickname":"dadou","age":18,"name":"xiaodou"}
        System.out.println(json);
    }
}
