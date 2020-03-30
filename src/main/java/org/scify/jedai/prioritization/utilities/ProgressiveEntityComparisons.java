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
package org.scify.jedai.prioritization.utilities;

import gnu.trove.iterator.TIntIterator;
import java.util.ArrayList;
import java.util.List;
import org.scify.jedai.blockprocessing.comparisoncleaning.WeightedNodePruning;
import org.scify.jedai.datamodel.AbstractBlock;
import org.scify.jedai.datamodel.Comparison;
import org.scify.jedai.utilities.comparators.DecComparisonWeightComparator;
import org.scify.jedai.utilities.enumerations.WeightingScheme;

/**
 *
 * @author gap2
 */
public class ProgressiveEntityComparisons extends WeightedNodePruning {

    protected boolean[] checkedEntity;

    public ProgressiveEntityComparisons(WeightingScheme wScheme) {
        super(wScheme);
    }

    @Override
    protected void setThreshold() {
    }

    @Override
    protected List<AbstractBlock> pruneEdges() {
        checkedEntity = new boolean[noOfEntities];
        for (int i = 0; i < noOfEntities; i++) {
            checkedEntity[i] = false;
        }
        return null;
    }

    public List<Comparison> getSortedEntityComparisons(int entityId) {
        checkedEntity[entityId] = true;
        final List<Comparison> entityComparisons = new ArrayList<>();
        if (weightingScheme.equals(WeightingScheme.ARCS)) {
            processArcsEntity(entityId);
        } else {
            processEntity(entityId);
        }

        for (TIntIterator entityIterator = validEntities.iterator(); entityIterator.hasNext();) {
            int neighborId = entityIterator.next();
            if (!checkedEntity[neighborId]) {
                double weight = getWeight(entityId, neighborId);
                if (weight < 0) {
                    continue;
                }

                Comparison comparison = getComparison(entityId, neighborId);
                comparison.setUtilityMeasure(weight);
                entityComparisons.add(comparison);
            }
        }
        
        if (1 <= entityComparisons.size()) {
            entityComparisons.sort(new DecComparisonWeightComparator());
            entityComparisons.remove(0); // has already been emitted
        }
        return entityComparisons;
    }
}
