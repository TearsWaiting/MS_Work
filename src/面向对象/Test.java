package 面向对象;

public class Test {
	public static void main(String[] args) {
		
		//创建动物（父类）对象和各种动物（子类）对象
		Animal A = new Animal();
		Eagle E = new Eagle();
		Elephant Ep = new Elephant(); 
		Monkey M = new Monkey();
		Rabbit R = new Rabbit();	
		Tiger T = new Tiger();
		HuananTiger HT = new HuananTiger();
		
		//调用方法
		A.Move();
		A.Eat();
		A.Grow();
		E.Move();
		E.Eat();
		E.Grow();
		Ep.Move();
		Ep.Eat();
		Ep.Grow();
		M.Move();
		M.Eat();
		M.Grow();
		R.Move();
		R.Eat();
		R.Grow();
		T.Move();
		T.Eat();
		T.Grow();
		HT.Move();
		HT.Eat();
		HT.Grow();
	}
}
