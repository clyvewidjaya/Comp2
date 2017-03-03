/**
 * Created by clyve on 07/02/17.
 */
public class StudentRecord {
    public String sid;
    public float assignment;
    public float midterm;
    public float finalExam;
    public float finalMark;
    public String letterGrade;

    public StudentRecord(String sid, float assignment, float midterm, float finalExam){
        setSid(sid);
        setAssignment(assignment);
        setMidterm(midterm);
        setFinalExam(finalExam);
    }

    public void countFinalMark(float assignment, float midterm, float finalExam){
        float mark;
        mark = (assignment*20/100) + (midterm*30/100) + (finalExam*50/100);
        setFinalMark(mark);
        if (mark > 100){
            setLetterGrade("WOW, SUCH WOW");
        } else if (mark >= 80 && mark <= 100){
            setLetterGrade("A");
        } else if (mark >= 70 && mark <= 79){
            setLetterGrade("B");
        } else if (mark >= 60 && mark <= 69){
            setLetterGrade("C");
        } else if (mark >= 50 && mark <= 59){
            setLetterGrade("D");
        } else {
            setLetterGrade("F");
        }

    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public float getAssignment() {
        return assignment;
    }

    public void setAssignment(float assignment) {
        this.assignment = assignment;
    }

    public float getMidterm() {
        return midterm;
    }

    public void setMidterm(float midterm) {
        this.midterm = midterm;
    }

    public float getFinalExam() {
        return finalExam;
    }

    public void setFinalExam(float finalExam) {
        this.finalExam = finalExam;
        countFinalMark(assignment, midterm, finalExam);
    }

    public float getFinalMark() {
        return finalMark;
    }

    public void setFinalMark(float finalMark) {
        this.finalMark = finalMark;
    }

    public String getLetterGrade() { return letterGrade; }

    public void setLetterGrade(String letterGrade) { this.letterGrade = letterGrade; }
}
