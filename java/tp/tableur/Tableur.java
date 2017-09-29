import java.util.Arrays;
import java.util.ArrayList;


public class Tableur{

    private static char COL_START = 'A';
    private static char COL_END = 'J';
    private static int LINE_START = 0;
    private static int LINE_END = 9;
    
    int size = 10;
    ArrayList<Character> data; // cols
    Case[][] cases;
    
    public Tableur(){
    
        cases = new Case[size][size];
        
        data = new ArrayList<Character>();
        for (int i = 0; i < size; i++) {
            data.add((char)(65 + (i/26)*6 + i)); // ascii
        }
        
        initCases();
    }
    
    public void initCases(){
    
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                cases[i][j] = new Case();
                cases[i][j].setValue("");
            }
        }
    }
    
    public String toString(){
        String res = "";
        
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
            
                if(j == 0){
                    res += "| ";
                }
            
                res += cases[i][j].getValue();
                res += " | ";
            }
            
            res += "\n";
        }
        
        return res;
    }
    
    public boolean testCol(char c){
        return c >= COL_START && c <= COL_END;
    }
    
    public boolean testLine(int i){
        return i >= LINE_START && i <= LINE_END;
    }
    
    /**
        change content of case(col,lin) to val : constant 
    */
    public void modifCase(char col, int line, double val){
    
        if(testCol(col) && testLine(line)){
        
            int i = data.indexOf(col);
            
            cases[i][line].setValue(val+"");
        }
        else{
            System.out.println("out of index : " + col + " - " + line);
        }
    }
    
    /**
    
        change content of case(col,lin) to val : 
        Label :  
            SomethingGood
            Numberof
            Dammit
        or : 
            =PLUS(A2;A3)
            =MOINS(B1;1,2)
            =FOIS(B1;A5)
            =DIV(A4;A5)
    */
    public void modifCase(char col, int line, String val){
    
        if(testCol(col) && testLine(line)){
        
            int i = data.indexOf(col);
            
            if(val.charAt(0) == '"'){ // text
                cases[i][line].setValue(val);
            }
            
            if(val.charAt(0) == '='){ // operation
                cases[i][line].setValue("operation" + val);
            }
        }
        else{
            System.out.println("out of index : " + col + " - " + line);
        }
    }
    
    /**
        change content of case(col,lin) to empty
    */
    public void modifCaseReset(char col, int line){
    
    }
    
    public static void main(String[] args){
    
        Tableur t = new Tableur();
        System.out.println(t);
        
        double val = 5.6;
        t.modifCase('A',5,val);
        t.modifCase('A',6,"\"zone de text\"");
        t.modifCase('C',5,"=DIV(A4;A5)");
        
        System.out.println(t);
    }
}
