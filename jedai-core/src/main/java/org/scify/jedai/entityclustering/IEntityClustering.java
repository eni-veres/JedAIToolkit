/*
* Copyright [2016-2018] [George Papadakis (gpapadis@yahoo.gr)]
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

package org.scify.jedai.entityclustering;

import org.scify.jedai.datamodel.EquivalenceCluster;
import org.scify.jedai.datamodel.SimilarityPairs;
import org.scify.jedai.utilities.IDocumentation;

/**
 *
 * @author G.A.P. II
 */

public interface IEntityClustering extends IDocumentation {
 
    public EquivalenceCluster[] getDuplicates(SimilarityPairs simPairs);
    
    public int getNumberOfGridConfigurations();
    
    public void setSimilarityThreshold(double th);
    
    public void setNextRandomConfiguration();
    
    public void setNumberedGridConfiguration(int iterationNumber);
    
    public void setNumberedRandomConfiguration(int iterationNumber);
}