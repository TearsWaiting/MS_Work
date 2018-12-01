package 面向对象;

public class Animal {
	
	//定义Animal类的属性
	private String category;
	private String bodysize;
	private String appearance;
	private int age;
	private double weight;
	private boolean sex;
	private	int x,y;
	private	int speed;
	private	int direction;
	
	//构造方法
	//(缺省构造器)
	public Animal() {
	}
	
	//声明方法
	public void Move() {
		System.out.println("动物会移动");
	}
	
	public void Eat() {
		System.out.println("动物会进食");
	}
	
	public void Grow() {
		System.out.println("动物会生长");
	}
	
	
	//封装（setter，getter方法）
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}

	public String getBodysize() {
		return bodysize;
	}

	public void setBodysize(String bodysize) {
		this.bodysize = bodysize;
	}

	public String getAppearance() {
		return appearance;
	}

	public void setAppearance(String appearance) {
		this.appearance = appearance;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public boolean getSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}
}
