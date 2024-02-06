import java.util.NoSuchElementException;
import java.lang.IllegalArgumentException;
import java.util.Scanner;

public class MemLinkedList
{
    private Node head;
    private Node tail;
    
    public MemLinkedList()
    {
        head = tail = new Node('F',1000,null,null);
    }
    
    public void request(int size) 
    {
        //if(size <= 0)
        //    throw new IllegalArgumentException("Size must be a non-negative integer.");
            
        if (head == tail) 
        {
            if (head.memory >= size)
            {
                Node temp = new Node('A',size,head,null);
                head.previous = temp;
                head.memory -= size;
                head = temp;
            }
            else
                System.out.println("Not enough free memory.");
        }
        else 
        {
            Node temp = head;
            boolean found = false;
            
            while (temp != null && found == false)
            {
                if (temp.flag == 'F' && temp.memory >= size)
                {
                    Node add = new Node('A',size,temp,temp.previous);
                    if(temp != head)
                        temp.previous.next = add;
                    temp.previous = add;
                    temp.memory -= size;
                    if(temp.memory == 0)
                    {
                        temp.previous.next = null;
                        if(temp == tail)
                            tail = add;
                    }
                    if(temp == head)
                        head = add;
                    found = true;
                }
                temp = temp.next;
            }

            if (found == false)
                System.out.println("Not enough free memory.");
        }
    }
    
    public void release(int size)
    {
        //if(size <= 0)
        //    throw new IllegalArgumentException("Size must be a non-negative integer.");
        
        Node temp = head;
        boolean found = false;
        
        while (temp != null && found == false)
        {
            if (temp.flag == 'A' && temp.memory == size)
            {
                temp.flag = 'F';
                found = true;
                
                if (temp.next != null && temp.next.flag == 'F')
                {
                    temp.memory += temp.next.memory;
                    if(temp.next != tail)
                    {
                        temp.next = temp.next.next;
                        temp.next.previous = temp;
                    }
                    else
                    {
                        temp.next = null;
                        tail = temp;
                    }
                }
                
                if (temp.previous != null && temp.previous.flag == 'F')
                {
                    temp.memory += temp.previous.memory;
                    if(temp.previous != head)
                    {
                        temp.previous = temp.previous.previous;
                        temp.previous.next = temp;
                    }
                    else
                    {
                        temp.previous = null;
                        head = temp;
                    }
                }
            }
            temp = temp.next;
        }
        
        if (found == false)
            System.out.println("No allocated memory of " + size + " KB to release.");
    }
    
    public void clear()
    {
        head = tail = new Node('F',1000,null,null);
    }
    
    public String print()
    {
        if(head == tail)
            return "[F | 1 MB]";
        
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        
        Node temp = head;

        while (temp.next != null)
        {
            sb.append(temp.flag + " | " + temp.memory + " KB <--> ");
            temp = temp.next;
        }
        
        sb.append(temp.flag + " | " + temp.memory + " KB");
        sb.append("]");
        return new String(sb);
    }
    
    public String reverseToString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        
        Node temp = tail;

        while (temp.previous != null)
        {
            sb.append(temp.flag + " | " + temp.memory + " KB <-- ");
            temp = temp.previous;
        }
        

        sb.append(temp.flag + " | " + temp.memory + " KB");
        sb.append(" ]");
        return new String(sb);
    }
    
    private static class Node 
    {
        char flag;
        int memory;
        
        Node next;
        Node previous;
        
        Node (char f, int m, Node n, Node p)
        {
            flag = f;
            memory = m;
            next = n;
            previous = p;
        }
    }

    
    public static void main(String[] args) {
        //Constructor
        MemLinkedList fopsList = new MemLinkedList();
        System.out.println("Initial List:\n" + fopsList.print());
        System.out.println();
        Scanner scan = new Scanner(System.in);
        int op;
        boolean done = false;
        
        while (!done)
        {
            
            System.out.println("Press 1 to request memory, 2 to release memory, 3 to release all memory, 4 to display, or 5 to quit.");
            while(true)
            {
                if(!scan.hasNextInt())
                {
                    scan.next();
                    System.out.println("Please enter a number between 1-5.");
                }
                else
                {
                    op = scan.nextInt();
                    if(op < 1 || op > 5)
                        System.out.println("Please enter a number between 1-5.");
                    else
                        break;
                }
            }
            
            int mem;
            
            switch(op)
            {
                case 1:
                {
                    System.out.println("How much memory would you like to request? Please enter a non-negative integer.");
                    while(true)
                    {
                        if(!scan.hasNextInt())
                        {
                            scan.next();
                            System.out.println("Please enter a non-negative integer.");
                        }
                        else
                        {
                            mem = scan.nextInt();
                            if (mem < 1)
                                System.out.println("Please enter a non-negative integer.");
                            else
                                break;
                        }
                    }
                    fopsList.request(mem);
                    break;
                }
                case 2:
                {
                    System.out.println("How much memory would you like to release? Please enter a non-negative integer.");
                    while(true)
                    {
                        if(!scan.hasNextInt())
                        {
                            scan.next();
                            System.out.println("Please enter a non-negative integer.");
                        }
                        else
                        {
                            mem = scan.nextInt();
                            if (mem < 1)
                                System.out.println("Please enter a non-negative integer.");
                            else
                                break;
                        }
                    }
                    fopsList.release(mem);
                    break;
                }
                case 3:
                {
                    System.out.println("Releasing memory...");
                    fopsList.clear();
                    break;
                }
                case 4:
                {
                    System.out.println("Displaying...");
                    fopsList.print();
                    break;
                }
                default:
                    done = true;
                    break;
            }
            
            System.out.println(fopsList.print());
        }
		
	}
}




