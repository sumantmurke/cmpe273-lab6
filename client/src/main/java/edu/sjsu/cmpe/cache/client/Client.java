package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.util.List;
//import com.google.common.hash.Hashing;

import com.google.common.hash.Hashing;

public class Client {

    public static void main(String[] args) throws Exception {
    	
    	char[] Mappedvalues = {'x', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
        System.out.println("Starting Cache Client...");
  
        List<CacheServiceInterface> AvailableServers = new ArrayList<CacheServiceInterface>();
        //adding server to the list
        AvailableServers.add(new DistributedCacheService("http://localhost:3000"));
        AvailableServers.add(new DistributedCacheService("http://localhost:3001"));
        AvailableServers.add(new DistributedCacheService("http://localhost:3002"));
        
        
        /*
         *  Function for putting key and values for sharding
         */
        
          System.out.println("Sharding of key => value onto the servers");
          System.out.println("");
          for(int i=1; i<=10; i++)	{
          	int bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(i)), AvailableServers.size());
          	AvailableServers.get(bucket).put(i, Character.toString(Mappedvalues[i]));
          	System.out.println("Key "+i+" with value "+Mappedvalues[i]+""+ " is sharded over localhost@300" +bucket);
          }
          
          /*
           *  Function for getting key and values from the respective servers
           */
          
          System.out.println("");
          System.out.println("Getting keys => values from the servers");
          System.out.println("");
          for(int j=1; j<=10; j++)	{
          	int bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(j)), AvailableServers.size());
          	System.out.println("Key " +j+ " with Value "+AvailableServers.get(bucket).get(j)+ " is fetched from the server: localhost@300" +bucket);      			
          }
        
        
        
        
        
        System.out.println("Existing Cache Client...");
    }

}
