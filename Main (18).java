import java.util.Scanner;

public class Main {
  private static int[][] board;
  private static int[][] y;
  private static int[][] box;
  private static int[][] test;
  private static int[][] hideboard;
  
  public static void main(String[] args) {
    
    boolean restart = true;
    
    while (restart){
      int attempts = 3;
      
      board = new int[9][9];
      test = new int[9][9];
      y =new int[9][9];
      box = new int[9][9];
      
      for (int i = 0;i < 3;i++){
        for(int n = 0;n < 3;n++){
          for (int j = 0;j < 3;j++){
            int rand=(int)(Math.random()*9)+1;
            
            while (! isUnique(rand,i*3+n,j+i*3)){
              rand=(int)(Math.random()*9)+1;
            }
            
            board[i * 3 + n][j + i * 3] = rand;
            test[i * 3 + n][j + i * 3] = rand;
             y[j + i * 3][i * 3 + n] = rand;
            setBox(i * 3 + n,j + i * 3,rand);
            
          }
        }
      }
      xmp(0,3);
      
      hideboard=board;
      
      HideVals();
      Scanner userInput = new Scanner(System.in);
      
      while (attempts > 0 && !victory()){
        boolean rechoose = true;
        int  x = -1;
        int yt = -1;
        printBoard(hideboard);
        while (rechoose){
          System.out.println("Enter X coord");
          
          char  xChar=userInput.next().charAt(0);
          while (!check(xChar)){
            System.out.println("Enter Y coord");
             xChar=userInput.next().charAt(0);
            
          }
          
           x=letterValue( xChar);
          
          System.out.println("Enter Y coord");
          char  ytChar=userInput.next().charAt(0);
          
          while (!check( ytChar)){
            System.out.println("Enter X coord");
             ytChar=userInput.next().charAt(0);
            
          }
           yt=letterValue( ytChar);
          if (hideboard[x][yt]!=0)
            System.out.println("ERR Slot not empty");
          else
            rechoose=false;
          
        }
        System.out.println("Guess number: ");
        int chooseNum=userInput.nextInt();
        if (test[ x][yt]==chooseNum){
          System.out.println("✓");
          hideboard[ x][yt]=chooseNum;
        }else{
          System.out.println("✘");
          attempts--;
          System.out.println("Attempts remaining: " + attempts);
        }
      }
      
      if (attempts==0){
        System.out.println("Game Over, you Lost");
      }else if(victory()){
        System.out.println("Game Over, you Won");
        printBoard(hideboard);
      }
      boolean loop=true;
      while (loop){
        System.out.println("Type 1 to play again, 2 to quit");
        int again = userInput.nextInt();
        if (again == 1){
          loop=false;
        }else {
          restart = false;
          loop=false;
          
        }
      }
    }
  }
  
  public static boolean victory(){
    
    for (int i=0;i<9;i++){
      for(int j=0;j<9;j++){
        if(test[i][j]!=hideboard[i][j])
          return false;
        
      }
    }
    return true;
  }
  
  public static int letterValue(char A){
    switch (A){
        
        case 'A': return 0;
        case 'B': return 1;
        case 'C': return 2;
        case 'D': return 3;
        case 'E': return 4;
        case 'F': return 5;
        case 'G': return 6;
        case 'H': return 7;
        case 'I': return 8;
    }
    return 0;
  }
  public static boolean check(char A){
    switch (A){
        case 'A': 
        case 'B':
        case 'C':
        case 'D':
        case 'E':
        case 'F':
        case 'G':
        case 'H':
        case 'I': return true;
        default: System.out.println("ERR invalid coord"); return false;
    }
  }
  public static void printBoard(int[][] arr){
    System.out.println("    A B C   D E F   G H I");
    for (int i = 0; i<9; i++) { 
      if(i%3==0)
        System.out.println("  -------------------------");
      switch (i){
        case 0: System.out.print("A "); break;
        case 1: System.out.print("B "); break;
        case 2: System.out.print("C "); break;
        case 3: System.out.print("D "); break;
        case 4: System.out.print("E "); break;
        case 5: System.out.print("F "); break;
        case 6: System.out.print("G "); break;
        case 7: System.out.print("H "); break;
        case 8: System.out.print("I "); break;
      }
      for (int j = 0; j<9; j++){   
        if(j%3==0)
          System.out.print("| ");
        if (arr[i][j]==0)
          System.out.print("  ");
        else
          System.out.print(arr[i][j] + " "); 
      }
      System.out.println("|");
    } 
    System.out.println("  -------------------------");
  } 
  public static void HideVals(){
    for (int i=0;i<9;i++){
      int hideNum=(int)(Math.random()*5)+2;
      int count=0;
      while (count<hideNum){
        int randpos=(int)(Math.random()*9);
        while (box[i][randpos]==0)
          randpos=(int)(Math.random()*9);
        box[i][randpos]=0;
        hideboard[i/3*3+randpos/3][i%3*3+randpos%3]=0;
        count++;
      }
    }
  }
  public static boolean xmp(int x, int yt){
    if ( x<8 &&  yt>=9){ 
       x += 1;
      yt = 0;
    } 
    if ( x>=9) 
      return true;
    if( yt== x/3*3){
      if ( yt==6){
         yt=0;
         x++;
        if ( x>=9)
          return true;
      }else{
         yt= x/3*3+3;
      }
    }
    for(int z=1;z<=9;z++){
      if (isUnique(z,x,yt)) { 
        board[x][yt] = z; 
        test[x][yt]=z;
        y[yt][x]=z;
        setBox(x,yt,z);
        if (xmp(x, yt+1)) 
          return true; 
        board[x][yt] = 0;
        test[x][yt]=0;
        y[yt][x]=0;
        setBox(x,yt,0);
      }
    }
    return false;
  }
  public static boolean isUnique(int random,int  xt, int  yt){
    for (int i=0;i<board[xt].length;i++){
      if (random==board[xt][i])
        return false;
    }
    for (int i=0;i< y[yt].length;i++){
      if(random== y[yt][i])
        return false;
    }
    for (int i=0;i<box[ xt/3*3+ yt/3].length;i++){
      if(random==box[ xt/3*3+ yt/3][i])
        return false;
    }
    return true;
  }
  public static void setBox(int i, int n,int rand){
      box[i/3*3+n/3][(i%3)*3+n%3]=rand;
  }
}