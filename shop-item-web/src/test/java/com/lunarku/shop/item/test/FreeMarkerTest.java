package com.lunarku.shop.item.test;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerTest {
	
	/**
	 * 测试freemarker的基本用法
	 * @throws Exception
	 */
	@Test
	public void testFreeMarker() throws Exception {
		// 1. 创建一个模板文件
		// 2. 创建一个Configuration对象
		Configuration configuration = new Configuration(Configuration.getVersion());
		// 3. 设置模板所在的路径
		configuration.setDirectoryForTemplateLoading(new File("F:\\eclipse_wsp\\taotao\\shop-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
		// 4. 设置模板字符集,一般为utf-8
		configuration.setDefaultEncoding("utf-8");
		// 5.使用Configuration对象加载一个模板文件,需要指定模板文件的文件名
		Template template = configuration.getTemplate("test.ftl");
		// 6.创建一个数据集,可以是pojo也可以是map,推荐使用map
		Map<Object,Object> data = new HashMap<>();
		
		data.put("hello", "hello freemarker");
		data.put("title", "freemarker example");
		// 测试对象
		Student student = new Student(1, "小明", 15, "西安雁塔");
		data.put("student", student);
		// 测试列表
		List<Student> stuList = new ArrayList<Student>();
		stuList.add(new Student(0, "小黄", 16, "西安长安区"));
		stuList.add(new Student(1, "小红", 17, "西安长安区"));
		stuList.add(new Student(2, "小绿", 18, "西安长安区"));
		stuList.add(new Student(3, "小蓝", 19, "西安长安区"));
		stuList.add(new Student(4, "小紫", 12, "西安长安区"));
		stuList.add(new Student(5, "小橙", 15, "西安长安区"));
		data.put("stuList", stuList);
		// 测试日期类型
		data.put("date", new Date());
		// 对null值的处理
		data.put("value", null);
		// 7. 创建一个writer对象，指定输出文件的路径及文件名
		FileWriter writer = new FileWriter(new File("D:\\temp","test.html"));
		// 8. 使用模板对象的process方法输出文件
		template.process(data, writer);
		// 9. 关闭流
		writer.close();
	}
	
}

class Student {
	
	private int id;
	private String name;
	private int age;
	private String address;
	//private Date brith;
	
	public Student() {
		
	}
	public Student(int id, String name, int age, String address) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.address = address;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
