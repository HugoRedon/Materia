/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package termo.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import termo.component.Compound;

/**
 *
 * @author Hugo
 */
@Entity
public class ExperimentalDataList implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.list);
        hash = 53 * hash + Objects.hashCode(this.source);
        hash = 53 * hash + Objects.hashCode(this.component);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ExperimentalDataList other = (ExperimentalDataList) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.list, other.list)) {
            return false;
        }
        if (!Objects.equals(this.source, other.source)) {
            return false;
        }
        if (!Objects.equals(this.component, other.component)) {
            return false;
        }
        return true;
    }
    
    
    @OneToMany(mappedBy = "dataList",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @OrderBy(value = "temperature")
    private List<ExperimentalData> list=new ArrayList();
    
    
    private String source;
    
    @ManyToOne( fetch = FetchType.EAGER,optional = true,
            targetEntity = Compound.class)
    private Compound component;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the list
     */
    public List<ExperimentalData> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<ExperimentalData> list) {
        this.list = list;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the component
     */
    public Compound getComponent() {
        return component;
    }

    /**
     * @param component the component to set
     */
    public void setComponent(Compound component) {
        this.component = component;
        //component.getExperimentalLists().add(this);
    }

    public void addExperimentalData(ExperimentalData data) {
        if(!list.contains(data)){
            data.setDataList(this);
            list.add(data);
        }
    }
    
    public void addExperimentalData(double temperature, double pressure){
    	list.add(new ExperimentalData(temperature, pressure));
    }

    public void removeExperimentalData(ExperimentalData data) {
        if(list.contains(data)){
            data.setDataList(null);
            list.remove(data);
        }
    }
}
