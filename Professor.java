import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class Professor extends Thread {
	
	
	boolean teachAbsent = true;
	Queue<Thread> StoreStudents;
	ArrayList<Thread> myStudents;
	public static long time = System.currentTimeMillis();
	long ex1 = 2000; // first exam
	long ex2 = 4000;
	long ex3 = 8000;
	int [] Exam1 = new int [14];
	int [] Exam2 = new int [14];
	int [] Exam3 = new int [14];
	
	public Professor (Queue<Thread>  StoreStudents){
		this.StoreStudents = StoreStudents;
	}
	public synchronized void fillExam1(int Score, int thread){
		Exam1[thread] = Score;
	}
	public synchronized void fillExam2(int Score, int thread){
		Exam2[thread] = Score;
	}
	public synchronized void fillExam3(int Score, int thread){
		Exam3[thread] = Score;
	}
	public void getStudents(ArrayList<Thread> myStudents){
		this.myStudents = myStudents;
	}
	private void msg(String m) {
		System.out.println("["+(System.currentTimeMillis() - time)+"] "+Thread.currentThread().getName()+": "+m);
	}
	public boolean exam1(){
		return System.currentTimeMillis() - time < ex1;
	}
	public boolean exam2(){
		return System.currentTimeMillis() - time < ex2;
	}
	public boolean exam3(){
		return System.currentTimeMillis() - time < ex3;
	}
	public void waitForExam (){
		
		if(System.currentTimeMillis() - time < ex1){
			while (System.currentTimeMillis() - time < ex1){
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			msg("Exam 1 Start");
		}else if (System.currentTimeMillis() - time < ex2){
			while (System.currentTimeMillis() - time < ex2){
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			msg("Exam 2 Start");
		}else{
			while (System.currentTimeMillis() - time < ex3){
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			msg("Exam 3 Start");
		}
		
	}
	
	public void DuringExam (){
		// Teacher goes to sleep for exact time
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void run (){
		Thread.currentThread().setName("Professor");
		try {
			Thread.sleep(500); // Gotta test if this works
			teachAbsent = false;
			msg("Teacher Finally made it to class");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			return;
		}
		
		// EXAM 1
		waitForExam();
		DuringExam();
		InterruptStudents ();
		
		//Professor takes Break
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// EXAM 2
		waitForExam();
		DuringExam();
		InterruptStudents ();
		
		//Professor takes another break
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Professor's almost home
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// EXAM 
		waitForExam();
		DuringExam();
		InterruptStudents ();
		
		// Take break to grade papers 
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		printExams();
		
		
		
		
	}
	private void printExams(){
		for (int i = 0; i < 14; i++){
			msg("Student " + (i+ 1) + " " + "Scored : " +
					Exam1[i] + ", " + Exam2[i] + ", "+ Exam3[i]);
		}
	}

	private void removeStudents(){
		for(int i = 0; i < 10; i++){
			StoreStudents.remove();
		}
	}
	private void InterruptStudents(){
		Iterator<Thread> itr = StoreStudents.iterator();
		msg("the size of the QUeue is : " + StoreStudents.size());
	      while(itr.hasNext()) {
	    	  Thread element = itr.next();
	    	  element.interrupt();
		  } 
	      removeStudents();
	}

	public boolean examInProgress(){
		// If it less than 1000
		long x1 = System.currentTimeMillis() - time;
		
		// Before x1
		return  x1 >= ex1 && x1 <= ex1 + 600 ||
				x1 >= ex2 && x1 <= ex2 + 600 ||
				x1 >= ex3 && x1 <= ex3 + 600;
		
	}
	
	
	public boolean examOver(){
		if(System.currentTimeMillis() - time < ex1 + 600){
			return exam1Over();
		}
		else if (System.currentTimeMillis() - time < ex2 + 600){
			return exam2Over();
		}
		else return exam3Over();
	}
	private boolean exam1Over(){
		return (System.currentTimeMillis() - time < ex1 + 600);
	}
	private boolean exam2Over(){
		return (System.currentTimeMillis() - time < ex2 + 600);
	}
	private boolean exam3Over(){
		return (System.currentTimeMillis() - time < ex3 + 600);
	}
	public boolean returnState(){
		return teachAbsent;
	}
	
	
}
