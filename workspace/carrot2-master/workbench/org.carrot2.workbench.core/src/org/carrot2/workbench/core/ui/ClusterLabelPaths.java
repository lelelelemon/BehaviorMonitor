
/*
 * Carrot2 project.
 *
 * Copyright (C) 2002-2015, Dawid Weiss, Stanisław Osiński.
 * All rights reserved.
 *
 * Refer to the full license file "carrot2.LICENSE"
 * in the root folder of the repository checkout or at:
 * http://www.carrot2.org/carrot2.LICENSE
 */

package org.carrot2.workbench.core.ui;

import java.util.ArrayList;
import java.util.List;

import org.carrot2.core.Cluster;

import com.carrotsearch.hppc.BitSet;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;

public class ClusterLabelPaths
{
    private final List<List<String>> paths = Lists.newArrayList(); 

    /**
     * Build label paths from a processing result and a list of clusters.
     */
    public static ClusterLabelPaths from(List<Cluster> topClusters, List<Cluster> clusters)
    {
        BitSet selected = new BitSet();
        for (Cluster c : clusters)
            selected.set(c.getId());
        
        ClusterLabelPaths clp = new ClusterLabelPaths();
        descend(topClusters, new ArrayList<String>(), selected, clp.paths);
        return clp;
    }

    /** Recursive descend on the list of clusters. */
    private static void descend(List<Cluster> clusters, ArrayList<String> path, 
        BitSet selected, List<List<String>> paths)
    {
        for (Cluster c : clusters)
        {
            path.add(c.getLabel());
            if (selected.get(c.getId()))
            {
                paths.add(Lists.newArrayList(path));
            }
            descend(c.getSubclusters(), path, selected, paths);
            path.remove(path.size() - 1);
        }
    }

    /**
     * Filter matching paths from a hierarchical set of clusters.
     */
    public List<Cluster> filterMatching(List<Cluster> topClusters)
    {
        List<Cluster> result = Lists.newArrayList();
        for (List<String> path : paths) 
        {
            follow(0, path, topClusters, result);
        }
        return result;
    }

    private void follow(int index, List<String> path, List<Cluster> clusters, List<Cluster> result)
    {
        String label = path.get(index);
        for (Cluster c : clusters)
        {
            if (Objects.equal(label, c.getLabel()))
            {
                if (index + 1 == path.size())
                    result.add(c);
                else
                    follow(index + 1, path, c.getSubclusters(), result);
            }
        }
    }
}
