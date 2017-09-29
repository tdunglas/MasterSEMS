import java.util.ArrayList;
import java.util.Scanner;

public class Score{

    int p1 = 0;
    int p2 = 0;
    String n1 = "equipe1";
    String n2 = "equipe2";
    boolean isFinished = false;
    
    ArrayList<Integer> li = new ArrayList<Integer>();
    
    public Score(){
        this.li.add(1);
        this.li.add(2);
        this.li.add(4);
    }
    
    public boolean testVal(int val){
        return li.contains(val);
    }
    
    public void setName1(String name){
        n1 = name;
    }
    
    public void setName2(String name){
        n2 = name;
    }
    
    public void setP1(int val) throws ExceptionInvalideScoreValue, ExceptionMatchFinished{
        
        if(isEndMatch()){
            throw new ExceptionMatchFinished();
        }
    
        if(testVal(val)){
            throw new ExceptionInvalideScoreValue(val);
        }
        
        if(this.p1 < val){
            this.p1 = val;
        }
        else{
            System.out.println("new score can't be inferior to previous one");
        }
        
    }
    
    public void setP2(int val) throws ExceptionInvalideScoreValue, ExceptionMatchFinished{
    
        if(isEndMatch()){
            throw new ExceptionMatchFinished();
        }
        
        if(testVal(val)){
            throw new ExceptionInvalideScoreValue(val);
        }
        
        if(this.p2 < val){
            this.p2 = val;
        }
        else{
            System.out.println("new score can't be inferior to previous one");
        }
    }

    /**
        Une méthode pour mettre à jour le score, recevant le score courant. 
    */
    
    public void maj(Score s) throws ExceptionInvalideScoreValue, ExceptionMatchFinished{
    
        if(isEndMatch()){
            throw new ExceptionMatchFinished();
        }
        
        setP1(s.p1);
        setP2(s.p2);
    }
    
    /**
        Une méthode pour enregistrer qu'un match est terminé. 
    */
    
    public void endMatch(){
        isFinished = true;
    }

    /**
        Une méthode renvoyant le score sous forme d'une chaîne, méthode appelée toString. 
    */
    
    public String toString(){
        return "Score du match " + n1 + "/" + n2 + " : " + p1 + " - " + p2;
    }
    
    /**
        Une méthode renvoyant le score sous forme d'un tableau de deux entiers. 
    */
    public int[] getScore(){
        int res[] = {p1,p2};
        return res;
    }
    /**
        Une méthode qui renvoie un booléen disant si le match est terminé ou non. 
    */
    public boolean isEndMatch(){
        return isFinished;
    }
    
    /**
        play a game
    */
    public void startMatch(){
        Scanner sc = new Scanner(System.in);
        Score update = new Score();
        String s;
        int i;
        boolean b;
        
        System.out.println("Start a new rugby match...");
        System.out.println("Enter team 1 name : ");
        s = sc.nextLine();
        setName1(s);
        
        System.out.println("Enter team 2 name : ");
        s = sc.nextLine();
        setName2(s);
        
        while(!isEndMatch()){
            
            System.out.println(this);
            
            try{
                System.out.println("Enter team 1 new score : ");
                i = sc.nextInt();
                update.setP1(i);
                
                System.out.println("Enter team 2 new score : ");
                i = sc.nextInt();
                update.setP2(i);
                
                maj(update);
                System.out.println(this);
                
                
                System.out.println("Match finished ? true / false");
                b = sc.nextBoolean();
                
                if(b){
                    endMatch();
                    System.out.println(this);
                    
                    if(p1 == p2){
                        System.out.println("EQUALITY");
                    }
                    else if(p1 > p2){
                        System.out.println("VICTORY TO " + n1);
                    }
                    else{
                        System.out.println("VICTORY TO " + n2);
                    }
                }
            }
            catch(ExceptionInvalideScoreValue | ExceptionMatchFinished e){
            }
        }
    }
    
    public static void main(String[] args){
        Score s1 = new Score();
        s1.startMatch();
        
    }
}
