
import java.io.IOException;
import java.io.FileWriter;
import java.io.File;
public class main {public static boolean check(int numexp) {
	 try{FileWriter myWriter = new FileWriter("visitexpr.txt",true);
	  myWriter.write("exp"+numexp+"is visited\n");
	 myWriter.close();
}	 catch(Exception e){}
	 return false;
	 }


    public static void main(String[] args) {//block number 1
		try{
		File output = new File("task2.txt");
		output.createNewFile();
		FileWriter w1 = new FileWriter("task2.txt",true);
		w1.write("block 1 is Visited"+"\n");
		w1.close();
		}catch (IOException e) {throw new RuntimeException(e);}

        int x=5;
        if((check(1)||x>5)){//block number 2
		try{
		FileWriter w2 = new FileWriter("task2.txt",true);
		w2.write("block 2 is Visited"+"\n");
		w2.close();
		}catch (IOException e) {throw new RuntimeException(e);}

            System.out.println(x);
        }
        else{//block number 3
		try{
		FileWriter w3 = new FileWriter("task2.txt",true);
		w3.write("block 3 is Visited"+"\n");
		w3.close();
		}catch (IOException e) {throw new RuntimeException(e);}


        }
        for(int i=0;i<x;i++){//block number 4
		try{
		FileWriter w4 = new FileWriter("task2.txt",true);
		w4.write("block 4 is Visited"+"\n");
		w4.close();
		}catch (IOException e) {throw new RuntimeException(e);}

        }
        if((check(2)||x==5) || (check(3)||x>5)){//block number 5
		try{
		FileWriter w5 = new FileWriter("task2.txt",true);
		w5.write("block 5 is Visited"+"\n");
		w5.close();
		}catch (IOException e) {throw new RuntimeException(e);}
x--;}


    }

}

