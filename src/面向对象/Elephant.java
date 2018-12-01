package 面向对象;

public class Elephant extends Animal{

	public Elephant() {
		super();
	}
	
	//方法重写
	public void Move() {
		System.out.println("大象会缓慢走动");
	}
	
	public void Eat() {
		System.out.println("大象会吃草");
	}
	
	public void Grow() {
		System.out.println("大象会生长");
	}
}
