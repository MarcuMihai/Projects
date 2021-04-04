#include <SoftwareSerial.h>
#define trigPin 13 //pinul trigger al senzorului ultrasonic, trimite pulsatii sonore, care atunci cand se lovesc de un obiect se intorc si sunt detectate de pinul echo. 
#define echoPin 12
#define pirPin 7 //pinul senzorului infrarosu conectat la pinul nr. 7 de pe placa.
#define buzzerPin 9//buzzer-ul este conectat la pinul nr. 9 de pe placa.
SoftwareSerial mySerial(4, 3); //pinii Tx si Rx ai SIM800L sunt conectati la pinii 4, respectiv 3.
String dataBluetooth; //initializez o variabila care va reprezenta combinatia de cifre citita de monitorul serial transmisa prin modulul bluetooth.
int activareAlarma=0; //variabila initializata cu 0 care indica faptul daca alarma este activata(=1)/dezactivata(=0).
void setup() {
  Serial.begin(9600);  //se initializeaza monitorul serial.
  mySerial.begin(9600); //se initializeaza monitorul serial ce comunica cu gsm.
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  pinMode(buzzerPin, OUTPUT);
  pinMode(pirPin,INPUT);
  //in liniile de mai sus sunt setati pinii senzorilor infrarosu si ultrasonic, si buzzer-ul ca fiind de intrare sau iesire.
}

void loop() { 
  modulBluetooth();
  if(activareAlarma==1){ //daca alarma este activa atunci apelez functiile implementate mai jos.
    senzorPIR(); 
    senzorUltrasonic();
  }
}
void senzorUltrasonic(){ //functie care citeste durata masurata de senzorul ultrasonic si apoi calculeaza distanta in functie de aceasta durata.
  long duration, distance;//variabile pentru distanta si duratie pentru senzorul ultrasonic
  digitalWrite(trigPin, LOW);
  digitalWrite(trigPin, HIGH);
  digitalWrite(trigPin, LOW);
  duration = pulseIn(echoPin, HIGH); // se citeste durata de la senzor.
  distance = (duration/2) / 29.1; //se calculeaza distanta in functie de durata.
  if (distance >= 200 || distance <= 0) // verificare daca obiectul se afla in zona de acoperire a senzorului.
    {
      Serial.println("Nu se detecteaza nimic!"); //se afiseaza pe monitorul serial un mesaj pentru cazul in care obiectul nu se afla in zona de acoperire a senzorului.
    }
  else {
      Serial.print(distance); //se afiseaza pe monitorul serial distanta.
      Serial.println(" cm");
    } 
    delay(500);
}

void senzorPIR(){ //functie care verifica daca senzorul PIR detecteaza miscare si activeaza buzzer-ul in cazul in care acesta a detectat.
  int valPir=digitalRead(pirPin); // in aceasta variabila se salveaza valoarea citita de senzorul PIR.
  if(valPir==HIGH) { //daca senzorul PIR detecteaza miscare se va activa buzzer-ul.
    digitalWrite(buzzerPin, HIGH);
    tone(buzzerPin, 400); // buzzer-ul canta la frecventa de 400h timp de 500ms.
    delay(500);
    tone(buzzerPin, 800); // buzzer-ul canta la frecventa de 800h timp de 500ms.
    delay(500);
    tone(buzzerPin, 400);
    delay(500);
    tone(buzzerPin, 800);
    delay(500);
    tone(buzzerPin, 400);
    delay(500);
    tone(buzzerPin, 800);
    delay(500);
    noTone(buzzerPin);
    modulGSM();
  }
  else {
    digitalWrite(buzzerPin, LOW); //se opreste buzzer-ul deoarece senzorul PIR nu a detectat miscare.
  }
}

void modulBluetooth(){ //functie care activeaza/dezactiveaza alarma in functie de datele primite de la telefon prin intermediul monitorului serial.
  if(Serial.available()>0){
    dataBluetooth=Serial.readString(); // in aceasta variabila salvez codul citit de catre monitorul serial.
    if(dataBluetooth=="1234"){
      activareAlarma=1; // daca se primeste codul corect de activare de la monitorul serial atunci se va activa alarma, variabila primind valoarea 1.
    }
    else if(dataBluetooth=="4321"){
      activareAlarma=0; //daca monitorul serial primeste codul corect de dezactivare de la telefon atunci variabila de activare a alarmei va deveni 0, adica alarma se va dezactiva
    }
  }
}
void modulGSM(){
  mySerial.println("AT+CMGF=1");
  delay(500);
  mySerial.println("AT+CMGS=\"+40743679195\""); //trimitere mesaj.
  //mySerial.println("ATD+ +40743679195;"); //trimitere apel.
  delay(500);
  mySerial.print("Miscare detectata!"); //continut mesaj
  delay(500);
  mySerial.write(26); //trimite ctrl+Z care este terminatorul de mesaj.
  delay(500);
}
