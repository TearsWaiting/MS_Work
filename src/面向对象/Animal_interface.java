package �������;
public interface Animal_interface {
	/*public static final*/double speed = 48.0;
	
	/*public abstract*/void run();
}

class dhd implements Animal_interface {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("�ϻ�������"+speed+"km/h"+"���ٶȱ���");
	}
	
}


class Testinterface implements Animal_interface{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("�ϻ�������"+speed+"km/h"+"���ٶȱ���");
	}

}