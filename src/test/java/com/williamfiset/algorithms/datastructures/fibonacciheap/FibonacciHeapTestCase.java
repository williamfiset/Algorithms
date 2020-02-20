package com.williamfiset.algorithms.datastructures.fibonacciheap;

import static java.util.Collections.sort;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.nnsoft.trudeau.collections.fibonacciheap.FibonacciHeap;


//Disclaimer: Based by help of "http://langrsoft.com/jeff/2011/11/test-driving-a-heap-based-priority-queue/">Test-Driving a Heap-Based Priority Queue</a>
//Credits to the respecti owner for code  


public final class FibonacciHeapTestCase
{

    private Queue<Integer> queue;

    @Before
    public void setUp()
    {
        queue = new FibonacciHeap<Integer>();
    }

    @After
    public void tearDown()
    {
        queue = null;
    }

    @Test
    public void emptyWhenCreated()
    {
        assertThat( queue.isEmpty(), is( true ) );
        assertThat( queue.poll(), nullValue() );
    }

    @Test
    public void noLongerEmptyAfterAdd()
    {
        queue.add( 50 );

        assertThat( queue.isEmpty(), is( false ) );
    }

    @Test
    public void singletonQueueReturnsSoleItemOnPoll()
    {
        queue.add( 50 );

        assertThat( queue.poll(), is( 50 ) );
    }

    @Test
    public void isEmptyAfterSoleElementRemoved()
    {
        queue.add( 50 );
        queue.poll();

        assertThat( queue.isEmpty(), is( true ) );
    }

    @Test
    public void returnsOrderedItems()
    {
        queue.add( 100 );
        queue.add( 50 );

        assertThat( queue.poll(), is( 50 ) );
        assertThat( queue.poll(), is( 100 ) );
        assertThat( queue.isEmpty(), is( true ) );
    }

    @Test
    public void insertSingleItem()
    {
        queue.add( 50 );

        assertThat( queue.poll(), is( 50 ) );
        assertThat( queue.isEmpty(), is( true ) );
    }

    @Test
    public void insertSameValuesAndReturnsOrderedItems()
    {
        queue.add( 50 );
        queue.add( 100 );
        queue.add( 50 );

        assertThat( queue.poll(), is( 50 ) );
        assertThat( queue.poll(), is( 50 ) );
        assertThat( queue.poll(), is( 100 ) );
        assertThat( queue.isEmpty(), is( true ) );
    }

    @Test
    public void returnsOrderedItemsFromRandomInsert()
    {
        final Random r = new Random( System.currentTimeMillis() );
        final List<Integer> expected = new ArrayList<Integer>();

        for ( int i = 0; i < 1000; i++ )
        {
            Integer number = new Integer( r.nextInt( 10000 ) );
            expected.add( number );

            queue.add( number );
        }
        sort( expected );

        for ( Integer integer : expected )
        {
            Integer i = queue.poll();
            assertThat( i, is( integer ) );
        }

        assertThat( queue.isEmpty(), is( true ) );
    }

    @Test
    public void addAllAndContinsItem()
    {
        Collection<Integer> c = new ArrayList<Integer>();

        c.add( 50 );
        c.add( 100 );
        c.add( 20 );
        c.add( 21 );

        queue.addAll( c );

        assertThat( queue.isEmpty(), is( false ) );
        assertThat( queue.containsAll( c ), is( true ) );

        assertThat( queue.contains( 100 ), is( true ) );
        assertThat( queue.contains( 21 ), is( true ) );
        assertThat( queue.contains( 50 ), is( true ) );
        assertThat( queue.contains( 20 ), is( true ) );
    }

    @Test
    public void clearQueue()
    {
        final Random r = new Random( System.currentTimeMillis() );
        for ( int i = 0; i < 1000; i++ )
        {
            Integer number = new Integer( r.nextInt( 10000 ) );
            queue.add( number );
        }

        assertThat( queue.isEmpty(), is( false ) );
        queue.clear();
        assertThat( queue.isEmpty(), is( true ) );
    }

    @Test
    public void offerPeekAndElement()
    {
        queue.offer( 50 );
        queue.offer( 100 );
        queue.offer( 20 );
        queue.offer( 21 );

        assertThat( queue.isEmpty(), is( false ) );
        assertThat( queue.peek(), is( 20 ) );
        assertThat( queue.element(), is( 20 ) );
        assertThat( queue.size(), is( 4 ) );
    }

    @Test( expected = NoSuchElementException.class )
    public void elementThrowsException()
    {
        queue.element();
    }

}
