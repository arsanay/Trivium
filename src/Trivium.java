
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Trivium {
    int[] keyStream = new int[120];
    int[] inp = new int[10];
    int[] enc = new int[10];
    int[] sr = new int [288];
    int[] keystream = new int [1000];
    String binarykeystream="";
    String cipher;
    int t1;
    int t2;
    int t3;
    int newt1;
    int newt2;
    int newt3;
    int z;
    int[] zi = new int [1000];    
 
    public Trivium(String key,String iv,String plain) {
        String iv_result = this.stringToBinary(iv);
       String key_result = this.stringToBinary(key);
       String plain_result = this.stringToBinary(plain);
        for (int k = 0; k < key_result.length(); k++) {
            sr[k] = Byte.parseByte(String.valueOf(key_result.charAt(k)));
        }
        if (key_result.length()<80) {
           for (int  k = key_result.length(); k < 80; k++) {
               sr[k]=0;
           } }
        for (int k = 80; k <=92; k++) {
            sr[k] = 0; 
        }
        for (int k = 93; k < iv_result.length(); k++) {
           sr[k] = Byte.parseByte(String.valueOf(iv_result.charAt(k-93)));
        }
        if (iv_result.length()<80) {
           for (int k = iv_result.length(); k < 80; k++) {
               sr[k+93]=0;
           } }
        for (int k = 173; k <=176; k++) {
            sr[k] = 0; 
        }
        for (int k = 177; k <= 284 ; k++) {
            sr[k] = 0;
        }
        for (int k = 285; k <= 287; k++) {
            sr[k] = 1;
        }
        System.out.print("Plain Text = ");
        for (int i = 0; i < plain_result.length(); i++) {
            System.out.print(Byte.parseByte(String.valueOf(plain_result.charAt(i))));
        }
        System.out.println("");
        System.out.println("Isi Register Sebelum Diacak = ");
        for (int i = 0; i < 288; i++) {
            System.out.print(sr[i]);
        }
        System.out.println("");
            keluar(plain_result);
            
        }
        
           
   
     public void keluar(String input){
         
         System.out.print("Keystream = ");
          for (int i = 0; i <1152+input.length(); i++) {            
            t1 = sr[65]  ^ sr[92];
            t2 = sr[161] ^ sr[176];
            t3 = sr[242] ^ sr[287];
            if(i>1151){
            z = t1^ t2^t3;
              System.out.print(z);
            keystream[i-1152] = z;
            }
            t1 = t1 ^ (sr[90]  & sr[91])  ^ sr[170];
            t2 = t2 ^ (sr[174] & sr[175]) ^ sr[263];
            t3 = t3 ^ (sr[285] & sr[286]) ^ sr[68];            
            for (int j = 287; j > 0; j--) {
                    sr[j] = sr[j-1];
                }
                sr[0] = t3;           
                sr[93] =t1;          
                sr[177] =t2;
               
                
          }
          System.out.println("");
     encrypt(input);
     }
 public String encrypt(String input){
      // String result = this.stringToBinary(input);
       String result = input;
       String result_string = "";
       Byte[] result_array = new Byte[result.length()];
       
       Byte[] result_xor_array = new Byte[result.length()];
   
      
      for (int i = 0; i < result_array.length; i++) {
           result_array[i] = Byte.parseByte(String.valueOf(result.charAt(i)));
           result_xor_array[i] = (byte) (this.keystream[i] ^ result_array[i]);
           result_string += result_xor_array[i];
           binarykeystream += keystream[i];
           
       }
      
     // stringBinaryToHex(result_string);
       System.out.println("Cipher Text = "+result_string);
     
      // System.out.println("");
    //   System.out.println("Hasil Enkripsi = \n"+result_string);
      decrypt(binarykeystream,result_string);
    //   hasil = result_string;
     //  hasil();
       //return this.stringBinaryToHex(result);
  //   result_string = result_string;
      cipher = result_string;
      return result_string;
      
   }
 public String decrypt (String keystream , String cipher){
       
       Byte[] a_array = new Byte[cipher.length()];
       Byte[] b_array = new Byte[keystream.length()];
      
       String plain_binary = "";
       String plain = "";
       Byte[] hasil = new Byte[cipher.length()];
      
       for (int i = 0; i < a_array.length; i++) {
           a_array[i] =Byte.parseByte(String.valueOf(cipher.charAt(i)));
        
       }
      
        for (int i = 0; i < b_array.length; i++) {
           b_array[i] =Byte.parseByte(String.valueOf(keystream.charAt(i)));
           
       }
   
        for (int i = 0; i < cipher.length(); i++) {
            hasil[i] = (byte) (b_array[i] ^ a_array[i]);
          //  System.out.println(a_array[i]+" "+b_array[i]);
          //  System.out.println(hasil[i]);
            plain_binary += hasil[i];
        
       }
        System.out.println("Dekripsi = "+plain_binary);
     //   System.out.println("");
       for (int i = 0; i < plain_binary.length(); i += 8) {
           int k = Integer.parseInt(plain_binary.substring(i,i+8),2);
           plain += (char)k;   
       }
       return plain;
   }
  public static String stringToBinary(String string){
       String result = "";
       String tmpStr;
       int tmpInt;
       char[] messChar = string.toCharArray();
       for (int i = 0; i < messChar.length; i++) {
           tmpStr = Integer.toBinaryString(messChar[i]);
        
           tmpInt = tmpStr.length();
          
           if (tmpInt !=8){
               tmpInt = 8-tmpInt;
               if (tmpInt ==8){
                   result += tmpStr;
               } else if (tmpInt > 0 ){
                   for (int j = 0; j < tmpInt; j++) {
                       result += "0";
                      
                   }
               } else { 
                   System.out.println("arguments bits to small ...");
               }
       }            
               result += tmpStr;
             // 
               }
       result += "";

       
       return result;
       
     }
        public String hexToBinary(String hex){
      String bin = "";
    String binFragment = "";
    int iHex;
    hex = hex.trim();
    hex = hex.replaceFirst("0x", "");

    for(int i = 0; i < hex.length(); i++){
        iHex = Integer.parseInt(""+hex.charAt(i),16);
        binFragment = Integer.toBinaryString(iHex);

        while(binFragment.length() < 4){
            binFragment = "0" + binFragment;
        }
        bin += binFragment;
    }
    return bin;
   }
    public static void main(String[] args) {
        Trivium tr = new Trivium("skripsi","arsana","paslon1"); 
       
    }
  
//      
}
