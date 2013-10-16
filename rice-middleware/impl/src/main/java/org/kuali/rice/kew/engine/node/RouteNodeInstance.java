/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.kew.engine.node;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.kuali.rice.kew.api.document.node.RouteNodeInstanceState;
import org.kuali.rice.kew.doctype.bo.DocumentType;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.krad.data.jpa.eclipselink.PortableSequenceGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Represents a materialized instance of a {@link RouteNode} definition on a {@link DocumentRouteHeaderValue}.  Node instances
 * are generated by the engine using the {@link RouteNode} as a prototype and connected as a 
 * Directed Acyclic Graph.
 *
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
@Entity
@Table(name="KREW_RTE_NODE_INSTN_T")
@NamedQueries({
	@NamedQuery(name="RouteNodeInstance.FindByRouteNodeInstanceId",query="select r from RouteNodeInstance r where r.routeNodeInstanceId = :routeNodeInstanceId"),
	@NamedQuery(name="RouteNodeInstance.FindActiveNodeInstances",query="select r from RouteNodeInstance r where r.documentId = :documentId and r.active = 1"),
	@NamedQuery(name="RouteNodeInstance.FindTerminalNodeInstances",query="select r from RouteNodeInstance r where r.documentId = :documentId and r.active = 0 and r.complete = 1"),
	@NamedQuery(name="RouteNodeInstance.FindInitialNodeInstances",query="select d.initialRouteNodeInstances from DocumentRouteHeaderValue d where d.documentId = :documentId"),
	@NamedQuery(name="RouteNodeInstance.FindProcessNodeInstances", query="select r from RouteNodeInstance r where r.process.routeNodeInstanceId = :processId"),
	@NamedQuery(name="RouteNodeInstance.FindRouteNodeInstances", query="select r from RouteNodeInstance r where r.documentId = :documentId")
})
public class RouteNodeInstance implements Serializable {
    
	private static final long serialVersionUID = 7183670062805580420L;
	
	@Id
	@GeneratedValue(generator="KREW_RTE_NODE_S")
    @PortableSequenceGenerator(name="KREW_RTE_NODE_S")
	@Column(name="RTE_NODE_INSTN_ID")
	private String routeNodeInstanceId;

    @Column(name="DOC_HDR_ID")
	private String documentId;

