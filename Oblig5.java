class Oblig5 {
  public int n, k, j;
  public int[] x, y;
  private NPunkter17 np;
  public int MAX_X, MAX_Y;
  private IntList sResult, pResult;
  private boolean tegn;
  private double[] seqTimes, parTimes;
  private long t;


  public Oblig5(int n, boolean tegn){
    this.n = n;
    this.tegn = tegn;
    x = new int[n];
    y = new int[n];
    k = Runtime.getRuntime().availableProcessors();
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
    j = 1;
    makePoints();
    seq();
    if(tegn){
      System.out.println("Result seq:");
      for(int i = 0; i < sResult.size(); i++){
        System.out.println(sResult.get(i));
      }
      System.out.println();
      TegnUt tu = new TegnUt(this, sResult);
    }

    par();
  }

  public void makePoints(){
    np = new NPunkter17(n);
    np.fyllArrayer(x, y);
  }


  public void seq(){
    SeqHull s;
    seqTimes = new double[j];

    for(int i = 0; i < j; i++){
      s = new SeqHull(n, x, y);
      t = System.nanoTime();
      s.start();
      seqTimes[i] = (System.nanoTime() / 1000000.0) - t;
      MAX_X = s.getMaxX();
      MAX_Y = s.getMaxY();
      sResult = s.getResult();

    }

  }



  public void par(){
    ParHull p;
    parTimes = new double[j];
    for(int i = 0; i < j; i++){
      p = new ParHull(n, x, y, k);
      t = System.nanoTime();
      p.start();
      parTimes[i] = (System.nanoTime() / 1000000.0) - t;
      pResult = p.getResult();
    }

  }

}
