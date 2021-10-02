public class DecoratorDesignPattern {
    public static void main(String args[]) {
        Dress sportyDress = new SportyDress(new BasicDress());
        sportyDress.assemble();
        System.out.println();
        
        Dress casualDress = new CasualDress(new BasicDress());
        casualDress.assemble();
        System.out.println();
        
        Dress fancyDress = new FancyDress(new BasicDress());
        fancyDress.assemble();
        System.out.println();
        
        Dress sportyFancyDress = new SportyDress(new FancyDress(new BasicDress()));
        sportyFancyDress.assemble();
        System.out.println();
        
        Dress casualFancyDress = new CasualDress(new FancyDress(new BasicDress()));
        casualFancyDress.assemble();
    }
}
interface Dress{
    public void assemble();
}
class BasicDress implements Dress{
    @Override
    public void assemble(){
        System.out.println("Basic Dress Features");   
    }
}
class DressDecorator implements Dress{
    protected Dress dress;
    
    public DressDecorator(Dress dress){
        this.dress = dress;
    }
    
    @Override
    public void assemble(){
        this.dress.assemble();
    }
}
class CasualDress extends DressDecorator{
    public CasualDress(Dress dress){
        super(dress);
    }
    
    @Override
    public void assemble(){
        super.assemble();
        System.out.println("Adding Casual Dress Features");
    }
}
class FancyDress extends DressDecorator{
    public FancyDress(Dress dress){
        super(dress);
    }
    @Override
    public void assemble(){
        super.assemble();
        System.out.println("Adding Fancy Dress Features");
    }
}
class SportyDress extends DressDecorator{
    public SportyDress(Dress dress){
        super(dress);
    }
    public void assemble(){
        super.assemble();
        System.out.println("Adding Sporty Dress Features");
    }
}
