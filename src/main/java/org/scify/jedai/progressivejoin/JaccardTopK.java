/*
 * Copyright [2016-2020] [George Papadakis (gpapadis@yahoo.gr)]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.scify.jedai.progressivejoin;

/**
 *
 * @author mthanos
 */
public class JaccardTopK {

    public int k;

    public JaccardTopK(int k) {
        this.k = k;
    }

    public int minoverlap(int len1, int len2, double thres) {
        return (int) Math.ceil(minoverlap_orig(len1, len2, thres));
    }

    public double minoverlap_orig(int len1, int len2, double thres) {
        return thres / (1 + thres) * (len1 + len2);
    }

    public int minsize(int len1, double thres) {
        return (int) Math.ceil(minsize_orig(len1, thres));
    }

    public double minsize_orig(int len1, double thres) {
        return thres * len1;
    }

    public int maxsize(int len2, double thres) {
        return (int) Math.floor(maxsize_orig(len2, thres));
    }

    public double maxsize_orig(int len2, double thres) {
        return (thres == 0) ? Double.POSITIVE_INFINITY : len2 / thres;
    }

    public double minprefix(int len1, int len2, double thres) {
        return len1 - minoverlap(len1, len2, thres) + 1;
    }

    public double midprefix(int len1, double thres) {
        return len1 - minoverlap(len1, len1, thres) + 1;
    }

    public double maxprefix(int len1, double thres) {
        return len1 - minsize(len1, thres) + 1;
    }

    public double computesim(int len1, int len2, int overlap) {
        return overlap / (0.0 + len1 + len2 - overlap);
    }

    public double upperbound_access(int len1, int len2, int pos1, int pos2) {
        double probe = upperbound_probe(len1, pos1);
        double ind = upperbound_index(len2, pos2);
        return probe * ind / (probe + ind - probe * ind);
    }

    public double upperbound_access_internal(int probe, int ind) {
        return (double) probe * ind / (probe + ind - probe * ind);
    }

    public double upperbound_index(int len1, int pos) {
        int removerlap = len1 - pos;
        return computesim(len1, len1, removerlap);
    }

    public double upperbound_probe(int len1, int pos) {
        int removerlap = len1 - pos;
        return computesim(len1, removerlap, removerlap);
    }
}
