public class StrategyDesignPattern {
    public static void main(String args[]) {
        Context context = new Context(new Addition());
        System.out.println(context.executeStrategy(2,3));
        
        context = new Context(new Subtraction());
        System.out.println(context.executeStrategy(2,3));
        
        context = new Context(new Multiplication());
        System.out.println(context.executeStrategy(2,3));
    }
}
interface Strategy{
    public float calculation(float a, float b);
}
class Addition implements Strategy{
    public float calculation(float a, float b){
        return a+b;
    }
}
class Subtraction implements Strategy{
    public float calculation(float a, float b){
        return a-b;
    }
}
class Multiplication implements Strategy{
    public float calculation(float a, float b){
        return a*b;
    }
}
class Context{
    private Strategy strategy;
    
    public Context(Strategy strategy){
        this.strategy = strategy;
    }
    
    public float executeStrategy(float a, float b){
        return strategy.calculation(a,b);
    }
}
