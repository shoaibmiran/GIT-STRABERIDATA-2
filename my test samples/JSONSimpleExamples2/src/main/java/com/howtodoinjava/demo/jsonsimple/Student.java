package com.howtodoinjava.demo.jsonsimple;

public class Student {
	 private Lecture[] lecture;

	    public void setStudentLecture(Lecture[] lecture) {
	        this.lecture = lecture;
	    }

	    public Lecture[] getStudentLecture() {
	        return lecture;
	    }

	    public static void main(String[] args) {
	        Student student = new Student();
	        Lecture[] lectures = new Lecture[3];
	        lectures[0] = new Lecture("Physics","s1");
	        lectures[1] = new Lecture("Mathematics","s2");
	        lectures[2] = new Lecture("Chemistry","s3");

	        student.setStudentLecture(lectures);

	        Lecture[] lectures1 = student.getStudentLecture();
	        for (int i = 0; i <lectures1.length; ++i) {
	            System.out.println(lectures1[i].getName());
	        }
	    }

}
