package �������;

public class Test {
	public static void main(String[] args) {
		
		//����������ࣩ����͸��ֶ�����ࣩ����
		Animal A = new Animal();
		Eagle E = new Eagle();
		Elephant Ep = new Elephant(); 
		Monkey M = new Monkey();
		Rabbit R = new Rabbit();	
		Tiger T = new Tiger();
		HuananTiger HT = new HuananTiger();
		
		//���÷���
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
