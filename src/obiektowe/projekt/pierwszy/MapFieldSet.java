//package obiektowe.projekt.pierwszy;
//
//import java.util.*;
//import java.util.stream.Stream;
//
//class MapFieldSet extends AbstractSet<Vector2d> implements Set<Vector2d> {
//    private final List<Vector2d> list = new ArrayList<>();
//    private final java.util.Map<Vector2d, Integer> indexMap = new HashMap<>();
//
//    @Override
//    public boolean add(Vector2d e) {
//        if (contains(e)) return false;
//        indexMap.put(e, list.size());
//        list.add(e);
//        return true;
//    }
//
//    @Override
//    public boolean remove(Object o) {
//        Integer indexBoxed = indexMap.remove(o);
//        if (indexBoxed == null) return false;
//        int index = indexBoxed;
//        int last = list.size() - 1;
//        Vector2d element = list.remove(last);
//        if (index != last) {
//            indexMap.put(element, index);
//            list.set(index, element);
//        }
//        return true;
//    }
//
//    public Vector2d removeRandom() {
//        return removeRandom(MapArea.MAP);
//    }
//
//    public Vector2d removeRandom(MapArea area) {
//        Stream<Vector2d> mapFieldStream = list.stream();
//        if (area == MapArea.JUNGLE) {
//            mapFieldStream = mapFieldStream.filter(Vector2d::inJungle);
//        }
//        if (area == MapArea.SAVANNA) {
//            mapFieldStream = mapFieldStream.filter(x -> !x.inJungle());
//        }
//
//        Random random = new Random();
//        Vector2d element = mapFieldStream.skip(random.nextInt((int) (mapFieldStream.count() - 1))).findFirst().get();
//        remove(element);
//        return element;
//    }
//
//    @Override
//    public boolean contains(Object o) {
//        return indexMap.containsKey(o);
//    }
//
//    @Override
//    public Iterator<Vector2d> iterator() {
//        return list.iterator();
//    }
//
//    @Override
//    public int size() {
//        return list.size();
//    }
//}