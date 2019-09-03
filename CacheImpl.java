import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * 
 * To be used as a singleton bean in spring context, can be unjected wherever needed.
 * Dependency of Function<K,V> can be autowired or can be setup from a @Bean method
 * @author user
 *
 * @param <K>
 * @param <V>
 */
public class CacheImpl<K,V> implements Cache<K,V>{

	private Function<K,V> function;
	private ConcurrentMap<K,CompletableFuture<V>> map;
	
	//@Autowired -- in spring context
	public CacheImpl(Function<K,V> function) {
		this.function = function;
		this.map = new ConcurrentHashMap<K,CompletableFuture<V>>();
	}
	
	@Override
	public V get(final K key) {
	
		/**
		 * Except the first call when an object has to be atomically added, subsequent call will be served by below if block
		 */
		if(Optional.ofNullable(map.get(key)).isPresent()){
			return map.get(key).join();
		}
		
		/**
		 * First call will not have the key, so create CF and add it to the map.
		 * Synchronize to see the correct data in map.
		 */
		synchronized (this) {
			if(!map.containsKey(key)) { // check again for presence of key in synchronized context
				CompletableFuture<V> cf = CompletableFuture.supplyAsync(() -> {
					return function.apply(key);// this is happening out of the current thread, so even if it takes time the current thread and other request will not be blocked.
				});
				map.put(key, cf);
			}
		}
		
		return map.get(key).join();
		
	}

	@Override
	public String toString() {
		return "Key = " + map.keySet().stream().map( e -> e.toString()).collect(Collectors.joining(",")) + " , Value = " + map.values().stream().map( e -> e.join().toString()).collect(Collectors.joining(",")) ;
	}
	
	
}
