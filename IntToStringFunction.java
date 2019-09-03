
public class IntToStringFunction implements Function<Integer, String> {

	@Override
	public String apply(Integer k) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Received request for key :" + k);
		return k.toString().toUpperCase();
	}

}
