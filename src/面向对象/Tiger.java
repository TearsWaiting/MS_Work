package 面向对象;

public class Tiger extends Animal{

	public Tiger() {
		super();
	}
	
	//方法重写
	public void Move() {
		System.out.println("老虎会奔跑");
	}
	
	public void Eat() {
		System.out.println("老虎会吃其他动物");
	}
	
	public void Grow() {
		System.out.println("老虎会生长");
	}
}