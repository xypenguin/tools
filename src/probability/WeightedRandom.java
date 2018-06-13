package probability;

import java.util.HashMap;
import java.util.Map;

/**
 * 重み付きランダム
 * @author xypenguin
 */
public class WeightedRandom<E> {

  private HashMap<E, Number> hashMap;

  public WeightedRandom() {
    hashMap = new HashMap<>();
  }

  /** 要素とその要素に対する重みを追加 */
  public WeightedRandom<E> add(E e, Number weight) {
    hashMap.put(e, weight);
    return this;
  }

  public E getRandom() {
    // 当選番号
    double val = Math.random();
    // 重みの合計
    int sum = hashMap.values()
        .stream()
        .mapToInt(Number::intValue)
        .sum();
    // 全体から順番に重みを引いていく
    for (Map.Entry<E, Number> entry : hashMap.entrySet()) {
      Number number = entry.getValue();
      // 重み全体を1とする
      val -= number.doubleValue() / sum;
      // 当選番号を0未満にした重みが当選
      if (val < 0) {
        return entry.getKey();
      }
    }
    return null; // 到達しない。
  }

  public static void main(String[] args) {
    WeightedRandom<String> wr = new WeightedRandom<>();
    wr.add("あ", 20);
    wr.add("い", 30);
    wr.add("う", 50);
    int[] arr = new int[3];
    for (int i = 0; i < 10000000; i++) {
      switch (wr.getRandom()) {
      case "あ":
        arr[0] += 1;
        break;
      case "い":
        arr[1] += 1;
        break;
      case "う":
        arr[2] += 1;
        break;
      default:
        break;
      }
    }
    System.out.println("あ = " + arr[0]);
    System.out.println("い = " + arr[1]);
    System.out.println("う = " + arr[2]);
  }

}
