package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.util.List;
//import com.google.common.hash.Hashing;

import com.google.common.hash.Hashing;

public class Client {

    public static void main(String[] args) throws Exception {
    	
    	char[] Mappedvalues = {'x', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
        System.out.println("Starting Cache Client...");
    /*  
        CacheServiceInterface cache = new DistributedCacheService(
                "http://localhost:3000");

        cache.put(1, "foo");
        System.out.println("put(1 => foo)");

        String value = cache.get(1);
        System.out.println("get(1) => " + value);

*/        
       
        List<CacheServiceInterface> AvailableServers = new ArrayList<CacheServiceInterface>();
        AvailableServers.add(new DistributedCacheService("http://localhost:3000"));
        AvailableServers.add(new DistributedCacheService("http://localhost:3001"));
        AvailableServers.add(new DistributedCacheService("http://localhost:3002"));
        
        
        
        
          System.out.println("Sharding of key => value onto the servers");
          System.out.println("");
          for(int key=1; key<=10; key++)	{
          	int bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(key)), AvailableServers.size());
          	AvailableServers.get(bucket).put(key, Character.toString(Mappedvalues[key]));
          	System.out.println("Key "+key+" with value "+Mappedvalues[key]+""
          			+ " is shaeded over localhost@300" +bucket);
          }
          
          System.out.println("");
          System.out.println("Getting keys => values from the servers");
          System.out.println("");
          for(int key1=1; key1<=10; key1++)	{
          	int bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(key1)), AvailableServers.size());
          	System.out.println("Key " +key1+ " with Value "+AvailableServers.get(bucket).get(key1)
          			+ " is fetched from the server: localhost@300" +bucket);      			
          }
        
        
        
        
        
        System.out.println("Existing Cache Client...");
    }

}
