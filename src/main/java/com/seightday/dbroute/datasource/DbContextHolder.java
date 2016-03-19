package com.seightday.dbroute.datasource;

public class DbContextHolder {

   private static final ThreadLocal<String> contextHolder =
            new ThreadLocal<String>();

   public static void setDb(String db) {
       if(db == null){
           throw new NullPointerException("db should not be null");
       }
      contextHolder.set(db);
   }

   public static String getDb() {
      return contextHolder.get();
   }

   public static void clearDb() {
      contextHolder.remove();
   }
}