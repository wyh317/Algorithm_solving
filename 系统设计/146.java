class LRUCache {
    //哈希表的键为key，值为key对应的Node节点（包含key和value）
    private HashMap<Integer, Node> map;
    private DoubleList cache;
    private int capacity;

    public LRUCache(int capacity){
        this.capacity = capacity;
        map = new HashMap<>();
        cache = new DoubleList();
    }

    public int get(int key){
        if(!map.containsKey(key))
            return -1;
        int value = map.get(key).value;
        put(key, value);
        return value;
    }

    public void put(int key, int value){
        Node x = new Node(key, value);
        //如果结构中本来有此key，则删除掉旧的节点，并把新的插到头部，然后更新map
        if(map.containsKey(key)){
            cache.remove(map.get(key));
            cache.addFirst(x);
            map.put(key, x);
        }
        //如果结构中原来没有此key
        else{
            //如果容量已满，那么需要先删除尾部的节点，然后再将x添加到头部，同时更新map
            if(capacity == cache.size()){
                Node last = cache.removeLast();
                map.remove(last.key);
            }
            cache.addFirst(x);
            map.put(key, x);
        }
    }

    class Node{
        private int key, value;
        private Node next, pre;
        public Node(int k, int v){
            this.key = k;
            this.value = v;
        }
    }

    class DoubleList{
        private Node head, tail;
        private int size;

        //在双向链表头部添加节点x
        public void addFirst(Node x){
            //先处理向空链表中加入节点的情况
            if(head == null)
                head = tail = x;
            else {
                x.next = head;
                head.pre = x;
                head = x;
            }
            size++;
        }
        //删除链表中的x节点
        public void remove(Node x){
            if(x == head && x == tail){
                head = null;
                tail = null;
            }
            else if(x == tail){
                x.pre.next = null;
                tail = x.pre;
            }
            else if(x == head){
                x.next.pre = null;
                head = x.next;
            }
            else {
                x.pre.next = x.next;
                x.next.pre = x.pre;
            }
            size--;
        }
        //删除并返回双向链表的最后一个节点
        public Node removeLast(){
            Node node = tail;
            remove(tail);
            return node;
        }
        //返回双向链表的长度
        public int size(){
            return size;
        }
    }
}
