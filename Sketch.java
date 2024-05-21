import processing.core.PApplet;

public class Sketch extends PApplet {
  
  // Related arrays for the (x, y) coordinate of the snowflakes
  float[] snowX = new float[40];
  float[] snowY = new float[40];
  
  // Array for hiding snowflakes 
  boolean[] blnHideSnow = new boolean[40]; 

  // Determine size of snowflakes 
  int snowDiameter = 25; 

  // Key values
  boolean blnUpPressed = false; 
  boolean blnDownPressed = false;
  boolean blnWPressed = false;
  boolean blnSPressed = false; 
  boolean blnAPressed = false; 
  boolean blnDPressed = false; 
  boolean blnHit = false; 

  // Initializing location variables
  float fltCircleX = 0;
  float fltCircleY = 0;
  float fltPlayerX = 10;
  float fltPlayerY = 200;

  // Defining variables
  int intLocation = 0;
  int intPlayerLives = 3;
  double dblClickingRadius = 20; 
  double dblRadius = 12;  
  
  public void settings() {
    size(400, 400);
  }

  public void setup() {
    background(0);
 
   // Generate random x and y values for snowflakes
   for(int i = 0; i < snowX.length; i++) {
      snowX[i] = random(width);
      snowY[i] = random(height);
      blnHideSnow[i] = false;
    }
  }

  public void draw() {
    background(300);

    // Add player representative
    fill(128, 191, 242);
    circle(fltPlayerX, fltPlayerY, 20);

    // Add hearts/life representatives
    fill (255, 79, 20); 
    if(intPlayerLives == 3) {
      square(310, 0, 30);
      square(340, 0, 30);
      square(370, 0, 30); 
    } else if(intPlayerLives == 2) {
      square(340, 0, 30);
      square(370, 0, 30); 
    } else if(intPlayerLives == 1) {
      square(370, 0, 30); 
    }

    if(key == 'w' || key == 'W' || key == 's' || key == 'S' || key == 'a' || key == 'A' || key == 'd' || key == 'D' || keyCode == DOWN || keyCode == UP) {
      movingPlayer();
    }

    if (blnHit == true) {
      blnHideSnow[intLocation] = true;
    }

    // Locate position of each snowflake 
    for (int j = 0; j < 40; j++) {
      // Sends touched snowflakes back up if player touches the snowflake
      if (dist(fltPlayerX, fltPlayerY, snowX[j], snowY[j]) <= dblRadius) {
        intPlayerLives -= 1;
        snowY[j] = 0; 
      } 
    }

    // Snow actions 
    snow();

    // Create boundaries
    if(fltPlayerX < 10) {
    fltPlayerX = 10; 
    } else if (fltPlayerX > width - 10) {
      fltPlayerX = width - 10;
    }

    if (fltPlayerY < 10) {
    fltPlayerY = 10;
    } else if (fltPlayerY > 390) {
      fltPlayerY = 390;
    } 

     
    // Create game end
    if (intPlayerLives == 0) {
      background(215, 239, 250);
      fill(66, 245, 176);
      textSize(40);
      text("Game Over!", 100, 200);
      }
    }
  
    private void movingPlayer() {
      if (blnWPressed) {
        fltPlayerY -= 2;
      }
      if (blnSPressed ) {
        fltPlayerY += 2;
      }
      if (blnAPressed) {
        fltPlayerX -= 2;
      }
      if (blnDPressed) {
        fltPlayerX += 2;
      }
    }
   
    public void snow(){
      fill(255);
      for(int i = 0; i < snowX.length; i++) {
        if (!blnHideSnow[i]) {
          circle(snowX[i], snowY[i], snowDiameter);
          snowY[i] += 2;

          // Reset snowflakes
          if (snowY[i] > height) {
            snowY[i] = 0;
          }

          // Check if snowflake is clicked
          if (mousePressed) {
            if (dist(snowX[i], snowY[i], mouseX, mouseY) < snowDiameter / 2) {
              blnHideSnow[i] = true;
            }
          }

          // Make snow fall faster
          if(blnDownPressed) {
            circle(snowX[i], snowY[i], snowDiameter);
            snowY[i] += 5;
          } else if(blnUpPressed) {
            circle(snowX[i], snowY[i], snowDiameter);
            snowY[i]--;
          } 
        }
      }   
    }
  
    public void keyPressed() {
      if (keyCode == DOWN) {
        blnDownPressed = true;
      }
      else if (keyCode == UP) {
        blnUpPressed = true;
      }
      if (key == 'w' || key == 'W') {
        blnWPressed = true;
      }
      else if (key == 's' || key == 'S') {
        blnSPressed = true;
      }
      else if (key == 'a' || key == 'A') {
        blnAPressed = true;
      }
      else if (key == 'd' || key == 'D') {
        blnDPressed = true;
      }
    }
   
    public void keyReleased() {
      if (keyCode == DOWN) {
        blnDownPressed = false;
       }
      else if (keyCode == UP) {
        blnUpPressed = false;
      }
  
      if (key == 'w' || key == 'W') {
        blnWPressed = false;
      }
      else if (key == 's' || key == 'S') {
        blnSPressed = false;
      }
      else if (key == 'a' || key == 'A') {
        blnAPressed = false;
      }
      else if (key == 'd' || key == 'D') {
        blnDPressed = false;
      }
    }
  
    public void mousePressed() {
      for (int i = 0; i < 40; i++) {
        if (dist(mouseX, mouseY, snowX[i], snowY[i]) < dblClickingRadius) {
          blnHit = true;
        }
      }
    }
  }