/* 
 * JBoss, Home of Professional Open Source 
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved. 
 * See the copyright.txt in the distribution for a 
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use, 
 * modify, copy, or redistribute it subject to the terms and conditions 
 * of the GNU Lesser General Public License, v. 2.1. 
 * This program is distributed in the hope that it will be useful, but WITHOUT A 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details. 
 * You should have received a copy of the GNU Lesser General Public License, 
 * v.2.1 along with this distribution; if not, write to the Free Software 
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
 * MA  02110-1301, USA.
 */
package org.switchyard.internal.io.graph;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * A Graph representing an array, internalized as an array of Graphs.
 *
 * @param <T> the array composite Class type
 *
 * @author David Ward &lt;<a href="mailto:dward@jboss.org">dward@jboss.org</a>&gt; (C) 2011 Red Hat Inc.
 */
@SuppressWarnings("serial")
public class ArrayGraph<T> implements Graph<T[]> {

    private Graph<T>[] _array;

    /**
     * Gets the graph array.
     * @return the graph array
     */
    public Graph<T>[] getArray() {
        return _array;
    }

    /**
     * Sets the graph array.
     * @param array the graph array
     */
    public void setArray(Graph<T>[] array) {
        _array = array;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public void compose(T[] object, Map<Integer,Object> visited) throws IOException {
        _array = (Graph<T>[])new Graph<?>[object.length];
        for (int i=0; i < object.length; i++) {
            _array[i] = GraphBuilder.build(object[i], visited);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public T[] decompose(Map<Integer,Object> visited) throws IOException {
        T[] object = (T[])new Object[_array.length];
        for (int i=0; i < _array.length; i++) {
            object[i] = _array[i].decompose(visited);
        }
        return object;
    }

    @Override
    public String toString() {
        return "ArrayGraph(array=" + Arrays.toString(getArray()) + ")";
    }

}
