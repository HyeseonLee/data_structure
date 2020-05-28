package cse2010.homework2;

import java.util.Comparator;
import java.util.Objects;

/*
 * © 2020 CSE2010 HW #2
 *
 * You can freely modify this class except the signatures of the public methods!!
 */
public class DLinkedList<T> {
    private Node<T> header;
    private Node<T> trailer;
    private int size;

    public DLinkedList() {
        header = new Node<T>(null, null, null);
        trailer = new Node<T>(null, header, null);
        header.setNext(trailer);
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0 ? true : false;
    } // size로만 empty 체크

    // Add new node n at the front
    public void addFirst(Node<T> n) {
        addAfter(header, n);
    }

    // Add new node n at the rear
    public void addLast(Node<T> n) {
        addBefore(trailer, n);
    }

    // Remove a node at the front
    public void removeFirst() {
        if (isEmpty())
            return;
        remove(header.getNext());
    }

    // Remove a node at the front
    public void removeLast() {
        if (isEmpty())
            return;
        remove(trailer.getPrev());
    }

    // Add new node n before node p
    // n을 P 앞 노드로 인서트
    public void addBefore(Node<T> p, Node<T> n) {
        Objects.requireNonNull(p, "cannot not be null");
        Objects.requireNonNull(n, "cannot not be null");

       /*
            Complete the code here
        */
       Node<T> predecessor = p.getPrev();

       n.setNext(p);
       n.setPrev(predecessor);
       predecessor.setNext(n);
       p.setPrev(n);
       size++;

    }

    // Add new node n after node p
    // n을 P 뒤 노드로 인서트
    public void addAfter(Node<T> p, Node<T> n) {
        Objects.requireNonNull(p, "cannot not be null");
        Objects.requireNonNull(n, "cannot not be null");

       /*
            Complete the code here
        */
        Node<T> successor = p.getNext();
        n.setPrev(p);
        n.setNext(successor);
        successor.setPrev(n);
        p.setNext(n);
        size++;
    }

    // Remove a node p
    public void remove(Node<T> p) {
        if (p == null || p == header || p == trailer)
            return;

       /*
            Complete the code here
        */
        Node<T> predecessor = p.getPrev();
        Node<T> successor = p.getNext();

        predecessor.setNext(successor);
        successor.setPrev(predecessor);
        p.setPrev(null);
        p.setNext(null);
        size--;
    }

    public Node<T> getFirstNode() {
        if (isEmpty())
            return null;
        else
            return header.getNext();
    }

    /**
     * Returns next node if exists.
     *
     * @param cur current node
     * @return next node if exits, null otherwise
     */
    public Node<T> getNextNode(Node<T> cur) {
        Objects.requireNonNull(cur, "cannot not be null");

        if (cur.getNext() == trailer)
            return null;
        else
            return cur.getNext();
    }

    public Node<T> getLastNode() {
        if (isEmpty())
            return null;
        else
            return trailer.getPrev();
    }

    /**
     * Returns next node if exists.
     *
     * @param cur current node
     * @return previous node if exits, null otherwise
     */
    public Node<T> getPrevNode(Node<T> cur) {
        Objects.requireNonNull(cur, "cannot not be null");

        if (cur.getPrev() == header)
            return null;
        else
            return cur.getPrev();
    }

    /**
     * Find a node with an element with the value of "n".
     *
     * @param n          value of the element to search
     * @param comparator comparator
     * @return the node which has an element "n", or null if not found
     */
    // 특정 값 갖고 있는 노드를 찾는 함수
    // 비교해야해. 근데 T라는 타입을 몰라. 그래서 두번째 파라미터를 넘겨준다(Comparator)
    // Comparator는 helper Object야. IntegerComparator, String~~
    public Node<T> find(T n, Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator, "cannot not be null");

        if (isEmpty()) return null;

        Node<T> current = getFirstNode();
        while (current != null) {
            if (comparator.compare(current.getInfo(), n) == 0) {
                break;
            }
            current = getNextNode(current);
        }
        return current;
    }
}

