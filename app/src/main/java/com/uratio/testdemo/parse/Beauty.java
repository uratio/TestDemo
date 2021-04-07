package com.uratio.testdemo.parse;

import javax.xml.bind.annotation.XmlElement;

//@XmlRootElement(name = "document")
//@XmlAccessorType(XmlAccessType.FIELD)
public class Beauty {

	@XmlElement(name = "name")
	private String nameStr;
	private String age;

	public String getNameStr() {
		return nameStr;
	}

	public void setNameStr(String nameStr) {
		this.nameStr = nameStr;
	}

	public String getAge() {
		return age;
	}
 
	public void setAge(String age) {
		this.age = age;
	}
 
	@Override
	public String toString() {
		return "美女资料 [年龄=" + age + ", 姓名=" + nameStr + "]";
	}
}