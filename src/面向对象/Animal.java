package �������;

public class Animal {
	
	//����Animal�������
	private String category;
	private String bodysize;
	private String appearance;
	private int age;
	private double weight;
	private boolean sex;
	private	int x,y;
	private	int speed;
	private	int direction;
	
	//���췽��
	//(ȱʡ������)
	public Animal() {
	}
	
	//��������
	public void Move() {
		System.out.println("������ƶ�");
	}
	
	public void Eat() {
		System.out.println("������ʳ");
	}
	
	public void Grow() {
		System.out.println("���������");
	}
	
	
	//��װ��setter��getter������
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
