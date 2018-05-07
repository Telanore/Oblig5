import java.util.concurrent.locks.ReentrantLock;

class ParHull{

  private final int n, k;
  private final int[] x, y;
  private IntList result;
  private int maxIndex, minIndex, maxYIndex;
  private int[] maxXs, minXs, maxYs;


  public ParHull(int n, int[] x, int[] y, int k){
    this.n = n;
    this.x = x;
    this.y = y;
    result = new IntList();
    this.k = k;
  }


  public void start(){
    makeHullThreads();
    startHulling();

  }

  /*
  Creates threads that search n/k numbers of the xy arrays for the largest values
  */
  // public void minMax(){
  //   Thread t;
  //   maxXs = new int[k];
  //   minXs = new int[k];
  //   maxYs = new int[k];
  //
  //   int start = 0, end = n/k;
  //
  //   for(int i = 0; i < k-1; i++){
  //     t = new Thread(new MinMaxWorker(start, end, i));
  //     t.start();
  //
  //     start = end;
  //     end += n/k;
  //   }
  //   t = new Thread(new MinMaxWorker(start, n, k-1));
  //   t.start();
  //
  //   for(int i = 0; i < k; i++){
  //     if(maxXs[i] > maxIndex){
  //       maxIndex = maxXs[i];
  //     }else if(minXs[i] < minIndex){
  //       minIndex = minXs[i];
  //     }else if(maxYs[i] > maxYIndex){
  //       maxYIndex = maxYs[i];
  //     }
  //   }
  // }


  public void startHulling(){
    makeHullThreads();

  }


  public void makeHullThreads(){
    int start = 0, end = n/k;
    Thread t;

    for(int i = 0; i < k-1; i++){
      t = new Thread(new HullWorker(start, end, i));
      t.start();

      start = end;
      end += n/k;
    }
    t = new Thread(new HullWorker(start, n, k-1));
    t.start();
  }



  private class HullWorker implements Runnable{

    private final int start, end, id;
    private int localMaxIndex, localMinIndex, localMaxYIndex;
    private int[] maxXs, minXs, maxYs;
    private IntList localRes;

    public HullWorker(int start, int end, int id){
      this.start = start;
      this.end = end;
      this.id = id;
      localRes = new IntList(n/k);
    }


    public void run(){
      makeMinMaxThreads();
      System.out.println("ID: " +id+ "\nStart: " +start+ " End: " +end+ " LocalMax: " +localMaxIndex+ " LocalMin: " +localMinIndex);
    }


    public void makeMinMaxThreads(){
      Thread t;
      maxXs = new int[k];
      minXs = new int[k];
      maxYs = new int[k];

      int s = 0, e = end/k;

      for(int i = 0; i < k-1; i++){
        t = new Thread(new MinMaxWorker(s, e, i));
        t.start();

        s = end;
        e += n/k;
      }
      t = new Thread(new MinMaxWorker(s, end, k-1));
      t.start();

      for(int i = 0; i < k; i++){
        if(maxXs[i] > maxIndex){
          localMaxIndex = maxXs[i];
        }else if(minXs[i] < minIndex){
          localMinIndex = minXs[i];
        }else if(maxYs[i] > maxYIndex){
          localMaxYIndex = maxYs[i];
        }
      }
    }

    private class MinMaxWorker implements Runnable{

      private final int start, end, id;
      private int localMaxX = 0, localMinX = 0, localMaxY = 0;

      public MinMaxWorker(int start, int end, int id){
        this.start = start;
        this.end = end;
        this.id = id;
      }


      public void run(){
        for(int i = start; i < end; i++){
          if(x[i] > localMaxX){
            localMaxX = x[i];
          }else if(x[i] < localMinX){
            localMinX = x[i];
          }
          if(y[i] > localMaxY){
            localMaxY = y[i];
          }
        }
        maxXs[id] = localMaxX;
        minXs[id] = localMinX;
        maxYs[id] = localMaxY;
      }

    }

  }


  public IntList getResult(){
    return result;
  }


}
