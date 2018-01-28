package com.jgg.sdp.domain.services.graph;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jgg.sdp.domain.graph.DCGEdge;
import com.jgg.sdp.domain.services.AbstractService;

@Repository
public class DCGEdgeService extends AbstractService<DCGEdge> {
	public List<DCGEdge> listEdges(Long idVersion, Long idGraph) {
		return list("listEdges", idVersion, idGraph);
	}

}