    @ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="BRCH_ID")
	private Branch branch;

    @ManyToOne
    @JoinColumn(name="RTE_NODE_ID", nullable = false)
    private RouteNode routeNode;

    @Column(name="ACTV_IND")
    private boolean active = false;

    @Column(name="CMPLT_IND")
    private boolean complete = false;

    @Column(name="INIT_IND")
    private boolean initial = true;

    @ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="PROC_RTE_NODE_INSTN_ID")
	private RouteNodeInstance process;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "KREW_RTE_NODE_INSTN_LNK_T",
            joinColumns = @JoinColumn(name = "FROM_RTE_NODE_INSTN_ID"),
            inverseJoinColumns = @JoinColumn(name = "TO_RTE_NODE_INSTN_ID"))
    private List<RouteNodeInstance> nextNodeInstances = new ArrayList<RouteNodeInstance>();
    
    @ManyToMany(mappedBy = "nextNodeInstances")
    @JoinTable(name = "KREW_RTE_NODE_INSTN_LNK_T",
            joinColumns = @JoinColumn(name = "TO_RTE_NODE_INSTN_ID", updatable = false, insertable = false),
            inverseJoinColumns = @JoinColumn(name = "FROM_RTE_NODE_INSTN_ID", updatable = false, insertable = false))
    private List<RouteNodeInstance> previousNodeInstances = new ArrayList<RouteNodeInstance>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nodeInstance", orphanRemoval = true)
    private List<NodeState> state = new ArrayList<NodeState>();
    	
    @Version
	@Column(name="VER_NBR")
	private Integer lockVerNbr;

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public boolean isComplete() {
        return complete;
    }
    public void setComplete(boolean complete) {
        this.complete = complete;
    }
    public Branch getBranch() {
        return branch;
    }
    public void setBranch(Branch branch) {
        this.branch = branch;
    }
    public RouteNode getRouteNode() {
        return routeNode;
    }
    public void setRouteNode(RouteNode node) {
        this.routeNode = node;
    }
    public String getRouteNodeInstanceId() {
        return routeNodeInstanceId;
    }
    public void setRouteNodeInstanceId(String routeNodeInstanceId) {
        this.routeNodeInstanceId = routeNodeInstanceId;
    }
    public String getDocumentId() {
        return documentId;
    }
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
    public List<RouteNodeInstance> getNextNodeInstances() {
        return nextNodeInstances;
    }
    public RouteNodeInstance getNextNodeInstance(int index) {
    	while (getNextNodeInstances().size() <= index) {
    		nextNodeInstances.add(new RouteNodeInstance());
    	}
    	return getNextNodeInstances().get(index);
    }
    public void setNextNodeInstances(List<RouteNodeInstance> nextNodeInstances) {
        this.nextNodeInstances = nextNodeInstances;
    }
    public List<RouteNodeInstance> getPreviousNodeInstances() {
        return previousNodeInstances;
    }
    public RouteNodeInstance getPreviousNodeInstance(int index) {
    	while (previousNodeInstances.size() <= index) {
    		previousNodeInstances.add(new RouteNodeInstance());
    	}
    	return (RouteNodeInstance) getPreviousNodeInstances().get(index);
    }
    public void setPreviousNodeInstances(List<RouteNodeInstance> previousNodeInstances) {
        this.previousNodeInstances = previousNodeInstances;
    }
    public boolean isInitial() {
        return initial;
    }
    public void setInitial(boolean initial) {
        this.initial = initial;
    }
    public List<NodeState> getState() {
        return state;
    }
    public void setState(List<NodeState> state) {
        this.state.clear();
    	this.state.addAll(state);
        //this.state = state;
    }
    public RouteNodeInstance getProcess() {
		return process;
	}
	public void setProcess(RouteNodeInstance process) {
		this.process = process;
	}
	public Integer getLockVerNbr() {
        return lockVerNbr;
    }
    public void setLockVerNbr(Integer lockVerNbr) {
        this.lockVerNbr = lockVerNbr;
    }

    public String getRouteNodeId() {
        if (getRouteNode() == null) {
            return null;
        }
        return getRouteNode().getRouteNodeId();
    }

    public NodeState getNodeState(String key) {
        for (Iterator iter = getState().iterator(); iter.hasNext();) {
            NodeState nodeState = (NodeState) iter.next();
            if (nodeState.getKey().equals(key)) {
                return nodeState;
            }
        }
        return null;
    }
    
    public void addNodeState(NodeState state) {
        this.state.add(state);
        state.setNodeInstance(this);
    }
    
    public void removeNodeState(String key) {
        for (Iterator iter = getState().iterator(); iter.hasNext();) {
            NodeState nodeState = (NodeState) iter.next();
            if (nodeState.getKey().equals(key)) {
                iter.remove();
                break;
            }
        }
    }
    
    public void addNextNodeInstance(RouteNodeInstance nextNodeInstance) {
        nextNodeInstances.add(nextNodeInstance);
        nextNodeInstance.getPreviousNodeInstances().add(this);
    }
    
    public void removeNextNodeInstance(RouteNodeInstance nextNodeInstance) {
        nextNodeInstances.remove(nextNodeInstance);
        nextNodeInstance.getPreviousNodeInstances().remove(this);
    }
    
    public void clearNextNodeInstances() {
        for (Iterator iterator = nextNodeInstances.iterator(); iterator.hasNext();) {
            RouteNodeInstance nextNodeInstance = (RouteNodeInstance) iterator.next();
            iterator.remove();
            nextNodeInstance.getPreviousNodeInstances().remove(this);
        }
    }
    
    public String getName() {
        return (getRouteNode() == null ? null : getRouteNode().getRouteNodeName());
    }
    
    public boolean isInProcess() {
        return getProcess() != null;
    }
    
    public DocumentType getDocumentType() {
        return KEWServiceLocator.getDocumentTypeService().findByDocumentId(getDocumentId());
    }
    
    /*
     * methods used to display route node instances' data on documentoperation.jsp
     */
    
    public NodeState getNodeStateByIndex(int index){
    	while (state.size() <= index) {
            state.add(new NodeState());
        }
        return getState().get(index);
    }   

    public void populateState(List<NodeState> state) {
        this.state.addAll(state);
    }

    public RouteNodeInstance deepCopy(Map<Object, Object> visited) {
        if (visited.containsKey(this)) {
            return (RouteNodeInstance)visited.get(this);
        }
        RouteNodeInstance copy = new RouteNodeInstance();
        visited.put(this, copy);
        copy.routeNodeInstanceId = routeNodeInstanceId;
        copy.documentId = documentId;
        copy.active = active;
        copy.complete = complete;
        copy.initial = initial;
        copy.lockVerNbr = lockVerNbr;
        // no need to deep copy route node because it's static configuration
        copy.routeNode = routeNode;
        if (branch != null) {
            copy.branch = branch.deepCopy(visited);
        }
        if (process != null) {
            copy.process = process.deepCopy(visited);
        }
        if (nextNodeInstances != null) {
            List<RouteNodeInstance> copies = new ArrayList<RouteNodeInstance>();
            for (RouteNodeInstance nextNodeInstance : nextNodeInstances) {
                copies.add(nextNodeInstance.deepCopy(visited));
            }
            copy.nextNodeInstances = copies;
        }
        if (previousNodeInstances != null) {
            List<RouteNodeInstance> copies = new ArrayList<RouteNodeInstance>();
            for (RouteNodeInstance previousNodeInstance : previousNodeInstances) {
                copies.add(previousNodeInstance.deepCopy(visited));
            }
            copy.previousNodeInstances = copies;
        }
        if (state != null) {
            List<NodeState> copies = new ArrayList<NodeState>();
            for (NodeState aState : state) {
                copies.add(aState.deepCopy(visited));
            }
            copy.state = copies;
        }
        return copy;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
            .append("routeNodeInstanceId", routeNodeInstanceId)
            .append("documentId", documentId)
            .append("branch", branch == null ? null : branch.getBranchId())
            .append("routeNode", routeNode == null ? null : routeNode.getRouteNodeName() + " " + routeNode.getRouteNodeId())
            .append("active", active)
            .append("complete", complete)
            .append("initial", initial)
            .append("process", process)
            .append("state", state == null ? null : state.size())
            .toString();
    }

	public static org.kuali.rice.kew.api.document.node.RouteNodeInstance to(RouteNodeInstance routeNodeInstance) {
		if (routeNodeInstance == null) {
			return null;
		}
		org.kuali.rice.kew.api.document.node.RouteNodeInstance.Builder builder = org.kuali.rice.kew.api.document.node
                .RouteNodeInstance.Builder.create();
		builder.setActive(routeNodeInstance.isActive());
		builder.setBranchId(routeNodeInstance.getBranch().getBranchId());
		builder.setComplete(routeNodeInstance.isComplete());
		builder.setDocumentId(routeNodeInstance.getDocumentId());
		builder.setId(routeNodeInstance.getRouteNodeInstanceId());
		builder.setInitial(routeNodeInstance.isInitial());
		builder.setName(routeNodeInstance.getName());
		if (routeNodeInstance.getProcess() != null) {
			builder.setProcessId(routeNodeInstance.getProcess().getRouteNodeInstanceId());
		}
		builder.setRouteNodeId(routeNodeInstance.getRouteNode().getRouteNodeId());
		List<RouteNodeInstanceState.Builder> states = new ArrayList<RouteNodeInstanceState.Builder>();
		for (NodeState stateBo : routeNodeInstance.getState()) {
			RouteNodeInstanceState.Builder stateBuilder = RouteNodeInstanceState.Builder.create();
			stateBuilder.setId(stateBo.getStateId());
			stateBuilder.setKey(stateBo.getKey());
			stateBuilder.setValue(stateBo.getValue());
			states.add(stateBuilder);
		}
		builder.setState(states);

        List<org.kuali.rice.kew.api.document.node.RouteNodeInstance.Builder> nextNodes = new ArrayList<org.kuali.rice.kew.api.document.node.RouteNodeInstance.Builder>();
        if (routeNodeInstance.getNextNodeInstances() != null) {
            for (RouteNodeInstance next : routeNodeInstance.getNextNodeInstances()) {
                // KULRICE-8152 - During load testing, sometimes routeNodeInstance.getNextNodeInstances() returns an
                // arraylist with size = 1 but all elements are null, which causes a "contract was null" error when the
                // create is called.  This check to see if next is not null prevents the error from occurring.
                if (next != null) {
                    // will this make things blow up?
                    nextNodes.add(org.kuali.rice.kew.api.document.node.RouteNodeInstance.Builder.create(RouteNodeInstance.to(next)));
                }
            }
        }
        builder.setNextNodeInstances(nextNodes);

		return builder.build();



	}
    
}

