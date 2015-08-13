/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package my.timu;


import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import util.sort.BleedSort;
import util.sort.QuickSort;

@State(Scope.Thread) @OutputTimeUnit(TimeUnit.MILLISECONDS)
public class MyBenchmark {


    private int[] 
            _1e4U = new int[(int)1e4],
            _1e5U = new int[(int)1e5],
            _1e6U = new int[(int)1e6],
            _1e7U = new int[(int)1e7]
            ;
    
    public int[] U(int[] x)
    {
        for(int i = 0; i < x.length; i++)
            x[i] = (int) (Math.random() * x.length);
        return x;
    }

    public int[] Iwf(int[] x, double fractionToSwap)
    {
        for(int i = 0; i < x.length; i++)
            x[i] = i;
        for(int i = 0; i < (int) (x.length * fractionToSwap); i++)
        {
            int 
                    y = (int) (Math.random() * x.length),
                    y2 = (int) (Math.random() * x.length)
                    ;
            int tmp = x[y];
            x[y] = x[y2];
            x[y2] = tmp;
        }
        return x;
    }
    
//    @Benchmark @BenchmarkMode(Mode.SampleTime) public void _1e4U() { U(_1e4U); }
//    @Benchmark @BenchmarkMode(Mode.SampleTime) public void _1e5U() { U(_1e5U); }
//    @Benchmark @BenchmarkMode(Mode.SampleTime) public void _1e6U() {  U(_1e6U); }
//    @Benchmark @BenchmarkMode(Mode.SampleTime) public void _1e7U() { U(_1e7U); }
//
//    @Benchmark @BenchmarkMode(Mode.SampleTime) public void int1e4UBleedSort() { BleedSort.bleedSort(U(_1e4U)); }
//    @Benchmark @BenchmarkMode(Mode.SampleTime) public void int1e4UJavaSort() {  Arrays.sort(U(_1e4U)); }
//    @Benchmark @BenchmarkMode(Mode.SampleTime) public void int1e4UQuickSort() { QuickSort.quickSort(U(_1e4U)); }
//
//    @Benchmark @BenchmarkMode(Mode.SampleTime) public void int1e5UBleedSort() { BleedSort.bleedSort(U(_1e5U)); }
//    @Benchmark @BenchmarkMode(Mode.SampleTime) public void int1e5UJavaSort() {  Arrays.sort(U(_1e5U)); }
//    @Benchmark @BenchmarkMode(Mode.SampleTime) public void int1e5UQuickSort() { QuickSort.quickSort(U(_1e5U)); }
//
//    @Benchmark @BenchmarkMode(Mode.SampleTime) public void int1e6UBleedSort() { BleedSort.bleedSort(U(_1e6U)); }
//    @Benchmark @BenchmarkMode(Mode.SampleTime) public void int1e6UJavaSort() {  Arrays.sort(U(_1e6U)); }
//    @Benchmark @BenchmarkMode(Mode.SampleTime) public void int1e6UQuickSort() { QuickSort.quickSort(U(_1e6U)); }
//
//    @Benchmark @BenchmarkMode(Mode.SampleTime) public void int1e7UBleedSort() { BleedSort.bleedSort(U(_1e7U)); }
//    @Benchmark @BenchmarkMode(Mode.SampleTime) public void int1e7UJavaSort() {  Arrays.sort(U(_1e7U)); }
//    @Benchmark @BenchmarkMode(Mode.SampleTime) public void int1e7UQuickSort() { QuickSort.quickSort(U(_1e7U)); }

    @Benchmark @BenchmarkMode(Mode.SampleTime) public void int1e6Iw010BleedSort()   { BleedSort.bleedSort(Iwf(_1e6U, 0.1)); }
    @Benchmark @BenchmarkMode(Mode.SampleTime) public void int1e6Iw010JavaSort()    {  Arrays.sort(Iwf(_1e6U, 0.1)); }
    @Benchmark @BenchmarkMode(Mode.SampleTime) public void int1e6Iw010QuickSort()   { QuickSort.quickSort(Iwf(_1e6U, 0.1)); }
    
/*

Benchmark                       Mode    Cnt  Score    Error  Units  Corrected
MyBenchmark._1e4U             sample 831058  0.241 ±  0.001  ms/op
MyBenchmark._1e5U             sample  84898  0.002 ±  0.001   s/op
MyBenchmark._1e6U             sample   8512  0.024 ±  0.001   s/op
MyBenchmark._1e7U             sample    985  0.236 ±  0.001   s/op
MyBenchmark.int1e4UBleedSort  sample 216492  0.924 ±  0.001  ms/op  0.683 ±  0.002
MyBenchmark.int1e4UJavaSort   sample 253489  0.789 ±  0.001  ms/op  0.548 ±  0.002
MyBenchmark.int1e4UQuickSort  sample 217394  0.920 ±  0.001  ms/op  0.679 ±  0.002
MyBenchmark.int1e5UBleedSort  sample  18752  0.011 ±  0.001   s/op  0.009 ±  0.002
MyBenchmark.int1e5UJavaSort   sample  22335  0.009 ±  0.001   s/op  0.007 ±  0.002
MyBenchmark.int1e5UQuickSort  sample  18748  0.011 ±  0.001   s/op  0.009 ±  0.002
MyBenchmark.int1e6UBleedSort  sample   2410  0.087 ±  0.001   s/op  0.063 ±  0.002
MyBenchmark.int1e6UJavaSort   sample   1978  0.105 ±  0.001   s/op  0.081 ±  0.002
MyBenchmark.int1e6UQuickSort  sample   1641  0.131 ±  0.001   s/op  0.107 ±  0.002
MyBenchmark.int1e7UBleedSort  sample    373  0.938 ±  0.009   s/op  0.914 ±  0.010
MyBenchmark.int1e7UJavaSort   sample    200  1.144 ±  0.009   s/op  1.120 ±  0.010
MyBenchmark.int1e7UQuickSort  sample    200  1.483 ±  0.014   s/op  1.459 ±  0.015

*/
}
