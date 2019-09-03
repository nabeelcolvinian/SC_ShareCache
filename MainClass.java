import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Main Class has been used like a spring context in which object will be created and dependencies will be added
 * TestCases have been added in the main method itself
 * @author user
 *
 */
public class MainClass {
	public static void main(String[] args) {
		
		Function<String, Integer> stringToIntFunction = new StringToIntFunction();
		Function<Integer, String> intToStringFunction = new IntToStringFunction();
		
		List<String> listString = Arrays.asList("123", "123", "111", "111", "123", "111");
		
		//Testing sequential addition
		Cache<String, Integer> stringToIntcache = new CacheImpl<String, Integer>(stringToIntFunction);
		listString.stream().forEach(s -> stringToIntcache.get(s));
		System.out.println(stringToIntcache);
		
		//Testing parallel addition
		Cache<String, Integer> stringToIntcache2 = new CacheImpl<String, Integer>(stringToIntFunction);
		listString.parallelStream().forEach(s -> stringToIntcache2.get(s));
		System.out.println(stringToIntcache);
		
		
		List<Integer> intList = Arrays.asList(123,123,111,111,123,111);

		//Testing sequential addition
		Cache<Integer, String> intToStringCache = new CacheImpl<Integer, String>(intToStringFunction);
		intList.stream().forEach(i -> intToStringCache.get(i));
		System.out.println(intToStringCache);
		
		//Testing parallel addition
		Cache<Integer, String> intToStringCache2 = new CacheImpl<Integer, String>(intToStringFunction);
		intList.parallelStream().forEach(i -> intToStringCache.get(i));
		System.out.println(intToStringCache);
	}
}
