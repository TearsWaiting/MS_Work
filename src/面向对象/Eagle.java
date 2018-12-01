package 面向对象;

public class Eagle extends Animal{

	public Eagle() {
		super();
	}
	
	//方法重写
	public void Move() {
		System.out.println("老鹰会飞翔");
	}
	
	public void Eat() {
		System.out.println("老鹰会吃小动物");
	}
	
	public void Grow() {
		System.out.println("老鹰会生长");
	}
}
