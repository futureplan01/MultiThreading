import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class Student implements Runnable {
	
	String name;
	int count = 0;
	boolean teachAbsent = true;
	Queue<Thread> StoreStudents;
	Thread SignificantOther;
	ArrayList<Thread> myStudents;
	Professor Instructor;
	Random rand = new Random();
	public static long time = System.currentTimeMillis();
	
	int Exam1Score = 0, Exam2Score = 0, Exam3Score = 0;
	
	public void msg(String m) {
		 System.out.println("["+(System.currentTimeMillis() - time)+"] "+Thread.currentThread().getName()+": "+m);
	}
	
	public Student (String name, Professor Instructor, Queue<Thread>  StoreStudents){
		// Student sleeps before trying to enter the class.. 
		try {
			Thread.sleep(rand.nextInt(50) + 1);
			this.name = name;
			this.Instructor = Instructor;
			this.StoreStudents = StoreStudents;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void SignificantOther(Thread SignificantOther){
		this.SignificantOther = SignificantOther;
	}
	public void getStudents(ArrayList<Thread> myStudents){
		this.myStudents = myStudents;
	}

	// One student enters at a time... 
	public synchronized void studentsEnterClassRoom(){
		// Once it enters it get stored in a queue
		if(StoreStudents.size() < 10 ){
			StoreStudents.add(Thread.currentThread());
			Thread.currentThread().setPriority(Thread.currentThread().getPriority() - 1);
		}else{
			Thread.yield();
			Thread.yield();
		}
	}
	
	public synchronized void setName (){
		Thread.currentThread().setName("" + count);
		count++;
	}
	public void Terminate(){
		if(SignificantOther != null){
			try {
				SignificantOther.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	// 
	public void preExam1(){
		
		while ( teachAbsent && Instructor.exam1()){
			try {
				Thread.sleep(50);
				teachAbsent = Instructor.returnState();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void preExam2(){
		msg("i'm waiting");
		
		
		while (Instructor.exam2()){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void preExam3(){
		msg("i'm waiting ");
		while(Instructor.exam3()){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				System.out.println("Peter's the DARTH VADER"); // DON"T WORRY ABOUT IT
			}
		}
	}
	public void run (){
		// makes sure the Threads have different names
		
		setName();
		
		// Exam 1
		preExam1();
		studentsEnterClassRoom();
		// if Students is taking exam put them to sleep for 10 seconds
		// Students who are not taking the exam busy wait until exam is over

		
		if(StoreStudents.contains(Thread.currentThread())){
			try {
				msg("taking exam 1");
				Thread.sleep(100000);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				Exam1Score = rand.nextInt(91) + 10;
				Instructor.fillExam1(rand.nextInt(91) + 10,Integer.parseInt(Thread.currentThread().getName()));
			}
		}else{
			// Exam is going on, Student Busy waits
			msg("Missed exam 1");

		}
		
		// Exam 2
		preExam2();
		studentsEnterClassRoom();
		
		if(StoreStudents.contains(Thread.currentThread())){
			try {
				msg("taking exam 2");
				Thread.sleep(100000);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				msg("finished exam 2");
				Exam2Score = rand.nextInt(91) + 10;
				Instructor.fillExam2(rand.nextInt(91) + 10,Integer.parseInt(Thread.currentThread().getName()));
			}
		}else{
			// Exam is going on, Student Busy waits
			msg("Missed exam 2");
			while(Instructor.examOver()){
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// whats my problem
		preExam3();
		studentsEnterClassRoom();
		
		if(StoreStudents.contains(Thread.currentThread())){
			try {
				msg("taking exam 3");
				Thread.sleep(100000);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				msg("finished exam 3");
				Exam3Score = rand.nextInt(91) + 10;
				Instructor.fillExam3(rand.nextInt(91) + 10,Integer.parseInt(Thread.currentThread().getName()));
			}
		}else{
			// Exam is going on, Student Busy waits
			msg("Missed exam 3");
			while(Instructor.examOver()){
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					msg("finished exam 3");
				}
			}
		}
		
		Terminate();
	}
}
