package 面向对象;
public interface Animal_interface {
	/*public static final*/double speed = 48.0;
	
	/*public abstract*/void run();
}

class dhd implements Animal_interface {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("老虎可以以"+speed+"km/h"+"的速度奔跑");
	}
	
}


class Testinterface implements Animal_interface{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("老虎可以以"+speed+"km/h"+"的速度奔跑");
	}

}