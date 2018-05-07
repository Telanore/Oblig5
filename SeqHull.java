


class SeqHull{

  private final int[] x, y;
  private final int n;
  private int minIndex, maxIndex, maxY;
  private IntList result;

  //Set<Integer> result = new LinkedHashSet<Integer>();

  public SeqHull(int n, int[] x, int[]y){
    this.n = n;
    this.x = x;
    this.y = y;
    result = new IntList();
  }


  public void start(){
    minMax();
    result.add(maxIndex);
    startHulling();
  }


  public void minMax(){

    for(int i = 0; i < n; i++){
      if(x[i] < x[minIndex]){
        minIndex = i;
      }else if(x[i] > x[maxIndex]){
        maxIndex = i;
      }
    }

    maxY = 0;
    for(int i = 0; i < n; i++){
      if(y[i] > maxY){
        maxY = y[i];
      }
    }

  }


  public void startHulling(){
    int[] res = calculateLine(maxIndex, minIndex);
    IntList neg = new IntList(n/2);
    IntList pos = new IntList(n/2);

    int dist, maxDist = 0, minDist = 0, maxDistIndex = -1, minDistIndex = 0;


    for(int i = 0; i < n; i++){
      dist = calculateDistance(res, x[i], y[i]);

      if(dist > 0){
        pos.add(i);
      }else if(dist < 0){
        neg.add(i);
      }
    }
    recursiveHull(maxIndex, minIndex, pos);

    result.add(minIndex);

    recursiveHull(minIndex, maxIndex, neg);

  }


  public void recursiveHull(int max, int min, IntList pos){

    IntList localPos = new IntList(pos.size()/2);
    int dist, maxDist = 0, maxDistIndex = -1;
    int[] res = calculateLine(max, min);

    System.out.printf("Max: %d Min: %d\n", max, min);

    for(int i = 0; i < pos.size(); i++){
      if(pos.get(i) != max || pos.get(i) != min){
        dist = calculateDistance(res, x[pos.get(i)], y[pos.get(i)]);
        if(dist > 0){
          localPos.add(pos.get(i));
          if(dist > maxDist){
            maxDist = dist;
            maxDistIndex = pos.get(i);
          }
        }
      }
    }

    if(maxDistIndex >= 0){
      recursiveHull(max, maxDistIndex, localPos);
      result.add(maxDistIndex);
      recursiveHull(maxDistIndex, min, localPos);
    }


  }


  public int[] calculateLine(int index1, int index2){
    int[] res = new int[3];

    res[0] = y[index1] - y[index2]; //a
    res[1] = x[index2] - x[index1]; //b
    res[2] = (y[index2] * x[index1]) - (y[index1] * x[index2]); //c

    return res;

  }

  public int calculateDistance(int[] res, int xCoord, int yCoord){
    int distance = (res[0] * xCoord) + (res[1] * yCoord) + res[2];
    return distance;
  }


  public int getMaxX(){
    return x[maxIndex];
  }

  public int getMaxY(){
    return maxY;
  }


  public IntList getResult(){
    return result;
  }


}
