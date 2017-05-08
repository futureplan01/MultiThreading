import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;


public class Main {
	
	//Student [] StudentArray = new Student [14];
	
	
	Queue<Thread> myQueue = new LinkedList<Thread>();
	Professor instructor = new Professor(myQueue);
	ArrayList<Thread> myStudents = new ArrayList();
	
	public static long time = System.currentTimeMillis();

	public Main (){
		startProgram();

	}

	public void msg(String m) {
		 System.out.println("["+(System.currentTimeMillis()-time)+"] "+Thread.currentThread().getName()+": "+m);
	}
	public void startProgram(){
		instructor.start();
		Student newStudent = new Student("Student",instructor,myQueue);
		for (int i = 0; i < 14; i+= 2){
			Thread t = new Thread (newStudent);
			myStudents.add(t);
			t.start();
			newStudent.SignificantOther(t);
			Thread j = new Thread (newStudent);
			myStudents.add(t);
			j.start();
		}
		
		instructor.getStudents(myStudents);
		newStudent.getStudents(myStudents);
	}
	
}
