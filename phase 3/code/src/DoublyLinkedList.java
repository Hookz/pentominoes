public class DoublyLinkedList<T> {

    public Node<T> first = null;
    public Node<T> last = null;

    static class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> prev;

        public Node(T data) {
            this.data = data;
        }

        public Node<T> get() {
            return this;
        }

        public Node<T> set(Node<T> node) {
            return node;
        }

        public Node<T> next() {
            return next;
        }

        //add a setter
        public  void setNext(Node<T> node) {
            next = node;
        }
        public Node<T> previous() {
            return prev;
        }

        //add a setter
        public  void setPrevious(Node<T> node) {
            prev = node;
        }

        public void displayNode() {
            System.out.print(data + " ");
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    public void addFirst(T data) {
        Node<T> newNode = new Node<T>(data);

        if (isEmpty()) {
            newNode.next = null;
            newNode.prev = null;
            first = newNode;
            last = newNode;

        } else {
            first.prev = newNode;
            newNode.next = first;
            newNode.prev = null;
            first = newNode;
        }
    }

    public Node<T> getAt(int index) {
        Node<T> current = first;
        int i = 1;
        while (i < index) {
            current = current.next;
            i++;
        }
        return current;
    }

    public boolean isEmpty() {
        return (first == null);
    }

    public void displayList() {
        Node<T> current = first;
        while (current != null) {
            current.displayNode();
            current = current.next;
        }
        System.out.println();
    }

    public void removeFirst() {
        if (!isEmpty()) {
            Node<T> temp = first;

            if (first.next == null) {
                first = null;
                last = null;
            } else {
                first = first.next;
                first.prev = null;
            }
            System.out.println(temp.toString() + " is popped from the list");
        }
    }

    public void removeLast() {
        Node<T> temp = last;

        if (!isEmpty()) {

            if (first.next == null) {
                first = null;
                last = null;
            } else {
                last = last.prev;
                last.next = null;
            }
        }
        System.out.println(temp.toString() + " is popped from the list");
    }
}
