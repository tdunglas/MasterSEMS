import processing.serial.*;

Serial myPort;
String val = "test";

// callibration
int initX = 0;
int initY = 0;
boolean init = false;

// snake
int sizeSideRect = 50;
int sizeSideRect_base = 50;
int posX = 0;
int posY = 0;
int speedX = 0;
int speedY = 0;
int speedX_old = 0;
int speedY_old = 0;
int life = 3;

// background
final int b_width = 1200;
final int b_height = 1200;
int b_color = 255;

int oldX = 0;
int oldY = 0;
int errorMargin = 10;

// food
int maxFood = 15;
int aleaA = 0;
int[][] foods;
int sizeFood = 30;
int score = 0;

// poison
int maxPoison = 10;
int aleaB = 0;
int[][] poison;
int sizePoison = 30;

void setup(){
  
  //size(b_width, b_height);
  size(1200, 1200);
  
  // choice 0, 1, 2
  String port_name = Serial.list()[0];
  myPort = new Serial(this, port_name, 9600);
  
  generateFood();
  generatePoison();
  
}

void draw()
{
  if ( myPort.available() > 0) 
  {  // If data is available,
    val = myPort.readStringUntil('\n');// read it and store it in val
  } 
  //println(val); //print it out in the console
  
  
  try {
    if(val != null) {
      String[] msg = splitTokens(val, ",");
      
      if(msg.length >=3){
        
        //println(val);
        //println(msg);
        
        if(!init){
           init = true; 
           initX = Integer.parseInt(msg[1]);
           initY = Integer.parseInt(msg[2]);
           
           oldX = initX;
           oldY = initY;
           
           print("init X " + initX + ";");
           print("init Y " + initY + ";\n");
        }
        else {
          
          int newX;
          int newY;
          
          //speed = Integer.parseInt(msg[0]);
          newX = Integer.parseInt(msg[1]);
          newY = Integer.parseInt(msg[2]);
          
          move(newX, newY);
          
          //print("speed " + speed + ";");
          print("posX " + posX + ";");
          print("posY " + posY + ";\n");
          
        }      
      }  
    }
  }
  
  catch(Exception e){
    
  }
  
  background(b_color);
  
  checkBorder();
  
  //rectMode(CENTER);
  fill(0);
  rect(posX, posY, sizeSideRect, sizeSideRect);
  
  checkCollision();
  checkCollisionPoison();
  showRemainingFood();
  showRemainingPoison();  
  
  textSize(32);
  text("score " + score, 32, 32);
  text("life " + life, 32, 64);
  
}

void checkCollision(){
  
  int x, y, xmax, ymax;
  int posXmax = posX + sizeSideRect;
  int posYmax = posY + sizeSideRect;
  
  for(int i=0; i<aleaA; i++){
    
    x = foods[i][0];
    y = foods[i][1];
    xmax = x + sizeFood;
    ymax = y + sizeFood;
        
    if((x <= posXmax && x >= posX) || (xmax <= posXmax && xmax >= posX))// x line ok
    {
      if((y <= posYmax && y >= posY) || (ymax <= posYmax && ymax >= posY)) // y ligne ok
      {
        score++;
        
        foods[i][0] = (int)random(b_width);
        foods[i][1] = (int)random(b_height);
        
        sizeSideRect += 10;
      }
    }    
    
  }
  
}

void checkCollisionPoison(){
  
  int x, y, xmax, ymax;
  int posXmax = posX + sizeSideRect;
  int posYmax = posY + sizeSideRect;
  
  for(int i=0; i<aleaB; i++){
    
    x = poison[i][0];
    y = poison[i][1];
    xmax = x + sizePoison;
    ymax = y + sizePoison;
        
    if((x <= posXmax && x >= posX) || (xmax <= posXmax && xmax >= posX))// x line ok
    {
      if((y <= posYmax && y >= posY) || (ymax <= posYmax && ymax >= posY)) // y ligne ok
      {
        life--;
        
        poison[i][0] = (int)random(b_width);
        poison[i][1] = (int)random(b_height);
        
        if(life <= 0){
           score = 0;
           life = 3;
           generateFood();
           generatePoison();
           sizeSideRect = sizeSideRect_base;
        }
      }
    }    
    
  }
  
}

void generateFood(){
  
  aleaA = (int)random(maxFood) + 1;  
  foods = new int[aleaA][2];
  
  int x,y;
  
  for(int i=0; i<aleaA; i++){
    x = (int)random(b_width); 
    y = (int)random(b_height);
    
    foods[i][0] = x;
    foods[i][1] = y;
  }
  
}

void showRemainingFood(){  
  
  fill(128);
  
  for(int i=0; i<aleaA; i++){
    rect(foods[i][0], foods[i][1], sizeFood, sizeFood);
  }  
  
}

void showRemainingPoison(){  
  
  fill(255, 0, 0);
  
  for(int i=0; i<aleaB; i++){
    rect(poison[i][0], poison[i][1], sizePoison, sizePoison);
  }  
  
}

void generatePoison(){
  
  aleaB = (int)random(maxPoison) + 3;  
  poison = new int[aleaB][2];
  
  int x,y;
  
  for(int i=0; i<aleaB; i++){
    x = (int)random(b_width); 
    y = (int)random(b_height);
    
    poison[i][0] = x;
    poison[i][1] = y;
  }
}

void move(int newX, int newY){
  
    // move +X
    if(newX >= oldX + errorMargin){
      
        speedX = newX - initX;
        
        if(speedX >= speedX_old){
          speedX_old = speedX;
          posX += speedX / 10;  
        }
    }
    else {
       speedX = 0; 
       speedX_old = 0; 
    }
    
    // move -X
    if(newX <= oldX - errorMargin){
      
        speedX = initX - newX;
        
        if(speedX >= speedX_old){
          
          speedX_old = speedX;                
          posX -= speedX / 10;
          
        }
    }
    else {
       speedX = 0; 
       speedX_old = 0; 
    }
    
    
    // move +Y
    if(newY >= oldY + errorMargin){
      
        speedY = newY - initY;
        
        if(speedY >= speedY_old){
          
          speedY_old = speedY;
          posY += speedY / 10;
          
        }
    }
    else {
       speedY = 0; 
       speedY_old = 0; 
    }
    
    // move -Y
    if(newY <= oldY - errorMargin){
      
        speedY = initY - newY;
        
        if(speedY >= speedX_old){
          
          speedY_old = speedY;
          posY -= speedY / 10;
          
        }
    }
    else {
       speedY = 0; 
       speedY_old = 0; 
    }
          
}

void checkBorder(){
    // checking border
  if(posX < 0){
   posX = 0; 
  }
  
  if(posX > b_width - sizeSideRect){
    posX = b_width - sizeSideRect; 
  }
  
  if(posY < 0){
     posY = 0; 
  }
  
  if(posY > b_height - sizeSideRect){
     posY = b_height - sizeSideRect; 
  }
}