public class AdaptorDesignPattern {
    public static void main(String args[]) {
        Pen pen = new PenAdaptor();
        AssignmentWork aw = new AssignmentWork();
        aw.setPen(pen);
        aw.completeAssignment("Done.");
    }
}
interface Pen{
    public void write(String str);
}
class PilotPen{
    public void mark(String str){
        System.out.println(str);
    }
}
class PenAdaptor implements Pen{
    PilotPen pp = new PilotPen();
   
    public void write(String str){
        pp.mark(str);    
    }
}
class AssignmentWork{
    Pen pen;
   
    public void setPen(Pen p){
        pen=p;
    }
   
    public void completeAssignment(String s){
        pen.write(s);
    }
}
