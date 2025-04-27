import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.annotation.JSONField;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: PersonObject
 * @Description: fastjson2 序列化对象属性，展开对象的属性值
 * @Author: luoxiaodou V=> dddou117
 * @Date: 2025-04-28
 * @Version: V1.0
 * @JDK: JDK21
 */
public class PersonObject {
    private String name;
    private int age;

    // 存储动态属性
    @JSONField(unwrapped = true)
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JSONField(unwrapped = true)
    private XiaoDou xiaoDou;

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

    public XiaoDou getXiaoDou() {
        return xiaoDou;
    }

    public void setXiaoDou(XiaoDou xiaoDou) {
        this.xiaoDou = xiaoDou;
    }

    public static void main(String[] args) {
        PersonObject personObject = new PersonObject();
        personObject.setName("xiaodou");
        personObject.setAge(18);
        Map<String, Object> map = new HashMap<>();
        map.put("name", "xiaodou2号");
        XiaoDou xiaoDou = new XiaoDou("小豆3号", 18);
        personObject.addAdditionalProperty("address", "123 Street");
        personObject.addAdditionalProperty("nickname", "dadou");
        personObject.addAdditionalProperty("map", map);
        personObject.setXiaoDou(xiaoDou);
        // 使用Fastjson序列化
        String json = JSON.toJSONString(personObject);
        System.out.println(json);  // 输出：{"address":"123 Street","nickname":"dadou","age":18,"name":"xiaodou"}
    }
}

class XiaoDou {
    private String name;
    private int age;

    public XiaoDou(String name, int age) {
        this.name = name;
        this.age = age;
    }

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
}