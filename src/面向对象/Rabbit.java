package 面向对象;

public class Rabbit extends Animal{
	
	public Rabbit() {
		super();
	}
	
	//方法重写
	public void Move() {
		System.out.println("兔子会跳跃");
	}
	
	public void Eat() {
		System.out.println("兔子会吃萝卜");
	}
	
	public void Grow() {
		System.out.println("兔子会生长");
	}
}
