#define O0 11
#define O1 10
#define O2 9
#define O3 6
#define O4 5
#define O5 3
#define I0 A0
#define I1 A1
#define I2 A2
#define I3 A3
#define I4 A4
#define I5 A5

static int UNIT = 200;

// exo 1
int coolFactor = 3;
bool msg[] = {1,1,1,0,0,0,1,1,1};
int pinBase = LED_BUILTIN;
int pin = 10;

// pin for potentiometer
const int analogInPin = I5; 
const int analogOutPin= O1; 
const int sensorValue = 0; 
const int outputValue = 0; 

// pin for joystick
const int analogInPinJT_1 = I4;
const int analogInPinJT_2 = I3;

int sensorValueJT_1 = 0;
int sensorValueJT_2 = 0;
int outputValueJT_1 = 0;
int outputValueJT_2 = 0;


// the setup function runs once when you press reset or power the board
void setup() {
  // initialize digital pin LED_BUILTIN as an output.
  //pinMode(LED_BUILTIN, OUTPUT);
  pinMode(pin, OUTPUT);
  
  //Initiate Serial communication.
  Serial.begin(9600);
}

// the loop function runs over and over again forever
void loop() {


  // potentiometer
  int sensorValue = analogRead(analogInPin);  
  
  int outputValue = map(sensorValue, 0, 1023, 0, 255);  
  //analogWrite(analogOutPin, outputValue);  
  
  //Serial.print("potentiometer ");
  Serial.print(sensorValue);
  Serial.print(',');
  //Serial.print(outputValue);
  
  //Serial.write("potentiometer ");
  //Serial.write(outputValue);
  //Serial.write(',');

  analogWrite(pin, sensorValue / 4); 

  // joystik
  sensorValueJT_1 = analogRead(analogInPinJT_1); 
  sensorValueJT_2 = analogRead(analogInPinJT_2); 
  
  outputValueJT_1 = map(sensorValueJT_1, 0, 1023, 0, 255);
  outputValueJT_2 = map(sensorValueJT_2, 0, 1023, 0, 255);
  
  //Serial.print("joystick (1) X ");
  Serial.print(sensorValueJT_1);
  Serial.print(',');
  
  //Serial.print("joystick (2) Y ");
  Serial.print(sensorValueJT_2);
  Serial.println(',');

  /*
  Serial.write(outputValueJT_1);
  Serial.write(',');
  Serial.write(outputValueJT_2);
  Serial.write(';');
  */
  
  delay(50);
  
}

void exo_blink(){
  for(int i=0; i<sizeof(msg) / sizeof(msg[0]); i++){
    if(msg[i]){
      morse_dot();
    }
    else{
        morse_dash();
     }
  }
     
  led_blink(2000);
}

void morse_dot(){
  led_blink(UNIT);
}

void morse_dash(){
  led_blink(UNIT * 3);
}

void led_blink(int i){
  digitalWrite(pin, HIGH);   
  delay(i);    
  digitalWrite(pin, LOW);  
  delay(UNIT);    
}

