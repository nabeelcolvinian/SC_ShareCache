
public class StringToIntFunction implements Function<String, Integer> {

	@Override
	public Integer apply(String k) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Received request for key :" + k);
		return Integer.valueOf(k);
	}

}
