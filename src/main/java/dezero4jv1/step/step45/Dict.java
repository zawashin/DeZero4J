package dezero4jv1.step.step45;


import java.nio.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 * <a href="https://stackoverflow.com/questions/1786206/is-there-a-java-equivalent-of-pythons-defaultdict">...</a>
 */
public class Dict<K, V> implements Cloneable {

    HashMap<K, V> valueMap;
    HashMap<Buffer, V> bufferValueMap; //別に用意しないとGeneticsが使えない
    ArrayList<K> keyList;
    ArrayList<Buffer> bufferKeyList;
    boolean array = false;

    public Dict() {
        valueMap = new HashMap<>();
        bufferValueMap = new HashMap<>();
        keyList = new ArrayList<>();
        bufferKeyList = new ArrayList<>();
    }

    public static void main(String[] args) {
        Dict<int[], double[]> dict = new Dict<>();
        int width = 4;
        int height = 3;
        int numActions = 4;
        Random rand = new Random(System.currentTimeMillis());
        double[] values = new double[numActions];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int[] state = new int[]{i, j};
                for (int a = 0; a < numActions; a++) {
                    values[a] = rand.nextDouble();
                }
                dict.put(state, values);
            }
        }
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                int[] state = new int[]{j, i};
                System.out.println(Arrays.toString(state) + Arrays.toString(dict.get(state)));
            }
        }
        System.out.println();

        Dict dict2 = dict.clone();
        System.out.println(dict.equals(dict2));
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                int[] state = new int[]{j, i};
                values = (double[]) dict2.get(state);
                System.out.println(Arrays.toString(state) + Arrays.toString(values));
            }
        }
    }

    public V get(K key) {
        /*
        if(!bufferValueMap.containsKey(key)) {
            return defaultValue;
        }

         */
        if (key.getClass().isArray()) {
            if (key instanceof int[]) {
                return bufferValueMap.get(IntBuffer.wrap((int[]) key));
            } else if (key instanceof double[]) {
                return bufferValueMap.get(DoubleBuffer.wrap((double[]) key));
            } else if (key instanceof float[]) {
                return bufferValueMap.get(FloatBuffer.wrap((float[]) key));
            } else if (key instanceof short[]) {
                return bufferValueMap.get(ShortBuffer.wrap((short[]) key));
            } else if (key instanceof byte[]) {
                return bufferValueMap.get(ByteBuffer.wrap((byte[]) key));
            } else if (key instanceof char[]) {
                return bufferValueMap.get(CharBuffer.wrap((char[]) key));
            } else if (key instanceof long[]) {
                return bufferValueMap.get(LongBuffer.wrap((long[]) key));
            } else {
                return valueMap.get(key);
            }
        } else {
            // キーが配列でない場合
            return valueMap.get(key);
        }
    }

    public void put(K key, V value) {
        if (key.getClass().isArray()) {
            if (key instanceof int[]) {
                bufferValueMap.put(IntBuffer.wrap((int[]) key), value);
                bufferKeyList.add(IntBuffer.wrap((int[]) key));
            } else if (key instanceof double[]) {
                bufferValueMap.put(DoubleBuffer.wrap((double[]) key), value);
                bufferKeyList.add(DoubleBuffer.wrap((double[]) key));
            } else if (key instanceof float[]) {
                bufferValueMap.put(FloatBuffer.wrap((float[]) key), value);
                bufferKeyList.add(FloatBuffer.wrap((float[]) key));
            } else if (key instanceof short[]) {
                bufferValueMap.put(ShortBuffer.wrap((short[]) key), value);
                bufferKeyList.add(ShortBuffer.wrap((short[]) key));
            } else if (key instanceof byte[]) {
                bufferValueMap.put(ByteBuffer.wrap((byte[]) key), value);
                bufferKeyList.add(ByteBuffer.wrap((byte[]) key));
            } else if (key instanceof char[]) {
                bufferValueMap.put(CharBuffer.wrap((char[]) key), value);
                bufferKeyList.add(CharBuffer.wrap((char[]) key));
            } else if (key instanceof long[]) {
                bufferValueMap.put(LongBuffer.wrap((long[]) key), value);
                bufferKeyList.add(LongBuffer.wrap((long[]) key));
            } else {
                valueMap.put(key, value);
                keyList.add(key);
            }
        } else {
            valueMap.put(key, value);
            keyList.add(key);
        }
    }

    public Dict clone() {
        Dict dict;
        try {
            dict = (Dict) super.clone();
            dict.valueMap = (HashMap<K,V>) valueMap.clone();
            dict.bufferValueMap = (HashMap<K,V>) bufferValueMap.clone();
            dict.keyList = (ArrayList<V>) keyList.clone();
            dict.bufferKeyList = (ArrayList<V>) bufferKeyList.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return dict;
    }

    public boolean equals(Dict<int[], double[]> dict2) {

        if (keyList.size() == 0) {
            array = false;
        } else if (bufferKeyList.size() == 0) {
            array = true;
        } else {
            throw new RuntimeException("Dict is empty");
        }
        boolean equal = false;
        if (this.valueMap.size() != dict2.valueMap.size()) {
            return equal;
        }
        int size = this.valueMap.size();
        for (int i = 0; i < size; i++) {
            K key1;
            V value1;
            if (array) {
                key1 = (K) bufferKeyList.get(i);
                if (key1 instanceof int[]) {
                    value1 = bufferValueMap.get(IntBuffer.wrap((int[]) key1));
                } else if (key1 instanceof double[]) {
                    value1 = bufferValueMap.get(DoubleBuffer.wrap((double[]) key1));
                } else if (key1 instanceof float[]) {
                    value1 = bufferValueMap.get(FloatBuffer.wrap((float[]) key1));
                } else if (key1 instanceof short[]) {
                    value1 = bufferValueMap.get(ShortBuffer.wrap((short[]) key1));
                } else if (key1 instanceof byte[]) {
                    value1 = bufferValueMap.get(ByteBuffer.wrap((byte[]) key1));
                } else if (key1 instanceof char[]) {
                    value1 = bufferValueMap.get(CharBuffer.wrap((char[]) key1));
                } else if (key1 instanceof long[]) {
                    value1 = bufferValueMap.get(LongBuffer.wrap((long[]) key1));
                } else {
                    value1 = valueMap.get(key1);
                }
            } else {
                key1 = keyList.get(i);
                // キーが配列でない場合
                value1 = valueMap.get(key1);
            }
            for (int j = 0; j < size; j++) {
                V value2;
                if (array) {
                    if (key1 instanceof int[]) {
                        value2 = bufferValueMap.get(IntBuffer.wrap((int[]) key1));
                    } else if (key1 instanceof double[]) {
                        value2 = bufferValueMap.get(DoubleBuffer.wrap((double[]) key1));
                    } else if (key1 instanceof float[]) {
                        value2 = bufferValueMap.get(FloatBuffer.wrap((float[]) key1));
                    } else if (key1 instanceof short[]) {
                        value2 = bufferValueMap.get(ShortBuffer.wrap((short[]) key1));
                    } else if (key1 instanceof byte[]) {
                        value2 = bufferValueMap.get(ByteBuffer.wrap((byte[]) key1));
                    } else if (key1 instanceof char[]) {
                        value2 = bufferValueMap.get(CharBuffer.wrap((char[]) key1));
                    } else if (key1 instanceof long[]) {
                        value2 = bufferValueMap.get(LongBuffer.wrap((long[]) key1));
                    } else {
                        value2 = valueMap.get(key1);
                    }
                } else {
                    value2 = valueMap.get(key1);
                }
                if (array) {
                    if (key1 instanceof int[]) {
                        if (Arrays.equals((int[]) value1, (int[]) value2)) {
                            equal = true;
                            break;
                        }
                    } else if (key1 instanceof double[]) {
                        if (Arrays.equals((double[]) value1, (double[]) value2)) {
                            equal = true;
                            break;
                        }
                    } else if (key1 instanceof float[]) {
                        if (Arrays.equals((float[]) value1, (float[]) value2)) {
                            equal = true;
                            break;
                        }
                    } else if (key1 instanceof short[]) {
                        if (Arrays.equals((short[]) value1, (short[]) value2)) {
                            equal = true;
                            break;
                        }
                    } else if (key1 instanceof byte[]) {
                        if (Arrays.equals((byte[]) value1, (byte[]) value2)) {
                            equal = true;
                            break;
                        }
                    } else if (key1 instanceof char[]) {
                        if (Arrays.equals((char[]) value1, (char[]) value2)) {
                            equal = true;
                            break;
                        }
                    } else if (key1 instanceof long[]) {
                        if (Arrays.equals((long[]) value1, (long[]) value2)) {
                            equal = true;
                            break;
                        }
                    } else {
                        if (Arrays.equals((Object[]) value1, (Object[]) value2)) {
                            equal = true;
                            break;
                        }
                    }
                } else {
                    // キーが配列でない場合
                    if (value1.equals(value2)) {
                        equal = true;
                        break;
                    }
                }
            }
        }
        return equal;
    }
}
