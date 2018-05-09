


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
    recursiveHull(maxIndex, minIndex, 0, pos);

    result.add(minIndex);

    recursiveHull(minIndex, maxIndex, 0, neg);

  }


  public void recursiveHull(int max, int min, int minDist, IntList pos){


    IntList localPos = new IntList(pos.size()/2);
    int dist, maxDist = 0, maxDistIndex = -1, closest = -1, closestDist = Integer.MAX_VALUE;
    int[] res = calculateLine(max, min);

    System.out.println("\nMax: " +max+ " Min: " +min);

    for(int i = 0; i < pos.size(); i++){
      if(max != pos.get(i)){
        if(min != pos.get(i)){
          dist = calculateDistance(res, x[pos.get(i)], y[pos.get(i)]);
          if(dist > 0){
            localPos.add(pos.get(i));
            if(dist > maxDist){
              maxDist = dist;
              maxDistIndex = pos.get(i);
            }
          }else if(dist == 0){
          //  localPos.add(pos.get(i));
            dist = distanceBetweenPoints(max, pos.get(i));
            if(dist < closestDist && dist > minDist){
              System.out.println("Closest: " +pos.get(i));
              closestDist = dist;
              closest = pos.get(i);
            }
          }
        }
      }
    }

    if(closest >= 0){
      System.out.println("closest >= 0");
      minDist = distanceBetweenPoints(max, closest);
      result.add(closest);
      recursiveHull(max, min, minDist, pos);


    }else if(maxDistIndex >= 0){
      System.out.println("MaxDistIndex = " +maxDistIndex);
      recursiveHull(max, maxDistIndex, minDist, localPos);
      result.add(maxDistIndex);
      recursiveHull(maxDistIndex, min, 0, localPos);
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


  public int distanceBetweenPoints(int max, int candidate){
    //distance = sqrt(((x[2] - x[1])^2) - ((y[2] - y[1])^2))
    int dist = ((x[candidate] - x[max])*(x[candidate] - x[max])) + ((y[candidate]-y[max])*(y[candidate]-y[max]));
    return dist;
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
