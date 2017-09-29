public class ExceptionInvalideScoreValue extends Exception{
    
    public ExceptionInvalideScoreValue(int val){
        System.out.println("Invalide score value for rugby match, " + val);
    }
}
