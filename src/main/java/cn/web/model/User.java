package cn.web.model;

public class User {
	private int id;
	private String name;
	private String openid;
	private String mail;
	private int age;
	private String sex;
	private String userlogo;
	private int level;
	private String autograph;
	private String pasw;

	private String nickName;

	public String getAutograph() {
		return this.autograph;
	}

	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getOpenid() {
		return this.openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getPasw() {
		return this.pasw;
	}

	public void setPasw(String pasw) {
		this.pasw = pasw;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUserlogo() {
		return this.userlogo;
	}

	public void setUserlogo(String userlogo) {
		this.userlogo = userlogo;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return "User{id=" + this.id + ", name='" + this.name + '\'' + ", openid='" + this.openid + '\'' + ", mail='"
				+ this.mail + '\'' + ", age=" + this.age + ", sex='" + this.sex + '\'' + ", userlogo='" + this.userlogo
				+ '\'' + ", level=" + this.level + ", autograph='" + this.autograph + '\'' + ", pasw='" + this.pasw
				+ '\'' + '}';
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
