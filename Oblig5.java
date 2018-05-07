class Oblig5 {
  public int n, k;
  public int[] x, y;
  private NPunkter17 np;
  public int MAX_X, MAX_Y;
  private IntList sResult, pResult;
  private boolean tegn;

  public Oblig5(int n, boolean tegn){
    this.n = n;
    this.tegn = tegn;
    x = new int[n];
    y = new int[n];
  }



  public static void main(String[] args) {
    int n;
    boolean tegn;

    if(args.length == 0){
      System.out.println("Please enter number of points to hull.");
      return;
    }else if(args.length == 1){
      n = Integer.parseInt(args[0]);
      tegn = false;
    }else{
      n = Integer.parseInt(args[0]);
      tegn = true;
    }

    Oblig5 o5 = new Oblig5(n, tegn);
    o5.start();
  }




  public void start(){
    makePoints();
    seq();

    if(tegn){
      System.out.println("Result seq:");
      for(int i = 0; i < sResult.size(); i++){
        System.out.println(sResult.get(i));
      }
      TegnUt t = new TegnUt(this, sResult);
    }
  }

  public void makePoints(){
    np = new NPunkter17(n);
    np.fyllArrayer(x, y);
  }


  public void seq(){
    SeqHull s = new SeqHull(n, x, y);
    s.start();
    MAX_X = s.getMaxX();
    MAX_Y = s.getMaxY();
    sResult = s.getResult();

  }
}
