//Proiect CAN: Intersectie cu 3 semafoare
int WalkRequest = 0;  
const int WalkButton = 2;
const int RedPedLED = 6;
const int GreenPedLED = 7;

const int Red1LED = 8;
const int Yellow1LED = 9;
const int Green1LED = 10;

const int Red2LED = 11;
const int Yellow2LED = 12;
const int Green2LED = 13;

volatile int buttonState = 0; 

int buzzerPin = 5;


void setup() {
  pinMode(RedPedLED, OUTPUT);
  pinMode(GreenPedLED, OUTPUT);
  pinMode(Red1LED, OUTPUT);
  pinMode(Yellow1LED, OUTPUT);
  pinMode(Green1LED, OUTPUT);
  pinMode(Red2LED, OUTPUT);
  pinMode(Yellow2LED, OUTPUT);
  pinMode(Green2LED, OUTPUT);
  pinMode(WalkButton, INPUT);

  attachInterrupt(0, pin_ISR, CHANGE);  //  Verifica in background daca butonul a fost apasat

  digitalWrite (Red1LED, HIGH);
  digitalWrite (Red2LED, HIGH);
  digitalWrite (RedPedLED, HIGH);

  pinMode(buzzerPin, OUTPUT);
}

void loop() {
  
  // Statia 1
  delay(2500); 
  digitalWrite(Red1LED, LOW); digitalWrite(Green1LED, HIGH);
  delay(10000);  
  digitalWrite(Green1LED, LOW);  digitalWrite(Yellow1LED, HIGH);
  delay(3500); 
  digitalWrite(Yellow1LED, LOW);  digitalWrite(Red1LED, HIGH);

  if (WalkRequest == 1) {  
    WalkCycle();  
  }+
  // Statia 2
  delay(2500);  
  digitalWrite(Red2LED, LOW); digitalWrite(Green2LED, HIGH); 
  delay(10000);
  digitalWrite(Green2LED, LOW); digitalWrite(Yellow2LED, HIGH); 
  delay(3500); 
  digitalWrite(Yellow2LED, LOW); digitalWrite(Red2LED, HIGH); 

  if (WalkRequest == 1) { 
    WalkCycle();
  }
}


void WalkCycle() {
  delay(3000);
  digitalWrite (GreenPedLED, HIGH); digitalWrite (RedPedLED, LOW); 
  for(int t=0;t<5;t++)
  {tone(buzzerPin,2000,500);
  delay(1500);}
  digitalWrite (GreenPedLED, LOW); digitalWrite(WalkButton, LOW);
  delay(250);
  for (int x = 0; x < 5; x++) { 
    digitalWrite(GreenPedLED, HIGH);
    tone(buzzerPin,2000,200);
    delay(300);
    digitalWrite(GreenPedLED, LOW);
    delay(250);
  }
  digitalWrite(RedPedLED, HIGH);
  WalkRequest = 0;
  asm volatile ("  jmp 0");
}

void pin_ISR() {
  buttonState = digitalRead(WalkButton);
  (WalkRequest = 1);
}
